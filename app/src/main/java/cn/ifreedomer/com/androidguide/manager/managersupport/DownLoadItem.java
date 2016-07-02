package cn.ifreedomer.com.androidguide.manager.managersupport;

import java.io.IOException;
import java.io.InputStream;

import cn.ifreedomer.com.androidguide.manager.DownLoadManager;
import cn.ifreedomer.com.androidguide.util.FileUtil;
import cn.ifreedomer.com.androidguide.util.LogUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadItem {
    private static final String TAG = "DownLoadItem";
    private String url;
    private String path;
    private DownLoadManager.DownLoaderListener downLoaderListener;

    public DownLoadItem() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public DownLoadManager.DownLoaderListener getDownLoaderListener() {
        return downLoaderListener;
    }

    public void setDownLoaderListener(DownLoadManager.DownLoaderListener downLoaderListener) {
        this.downLoaderListener = downLoaderListener;
    }

    public void beginDownLoader(OkHttpClient httpClientParam) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = httpClientParam.newCall(request);
        if (downLoaderListener != null) {
            downLoaderListener.downLoadBegin();
        }
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (downLoaderListener != null) {
                    downLoaderListener.downLoadFailed();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.body() != null) {
                    InputStream inputStream = response.body().byteStream();
                    FileUtil.copyFile(inputStream, path);
                    if (downLoaderListener != null) {
                        downLoaderListener.downLoadFinish(DownLoadItem.this);
                    }
                } else {
                    LogUtil.info(TAG, "request failed");
                }

            }
        });
    }

}