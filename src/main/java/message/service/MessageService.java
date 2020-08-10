package message.service;

/*邮件服务层接口实现类*/

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class MessageService implements IMessageService{
    /*发送邮件方法*/
    @Override
    public boolean send(String from, String password, String toUser, String submit, String content) {
        try {
            //截取发件人名称和收件人名称
            String userName = from;
            from = from +"@yukino.asia";
            String toUserName = toUser.substring(toUser.indexOf("@"));
            HtmlEmail htmlEmail = new HtmlEmail();
            //邮件服务器的地址
            int index = from.lastIndexOf('@') + 1;
            String domainName = from.substring(index);
            htmlEmail.setHostName(domainName);
            //邮件服务器账号和密码
            htmlEmail.setAuthentication(from, password);
            //设置发件人：第一个参数为发件人地址；第二个参数为收件人收到邮件时看到的发件人“姓名”
            htmlEmail.setFrom(from, userName);
            //设置收件人：第一个参数为收件人邮件地址；第二个参数为收件人收到邮件时看到的收件人“姓名”
            htmlEmail.addTo(toUser, toUserName);
            //设置邮件内容编码方式
            htmlEmail.setCharset("UTF-8");
            //标题
            htmlEmail.setSubject(submit);
            //邮件内容
            htmlEmail.setHtmlMsg(content); //发送包含html标签的邮件
            //发送邮件
            htmlEmail.send();
            return true;
        }catch (EmailException e) {
            e.printStackTrace();
        }
        return false;

    }
}
