package record.dao;

import vo.Email;

import java.util.List;

public interface IRecordDao {
    List<Email> page(String to, String address, int pageNo);

    boolean save(int accountId,String to,String fileName);

    /*获取总页数*/
    int getCount(String to,String userName);

}
