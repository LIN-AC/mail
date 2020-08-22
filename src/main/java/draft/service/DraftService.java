package draft.service;

import draft.dao.DraftDao;
import draft.dao.IDraftDao;

import tool.db.DBLink;
import tool.db.IRowMapper;
import vo.Draft;
import vo.Email;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DraftService implements IDraftService{
    private IDraftDao draftDao =new DraftDao();
    @Override
    public List<Draft> page(String to, String address, int pageNo) {
        if (to!=null&&!to.equals("")){
            to = "%" + to + "%";
        }
        return draftDao.page(to,address,pageNo);
    }

    @Override
    public int getCount(String to, String userName) {
        if (to!=null&&!to.equals("")){
            to = "%" + to + "%";
        }
        return draftDao.getCount(to,userName);
    }

    @Override
    public boolean save(String userName, String content, String subject, String to) {
        return draftDao.save(userName, content, subject, to);
    }

    @Override
    public boolean change(String userName, String content, String subject, String to,String id) {
        return draftDao.change(userName,content,subject,to,id);
    }

    @Override
    public Draft show(int id, String userName) {
        return draftDao.show(id,userName);
    }

    @Override
    public boolean delete(String userName, String id) {
        return draftDao.delete(userName,id);
    }
}
