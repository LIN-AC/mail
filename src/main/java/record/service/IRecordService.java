package record.service;

import vo.Email;

import java.util.List;

public interface IRecordService {
    /**
     * 发件箱分页抽象方法
     */
    List<Email> page(String to, String address, int pageNo);
    /*
    获取总页数
    */
    int getCount(String to,String userName);

}
