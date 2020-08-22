package message.service;

import vo.Email;

import java.util.List;

public interface IMessageService {
    /*发送邮件抽象方法*/
    boolean send(String from, String password, String toUser, String submit, String content);
    /*收件箱分页抽象方法*/
    List<Email> inboxPage(String from,String userName,int pageNO);
    /*获取数据数量抽象方法*/
    int getCount(String from,String userName);
}
