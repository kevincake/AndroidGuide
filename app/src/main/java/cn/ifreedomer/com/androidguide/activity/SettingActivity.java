package cn.ifreedomer.com.androidguide.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xiaomi.mipush.sdk.MiPushClient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ifreedomer.com.androidguide.GuideApplication;
import cn.ifreedomer.com.androidguide.R;
import cn.ifreedomer.com.androidguide.activity.base.BaseActivity;
import cn.ifreedomer.com.androidguide.constants.Constants;
import cn.ifreedomer.com.androidguide.constants.SaveConstants;
import cn.ifreedomer.com.androidguide.event.UpdateEvent;
import cn.ifreedomer.com.androidguide.manager.AppManager;
import cn.ifreedomer.com.androidguide.manager.DownLoadManager;
import cn.ifreedomer.com.androidguide.manager.NotifycationManager;
import cn.ifreedomer.com.androidguide.manager.managersupport.DownLoadItem;
import cn.ifreedomer.com.androidguide.model.UserModel;
import cn.ifreedomer.com.androidguide.service.UpdateService;
import cn.ifreedomer.com.androidguide.util.CacheUtil;
import cn.ifreedomer.com.androidguide.util.IntentUtils;
import cn.ifreedomer.com.androidguide.util.SaveUtil;
import cn.ifreedomer.com.androidguide.widget.LoadingDialog;
import cn.ifreedomer.com.androidguide.widget.SettingItemView;
import cn.ifreedomer.com.androidguide.widget.TipsDialog;

public class SettingActivity extends BaseActivity implements View.OnClickListener, LoadingDialog.onLoadCancelLisener, DownLoadManager.DownLoaderListener, CompoundButton.OnCheckedChangeListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ll_layout)
    LinearLayout llLayout;
    @Bind(R.id.rootview)
    RelativeLayout rootview;
    private ArrayList<SettingItemView> itemViews = new ArrayList<>();
    private static final int CHECK_UPDATE_INDEX = 0;
    private static final int REMOVE_AD_INDEX = 1;
    private static final int COMMON_POINT_INDEX = 3;
    private static final int PUSH_INDEX = 2;
    private static final int HELP_INDEX = 4;
    private static final int FEEDBACK_INDEX = 5;
    private static final int ABOUT_INDEX = 6;

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
            if (i == REMOVE_AD_INDEX) {
                itemView.setVisibility(View.GONE);
                itemView.setShowSwitch(true);
                itemView.setTitle(getString(R.string.remove_ad));
                itemView.setSwitchListner(this);
            }


            if (i == 3) {
                itemView.setShowSwitch(false);
                itemView.setTitle(getString(R.string.common_point));
            }

            if (i == 2) {
                itemView.setVisibility(View.GONE);
                itemView.setShowSub(true);
                itemView.setTitle(getString(R.string.close_send));
                itemView.setShowSwitch(true);
                itemView.setSubTitle(getString(R.string.send_introduce));
                itemView.setCheck(SaveUtil.get(SaveConstants.ISPUSH));
            }


            if (i == 4) {
                itemView.setTitle(getString(R.string.help));
                itemView.setMarginTop(R.dimen.dp15);
            }
            if (i == 5) {
                itemView.setTitle(getString(R.string.feedback));
                itemView.setShowSub(true);
                itemView.setSubTitle(getString(R.string.comment_introduce));
            }
            if (i == ABOUT_INDEX) {
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
        SettingItemView itemView = (SettingItemView) v;
        switch (v.getId()) {
            case CHECK_UPDATE_INDEX:
                UpdateService.checkUpdate();
                LoadingDialog loadingDialog = (LoadingDialog) showLoadingDialog();
                loadingDialog.setOnLoadCancelLisener(this);
                break;
            case COMMON_POINT_INDEX:
                Uri uri = Uri.parse("market://details?id="+getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case HELP_INDEX:
                IntentUtils.startHelpActivity(this);
                break;
            case PUSH_INDEX:
                boolean isPush = !SaveUtil.get(SaveConstants.ISPUSH);
                SaveUtil.save(SaveConstants.ISPUSH, isPush);
                itemView.setCheck(isPush);
                if (isPush) {
                    MiPushClient.registerPush(GuideApplication.getInstance(), Constants.XIAOMI_PUSNID, Constants.XIAOMI_PUSHKEY);
                } else {
                    MiPushClient.unregisterPush(GuideApplication.getInstance());
                }
                break;
            case FEEDBACK_INDEX:
                IntentUtils.startFeedbackActivity(this);
                break;
            case REMOVE_AD_INDEX:
                if (AppManager.getInstance().getUser() != null && AppManager.getInstance().getUser().isRecharge()) {
                    itemView.setCheck(!itemView.isCheck());
                } else {
                    TipsDialog.showDialog(this, getString(R.string.remove_ad_tip), getString(R.string.ad_content), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            IntentUtils.startPayActivity(SettingActivity.this);
                        }
                    });
                }
                break;
            case ABOUT_INDEX:
                IntentUtils.startAboutActivity(this);
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
                downLoadItem.setPath(CacheUtil.getSdCacheDir() + url.substring(lastIndexOf + 1, url.length()));
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {

//            break;
//            case
        }
    }
}
