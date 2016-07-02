package cn.ifreedomer.com.androidguide.callback;

/**
 * @author:eavawu
 * @date: 6/25/16.
 * @todo:
 */
public interface NetResultCallBack<T> {
    void onSuccess(T t);
    void onFailed(int code);
}
