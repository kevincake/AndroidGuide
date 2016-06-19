package cn.ifreedomer.com.androidguide;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import cn.ifreedomer.com.androidguide.adapter.NavExpandAdapter;
import cn.ifreedomer.com.androidguide.event.ContentEvent;
import cn.ifreedomer.com.androidguide.manager.NotifycationManager;
import cn.ifreedomer.com.androidguide.model.ContentModel;
import cn.ifreedomer.com.androidguide.model.NavExpandedModel;
import cn.ifreedomer.com.androidguide.model.SubTitleModel;

public class MainActivity extends AppCompatActivity {
    public static final int APPINDEX = 0;
    public static final int COMPONENT_INDEX = 1;
    public static final int PRESISENT_INDEX = 2;
    private DrawerLayout mDrawerLayout;
    NavExpandAdapter mMenuAdapter;
    ExpandableListView expandableList;
    List<NavExpandedModel> dataList;
    HashMap<NavExpandedModel, List<String>> listDataChild;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeAsUpIndicator(R.mipmap.menu);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.whiteTextColor));
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        prepareListData();
        mMenuAdapter = new NavExpandAdapter(this, dataList, expandableList);

        // setting list adapter
        expandableList.setAdapter(mMenuAdapter);


        expandableList.setGroupIndicator(null);
        NotifycationManager.getInstance().register(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview);
    }

    private void prepareListData() {
//        List<> dataList = new ArrayList()


        dataList = new ArrayList<NavExpandedModel>();

        //添加application
        NavExpandedModel applicationModel = new NavExpandedModel();
        applicationModel.setTitleName(getString(R.string.application));
        dataList.add(applicationModel);
        applicationModel.setTitleName(getString(R.string.application));
        HashMap<SubTitleModel, List<ContentModel>> contentMap = applicationModel.getContentMap();
        //usage
        SubTitleModel subTitleAppUsage = new SubTitleModel();
        subTitleAppUsage.setSubTitle(getString(R.string.usage));
        //设置下标位置
        subTitleAppUsage.setPosition(0);
        contentMap.put(subTitleAppUsage, new ArrayList<ContentModel>());
        sort(contentMap);

        //添加component
        NavExpandedModel componentModel = new NavExpandedModel();
        componentModel.setTitleName(getString(R.string.component));
        dataList.add(componentModel);
        HashMap<SubTitleModel, List<ContentModel>> componentMap = componentModel.getContentMap();

        SubTitleModel subTitleActivity = new SubTitleModel();
        subTitleActivity.setPosition(0);
        subTitleActivity.setSubTitle(getString(R.string.activity));
        componentMap.put(subTitleActivity, new ArrayList<ContentModel>());

        SubTitleModel subTitleBroadCast = new SubTitleModel();
        subTitleBroadCast.setPosition(1);
        subTitleBroadCast.setSubTitle(getString(R.string.broadCast));
        componentMap.put(subTitleBroadCast, new ArrayList<ContentModel>());

        SubTitleModel subTitleService = new SubTitleModel();
        subTitleService.setPosition(2);
        subTitleService.setSubTitle(getString(R.string.service));
        componentMap.put(subTitleService, new ArrayList<ContentModel>());


        SubTitleModel subTitleContentPro = new SubTitleModel();
        subTitleContentPro.setPosition(3);
        subTitleContentPro.setSubTitle(getString(R.string.contentProvider));
        componentMap.put(subTitleContentPro, new ArrayList<ContentModel>());
        sort(componentMap);


        //presisent
        //添加component
        //添加component
        NavExpandedModel presisentModel = new NavExpandedModel();
        presisentModel.setTitleName(getString(R.string.presisent));
        dataList.add(presisentModel);

        HashMap<SubTitleModel, List<ContentModel>> presisentModelContentMap = presisentModel.getContentMap();

        SubTitleModel subTitleFile = new SubTitleModel();
        subTitleActivity.setPosition(0);
        subTitleActivity.setSubTitle(getString(R.string.file));
        presisentModelContentMap.put(subTitleActivity, new ArrayList<ContentModel>());

        SubTitleModel subTitleSharePreference = new SubTitleModel();
        subTitleSharePreference.setPosition(1);
        subTitleSharePreference.setSubTitle(getString(R.string.shareprefrence));
        presisentModelContentMap.put(subTitleSharePreference, new ArrayList<ContentModel>());

        SubTitleModel subTitleSqlite = new SubTitleModel();
        subTitleSqlite.setPosition(2);

        subTitleSqlite.setSubTitle(getString(R.string.sqlite));
        presisentModelContentMap.put(subTitleSqlite, new ArrayList<ContentModel>());

        SubTitleModel subTitleContentProvider = new SubTitleModel();
        subTitleContentProvider.setPosition(3);
        subTitleContentProvider.setSubTitle(getString(R.string.contentProvider));
        presisentModelContentMap.put(subTitleContentProvider, new ArrayList<ContentModel>());
        sort(presisentModelContentMap);


    }

    public void sort(HashMap<SubTitleModel, List<ContentModel>> hashMap) {
        SubTitleModel[] subTitleModels = new SubTitleModel[hashMap.keySet().size()];
        subTitleModels = (SubTitleModel[]) hashMap.keySet().toArray(subTitleModels);
        Arrays.sort(subTitleModels, new Comparator<SubTitleModel>() {
            @Override
            public int compare(SubTitleModel subTitleModel1, SubTitleModel subTitleModel2) {
                if (subTitleModel1.getPosition() == subTitleModel2.getPosition()) {
                    return 0;
                } else if (subTitleModel1.getPosition() >= subTitleModel2.getPosition()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        //revision: this don't works, use setOnChildClickListener() and setOnGroupClickListener() above instead
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotifycationManager.getInstance().unregister(this);
    }

    public void setContentRecycleView(){

    }


    //eventbus 更新标题和内容
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onContentEvent(ContentEvent contentEvent) {
        List<ContentModel> contentModels = contentEvent.getContentModels();
        String title = contentEvent.getSubTitle();
        mToolbar.setTitle(title);
    }

}
