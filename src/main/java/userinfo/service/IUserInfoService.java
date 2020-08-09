package userinfo.service;

import vo.UserInfo;

public interface IUserInfoService {
    boolean unique(String userName);
    UserInfo login(String userName,String password);
    boolean register(String userName,String password);
}
