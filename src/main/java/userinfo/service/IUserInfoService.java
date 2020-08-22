package userinfo.service;

import vo.UserInfo;

public interface IUserInfoService {
    boolean unique(String userName);
    UserInfo login(String userName,String password);
    boolean register(String userName,String password);
    int getId(String userName);
    boolean update(String userName,String password);
    boolean imageUpdate(String name,String imageName,String newName);
}
