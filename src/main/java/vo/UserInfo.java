package vo;

public class UserInfo {
    private String id;
    private String userName;
    private String password;
    private String imageName;

    public UserInfo() {
    }

    public UserInfo(String id, String userName, String password,String profile) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.imageName = profile;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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
