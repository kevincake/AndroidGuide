package cn.ifreedomer.com.androidguide.model;

import cn.ifreedomer.com.androidguide.callback.SimpleRetrofitCallBack;
import cn.ifreedomer.com.androidguide.network.HttpClient;

/**
 * @author:eavawu
 * @date: 6/25/16.
 * @todo:
 */
public class UserModel extends BaseModel {

    private String name;
    private String pwd;
    private String account;
    private boolean isRecharge;
    private String token;
    private String confirmPwd;
    private boolean isLogin;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isRecharge() {
        return isRecharge;
    }

    public void setRecharge(boolean recharge) {
        isRecharge = recharge;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    public void signUp(SimpleRetrofitCallBack<LoginResult> simpleRetrofitCallBack){
        HttpClient.getInstance().signUp(this,simpleRetrofitCallBack);
    }
    public void signIn(SimpleRetrofitCallBack<LoginResult> simpleRetrofitCallBack){
        HttpClient.getInstance().signIn(this,simpleRetrofitCallBack);
    }
}
