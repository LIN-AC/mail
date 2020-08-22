package draft.dao;

import vo.Draft;
import vo.Email;

import java.util.List;

public interface IDraftDao {
    List<Draft> page(String to, String address, int pageNo);

    boolean save(String userName,String content,String subject, String to);

    boolean change(String userName,String content,String subject,String to,String id);

    /*获取总页数*/
    int getCount(String to,String userName);

    Draft show(int id,String userName);

    //删除草稿
    boolean delete(String userName,String id);

}
