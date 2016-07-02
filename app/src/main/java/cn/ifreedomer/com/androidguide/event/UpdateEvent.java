package cn.ifreedomer.com.androidguide.event;

import cn.ifreedomer.com.androidguide.model.UpdateInfo;

/**
 * @author:eavawu
 * @date: 7/2/16.
 * @todo:
 */
public class UpdateEvent {
    public UpdateEvent(UpdateInfo updateInfo) {
        this.updateInfo = updateInfo;
    }

    private UpdateInfo updateInfo;

    public UpdateInfo getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(UpdateInfo updateInfo) {
        this.updateInfo = updateInfo;
    }
}
