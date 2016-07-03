package cn.ifreedomer.com.androidguide.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ifreedomer.com.androidguide.R;
import cn.ifreedomer.com.androidguide.activity.base.BaseActivity;
import cn.ifreedomer.com.androidguide.util.AppInfoUtil;
import cn.ifreedomer.com.androidguide.widget.AboutItemView;

public class AboutActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.install_ll)
    LinearLayout installLl;
    @Bind(R.id.visit_ll)
    LinearLayout visitLl;
    private ArrayList<AboutItemView> installList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initItems();
    }

    private void initItems() {
        AboutItemView versionItemView = new AboutItemView(this);
        versionItemView.setTitle(getString(R.string.version_title));
        versionItemView.setContent(AppInfoUtil.getVersionCode() + "");
        installLl.addView(versionItemView);

        AboutItemView copyRightItemView = new AboutItemView(this);
        versionItemView.setTitle(getString(R.string.copyright));
        versionItemView.setContent("Apache 2.0 License");
        installLl.addView(copyRightItemView);

        AboutItemView emailItemView = new AboutItemView(this);
        emailItemView.setTitle(getString(R.string.gmail_title));
        emailItemView.setContent(getString(R.string.email));
        emailItemView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        visitLl.addView(emailItemView);


        AboutItemView githubItemView = new AboutItemView(this);
        githubItemView.setTitle(getString(R.string.github_title));
        githubItemView.setContent(getString(R.string.github_content));
        githubItemView.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
        visitLl.addView(githubItemView);


//        installList.add(0,"0.0.8");
//
//        for (int i = 0; i < 3; i++) {
//            installLl.addView(new AboutItemView(this));
//        }
//        for (int i = 0; i < 3; i++) {
//            visitLl.addView(new AboutItemView(this));
//        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.about_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}