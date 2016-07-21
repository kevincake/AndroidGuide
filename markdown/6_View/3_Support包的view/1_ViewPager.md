这是谷歌官方给我们提供的一个兼容低版本安卓设备的软件包，里面包囊了只有在安卓3.0以上可以使用的api。而viewpager就是其中之一利用它，我们可以做很多事情，从最简单的导航，到页面菜单等等。那如何使用它呢，与LisstView类似，我们也需要一个适配器，他就是PagerAdapter。看一下api的图片，
  ![](http://img.my.csdn.net/uploads/201211/10/1352550609_8872.png)
ViewPager的功能就是可以使视图滑动，就像Lanucher左右滑动那样。分三个步骤来使用它：
1. 在住布局文件里加入
```

<android.support.v4.view.ViewPager   
 <!-- 这个组件，注意这个组件是用来显示左右滑动的界面的，如果不加载xml布局文件，他是不会显示内容的。 -->
        android:id="@+id/viewpager"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center" >
```
2. 加载要显示的页卡
```
PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {

                return arg0 == arg1;
            }

            @Override
            public int getCount() {

                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                    Object object) {
                container.removeView(viewList.get(position));

            }

            @Override
            public int getItemPosition(Object object) {

                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {

                return titleList.get(position);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                weibo_button=(Button) findViewById(R.id.button1);
                weibo_button.setOnClickListener(new OnClickListener() {
                    
                    public void onClick(View v) {
                        intent=new Intent(ViewPagerDemo.this,WeiBoActivity.class);
                        startActivity(intent);
                    }
                });
                return viewList.get(position);
            }

        };
        viewPager.setAdapter(pagerAdapter);

```
3. 在Activity里实例化ViewPager组件，并设置它的Adapter（就是PagerAdapter，方法与ListView一样的），在这里一般需要重写PagerAdapter。
```
PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {

                return arg0 == arg1;
            }

            @Override
            public int getCount() {

                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                    Object object) {
                container.removeView(viewList.get(position));

            }

            @Override
            public int getItemPosition(Object object) {

                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {

                return titleList.get(position);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                weibo_button=(Button) findViewById(R.id.button1);
                weibo_button.setOnClickListener(new OnClickListener() {
                    
                    public void onClick(View v) {
                        intent=new Intent(ViewPagerDemo.this,WeiBoActivity.class);
                        startActivity(intent);
                    }
                });
                return viewList.get(position);
            }

        };
        viewPager.setAdapter(pagerAdapter);

```
这是重写PagerAdapter的一个方法，我们还可以这样做：
```
public class MyViewPagerAdapter extends PagerAdapter{
        private List<View> mListViews;
        
        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)   {   
            container.removeView(mListViews.get(position));//删除页卡
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡       
             container.addView(mListViews.get(position), 0);//添加页卡
             return mListViews.get(position);
        }

        @Override
        public int getCount() {         
            return  mListViews.size();//返回页卡的数量
        }
        
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {           
            return arg0==arg1;//官方提示这样写
        }
    }
```
大同小异，有一定很重要，就是我们需要重写哪些方法。从上面的图片可以看到，ViewPager的适配器是PagerAdapter，它是基类提供适配器来填充页面ViewPager内部，你很可能想要使用一个更具体的实现,如FragmentPagerAdapter或FragmentStatePagerAdapter。在这里需要说明一下，其实ViewPager应该和Fragment一起使用，至少谷歌官方是这么想的，但是在3.0之下，我们没有必要这么做。下面要注意，当你实现一个PagerAdapter,你必须至少覆盖以下方法:
```
instantiateItem(ViewGroup, int)
destroyItem(ViewGroup, int, Object)
getCount()
isViewFromObject(View, Object)
```
从上面的例子中可以看到，我们最少也是实现了上面四个方法，当然如果你想让程序更健壮或是功能更全面，你可以重写其他的方法。下面看一下第一个完整的示例代码：
主页面Activity：
```
package com.example.viewpagerdemo;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ViewPagerDemo extends Activity {

    private View view1, view2, view3;//需要滑动的页卡
    private ViewPager viewPager;//viewpager
    private PagerTitleStrip pagerTitleStrip;//viewpager的标题
    private PagerTabStrip pagerTabStrip;//一个viewpager的指示器，效果就是一个横的粗的下划线
    private List<View> viewList;//把需要滑动的页卡添加到这个list中
    private List<String> titleList;//viewpager的标题
    private Button weibo_button;//button对象，一会用来进入第二个Viewpager的示例
   private Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_demo);
        initView();
    }
      /*在这里需要说明一下，在上面的图片中我们看到了，PagerTabStrip，PagerTitleStrip，他们其实是viewpager的一个指示器，前者效果就是一个横的粗的下划线，后者用来显示各个页卡的标题，当然而这也可以共存。在使用他们的时候需要注意，看下面的布局文件，要在android.support.v4.view.ViewPager里面添加
android.support.v4.view.PagerTabStrip以及android.support.v4.view.PagerTitleStrip。

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //pagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pagertitle);
        pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagertab);
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.gold)); 
        pagerTabStrip.setDrawFullUnderline(false);
        pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.azure));
        pagerTabStrip.setTextSpacing(50);
        /*
        weibo_button=(Button) findViewById(R.id.button1);
        weibo_button.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                intent=new Intent(ViewPagerDemo.this,WeiBoActivity.class);
                startActivity(intent);
            }
        });
        */
        
        view1 = findViewById(R.layout.layout1);
        view2 = findViewById(R.layout.layout2);
        view3 = findViewById(R.layout.layout3);

        LayoutInflater lf = getLayoutInflater().from(this);
        view1 = lf.inflate(R.layout.layout1, null);
        view2 = lf.inflate(R.layout.layout2, null);
        view3 = lf.inflate(R.layout.layout3, null);

        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        titleList = new ArrayList<String>();// 每个页面的Title数据
        titleList.add("wp");
        titleList.add("jy");
        titleList.add("jh");

        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {

                return arg0 == arg1;
            }

            @Override
            public int getCount() {

                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                    Object object) {
                container.removeView(viewList.get(position));

            }

            @Override
            public int getItemPosition(Object object) {

                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {

                return titleList.get(position);//直接用适配器来完成标题的显示，所以从上面可以看到，我们没有使用PagerTitleStrip。当然你可以使用。

            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                weibo_button=(Button) findViewById(R.id.button1);//这个需要注意，我们是在重写adapter里面实例化button组件的，如果你在onCreate()方法里这样做会报错的。
                weibo_button.setOnClickListener(new OnClickListener() {
                    
                    public void onClick(View v) {
                        intent=new Intent(ViewPagerDemo.this,WeiBoActivity.class);
                        startActivity(intent);
                    }
                });
                return viewList.get(position);
            }

        };
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_pager_demo, menu);
        return true;
    }

}
```
它的布局文件：
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical" >

    <android.support.v4.view.ViewPager
        android:id="@+id/viewpager"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center" >
              
        <android.support.v4.view.PagerTabStrip  
            android:id="@+id/pagertab"  
            android:layout_width="wrap_content"  
            android:layout_height="wrap_content"  
            android:layout_gravity="top"/>  
         
    </android.support.v4.view.ViewPager>

</LinearLayout>
<!--注意事项:   
    1.这里ViewPager和 PagerTabStrip都要把包名写全了，不然会ClassNotFount  
    2.API中说：在布局xml把PagerTabStrip当做ViewPager的一个子标签来用，不能拿出来，不然还是会报错  
    3.在PagerTabStrip标签中可以用属性android:layout_gravity=TOP|BOTTOM来指定title的位置  
    4.如果要显示出PagerTabStrip某一页的title,需要在ViewPager的adapter中实现getPageTitle(int)--> 
```
这样就完成了一个简单的ViewPager的使用示例，看一下效果图：
</br>![](http://img.my.csdn.net/uploads/201211/10/1352553548_4447.png)</br>
</br>![](http://img.my.csdn.net/uploads/201211/10/1352553574_3792.png)</br>
可以左右滑动页卡，但是仔细看一下，标题的效果不好，不能一次显示一个，而且标题还滑动。其实在
 pi里面提供了一个pagerTabStrip.setTextSpacing()来设置标题的距离，但是我在这里设置了，没有效果不知道为什么，明白的朋友希望能够赐教一下。
所以就有人使用下面这样的方法来仿微博的效果，
</br>![](http://img.my.csdn.net/uploads/201211/10/1352553919_6995.png)</br>
这个标题就固定了，而且可以左右滑动，也有下面的横线，来指示页卡。方法和上面的差不多，区别在于这个横线需要我们自己来做，其实就是一个图片。这个例子是网上的一篇文章，看代码：
主Activity：
```

package com.example.viewpagerdemo;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WeiBoActivity extends Activity {

    private ViewPager viewPager;//页卡内容
    private ImageView imageView;// 动画图片
    private TextView textView1,textView2,textView3;
    private List<View> views;// Tab页面列表
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private View view1,view2,view3;//各个页卡
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weibo);
        InitImageView();
        InitTextView();
        InitViewPager();
    }

    private void InitViewPager() {
        viewPager=(ViewPager) findViewById(R.id.vPager);
        views=new ArrayList<View>();
        LayoutInflater inflater=getLayoutInflater();
        view1=inflater.inflate(R.layout.lay1, null);
        view2=inflater.inflate(R.layout.lay2, null);
        view3=inflater.inflate(R.layout.lay3, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }
     /**
      *  初始化头标
      */

    private void InitTextView() {
        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView3 = (TextView) findViewById(R.id.text3);

        textView1.setOnClickListener(new MyOnClickListener(0));
        textView2.setOnClickListener(new MyOnClickListener(1));
        textView3.setOnClickListener(new MyOnClickListener(2));
    }

    /**
     2      * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
     3 */

    private void InitImageView() {
        imageView= (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置
    }
<img src="http://img.my.csdn.net/uploads/201211/10/1352554452_1685.jpg" alt="">
    /** 
     *     
     * 头标点击监听 3 */
    private class MyOnClickListener implements OnClickListener{
        private int index=0;
        public MyOnClickListener(int i){
            index=i;
        }
        public void onClick(View v) {
            viewPager.setCurrentItem(index);            
        }
        
    }
    
    public class MyViewPagerAdapter extends PagerAdapter{
        private List<View> mListViews;
        
        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)   {   
            container.removeView(mListViews.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {          
             container.addView(mListViews.get(position), 0);
             return mListViews.get(position);
        }

        @Override
        public int getCount() {         
            return  mListViews.size();
        }
        
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {           
            return arg0==arg1;
        }
    }

    public class MyOnPageChangeListener implements OnPageChangeListener{

        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量
        public void onPageScrollStateChanged(int arg0) {
            
            
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
            
            
        }

        public void onPageSelected(int arg0) {
            /*两种方法，这个是一种，下面还有一种，显然这个比较麻烦
            Animation animation = null;
            switch (arg0) {
            case 0:
                if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                }
                break;
            case 1:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                }
                break;
            case 2:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, two, 0, 0);
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(one, two, 0, 0);
                }
                break;
                
            }
            */
            Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);//显然这个比较简洁，只有一行代码。
            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            imageView.startAnimation(animation);
            Toast.makeText(WeiBoActivity.this, "您选择了"+ viewPager.getCurrentItem()+"页卡", Toast.LENGTH_SHORT).show();
        }
        
    }
}
```
它的布局文件：
```
 <?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical" >

    <LinearLayout
        android:id="@+id/linearLayout1"
        android:layout_width="fill_parent"
        android:layout_height="40.0dip"
        android:background="#FFFFFF" >

        <TextView
            android:id="@+id/text1"
            android:layout_width="fill_parent"
            android:layout_height="fill_parent"
            android:layout_weight="1.0"
            android:gravity="center"
            android:text=" @我"
            android:textColor="#000000"
            android:textSize="20.0dip" />

        <TextView
            android:id="@+id/text2"
            android:layout_width="fill_parent"
            android:layout_height="fill_parent"
            android:layout_weight="1.0"
            android:gravity="center"
            android:text="评论"
            android:textColor="#000000"
            android:textSize="20.0dip" />

        <TextView
            android:id="@+id/text3"
            android:layout_width="fill_parent"
            android:layout_height="fill_parent"
            android:layout_weight="1.0"
            android:gravity="center"
            android:text="私信"
            android:textColor="#000000"
            android:textSize="20.0dip" />
    </LinearLayout>

    <ImageView
        android:id="@+id/cursor"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:scaleType="matrix"
        android:src="@drawable/a" />

    <android.support.v4.view.ViewPager
        android:id="@+id/vPager"
        android:layout_width="wrap_content"
        android:layout_height="0dp"
        android:layout_gravity="center"
        android:layout_weight="1.0"
        android:background="#000000"
        android:flipInterval="30"
        android:persistentDrawingCache="animation" />
</LinearLayout>
```
效果如下：
![](http://img.my.csdn.net/uploads/201211/10/1352554877_7172.png)</br>
![](http://img.my.csdn.net/uploads/201211/10/1352554877_7172.png)</br>
下面使用ViewPager来实现一个程序引导的demo：
 一般来说，引导界面是出现第一次运行时出现的，之后不会再出现。所以需要记录是否是第一次使用程序，办法有很多，最容易想到的就是使用SharedPreferences来保存。步骤如下：
  1、程序进入欢迎界面，SplashActivity，在这里读取SharedPreferences里面的变量，先设置为true。进入引导界面，然后设置为false。
  2、之后每次进入欢迎界面读取SharedPreferences里面的变量，因为是false，所以不会运行引导界面了。
代码如下：
SplashActivity.java,欢迎界面。
```
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**

 */
public class SplashActivity extends Activity {
    boolean isFirstIn = false;

    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    // 延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 3000;

    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    /**
     * Handler:跳转到不同界面
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case GO_HOME:
                goHome();
                break;
            case GO_GUIDE:
                goGuide();
                break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        init();
    }

    private void init() {
        // 读取SharedPreferences中需要的数据
        // 使用SharedPreferences来记录程序的使用次数
        SharedPreferences preferences = getSharedPreferences(
                SHAREDPREFERENCES_NAME, MODE_PRIVATE);

        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        isFirstIn = preferences.getBoolean("isFirstIn", true);

        // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
        if (!isFirstIn) {
            // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
            mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
        }

    }

    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

    private void goGuide() {
        Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }
}

```
GuideActivity.java引导界面：
```
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.eoe.leigo.splash.adapter.ViewPagerAdapter;

/**
 * 

 * 
 */
public class GuideActivity extends Activity implements OnPageChangeListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);

        // 初始化页面
        initViews();

        // 初始化底部小点
        initDots();
    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();
        // 初始化引导图片列表
        views.add(inflater.inflate(R.layout.what_new_one, null));
        views.add(inflater.inflate(R.layout.what_new_two, null));
        views.add(inflater.inflate(R.layout.what_new_three, null));
        views.add(inflater.inflate(R.layout.what_new_four, null));

        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(views, this);
        
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        // 绑定回调
        vp.setOnPageChangeListener(this);
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[views.size()];

        // 循环取得小点图片
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);// 都设为灰色
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1
                || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    // 当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        setCurrentDot(arg0);
    }

}

