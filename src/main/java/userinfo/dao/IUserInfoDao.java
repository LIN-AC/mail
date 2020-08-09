package userinfo.dao;

import vo.UserInfo;

public interface IUserInfoDao {
    UserInfo login(String userName,String password);
    boolean register(String id,String userName,String password);
}
