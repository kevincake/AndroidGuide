package cn.ifreedomer.com.androidguide.retrofit_service;

import java.util.Map;

import cn.ifreedomer.com.androidguide.constants.HttpConstants;
import cn.ifreedomer.com.androidguide.model.HttpResult;
import cn.ifreedomer.com.androidguide.model.IsPhoneRegister;
import cn.ifreedomer.com.androidguide.model.LoginResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author:eavawu
 * @date: 4/30/16.
 * @todo:登陆和注册模块的service
 */
public interface UserHttpDef {

    @GET(HttpConstants.ISPHONE_REGISTER)
    Call<HttpResult<IsPhoneRegister>> getIsPhoneRegister(@Query("phone") String phone);

    @GET("sign/signUp")
    Call<HttpResult<LoginResult>> postSignUp(@QueryMap Map<String, String> userParams);

    @GET("sign/signIn")
    Call<HttpResult<LoginResult>> getSignIn(@Query(HttpConstants.ACCOUNT) String account, @Query(HttpConstants.PASSWORD) String password);


}
