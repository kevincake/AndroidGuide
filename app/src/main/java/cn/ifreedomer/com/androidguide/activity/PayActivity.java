package cn.ifreedomer.com.androidguide.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import c.b.BP;
import c.b.PListener;
import cn.ifreedomer.com.androidguide.R;
import cn.ifreedomer.com.androidguide.activity.base.BaseActivity;
import cn.ifreedomer.com.androidguide.constants.Constants;
import cn.ifreedomer.com.androidguide.manager.AppManager;
import cn.ifreedomer.com.androidguide.model.RechargeContentItem;
import cn.ifreedomer.com.androidguide.util.IntentUtils;
import cn.ifreedomer.com.androidguide.util.LogUtil;

public class PayActivity extends BaseActivity {
    private static final String TAG = "PayActivity";
    @Bind(R.id.textpay_btn)
    Button textpayBtn;
    public static final int PLUGINVERSION = 7;
    @Bind(R.id.recharge_content_layout)
    LinearLayout rechargeContentLayout;
    private List<RechargeContentItem> rechargeContentItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        BP.init(this, Constants.BOMB_KEY);


        int pluginVersion = BP.getPluginVersion();
        if (pluginVersion < PLUGINVERSION) {// 为0说明未安装支付插件, 否则就是支付插件的版本低于官方最新版
            Toast.makeText(
                    PayActivity.this,
                    pluginVersion == 0 ? getString(R.string.not_install_plugin)
                            : getString(R.string.update_plugin), Toast.LENGTH_SHORT).show();
            installBmobPayPlugin("bp.db");
        }
        initData();
    }

    private void initData() {
        RechargeContentItem removeAddItem = new RechargeContentItem();
        removeAddItem.setHasRight(true);
        removeAddItem.setTitle(getString(R.string.remove_add));
        removeAddItem.setSubTitle(getString(R.string.remove_add_subtitle));
        rechargeContentItems.add(removeAddItem);


        RechargeContentItem openGradeItem = new RechargeContentItem();
        openGradeItem.setHasRight(true);
        openGradeItem.setTitle(getString(R.string.open_grade_content));
        openGradeItem.setSubTitle(getString(R.string.upgrade_knowleage));
        rechargeContentItems.add(openGradeItem);


        RechargeContentItem updateItem = new RechargeContentItem();
        updateItem.setHasRight(true);
        updateItem.setTitle(getString(R.string.update_knowleage));
        updateItem.setSubTitle(getString(R.string.upadte_content));
        rechargeContentItems.add(updateItem);

        for (RechargeContentItem item : rechargeContentItems) {
            View view = View.inflate(this, R.layout.recharge_right_item, null);
            TextView itemTitleTv = (TextView) view.findViewById(R.id.recharge_title_tv);
            TextView itemSubTitle = (TextView) view.findViewById(R.id.recharge_subtitle_tv);
            itemSubTitle.setText(item.getSubTitle());
            itemTitleTv.setText(item.getTitle());
            rechargeContentLayout.addView(view);
        }
    }

    /**
     * 调用支付
     *
     * @param alipayOrWechatPay 支付类型，true为支付宝支付,false为微信支付
     */
    void pay(final boolean alipayOrWechatPay) {
//        LoadingDialog loadingDialog = new LoadingDialog(this,R.style.Theme_Light_Dialog);
//        loadingDialog.show();
        final String name = "hELLO";
//
        BP.ForceExit();
//        loadingDialog.dismiss();
        BP.pay(name, "", 0.02, alipayOrWechatPay, new PListener() {

            // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
            @Override
            public void unknow() {
                Toast.makeText(PayActivity.this, getString(R.string.manual_quary), Toast.LENGTH_SHORT)
                        .show();
            }

            // 支付成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                Toast.makeText(PayActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();
            }

            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
//                order.setText(orderId);
                LogUtil.info(TAG, orderId + "");

            }

            // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
            @Override
            public void fail(int code, String reason) {

                // 当code为-2,意味着用户中断了操作
                // code为-3意味着没有安装BmobPlugin插件
                if (code == -3) {
                    Toast.makeText(
                            PayActivity.this,
                            getString(R.string.reinstall),
                            Toast.LENGTH_SHORT).show();
                    installBmobPayPlugin("bp.db");
                } else {
                    Toast.makeText(PayActivity.this, "支付中断!", Toast.LENGTH_SHORT)
                            .show();
                }
//                tv.append(name + "'s pay status is fail, error code is \n"
//                        + code + " ,reason is " + reason + "\n\n");
//                hideDialog();
            }
        });
    }

    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.textpay_btn)
    public void onClick() {
        if (AppManager.getInstance().getUser() == null) {
            IntentUtils.startLoginActivity(this);
            return;
        }
        pay(true);
    }
}
