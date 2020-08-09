package userinfo.service;

import vo.UserInfo;

public interface IUserInfoService {
    UserInfo login(String userName,String password);
    boolean register(String userName,String password);
}
