package cn.ifreedomer.com.androidguide.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ifreedomer.com.androidguide.R;
import cn.ifreedomer.com.androidguide.activity.base.BaseActivity;
import cn.ifreedomer.com.androidguide.event.UpdateEvent;
import cn.ifreedomer.com.androidguide.manager.AppManager;
import cn.ifreedomer.com.androidguide.manager.DownLoadManager;
import cn.ifreedomer.com.androidguide.manager.NotifycationManager;
import cn.ifreedomer.com.androidguide.manager.managersupport.DownLoadItem;
import cn.ifreedomer.com.androidguide.model.UserModel;
import cn.ifreedomer.com.androidguide.service.UpdateService;
import cn.ifreedomer.com.androidguide.util.CacheUtil;
import cn.ifreedomer.com.androidguide.util.IntentUtils;
import cn.ifreedomer.com.androidguide.widget.LoadingDialog;
import cn.ifreedomer.com.androidguide.widget.SettingItemView;
import cn.ifreedomer.com.androidguide.widget.TipsDialog;

public class SettingActivity extends BaseActivity implements View.OnClickListener, LoadingDialog.onLoadCancelLisener, DownLoadManager.DownLoaderListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ll_layout)
    LinearLayout llLayout;
    @Bind(R.id.rootview)
    RelativeLayout rootview;
    private ArrayList<SettingItemView> itemViews = new ArrayList<>();
    private static final int CHECK_UPDATE_INDEX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        NotifycationManager.getInstance().register(this);
        initItems();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotifycationManager.getInstance().unregister(this);
    }

    private void initItems() {
        for (int i = 0; i < 8; i++) {
            SettingItemView itemView = new SettingItemView(this);
            itemView.setShowSwitch(false);
            itemView.setShowSub(false);
            itemView.setId(i);
            itemView.setOnClickListener(this);
            if (i == CHECK_UPDATE_INDEX) {
                itemView.setTitle(getString(R.string.check_update));
            }
            if (i == 1) {
                itemView.setShowSwitch(true);
                itemView.setTitle(getString(R.string.remove_ad));
            }


            if (i == 2) {
                itemView.setShowSwitch(false);
                itemView.setTitle(getString(R.string.set_theme));
            }

            if (i == 3) {
                itemView.setShowSub(true);
                itemView.setTitle(getString(R.string.close_send));
                itemView.setShowSwitch(true);
                itemView.setSubTitle(getString(R.string.send_introduce));
            }


            if (i == 4) {
                itemView.setTitle(getString(R.string.help));
                itemView.setMarginTop(R.dimen.dp15);
            }
            if (i == 5) {
                itemView.setTitle(getString(R.string.comment_point));
                itemView.setShowSub(true);
                itemView.setSubTitle(getString(R.string.comment_introduce));
            }
            if (i == 6) {
                itemView.setTitle(getString(R.string.about));
                itemView.setShowSub(true);
                itemView.setSubTitle(getString(R.string.app_introduce));
            }
            if (i == 7) {
                itemView.setMarginTop(R.dimen.dp20);
                itemView.setTitle(getString(R.string.quit));
                UserModel user = AppManager.getInstance().getUser();
                if (user != null) {
                    itemView.setShowSub(true);
                    itemView.setSubTitle(user.getName());
                } else {
                    itemView.setVisibility(View.GONE);
                }
            }

            llLayout.addView(itemView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case CHECK_UPDATE_INDEX:
                UpdateService.checkUpdate();
                LoadingDialog loadingDialog = (LoadingDialog) showLoadingDialog();
                loadingDialog.setOnLoadCancelLisener(this);
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDialog(final UpdateEvent updateEvent) {
        dismissDialog();
        TipsDialog.showDialog(this, getString(R.string.update_title), getString(R.string.update_content), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DownLoadItem downLoadItem = new DownLoadItem();
                downLoadItem.setDownLoaderListener(SettingActivity.this);
                int lastIndexOf = updateEvent.getUpdateInfo().getUrl().lastIndexOf("/");
                String url = updateEvent.getUpdateInfo().getUrl();
                downLoadItem.setPath(CacheUtil.getSdCacheDir() + url.substring(lastIndexOf+1, url.length()));
                downLoadItem.setUrl(updateEvent.getUpdateInfo().getUrl());
                DownLoadManager.getInstance().beginDownLoad(downLoadItem);
            }
        });
    }


    @Override
    public void onCancel(View view) {
        dismissDialog();
    }


    @Override
    public void downLoadBegin() {
        showLoadingDialog();
    }

    @Override
    public void downLoadFinish(DownLoadItem downLoadItem) {
        dismissDialog();
        IntentUtils.startInstallActivity(this, downLoadItem.getPath());
    }

    @Override
    public void downLoadFailed() {
        dismissDialog();
    }
}
