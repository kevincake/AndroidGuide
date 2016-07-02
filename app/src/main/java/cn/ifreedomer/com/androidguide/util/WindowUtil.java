package cn.ifreedomer.com.androidguide.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;

/**
 * @author:eavawu
 * @date: 5/29/16.
 * @todo:
 */
public class WindowUtil {
    public static Point getWindowSize(Context context) {
        Activity activity = (Activity) context;
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }
}
