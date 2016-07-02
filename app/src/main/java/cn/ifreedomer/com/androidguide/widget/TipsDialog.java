package cn.ifreedomer.com.androidguide.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import cn.ifreedomer.com.androidguide.R;


/**
 * 创建者:eava
 * 创建时间:2016/3/23 11:14
 * 功能说明:
 */
public class TipsDialog {
    //确定按钮需要固定的
    public static void showDialog(Context context, String title, String msg, DialogInterface.OnClickListener dialogClickListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(context.getString(R.string.confirm), dialogClickListener)
                .setNegativeButton(context.getString(R.string.setup_profile_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    //确定按钮不固定的
    public static void showDialog(Context context, String title, String msg,String confirm, DialogInterface.OnClickListener dialogClickListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(confirm, dialogClickListener)
                .show();
    }
    public static void showAutoCloseDialog(Context context, String title, String msg){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }
}
