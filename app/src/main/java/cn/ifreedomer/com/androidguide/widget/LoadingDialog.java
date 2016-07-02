package cn.ifreedomer.com.androidguide.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ifreedomer.com.androidguide.R;

/**
 * @author:eavawu
 * @date: 6/28/16.
 * @todo:
 */
public class LoadingDialog extends AlertDialog implements View.OnClickListener {
   public interface onLoadCancelLisener {
        void onCancel(View view);
    }

    private onLoadCancelLisener onLoadCancelLisener;
    @Bind(R.id.content_pb)
    ContentLoadingProgressBar contentPb;
    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.rootview)
    LinearLayout rootview;
    private Context context;

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public onLoadCancelLisener getOnLoadCancelLisener() {
        return onLoadCancelLisener;
    }

    public void setOnLoadCancelLisener(onLoadCancelLisener onLoadCancelLisener) {
        this.onLoadCancelLisener = onLoadCancelLisener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.loading_dialog, null);


        // 设置宽度为屏宽、靠近屏幕底部。
        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.BOTTOM;

        window.setWindowAnimations(R.style.dialogStyle);
        window.setAttributes(wlp);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        this.setContentView(dialogView);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        contentPb.show();
    }

    @Override
    public void onClick(View v) {
        if (onLoadCancelLisener != null) {
            onLoadCancelLisener.onCancel(v);
        }
    }
}
