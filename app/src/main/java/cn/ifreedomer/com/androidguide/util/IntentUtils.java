package cn.ifreedomer.com.androidguide.util;

import android.content.Context;
import android.content.Intent;

import cn.ifreedomer.com.androidguide.activity.ReaderActivity;
import cn.ifreedomer.com.androidguide.constants.IntentConstants;

/**
 * @author:eavawu
 * @date: 6/23/16.
 * @todo:
 */
public class IntentUtils {
    public static void startReaderActivity(Context context,String path){
        Intent intent = new Intent(context, ReaderActivity.class);
        intent.putExtra(IntentConstants.READER_PATH,path);
        context.startActivity(intent);
    }
}
