package cn.ifreedomer.com.androidguide.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhy.base.adapter.recyclerview.DividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifreedomer.com.androidguide.R;
import cn.ifreedomer.com.androidguide.activity.base.BaseActivity;
import cn.ifreedomer.com.androidguide.adapter.MainRvAdapter;
import cn.ifreedomer.com.androidguide.adapter.NavExpandAdapter;
import cn.ifreedomer.com.androidguide.constants.Constants;
import cn.ifreedomer.com.androidguide.event.ContentEvent;
import cn.ifreedomer.com.androidguide.manager.AppManager;
import cn.ifreedomer.com.androidguide.manager.NotifycationManager;
import cn.ifreedomer.com.androidguide.model.ContentModel;
import cn.ifreedomer.com.androidguide.model.NavExpandedModel;
import cn.ifreedomer.com.androidguide.model.SubTitleModel;
import cn.ifreedomer.com.androidguide.model.UserModel;
import cn.ifreedomer.com.androidguide.util.CacheUtil;
import cn.ifreedomer.com.androidguide.util.IntentUtils;
import cn.ifreedomer.com.androidguide.util.LogUtil;
import cn.ifreedomer.com.androidguide.util.ZipUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener, SearchView.OnQueryTextListener {
    private static final String TAG = "MainActivity";
    public static final int APPINDEX = 0;
    public static final int COMPONENT_INDEX = 1;
    public static final int PRESISENT_INDEX = 2;

    @BindView(R.id.frame_content)
    FrameLayout frameContent;
    @BindView(R.id.navigationmenu)
    ExpandableListView navigationmenu;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.ad_root_ll)
    LinearLayout adRootLl;
    private DrawerLayout mDrawerLayout;
    NavExpandAdapter mMenuAdapter;
    ExpandableListView expandableList;
    List<NavExpandedModel> dataList;
    HashMap<NavExpandedModel, List<String>> listDataChild;
    private List<ContentModel> allContentModels = new ArrayList<>();
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private TextView nameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotifycationManager.getInstance().register(this);
        ButterKnife.bind(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeAsUpIndicator(R.mipmap.menu);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.whiteTextColor));
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        initNavgationView();
        Unzip();



    }

    private void initNavgationView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        View headerView = navigationView.getHeaderView(0);
        ImageView buyIv = (ImageView) headerView.findViewById(R.id.buy_iv);
        if (buyIv != null) {
            buyIv.setOnClickListener(this);
        }

        nameTv = (TextView) headerView.findViewById(R.id.login_tv);
        ImageView settingIv = (ImageView) headerView.findViewById(R.id.setting_iv);
        settingIv.setOnClickListener(this);
        nameTv.setOnClickListener(this);
        expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }



    public void Unzip() {
        new AsyncTask<String, Integer, String>() {

            @Override
            protected String doInBackground(String... params) {
                ZipUtil.UnZipMarkdown();
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                parseAllData();
            }
        }.execute();
    }


    public void parseAllData() {
        try {
            String rootFolder = CacheUtil.getAppCacheDir() + Constants.MARKDOWN;
            File rootFile = new File(rootFolder);
            String[] markdowns = rootFile.list();
            dataList = new ArrayList<NavExpandedModel>();
            NavExpandedModel applicationModel = null;
            if (markdowns == null) {
                return;
            }
            for (int i = 0; i < markdowns.length; i++) {
                if (markdowns[i].startsWith(".")) continue;
                applicationModel = new NavExpandedModel();
                dataList.add(applicationModel);
                String[] split = markdowns[i].split("_");
                //position
                applicationModel.setPosition(Integer.parseInt(split[0]));
                //name
                applicationModel.setTitleName(split[1]);

                HashMap<SubTitleModel, List<ContentModel>> contentMap = applicationModel.getContentMap();
                String secondPath = rootFolder + "/" + markdowns[i];
                File file = new File(secondPath);
                String[] list = file.list();
                if (list == null) continue;
                for (int j = 0; j < list.length; j++) {
                    if (list[j].startsWith(".")) {
                        continue;
                    }
                    SubTitleModel subTitleModel = new SubTitleModel();
                    String[] fileNameSplit = list[j].split("_");
                    subTitleModel.setPosition(Integer.parseInt(fileNameSplit[0]));
                    subTitleModel.setSubTitle(fileNameSplit[1]);
                    File subFile = new File(secondPath + "/" + list[j]);
                    String[] subFileList = subFile.list();

                    List<ContentModel> contentModels = new ArrayList<>();
                    contentMap.put(subTitleModel, contentModels);

                    if (subFileList == null || subFileList.length == 0) continue;
                    ;
                    for (int k = 0; k < subFileList.length; k++) {
                        if (subFileList[k].startsWith(".")) {
                            continue;
                        }

                        String[] subsubFiles = subFileList[k].split("_");
                        ContentModel contentModel = new ContentModel();
                        contentModel.setRealFileName(rootFolder + "/" + markdowns[i] + "/" + list[j] + "/" + subFileList[k]);
                        contentModel.setTime(System.currentTimeMillis());
                        contentModel.setContent(subFileList[k]);
                        contentModel.setTitle(subsubFiles[1].substring(0, subsubFiles[1].length() - ".md".length()));
                        contentModel.setPosition(Integer.parseInt(subsubFiles[0]));
                        contentModels.add(contentModel);
                        allContentModels.add(contentModel);
                    }
                    sort(contentMap);

                }

            }
            LogUtil.error(TAG, dataList.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        sortNav(dataList);
        setNavUI();
    }


    private void setNavUI() {
        mMenuAdapter = new NavExpandAdapter(this, dataList, expandableList);

        // setting list adapter
        expandableList.setAdapter(mMenuAdapter);


        expandableList.setGroupIndicator(null);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        Set<Map.Entry<SubTitleModel, List<ContentModel>>> entries = dataList.get(0).getContentMap().entrySet();
        Iterator<Map.Entry<SubTitleModel, List<ContentModel>>> iterator = entries.iterator();
//        for ()
        List<ContentModel> value = iterator.next().getValue();
        setContentRecycleView(value);

//        setContentRecycleView(null);
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


    public void sortNav(List<NavExpandedModel> navExpandedModels) {
        Collections.sort(navExpandedModels, new Comparator<NavExpandedModel>() {
            @Override
            public int compare(NavExpandedModel lhs, NavExpandedModel rhs) {
                if (lhs.getPosition() > rhs.getPosition()) {
                    return 1;
                } else if (lhs.getPosition() < rhs.getPosition()) {
                    return -1;
                }
                return 0;
            }
        });

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
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchViewMenuItem = menu.findItem(R.id.search_menu);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchViewMenuItem);
        mSearchView.setOnQueryTextListener(this);
        int searchImgId = android.support.v7.appcompat.R.id.search_button; // I used the explicit layout ID of searchview's ImageView
        ImageView v = (ImageView) mSearchView.findViewById(searchImgId);
        v.setImageResource(R.mipmap.search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        MenuItem item = menu.findItem(R.id.search_menu);
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
    protected void onStart() {
        super.onStart();
        UserModel userModel = AppManager.getInstance().getUser();
        if (userModel != null) {
            nameTv.setText(userModel.getName());
            nameTv.setClickable(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotifycationManager.getInstance().unregister(this);
    }

    public void setContentRecycleView(List<ContentModel> contentModels) {
        if (contentModels == null || contentModels.isEmpty())
            contentModels = new ArrayList<>();

//        for (int i = 0; i < 10; i++) {
//            ContentModel contentModel = new ContentModel();
//            contentModel.setContent("这是内容");
//            contentModel.setTitle("Usage" + i);
//            contentModel.setTime("2016.10.10");
//            contentModels.add(contentModel);
//        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        MainRvAdapter mainRvAdapter = new MainRvAdapter(this, R.layout.rv_main_item, contentModels);
        mRecyclerView.setAdapter(mainRvAdapter);

    }


    //eventbus 更新标题和内容
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onContentEvent(ContentEvent contentEvent) {
        List<ContentModel> contentModels = contentEvent.getContentModels();
        String title = contentEvent.getSubTitle();
        mToolbar.setTitle(title);
        setContentRecycleView(contentModels);
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buy_iv:
                IntentUtils.startPayActivity(this);
//                parseAllData();
                break;
            case R.id.login_tv:
                IntentUtils.startLoginActivity(this);
                break;
            case R.id.setting_iv:
                IntentUtils.startSettingActivity(this);
                break;
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (TextUtils.isEmpty(query)) {
            return false;
        }
        if (allContentModels != null && !allContentModels.isEmpty()) {

            List<ContentModel> contentModels = searchContentModel(query);
            setContentRecycleView(contentModels);
        }
        return false;
    }

    public List<ContentModel> searchContentModel(String searchKey) {
        List<ContentModel> contentModels = new ArrayList<>();
        for (int i = 0; i < allContentModels.size(); i++) {
            ContentModel contentModel = allContentModels.get(i);
            if (contentModel.getTitle().toLowerCase().contains(searchKey.toLowerCase())) {
                contentModels.add(contentModel);
            }
            ;
        }
        return contentModels;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
