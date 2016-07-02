package cn.ifreedomer.com.androidguide.model;


/**
 * @author:eavawu
 * @date: 5/3/16.
 * @todo:
 */
public class LoginResult extends TokenBase {
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "user=" + user +
                '}';
    }
}
