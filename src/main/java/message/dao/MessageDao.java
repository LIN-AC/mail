package message.dao;

import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.commons.mail.util.MimeMessageUtils;
import tool.db.DBLink;
import tool.db.IRowMapper;
import vo.Email;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MessageDao implements IMessageDao{
    /**
     * 获取文件路径前缀
     *
     * @author 高焕杰
     **/
    private static String getFilePathPrefix(String accountAddress){
        int index = accountAddress.lastIndexOf('@');
        //获取域名
        String domainName = accountAddress.substring(index+1);
        //获取用户账户名
        String accountName = accountAddress.substring(0, index);
        //文件存储路径前缀
        return "C:\\Program Files (x86)\\hMailServer\\Data\\"+domainName+"\\"+accountName;
    }


    //收件箱分页实现方法
    @Override
    public List<Email> inboxPage(String from,final String userName, int pageNO) {
        int pageSize = 3;
        String where = " where messageaccountid = (select accountid from hm_accounts where accountaddress='"+userName+"')";
        if (from!=null&&!from.equals("")){
            where = where + " and messagefrom like '"+from+"'";
        }
        String sql = "select messageid messageId, messagefilename messageFileName, messagecreatetime messageCreateTime from hm_messages ";
        sql =sql + where + " order by messageid desc limit "+(pageNO-1)*pageSize+","+pageSize;
        class RowMapper implements IRowMapper {
            List<Email> emails = new ArrayList<Email>();
            public void rowMapper(ResultSet resultSet) {
                try {
                    //获取文件完整路径
                    String prefix = getFilePathPrefix(userName);
                    while(resultSet.next()) {
                        Email email = new Email();
                        //获取id
                        email.setId(resultSet.getInt("messageId"));
                        //获取时间
                        email.setDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(resultSet.getTimestamp("messageCreateTime")));
                        String messageFileName = resultSet.getString("messageFileName");
                        String pathName = prefix + "\\" + messageFileName.substring(1, 3) + "\\" + messageFileName;
                        File file = new File(pathName);
                        //解析eml文件
                        try {
                            MimeMessage mimeMessage = MimeMessageUtils.createMimeMessage(null, file);
                            MimeMessageParser messageParser = new MimeMessageParser(mimeMessage);
                            //获取发件人
                            email.setFrom(messageParser.getFrom());
                            //获取收件人
                            email.setTo(messageParser.getTo());
                            //获取主题
                            email.setSubject(messageParser.getSubject());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        emails.add(email);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        RowMapper rowMapper = new RowMapper();
        new DBLink().select(sql,rowMapper);
        return rowMapper.emails;
    }
    /*获取数据总量*/
    @Override
    public int getCount(String from, String userName) {
        String where = " where messageaccountid = (select accountid from hm_accounts where accountaddress='"+userName+"')";
        if (from!=null&&!from.equals("")){
            where = where + " and messagefrom like '"+from+"'";
        }
        String sql = "select count(messageid) count from hm_messages " + where;
        Object count = new DBLink().getValue(sql, "count");
        return Integer.parseInt(count.toString());
    }

}
