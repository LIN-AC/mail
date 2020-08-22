package userinfo.dao;

import tool.db.DBLink;
import tool.db.IRowMapper;
import vo.UserInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoDao implements IUserInfoDao{
    private static int getAccountid(String sql) {
        class RowMapper implements IRowMapper{
            int accountId;
            public void rowMapper(ResultSet resultSet) {
                try {
                    if(resultSet.next()) {
                        accountId =resultSet.getInt("accountid");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        RowMapper rowMapper = new RowMapper();
        new DBLink().select(sql, rowMapper);
        return rowMapper.accountId;
    }

    private static int getDomainid(String sql) {
        class RowMapper implements IRowMapper{
            int domainId;
            public void rowMapper(ResultSet resultSet) {
                try {
                    if(resultSet.next()) {
                        domainId =resultSet.getInt("domainid");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        RowMapper rowMapper = new RowMapper();
        new DBLink().select(sql, rowMapper);
        return rowMapper.domainId;
    }

    //判断账号是否唯一
    @Override
    public boolean unique(String userName) {
        boolean flag = new DBLink().exist("select accountid from hm_accounts where accountaddress = '"+userName+"'");
        System.out.println(flag);
        return flag;
    }

    @Override
    public UserInfo login(final String userName, final String password) {
        final UserInfo userInfo = new UserInfo();
        String sql = "select * from hm_accounts where accountaddress=? and accountpassword=?";
        new DBLink().select(sql, new IRowMapper() {
            @Override
            public void rowMapper(ResultSet resultSet) {
                try {
                    while (resultSet.next()) {
                        userInfo.setId(resultSet.getString("accountid"));
                        userInfo.setUserName(userName);
                        userInfo.setImageName(resultSet.getString("accountImage"));
                        userInfo.setPassword(password);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },userName,password);
        return userInfo;
    }

    @Override
    public boolean register(String userName, String password) {
        String domainName = userName.substring(userName.indexOf("@")+1);
        //获取域名id
        int accountDomainId = getDomainid("select domainid from hm_domains where domainname = '"+domainName+"'");

        String sql = "insert into hm_accounts" +
                "(accountdomainid,accountadminlevel,accountaddress,accountpassword" +
                ",accountactive,accountisad,accountaddomain,accountadusername,accountmaxsize" +
                ",accountvacationmessageon,accountvacationmessage,accountvacationsubject,accountpwencryption,accountforwardenabled" +
                ",accountforwardaddress,accountforwardkeeporiginal,accountenablesignature,accountsignatureplaintext,accountsignaturehtml" +
                ",accountlastlogontime,accountvacationexpires,accountvacationexpiredate,accountpersonfirstname,accountpersonlastname)" +
                "values("+accountDomainId+",0,?,'"+password+"'" +
                ",1,0,'','',1024" +
                ",0,'','',2,0" +
                ",'',0,0,'',''" +
                ",now(),0,now(),'','')";
        if (new DBLink().update(sql,userName)){
            //获取账户id
            int accountId = getAccountid("select accountid from hm_accounts where accountaddress = '"+userName+"'");
            sql = "insert into hm_imapfolders" +
                    "(folderaccountid,folderparentid,foldername" +
                    ",folderissubscribed,foldercreationtime,foldercurrentuid)" +
                    "values("+accountId+",-1,'INBOX'" +
                    ",1,now(),0)";
            if (new DBLink().update(sql)) {
                return true;
            }

        }

        return false;
    }

    @Override
    public int getId(String userName) {
        final int[] id = new int[1];
        String sql = "select accountid from hm_accounts  where  accountaddress= ?";
        new DBLink().select(sql, new IRowMapper() {
            @Override
            public void rowMapper(ResultSet rs) {
               try{
                   while (rs.next()){
                       id[0] = rs.getInt("accountid");
                   }
               } catch (SQLException throwables) {
                   throwables.printStackTrace();
               }
            }
        },userName);
        return id[0];
    }
    //修改密码
    @Override
    public boolean update(String userName, String password) {
        String sql = "update hm_accounts set accountpassword=? where accountaddress=?";
        return new DBLink().update(sql,password,userName);
    }
    //修改用户头像
    @Override
    public boolean imageUpdate(String name, String newName) {

        boolean flag = new DBLink().update("update hm_accounts set accountImage=? where accountaddress=?",newName,name);

        System.out.println("dao层");
        System.out.println(flag);
        return flag;
    }
}
