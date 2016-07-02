package cn.ifreedomer.com.androidguide.network;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.ifreedomer.com.androidguide.callback.SimpleRetrofitCallBack;
import cn.ifreedomer.com.androidguide.constants.HttpConstants;
import cn.ifreedomer.com.androidguide.manager.AppManager;
import cn.ifreedomer.com.androidguide.model.HttpResult;
import cn.ifreedomer.com.androidguide.model.LoginResult;
import cn.ifreedomer.com.androidguide.model.UpdateInfo;
import cn.ifreedomer.com.androidguide.model.UserModel;
import cn.ifreedomer.com.androidguide.retrofit_service.UpdateHttpDef;
import cn.ifreedomer.com.androidguide.retrofit_service.UserHttpDef;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author:eavawu
 * @date: 6/25/16.
 * @todo:
 */
public class HttpClient {
    public static HttpClient httpClient = new HttpClient();
    //    public static final String BASE_URL = "http://115.28.38.189:8080/Beauty/";
    public static final String BASE_URL = "http://192.168.0.103:8080/";
    private static final int DEFAULT_TIMEOUT = 5;
    private UserHttpDef userHttpDef;
    private UpdateHttpDef updateHttpDef;
    private static final String TAG = "HttpClient";

    private HttpClient() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (AppManager.getInstance().isLogin()) {
                    Request.Builder requstbuilder = request.newBuilder().addHeader(HttpConstants.TOKEN, AppManager.getInstance().getUser().getId() + "_" + AppManager.getInstance().getToken());
                    request = requstbuilder.build();
                }

                return chain.proceed(request);
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        userHttpDef = retrofit.create(UserHttpDef.class);
        updateHttpDef = retrofit.create(UpdateHttpDef.class);
    }

    public static HttpClient getInstance() {
        return httpClient;
    }

    public static void main(String[] args) {
        UserModel userModel = new UserModel();
        userModel.setName("Nihao");
        userModel.setAccount("3333@qq.com");
        userModel.setPwd("123456");
        userModel.setConfirmPwd("123456");
//        userModel.signUp(new NetResultCallBack<UserModel>() {
//            @Override
//            public void onSuccess(UserModel userModel) {
//
//            }
//
//            @Override
//            public void onFailed(int code) {
//
//            }
//        });
    }



    public void signUp(UserModel userModel,SimpleRetrofitCallBack resultCallBack) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put(HttpConstants.NAME, userModel.getName());
        params.put(HttpConstants.ACCOUNT, userModel.getAccount());
        params.put(HttpConstants.PASSWORD, userModel.getPwd());
        params.put(HttpConstants.CONFIRM_PWD, userModel.getConfirmPwd());
        final Call<HttpResult<LoginResult>> httpResultCall = userHttpDef.postSignUp(params);
        httpResultCall.enqueue(resultCallBack);

    }


    public void signIn(UserModel userModel, SimpleRetrofitCallBack<LoginResult> simpleRetrofitCallBack) {
        final Call<HttpResult<LoginResult>> httpResultCall = userHttpDef.getSignIn(userModel.getAccount(),userModel.getPwd());
        httpResultCall.enqueue(simpleRetrofitCallBack);
    }

    public void getNewestVersion(long curVersion,SimpleRetrofitCallBack<UpdateInfo> simpleRetrofitCallBack){
        Call<HttpResult<UpdateInfo>> httpResultCall = updateHttpDef.getNewestVersion(curVersion);
        httpResultCall.enqueue(simpleRetrofitCallBack);
    }

}

