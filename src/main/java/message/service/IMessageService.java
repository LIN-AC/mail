package message.service;

public interface IMessageService {
    /*发送邮件抽象方法*/
    boolean send(String from, String password, String toUser, String submit, String content);
}
