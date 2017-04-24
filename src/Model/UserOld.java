package Model;

import java.util.List;

/**
 * Created by JARVIS on 4/13/17.
 */
public class UserOld {

    private String userName;
    private String email;
    private String password;
    private String userType;
    private String city;
    private String state;
    private String title;

    public UserOld(String userName, String email, String password, String userType, String city, String state, String title){
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.city = city;
        this.state = state;
        this.title = title;
    }

    public UserOld(List<String> userInfo) {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
