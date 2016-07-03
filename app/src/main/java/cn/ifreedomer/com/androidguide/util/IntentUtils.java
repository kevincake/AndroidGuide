package cn.ifreedomer.com.androidguide.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import cn.ifreedomer.com.androidguide.activity.AboutActivity;
import cn.ifreedomer.com.androidguide.activity.FeedBackActivity;
import cn.ifreedomer.com.androidguide.activity.HelpActivity;
import cn.ifreedomer.com.androidguide.activity.MainActivity;
import cn.ifreedomer.com.androidguide.activity.PayActivity;
import cn.ifreedomer.com.androidguide.activity.ReaderActivity;
import cn.ifreedomer.com.androidguide.activity.SettingActivity;
import cn.ifreedomer.com.androidguide.activity.SignInActivity;
import cn.ifreedomer.com.androidguide.activity.SignUpActivity;
import cn.ifreedomer.com.androidguide.constants.IntentConstants;

/**
 * @author:eavawu
 * @date: 6/23/16.
 * @todo:
 */
public class IntentUtils {
    public static void startReaderActivity(Context context, String path) {
        Intent intent = new Intent(context, ReaderActivity.class);
        intent.putExtra(IntentConstants.READER_PATH, path);
        context.startActivity(intent);
    }

    public static void startPayActivity(Context context) {
        Intent intent = new Intent(context, PayActivity.class);
        context.startActivity(intent);
    }

    public static void startLoginActivity(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        context.startActivity(intent);
    }

    public static void startSignUpActivity(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }

    public static void startMainActivity(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public static void startSettingActivity(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    public static void startInstallActivity(Context context, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startAboutActivity(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    public static void startFeedbackActivity(Context context) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        context.startActivity(intent);
    }

    public static void startHelpActivity(Context context) {
        context.startActivity(new Intent(context, HelpActivity.class));
    }
}
