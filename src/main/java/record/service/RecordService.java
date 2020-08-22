package record.service;

import record.dao.IRecordDao;
import record.dao.RecordDao;
import vo.Email;

import java.util.List;

public class RecordService implements IRecordService{
    IRecordDao recordDao = new RecordDao();
    @Override
    public List<Email> page(String to, String address, int pageNo) {
        if (to!=null&&!to.equals("")){
            to = "%" + to + "%";
        }
        return recordDao.page(to,address,pageNo);
    }

    @Override
    public int getCount(String to, String userName) {
        if (to!=null&&!to.equals("")){
            to = "%" + to + "%";
        }
        return recordDao.getCount(to,userName);
    }
}
