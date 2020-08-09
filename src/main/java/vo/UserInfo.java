package vo;

public class UserInfo {
    private String id;
    private String userName;
    private String password;
    private String profile;

    public UserInfo() {
    }

    public UserInfo(String id, String userName, String password,String profile) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
