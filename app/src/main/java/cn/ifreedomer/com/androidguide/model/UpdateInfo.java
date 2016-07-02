package cn.ifreedomer.com.androidguide.model;

/**
 * @author:eavawu
 * @date: 7/2/16.
 * @todo:
 */
public class UpdateInfo {
    private int versionCode = 1;
    private String url = "";

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
