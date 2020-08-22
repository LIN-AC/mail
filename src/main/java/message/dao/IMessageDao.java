package message.dao;

import vo.Email;

import java.util.List;

public interface IMessageDao {
    /*收件箱分页抽象方法*/

    List<Email> inboxPage(String from,String userName,int pageNO);

    /*获取数据数量抽象方法*/
    int getCount(String from,String userName);
}
