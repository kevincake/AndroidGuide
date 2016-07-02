package cn.ifreedomer.com.androidguide.manager;

import cn.ifreedomer.com.androidguide.manager.managersupport.DownLoadItem;
import okhttp3.OkHttpClient;

/**
 * @author:eavawu
 * @date: 7/2/16.
 * @todo:
 */
public class DownLoadManager {
    private static final String TAG = "DownLoadManager";

    public interface DownLoaderListener {
        void downLoadBegin();

        void downLoadFinish(DownLoadItem downLoadItem);

        void downLoadFailed();
    }

    private OkHttpClient httpClient;
    private static DownLoadManager downLoadManager = new DownLoadManager();
    private DownLoaderListener downLoaderListener;

    private DownLoadManager() {
        httpClient = new OkHttpClient();
    }

    public static DownLoadManager getInstance() {
        return downLoadManager;
    }

    public void beginDownLoad(String url, final String path, DownLoaderListener downLoaderListener) {
        DownLoadItem downLoadItem = new DownLoadItem();
        downLoadItem.setDownLoaderListener(downLoaderListener);
        downLoadItem.setUrl(url);
        downLoadItem.setPath(path);
        downLoadItem.beginDownLoader(httpClient);
    }

    public void beginDownLoad(DownLoadItem downLoadItem) {
        downLoadItem.beginDownLoader(httpClient);
    }


}
