package record.dao;

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

public class RecordDao implements IRecordDao {
    //获取文件目录
    private static String pathPrefix(String address){
        int index = address.lastIndexOf('@');
        //获取用户账户名
        String accountName = address.substring(0, index);
        //文件存储路径前缀
        return "C:\\Program Files (x86)\\hMailServer\\Data\\"+"\\record"+"\\"+accountName;
    }
    @Override
    public List<Email> page(String to, final String userName, int pageNo) {
        int pageSize = 3;
        String where = " where recordaccountid = (select accountid from hm_accounts where accountaddress='"+userName+"')";
        if (to!=null&&!to.equals("")){
            where = where + " and recordto like '"+to+"'";
        }
        String sql = "select recordid recordId,recordto recordTo,recordfilename recordFileName,recordcreatetime recordCreateTime from hm_records ";
        sql =sql + where + " order by recordid desc limit "+(pageNo-1)*pageSize+","+pageSize;
        class RowMapper implements IRowMapper {
            List<Email> emails = new ArrayList<Email>();
            public void rowMapper(ResultSet resultSet) {
                try {
                    //获取文件完整路径
                    String prefix = pathPrefix(userName);
                    while(resultSet.next()) {
                        Email email = new Email();
                        //获取id
                        email.setId(resultSet.getInt("recordId"));
                        //获取时间
                        email.setDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(resultSet.getTimestamp("recordCreateTime")));
                        String messageFileName = resultSet.getString("recordFileName");
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

    @Override
    public boolean save(int accountId, String to, String fileName) {
        String sql ="insert into hm_records(recordaccountid,recordto,recordfilename,recordcreatetime) values (?,?,?,now())";
        return new DBLink().update(sql,accountId,to,fileName);
    }

    //获取总数据数
    @Override
    public int getCount(String to, String userName) {
        String where = " where recordaccountid = (select accountid from hm_accounts where accountaddress='"+userName+"')";
        if (to!=null&&!to.equals("")){
            where = where + " and recordto like '"+to+"'";
        }
        String sql = "select count(recordid) count from hm_records ";
        sql =sql + where ;
        String count = new DBLink().getValue(sql, "count").toString();
        return Integer.parseInt(count);
    }
}
