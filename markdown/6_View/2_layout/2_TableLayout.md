TabLayout(表格布局) 
在开始之前，我们先来看看官网文档的说明,如何与ViewPager进行联动：

>You should set a listener via setOnTabSelectedListener(OnTabSelectedListener) to be notified when any tab’s selection state has been changed. 
If you’re using a ViewPager together with this layout, you can use setTabsFromPagerAdapter(PagerAdapter) which will populate the tabs using the given PagerAdapter’s page titles. You should also use a TabLayout.TabLayoutOnPageChangeListener to forward the scroll and selection changes to this layout

恩，看起来很容易啊，简单翻译一下就是：

通过setOnTabSelectedListener设置一个监听器来响应选项卡的选择状态 
可以通过setTabsFromPagerAdapter来使用PagerAdapter的page title 
使用TabLayout.TabLayoutOnPageChangeListener来联动滑动
ok，大体就是这样，在大体的翻译过程中，其实已经把步骤列出来了，接下来我们只需要按照上面的步骤来编写我们的代码就OK啦。 
come on~，首先出场的是xml布局文件
```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <android.support.design.widget.TabLayout
        android:id="@+id/tl"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:tabIndicatorColor="#FF00FF00"
        app:tabSelectedTextColor="#FF00FF00"
        app:tabTextColor="#FF000000"
        app:tabMode="scrollable"
        app:tabGravity="center"/>

    <android.support.v4.view.ViewPager
        android:id="@+id/viewpager"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

</LinearLayout>
```
恩，很简单，就是一个TabLayout和一个ViewPager,并且是上下排列的。

在Activity中，首先我们要find这两个控件
```
 mTabLayout = (TabLayout) findViewById(R.id.tl);
    mViewPager = (ViewPager) findViewById(R.id.viewpager);
```
为了演示程序，还需要两个伪数据来充当ViewPager的内容和title。
```
private String[] mTitle = new String[20];
private String[] mData = new String[20];

{
    for(int i=0;i<20;i++) {
        mTitle[i] = "title" + i;
        mData[i] = "data" + i;
    }
}
```
下面，我们将要按照上面的步骤，一点点来撸代码啦： 
第一步，设置TabLayout的选项卡监听：
```
mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
```
当我们的tab选择时，让viewpager选中对应的item。 
第二步，设置Tab的标题来自PagerAdapter.getPageTitle()。
```
mTabLayout.setTabsFromPagerAdapter(mAdapter);
```
第三步，设置TabLayout.TabLayoutOnPageChangeListener,给谁设置呢？当然是ViewPager了，怎么设置呢？
```
final TabLayout.TabLayoutOnPageChangeListener listener =
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout);
mViewPager.addOnPageChangeListener(listener);
```
哦，原来TabLaout.TabLayoutOnPageChangeLisetener继承自OnPageChangeListener，不行来看
```
public static class TabLayoutOnPageChangeListener implements OnPageChangeListener {...}

```
注意，这里是addOnPageChangeListener,也就是说，你还可以add N个Listener而不会被覆盖掉。 
到这里，步骤我们都走完了，但是别忘了给ViewPage设置Adapter。
```
mViewPager.setAdapter(mAdapter);
```
最后是Adapter的代码：
```
private PagerAdapter mAdapter = new PagerAdapter() {
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }

        @Override
        public int getCount() {
            return mData.length;
        }

        @Override
        public Object instantiateItem(View container, int position) {
            TextView tv = new TextView(MainActivity.this);
            tv.setTextSize(30.f);
            tv.setText(mData[position]);
            ((ViewPager) container).addView(tv);
            return tv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    };
```
运行一下代码，看看效果：
![](http://img.blog.csdn.net/20150723085527778)</br>
是不是很简单？ 几行代码就搞定了这种效果，当然，你也可以选择自己重写View的方式实现，可以参考我的另一篇博客<a herf="http://blog.csdn.net/qibin0506/article/details/42046559">《打造史上最容易使用的Tab指示符——Indicator》。 </a>

如果，你感觉这就简单，那你也太容易满足啦，Google还给我们提供了一个方法，将这些步骤封装到一块，让我们开发者可以一步搞定。public void setupWithViewPager (ViewPager viewPager)，继续看看文档的说明：
>The one-stop shop for setting up this TabLayout with a ViewPager.

>This method will:

>Add a ViewPager.OnPageChangeListener that will forward events to this TabLayout. 
Populate the TabLayout’s tabs from the ViewPager’s PagerAdapter. 
Set our TabLayout.OnTabSelectedListener which will forward selected events to the ViewPager

大体解释一下就是：
>这个方法是addOnPageChangeListener和setOnTabSelectedListener的封装。

所以我们注释掉对应代码，替换成setupWithViewPager。
```
//        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                mViewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
        mTabLayout.setTabsFromPagerAdapter(mAdapter);

//        final TabLayout.TabLayoutOnPageChangeListener listener =
//                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout);
//        mViewPager.addOnPageChangeListener(listener);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
```
效果还是上面的效果，但是代码量明显少了，仅需两行代码搞定。 
但是需要注意一下，setupWithViePager必须在ViewPager.setAdapter()之后调用！为什么呢？来源码找答案：
```
public void setupWithViewPager(ViewPager viewPager) {
    PagerAdapter adapter = viewPager.getAdapter();
    if(adapter == null) {
        throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
    } else {
        ...
    }
}
```
好了，到现在TabLayout和ViewPager一块使用的用法就讲解完了，相信还是非常简单的，仅仅两行代码就可以搞定。 
在以后的项目中，肯定会经常用到它们两个好基友的，但是so easy,不怕不怕~~

