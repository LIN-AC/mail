package userinfo.service;

import tool.MD5Tool;
import userinfo.dao.IUserInfoDao;
import userinfo.dao.UserInfoDao;
import vo.UserInfo;

import java.io.File;
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
        password = MD5Tool.encrypt(password);
        return userInfoDao.login(userName,password);
    }

    //注册
    @Override
    public boolean register(String userName, String password) {
        userName = userName +"@yukino.asia";
        password = MD5Tool.encrypt(password);
        return userInfoDao.register(userName,password);
    }

    @Override
    public int getId(String userName) {
        userName = userName + "@yukino.asia";
        return userInfoDao.getId(userName);
    }
    //修改密码
    @Override
    public boolean update(String userName, String password) {
        password = MD5Tool.encrypt(password);
        return userInfoDao.update(userName, password);
    }

    @Override
    public boolean imageUpdate(String userName, String imageName, String fileName) {
        if (imageName!=null && !imageName.equals("")){
            File file = new File("D:\\images\\userinfo\\" + imageName);
            if (file.delete()){
                System.out.println("service处删除file");
                return userInfoDao.imageUpdate(userName, fileName);
            }
        }else {
            return userInfoDao.imageUpdate(userName,fileName);
        }
        return false;
    }
}
