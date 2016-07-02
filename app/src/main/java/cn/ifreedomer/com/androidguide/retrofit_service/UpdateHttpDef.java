package cn.ifreedomer.com.androidguide.retrofit_service;

import cn.ifreedomer.com.androidguide.model.HttpResult;
import cn.ifreedomer.com.androidguide.model.UpdateInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author:eavawu
 * @date: 7/2/16.
 * @todo:
 */
public interface UpdateHttpDef {
    @GET("update/getNewestVersion")
    Call<HttpResult<UpdateInfo>> getNewestVersion(@Query("versionCode") long curVersion);


}
