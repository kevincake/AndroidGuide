package cn.ifreedomer.com.androidguide.manager;

import cn.ifreedomer.com.androidguide.model.UserModel;

/**
 * @author:eavawu
 * @date: 6/25/16.
 * @todo:
 */
public class AppManager {
    private static AppManager appmanager = new AppManager();
    private static UserModel user = null;
    private String token = "";

    public boolean isLogin() {
        if (user==null){
            return false;
        }
        return user.isLogin();
    }

    public void setLogin(boolean isLogin) {
        user.setLogin(isLogin);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private AppManager() {
    }


    public static AppManager getInstance() {
        return appmanager;
    }

    public  UserModel getUser() {
        return user;
    }

    public  void setUser(UserModel user) {
        AppManager.user = user;
    }
}
