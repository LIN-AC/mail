package draft.dao;

import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.commons.mail.util.MimeMessageUtils;
import tool.db.DBLink;
import tool.db.IRowMapper;
import vo.Draft;
import vo.Email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DraftDao implements IDraftDao{
    //获取文件目录
    private static String pathPrefix(String address){
        int index = address.lastIndexOf('@');
        String substring = address.substring(index+1, address.length());
//        System.out.println(substring);
        //获取用户账户名
        String accountName = address.substring(0, index);
        //文件存储路径前缀
//        return "C:\\Program Files (x86)\\hMailServer\\Data\\"+"\\draft"+"\\"+accountName;
        return "D:\\Program Files (x86)\\hMailServer\\Data\\"+substring+"\\draft"+"\\"+accountName;

    }
    @Override
    public List<Draft> page(String to, final String userName, int pageNo) {
        int pageSize = 3;
        String where = " where draftaccountid = (select accountid from hm_accounts where accountaddress='"+userName+"')";
        if (to!=null&&!to.equals("")){
            where = where + " and draftto like '"+to+"'";
        }
        String sql = "select draftid draftId, draftto draftTo, draftsubject draftSubject, draftfilename draftFileName, draftcreatetime draftCreateTime from hm_drafts ";
        sql =sql + where + " order by draftid desc limit "+(pageNo-1)*pageSize+","+pageSize;
        class RowMapper implements IRowMapper{
            List<Draft> draftList = new ArrayList<Draft>();
            public void rowMapper(ResultSet resultSet) {
                try {
                    while(resultSet.next()) {
                        Draft draft = new Draft();
                        //获取id
                        draft.setId(resultSet.getInt("draftid"));
                        //获取收件人
                        draft.setTo(resultSet.getString("draftto"));
                        //获取主题
                        draft.setSubject(resultSet.getString("draftsubject"));
                        //获取时间
                        draft.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("draftcreatetime")));
                        draftList.add(draft);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        RowMapper rowMapper = new RowMapper();
        new DBLink().select(sql,rowMapper);
        return rowMapper.draftList;
    }

    @Override
    public boolean save(String userName,String content,String subject, String to) {
        class RowMapper implements IRowMapper {
            int accountId;
            public void rowMapper(ResultSet resultSet) {
                try {
                    if (resultSet.next()) {
                        accountId = resultSet.getInt("accountid");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        RowMapper rowMapper = new RowMapper();
        String sql = "select accountid from hm_accounts where accountaddress =?";
        new DBLink().select(sql, rowMapper, userName);
        //生成文件名

        String fileName = "{" + UUID.randomUUID().toString() + "}.eml";
        // 生成文件创建时间
        String createTime = new Timestamp(System.currentTimeMillis()).toString();

        sql = "insert into hm_drafts (draftaccountid,draftto,draftsubject,draftfilename,draftcreatetime) values (?,?,?,?,?)";
        if (new DBLink().update(sql, rowMapper.accountId, to, subject, fileName, createTime)) {
            String prefix = pathPrefix(userName);
            File file = new File(prefix);
            System.out.println("file="+file);
            if (!file.exists()) {
                file.mkdirs();
            }

            String pathName = prefix +"\\"+ fileName;
            System.out.println("pathname="+pathName);
            try {
                Message message = new MimeMessage(Session.getInstance(System.getProperties()));
                // 创建消息部分
                MimeBodyPart body = new MimeBodyPart();
                // 填充邮件消息内容
                body.setText(content);
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(body);
                // 设置内容
                message.setContent(multipart);
                // 存储文件
                file = new File(pathName);
                message.writeTo(new FileOutputStream(file));
                System.out.println("已将邮件保存为草稿");
                return true;
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    //修改草稿箱
    @Override
    public boolean change(String userName, String content, String subject, String to,String id) {
        // 获取草稿文件名
        String sql = "select draftfilename fileName from hm_drafts where draftid = " + id;
        class RowMapper implements IRowMapper {
            String fileName;
            public void rowMapper(ResultSet resultSet) {
                try {
                    if (resultSet.next()) {
                        fileName = resultSet.getString("fileName");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        RowMapper rowMapper = new RowMapper();
        new DBLink().select(sql, rowMapper);
        String draftFileName = rowMapper.fileName;

        // 更新数据库记录
        sql = "update hm_drafts set draftto=?,draftsubject=?,draftcreatetime=now() where draftid =?";
        if (new DBLink().update(sql, to, subject, id)) {
            String prefix = pathPrefix(userName);
            String pathName = prefix + "\\" + draftFileName;
            File file = new File(pathName);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
            try {
                Message message = new MimeMessage(Session.getInstance(System.getProperties()));
                // 创建消息部分
                MimeBodyPart body = new MimeBodyPart();
                // 填充邮件消息内容
                body.setText(content);
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(body);
                // 设置内容
                message.setContent(multipart);
                // 存储文件
                message.writeTo(new FileOutputStream(new File(pathName)));
                System.out.println("草稿修改成功");
                return true;
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }



    //获取总数据数
    @Override
    public int getCount(String to, String userName) {
        String where = "where draftaccountid = (select accountid from hm_accounts where accountaddress='"+userName+"')";
        if (to!=null&&!to.equals("")){
            where = where + " and draftto like '"+to+"'";
        }
        String sql = "select count(draftid) total from hm_drafts ";
        sql =sql + where ;
        class RowMapper implements IRowMapper{
            int total;
            public void rowMapper(ResultSet resultSet) {
                try {
                    if(resultSet.next()) {
                        total =resultSet.getInt("total");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        RowMapper rowMapper = new RowMapper();
        new DBLink().select(sql, rowMapper);
        return rowMapper.total;
    }

    @Override
    public Draft show(int id,String userName) {
        String sql = "select draftid draftId, draftto draftTo, draftsubject draftSubject, draftfilename draftFileName, draftcreatetime draftCreateTime from hm_drafts "
                + "where draftId=?";
        class RowMapper implements IRowMapper {
            Draft draft = new Draft();
            public void rowMapper(ResultSet resultSet) {
                try {
                    if (resultSet.next()) {
                        // 获取id
                        draft.setId(resultSet.getInt("draftId"));
                        // 获取收件人
                        draft.setTo(resultSet.getString("draftTo"));
                        // 获取主题
                        draft.setSubject(resultSet.getString("draftSubject"));
                        // 获取文件名字
                        String fileName = resultSet.getString("draftFileName");
                        String pathName = pathPrefix(userName) + "\\" + fileName;
                        File file = new File(pathName);
                        String content = "";
                        // 解析eml文件
                        try {
                            MimeMessage mimeMessage = MimeMessageUtils.createMimeMessage(null, file);
                            MimeMessageParser messageParser = new MimeMessageParser(mimeMessage);
                            messageParser = messageParser.parse();
                            content = messageParser.hasPlainContent() ? messageParser.getPlainContent() : "";// 获取HTML文件内容
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // 获取HTML邮件内容
                        System.out.println(content);
                        draft.setHtmlContent(content);
                        // 获取时间
                        String draftCreateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("draftCreateTime"));
                        System.out.println(draftCreateTime);
                        draft.setDate(draftCreateTime);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        RowMapper rowMapper = new RowMapper();
        new DBLink().select(sql, rowMapper, id);
        return rowMapper.draft;
    }

    @Override
    public boolean delete(String userName,String ids) {
        String prefix = pathPrefix(userName);
        for (String id : ids.split(",")) {
            // 获取草稿文件名
            String sql = "select draftfilename fileName from hm_drafts where draftid = " + id;
            class RowMapper implements IRowMapper {
                String fileName;
                public void rowMapper(ResultSet resultSet) {
                    try {
                        if (resultSet.next()) {
                            fileName = resultSet.getString("fileName");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            RowMapper rowMapper = new RowMapper();
            new DBLink().select(sql, rowMapper);
            String draftFileName = rowMapper.fileName;
            // 获取文件完整路径
            String pathName = prefix + "\\" + draftFileName;
            File file = new File(pathName);
            System.out.println(file);
            if (file.isFile() || file.exists()) {
                file.delete();
            }
        }
        String sql = "delete from hm_drafts where draftid in (?)";
        return new DBLink().update(sql,ids);
    }
}