```
ViewPagerAdapter.java。ViewPager的适配器：
```
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import cn.eoe.leigo.splash.MainActivity;
import cn.eoe.leigo.splash.R;

/**
 */
public class ViewPagerAdapter extends PagerAdapter {

    // 界面列表
    private List<View> views;
    private Activity activity;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";
    public ViewPagerAdapter(List<View> views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }

    // 销毁arg1位置的界面
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(views.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    // 获得当前界面数
    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    // 初始化arg1位置的界面
    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(views.get(arg1), 0);
        if (arg1 == views.size() - 1) {
            ImageView mStartWeiboImageButton = (ImageView) arg0
                    .findViewById(R.id.iv_start_weibo);
            mStartWeiboImageButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 设置已经引导
                    setGuided();
                    goHome();

                }

            });
        }
        return views.get(arg1);
    }

    private void goHome() {
        // 跳转
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
    /**
     * 
     * method desc：设置已经引导过了，下次启动不用再次引导
     */
    private void setGuided() {
        SharedPreferences preferences = activity.getSharedPreferences(
                SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        // 存入数据
        editor.putBoolean("isFirstIn", false);
        // 提交修改
        editor.commit();
    }

    // 判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

}

```
至于MainActivity随便了。
效果如下：
</br>![](http://img.blog.csdn.net/20130522225414348)</br>

###所以总结一下，我们可以使用ViewPager做什么：
1. 程序使用导航，外加底部圆点的效果，这个在微信示例里介绍了 
2. 页卡滑动，加上菜单的效果，不管是之前的支持手势也支持底部图标点击的微信，还是今天的微博。

出处:<a href="http://blog.csdn.net/wangjinyu501/article/details/8169924">http://blog.csdn.net/wangjinyu501/article/details/8169924</a>