package draft.service;

import vo.Draft;
import vo.Email;

import java.util.List;

public interface IDraftService {
    /**
     * 草稿箱分页抽象方法
     */
    List<Draft> page(String to, String address, int pageNo);
    /*
    获取总页数
    */
    int getCount(String to,String userName);

    boolean save(String userName,String content,String subject, String to);

    boolean change(String userName,String content,String subject,String to,String id);

    Draft show(int id,String userName);

    //删除草稿
    boolean delete(String userName,String id);

}
