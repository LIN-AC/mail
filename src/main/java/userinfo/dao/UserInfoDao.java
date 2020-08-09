package userinfo.dao;

import tool.db.DBLink;
import tool.db.IRowMapper;
import vo.UserInfo;

import java.sql.ResultSet;

public class UserInfoDao implements IUserInfoDao{
    //判断账号是否唯一
    @Override
    public boolean unique(String userName) {
      boolean flag=true;
      if (new DBLink().exist("select id from user_info where user_name=?",userName)){
          flag = false;
      }
        System.out.println(flag);
      return flag;
    }

    @Override
    public UserInfo login(String userName, String password) {
        final UserInfo userInfo = new UserInfo();
        String sql = "select id,user_name,profile from user_info where user_name=? and password=?";
        new DBLink().select(sql, new IRowMapper() {
            @Override
            public void rowMapper(ResultSet resultSet) {
                try {
                    while (resultSet.next()) {
                        userInfo.setId(resultSet.getString("id"));
                        userInfo.setUserName(resultSet.getString("user_name"));
                        userInfo.setProfile(resultSet.getString("profile"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },userName,password);
        return userInfo;
    }

    @Override
    public boolean register(String id, String userName, String password) {
        String sql = "insert into user_info (id,user_name,password) values(?,?,?)";
        return new DBLink().update(sql,id,userName,password);
    }
}
