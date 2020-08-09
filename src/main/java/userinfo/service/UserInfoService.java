package userinfo.service;

import tool.MD5Tool;
import userinfo.dao.IUserInfoDao;
import userinfo.dao.UserInfoDao;
import vo.UserInfo;

import java.util.UUID;

public class UserInfoService implements IUserInfoService{
    private IUserInfoDao userInfoDao = new UserInfoDao();
    @Override
    public UserInfo login(String userName,String password) {
        password = MD5Tool.getMD5(password);
        return userInfoDao.login(userName,password);
    }

    @Override
    public boolean register(String userName, String password) {
        password = MD5Tool.getMD5(password);
        String id = UUID.randomUUID().toString();
        return userInfoDao.register(id,userName,password);
    }
}
