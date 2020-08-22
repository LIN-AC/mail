package message.service;

/*邮件服务层接口实现类*/

import message.dao.IMessageDao;
import message.dao.MessageDao;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import record.dao.IRecordDao;
import record.dao.RecordDao;
import userinfo.dao.IUserInfoDao;
import userinfo.dao.UserInfoDao;
import userinfo.service.IUserInfoService;
import userinfo.service.UserInfoService;
import vo.Email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class MessageService implements IMessageService{
    private IUserInfoService userInfoService = new UserInfoService();
    private IRecordDao recordDao = new RecordDao();
    private IMessageDao messageDao = new MessageDao();
    private IUserInfoDao userInfoDao = new UserInfoDao();

    //获取文件目录
    private static String pathPrefix(String address){
        int index = address.lastIndexOf('@');
        //获取用户账户名
        String accountName = address.substring(0, index);
        //文件存储路径前缀
        return "C:\\Program Files (x86)\\hMailServer\\Data\\"+"\\record"+"\\"+accountName;
    }
    /*发送邮件方法*/
    @Override
    public boolean send(String from, String password, String to, String subject, String content) {
        try {
            //截取发件人名称和收件人名称
            String userName = from.substring(0,from.indexOf("@"));
            System.out.println(userName);
            String toUserName = to.substring(0,to.indexOf("@"));
            System.out.println(toUserName);
            HtmlEmail htmlEmail = new HtmlEmail();
            //邮件服务器的地址
            int index = from.lastIndexOf('@') + 1;
            String domainName = from.substring(index);
            htmlEmail.setHostName(domainName);
            System.out.println(domainName);
            //邮件服务器账号和密码
            System.out.println("账户密码："+from+":"+password);
            htmlEmail.setAuthentication(from, password);
            //设置发件人：第一个参数为发件人地址；第二个参数为收件人收到邮件时看到的发件人“姓名”
            htmlEmail.setFrom(from, userName);
            //设置收件人：第一个参数为收件人邮件地址；第二个参数为收件人收到邮件时看到的收件人“姓名”
            htmlEmail.addTo(to, toUserName);
            //设置邮件内容编码方式
            htmlEmail.setCharset("UTF-8");
            //标题
            htmlEmail.setSubject(subject);
            //邮件内容
            htmlEmail.setHtmlMsg(content); //发送包含html标签的邮件
            //发送邮件
            htmlEmail.send();//服务器25端口报错，未解决
            // 生成文件名
            String fileName = "{" + UUID.randomUUID().toString() + "}.eml";
            // 向数据库添加邮件
            String sql = "insert into hm_records (recordaccountid,recordto,recordfilename,recordcreatetime) values (?,?,?,now())";
            int id = userInfoDao.getId(userName);
            if (recordDao.save(id,to,fileName)){
                //生产eml文件
                String prefix = pathPrefix(from);
                File file = new File(prefix);
                if (!file.exists()) {
                    file.mkdirs();
                }

                String pathName = prefix + "\\" + fileName;
                try {
                    MimeMessage message = new MimeMessage(Session.getInstance(System.getProperties()));
                    // 创建消息部分
                    MimeBodyPart body = new MimeBodyPart();
                    // 填充邮件消息内容
                    body.setText(content);
                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(body);
                    // 设置内容
                    message.setContent(multipart);
                    //设置主题
                    message.setSubject(subject);
                    //设置发件人
                    message.setFrom(new InternetAddress(from));
                    //设置收件人
                    message.setRecipients(Message.RecipientType.TO, to);
                    // 存储文件
                    file = new File(pathName);
                    message.writeTo(new FileOutputStream(file));
                    return true;
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }catch (EmailException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*分页方法*/
    @Override
    public List<Email> inboxPage(String from,String userName,int pageNo) {
        if (from!=null&&!from.equals("")){
            from = "%" + from + "%";
        }
        return messageDao.inboxPage(from,userName,pageNo);
    }

    /*获取数据数量抽象方法*/
    @Override
    public int getCount(String from, String userName) {
        if (from!=null&&!from.equals("")){
            from = "%" + from + "%";
        }
        return messageDao.getCount(from,userName);
    }
}
