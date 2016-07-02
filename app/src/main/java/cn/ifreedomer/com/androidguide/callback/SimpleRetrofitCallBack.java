package cn.ifreedomer.com.androidguide.callback;

import java.io.IOException;

import cn.ifreedomer.com.androidguide.constants.HttpConstants;
import cn.ifreedomer.com.androidguide.model.HttpResult;
import cn.ifreedomer.com.androidguide.util.LogUtil;
import cn.ifreedomer.com.androidguide.util.ToastUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author:eavawu
 * @date: 6/26/16.
 * @todo:
 */
public abstract class SimpleRetrofitCallBack<T> implements Callback<HttpResult<T>> {
    private static final String TAG = "TAG";
    @Override
    public void onFailure(Call<HttpResult<T>> call, Throwable t) {

    }

    public void showError(Response response) {
        try {
//            LogUtil.error(TAG, "onResponse:"+response.errorBody().string());
            if (response.errorBody() != null) {
                ToastUtil.showToast(response.errorBody().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onResponse(Call<HttpResult<T>> call, Response<HttpResult<T>> response) {
        LogUtil.info(TAG,"onResponse:"+response.toString());
        Response<HttpResult<T>> resultResponse = response;
        if (response.errorBody() != null) {
            showError(response);
        } else if (resultResponse.body().getResultCode() == HttpConstants.FAILED) {
            ToastUtil.showToast(resultResponse.body().getMsg());
        } else {
            if (resultResponse.body().getData()!=null){
                onSuccess(resultResponse.body().getData());
            }

        }

    }

    public abstract void onSuccess(T t);


}
