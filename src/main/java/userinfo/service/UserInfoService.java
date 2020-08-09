package userinfo.service;

import tool.MD5Tool;
import userinfo.dao.IUserInfoDao;
import userinfo.dao.UserInfoDao;
import vo.UserInfo;

import java.util.UUID;

public class UserInfoService implements IUserInfoService{
    private IUserInfoDao userInfoDao = new UserInfoDao();

    //判断用户名是否唯一
    @Override
    public boolean unique(String userName) {
        userName = userName +"@yukino.asia";
        return userInfoDao.unique(userName);
    }

    //登录
    @Override
    public UserInfo login(String userName,String password) {
        userName = userName +"@yukino.asia";
        password = MD5Tool.getMD5(password);
        return userInfoDao.login(userName,password);
    }

    //注册
    @Override
    public boolean register(String userName, String password) {
        userName = userName +"@yukino.asia";
        password = MD5Tool.getMD5(password);
        String id = UUID.randomUUID().toString();
        return userInfoDao.register(id,userName,password);
    }
}
