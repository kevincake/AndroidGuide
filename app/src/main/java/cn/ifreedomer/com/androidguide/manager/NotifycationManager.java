package cn.ifreedomer.com.androidguide.manager;

import org.greenrobot.eventbus.EventBus;

/**
 * @author:eavawu
 * @date: 6/19/16.
 * @todo:
 */
public class NotifycationManager {
    private NotifycationManager() {
    }
    ;
    private static NotifycationManager notifycationManager = new NotifycationManager();

    public static NotifycationManager getInstance() {
        return notifycationManager;
    }

    public void post(Object object) {
        EventBus.getDefault().post(object);
    }

    public void register(Object object) {
        EventBus.getDefault().register(object);
    }

    public void unregister(Object object) {
        EventBus.getDefault().unregister(object);
    }
}
