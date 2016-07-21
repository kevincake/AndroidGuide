 Android Fragment 真正的完全解析(上)
转载请标明出处：<a href="http://blog.csdn.net/lmj623565791/article/details/37970961">http://blog.csdn.net/lmj623565791/article/details/37970961</a>
自从Fragment出现，曾经有段时间，感觉大家谈什么都能跟Fragment谈上关系，做什么都要问下Fragment能实现不~~~哈哈，是不是有点过~~~
本篇博客力求为大家说明Fragment如何产生，什么是Fragment，Fragment生命周期，如何静态和动态的使用Fragment，Fragment回退栈，Fragment事务；以及Fragment的一些特殊用途，例如：没有布局的Fragment有何用处？Fragment如何与Activity交互？Fragment如何创建对话框？Fragment如何与ActionBar集成等等。
###1、Fragment的产生与介绍
Android运行在各种各样的设备中，有小屏幕的手机，超大屏的平板甚至电视。针对屏幕尺寸的差距，很多情况下，都是先针对手机开发一套App，然后拷贝一份，修改布局以适应平板神马超级大屏的。难道无法做到一个App可以同时适应手机和平板么，当然了，必须有啊。Fragment的出现就是为了解决这样的问题。你可以把Fragment当成Activity的一个界面的一个组成部分，甚至Activity的界面可以完全有不同的Fragment组成，更帅气的是Fragment拥有自己的生命周期和接收、处理用户的事件，这样就不必在Activity写一堆控件的事件处理的代码了。更为重要的是，你可以动态的添加、替换和移除某个Fragment。
###2、Fragment的生命周期
Fragment必须是依存与Activity而存在的，因此Activity的生命周期会直接影响到Fragment的生命周期。官网这张图很好的说明了两者生命周期的关系：
</br>![](http://img.blog.csdn.net/20140719225005356?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbG1qNjIzNTY1Nzkx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)</br>
可以看到Fragment比Activity多了几个额外的生命周期回调方法：
```
onAttach(Activity)
```
当Fragment与Activity发生关联时调用。
```
onCreateView(LayoutInflater, ViewGroup,Bundle)
```
创建该Fragment的视图
```
onActivityCreated(Bundle)
```
当Activity的onCreate方法返回时调用
```
onDestoryView()
```
与onCreateView想对应，当该Fragment的视图被移除时调用
```
onDetach()
```
与onAttach相对应，当Fragment与Activity关联被取消时调用
注意：除了onCreateView，其他的所有方法如果你重写了，必须调用父类对于该方法的实现，
###3、静态的使用Fragment
嘿嘿，终于到使用的时刻了~~
这是使用Fragment最简单的一种方式，把Fragment当成普通的控件，直接写在Activity的布局文件中。步骤：
#####1、继承Fragment，重写onCreateView决定Fragemnt的布局
#####2、在Activity中声明此Fragment，就当和普通的View一样
下面展示一个例子（我使用2个Fragment作为Activity的布局，一个Fragment用于标题布局，一个Fragment用于内容布局）：
TitleFragment的布局文件:
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="45dp"
    android:background="@drawable/title_bar" >

    <ImageButton
        android:id="@+id/id_title_left_btn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true"
        android:layout_marginLeft="3dp"
        android:background="@drawable/showleft_selector" />

    <TextView
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:gravity="center"
        android:text="我不是微信"
        android:textColor="#fff"
        android:textSize="20sp"
        android:textStyle="bold" />

</RelativeLayout>
```
TitleFragment
```
package com.zhy.zhy_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class TitleFragment extends Fragment
{

    private ImageButton mLeftMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_title, container, false);
        mLeftMenu = (ImageButton) view.findViewById(R.id.id_title_left_btn);
        mLeftMenu.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getActivity(),
                        "i am an ImageButton in TitleFragment ! ",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

```
同理还有ContentFragment的其布局文件：
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical" >

    <TextView
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:gravity="center"
        android:text="使用Fragment做主面板"
        android:textSize="20sp"
        android:textStyle="bold" />

</LinearLayout>
```

```
package com.zhy.zhy_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContentFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

}

```
MainActivity
```
package com.zhy.zhy_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

}

```
Activity的布局文件
```

<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <fragment
        android:id="@+id/id_fragment_title"
        android:name="com.zhy.zhy_fragments.TitleFragment"
        android:layout_width="fill_parent"
        android:layout_height="45dp" />

    <fragment
        android:layout_below="@id/id_fragment_title"
        android:id="@+id/id_fragment_content"
        android:name="com.zhy.zhy_fragments.ContentFragment"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent" />

</RelativeLayout>
```

是不是把Fragment当成普通的View一样声明在Activity的布局文件中，然后所有控件的事件处理等代码都由各自的Fragment去处理，瞬间觉得Activity好干净有木有~~代码的可读性、复用性以及可维护性是不是瞬间提升了~下面看下效果图：
</br>![](http://img.blog.csdn.net/20140719230904593)</br>

###4、动态的使用Fragment
上面已经演示了，最简单的使用Fragment的方式~下面介绍如何动态的添加、更新、以及删除Fragment
为了动态使用Fragment，我们修改一下Actvity的布局文件，中间使用一个FrameLayout，下面添加四个按钮~~嘿嘿~不是微信的按钮- -！
```
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <fragment
        android:id="@+id/id_fragment_title"
        android:name="com.zhy.zhy_fragments.TitleFragment"
        android:layout_width="fill_parent"
        android:layout_height="45dp" />

    <include
        android:id="@+id/id_ly_bottombar"
        android:layout_width="fill_parent"
        android:layout_height="55dp"
        android:layout_alignParentBottom="true"
        layout="@layout/bottombar" />

    <FrameLayout
        android:id="@+id/id_content"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:layout_above="@id/id_ly_bottombar"
        android:layout_below="@id/id_fragment_title" />

</RelativeLayout>
```
底部四个按钮的布局就不贴了，到时看效果图就明白了~~
下面主Activity
```
package com.zhy.zhy_fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener
{
    private LinearLayout mTabWeixin;
    private LinearLayout mTabFriend;

    private ContentFragment mWeixin;
    private FriendFragment mFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // 初始化控件和声明事件
        mTabWeixin = (LinearLayout) findViewById(R.id.tab_bottom_weixin);
        mTabFriend = (LinearLayout) findViewById(R.id.tab_bottom_friend);
        mTabWeixin.setOnClickListener(this);
        mTabFriend.setOnClickListener(this);

        // 设置默认的Fragment
        setDefaultFragment();
    }

    private void setDefaultFragment()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mWeixin = new ContentFragment();
        transaction.replace(R.id.id_content, mWeixin);
        transaction.commit();
    }

    @Override
    public void onClick(View v)
    {
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        switch (v.getId())
        {
        case R.id.tab_bottom_weixin:
            if (mWeixin == null)
            {
                mWeixin = new ContentFragment();
            }
            // 使用当前Fragment的布局替代id_content的控件
            transaction.replace(R.id.id_content, mWeixin);
            break;
        case R.id.tab_bottom_friend:
            if (mFriend == null)
            {
                mFriend = new FriendFragment();
            }
            transaction.replace(R.id.id_content, mFriend);
            break;
        }
        // transaction.addToBackStack();
        // 事务提交
        transaction.commit();
    }

}

```
可以看到我们使用FragmentManager对Fragment进行了动态的加载，这里使用的是replace方法~~下一节我会详细介绍FragmentManager的常用API。
注：如果使用Android3.0以下的版本，需要引入v4的包，然后Activity继承FragmentActivity，然后通过getSupportFragmentManager获得FragmentManager。不过还是建议版Menifest文件的uses-sdk的minSdkVersion和targetSdkVersion都改为11以上，这样就不必引入v4包了。
代码中间还有两个Fragment的子类，ContentFragment上面已经见过，FriendFragment其实类似：
```

package com.zhy.zhy_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FriendFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

}
```
</br>![](http://img.blog.csdn.net/20140720002532641)</br>
可以看到很好的实现了效果，其实这个效果以前的博客中也出现过，在博客：Android项目Tab类型主界面大总结 Fragment+TabPageIndicator+ViewPager，有兴趣可以看看。ps:为了代码的简洁，就不添加按钮的点击变化什么的了，主要讲解功能了~~~
###5、Fragment家族常用的API
Fragment常用的三个类：
```
android.app.Fragment 主要用于定义Fragment
android.app.FragmentManager 主要用于在Activity中操作Fragment
android.app.FragmentTransaction 
```
保证一些列Fragment操作的原子性，熟悉事务这个词，一定能明白~
**a、获取FragmentManage的方式：**
```
getFragmentManager() // v4中，getSupportFragmentManager
```
**b、主要的操作都是FragmentTransaction的方法**
```
FragmentTransaction transaction = fm.benginTransatcion();//开启一个事务
transaction.add() 
```
往Activity中添加一个Fragment
```
transaction.remove() 
```
从Activity中移除一个Fragment，如果被移除的Fragment没有添加到回退栈（回退栈后面会详细说），这个Fragment实例将会被销毁。
```
transaction.replace()
```
使用另一个Fragment替换当前的，实际上就是remove()然后add()的合体~
```
transaction.hide()
```
隐藏当前的Fragment，仅仅是设为不可见，并不会销毁
```
transaction.show()
```
显示之前隐藏的Fragment
```
detach()
```
会将view从UI中移除,和remove()不同,此时fragment的状态依然由FragmentManager维护。
```
attach()
```
重建view视图，附加到UI上并显示。
```
transatcion.commit()//提交一个事务
```
注意：常用Fragment的哥们，可能会经常遇到这样Activity状态不一致：State loss这样的错误。主要是因为：commit方法一定要在Activity.onSaveInstance()之前调用。
上述，基本是操作Fragment的所有的方式了，在一个事务开启到提交可以进行多个的添加、移除、替换等操作。
值得注意的是：如果你喜欢使用Fragment，一定要清楚这些方法，哪个会销毁视图，哪个会销毁实例，哪个仅仅只是隐藏，这样才能更好的使用它们。
</br>**a、比如：我在FragmentA中的EditText填了一些数据，当切换到FragmentB时，如果希望会到A还能看到数据，则适合你的就是hide和show；也就是说，希望保留用户操作的面板，你可以使用hide和show，当然了不要使劲在那new实例，进行下非null判断。**</br>
</br>**b、再比如：我不希望保留用户操作，你可以使用remove()，然后add()；或者使用replace()这个和remove,add是相同的效果。**</br>
</br>**c、remove和detach有一点细微的区别，在不考虑回退栈的情况下，remove会销毁整个Fragment实例，而detach则只是销毁其视图结构，实例并不会被销毁。那么二者怎么取舍使用呢？如果你的当前Activity一直存在，那么在不希望保留用户操作的时候，你可以优先使用detach。**</br>

上述已经介绍完成了Fragment常用的一些方法，相信看完，大家一定清楚了Fragment的产生理由，以及如何使用Fragment，再根据API的讲解，也能明白，曾经为何觉得Fragment会出现一些列乱七八槽的问题，终究是因为没有弄清楚其生命周期。
接下来为大家介绍
#####1、如何管理Fragment回退栈
#####2、Fragment如何与Activity交互
#####3、Fragment与Activity交互的最佳实践
#####4、没有视图的Fragment的用处
#####5、使用Fragment创建对话框
#####6、如何与ActionBar，MenuItem集成等~~


Android Fragment 真正的完全解析（下）
转载请标明出处：http://blog.csdn.net/lmj623565791/article/details/37992017
上篇博客中已经介绍了Fragment产生原因，以及一些基本的用法和各种API，如果你还不了解，请看：Android Fragment 真正的完全解析（上）。
本篇将介绍上篇博客提到的：如何管理Fragment回退栈，Fragment如何与Activity交互，Fragment与Activity交互的最佳实践，没有视图的Fragment的用处，使用Fragment创建对话框，如何与ActionBar，MenuItem集成等~~
####1、管理Fragment回退栈
类似与Android系统为Activity维护一个任务栈，我们也可以通过Activity维护一个回退栈来保存每次Fragment事务发生的变化。如果你将Fragment任务添加到回退栈，当用户点击后退按钮时，将看到上一次的保存的Fragment。一旦Fragment完全从后退栈中弹出，用户再次点击后退键，则退出当前Activity。
看这样一个效果图：
</br>![](http://img.blog.csdn.net/20140720144355734)</br>
点击第一个按钮，切换到第二个界面，点击第二个按钮，切换到第三个界面，然后点击Back键依次回退。这像不像初学Android时的Activity跳转，当然了，这里肯定不是，不然我就跪了。这里是Fragment实现的，用户点击Back，实际是Fragment回退栈不断的弹栈。
如何添加一个Fragment事务到回退栈：
```
FragmentTransaction.addToBackStack(String)
```
下面讲解代码：很明显一共是3个Fragment和一个Activity.
先看Activity的布局文件：
```
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <FrameLayout
        android:id="@+id/id_content"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent" >
    </FrameLayout>

</RelativeLayout>
```
不同的Fragment就在这个FrameLayout中显示。
MainActivity.java
```
package com.zhy.zhy_fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.add(R.id.id_content, new FragmentOne(),"ONE");
        tx.commit();
    }

}

```
很简单，直接将FragmentOne添加到布局文件中的FrameLayout中，注意这里并没有调用FragmentTransaction.addToBackStack(String)，因为我不喜欢在当前显示时，点击Back键出现白板。而是正确的相应Back键，即退出我们的Activity.
下面是FragmentOne
```
package com.zhy.zhy_fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentOne extends Fragment implements OnClickListener
{

    private Button mBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        mBtn = (Button) view.findViewById(R.id.id_fragment_one_btn);
        mBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        FragmentTwo fTwo = new FragmentTwo();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.replace(R.id.id_content, fTwo, "TWO");
        tx.addToBackStack(null);
        tx.commit();

    }

}

```
我们在点击FragmentOne中的按钮时，使用了replace方法，如果你看了前一篇博客，一定记得replace是remove和add的合体，并且如果不添加事务到回退栈，前一个Fragment实例会被销毁。这里很明显，我们调用tx.addToBackStack(null);将当前的事务添加到了回退栈，所以FragmentOne实例不会被销毁，但是视图层次依然会被销毁，即会调用onDestoryView和onCreateView，证据就是：仔细看上面的效果图，我们在跳转前在文本框输入的内容，在用户Back得到第一个界面的时候不见了。
接下来FragmentTwo
```
package com.zhy.zhy_fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentTwo extends Fragment implements OnClickListener
{

    private Button mBtn ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        mBtn = (Button) view.findViewById(R.id.id_fragment_two_btn);
        mBtn.setOnClickListener(this);
        return view ; 
    }
    @Override
    public void onClick(View v)
    {
        FragmentThree fThree = new FragmentThree();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.hide(this);
        tx.add(R.id.id_content , fThree, "THREE");
//      tx.replace(R.id.id_content, fThree, "THREE");
        tx.addToBackStack(null);
        tx.commit();
    }


}

```
这里点击时，我们没有使用replace，而是先隐藏了当前的Fragment，然后添加了FragmentThree的实例，最后将事务添加到回退栈。这样做的目的是为了给大家提供一种方案：如果不希望视图重绘该怎么做，请再次仔细看效果图，我们在FragmentTwo的EditText填写的内容，用户Back回来时，数据还在~~~
最后FragmentThree就是简单的Toast了：
```
package com.zhy.zhy_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class FragmentThree extends Fragment implements OnClickListener
{

    private Button mBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        mBtn = (Button) view.findViewById(R.id.id_fragment_three_btn);
        mBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        Toast.makeText(getActivity(), " i am a btn in Fragment three",
                Toast.LENGTH_SHORT).show();
    }

}

```
好了，经过上面的介绍，应该已经知道Fragment回退栈是怎么一回事了，以及hide，replace等各自的应用的场景。
这里极其注意一点：上面的整体代码不具有任何参考价值，纯粹为了显示回退栈，在后面讲解了Fragment与Activity通信以后，会重构上面的代码！
####2、Fragment与Activity通信
因为所有的Fragment都是依附于Activity的，所以通信起来并不复杂，大概归纳为：
</br>**a、如果你Activity中包含自己管理的Fragment的引用，可以通过引用直接访问所有的Fragment的public方法**
</br>**b、如果Activity中未保存任何Fragment的引用，那么没关系，每个Fragment都有一个唯一的TAG或者ID,可以通过getFragmentManager.findFragmentByTag()或者findFragmentById()获得任何Fragment实例，然后进行操作。**
</br>**c、在Fragment中可以通过getActivity得到当前绑定的Activity的实例，然后进行操作。</br>
注：如果在Fragment中需要Context，可以通过调用getActivity(),如果该Context需要在Activity被销毁后还存在，则使用getActivity().getApplicationContext()**
#####3、Fragment与Activity通信的最佳实践
因为要考虑Fragment的重复使用，所以必须降低Fragment与Activity的耦合，而且Fragment更不应该直接操作别的Fragment，毕竟Fragment操作应该由它的管理者Activity来决定。
下面我通过两种方式的代码，分别重构，FragmentOne和FragmentTwo的点击事件，以及Activity对点击事件的响应：
首先看FragmentOne
```

package com.zhy.zhy_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentOne extends Fragment implements OnClickListener
{
    private Button mBtn;

    /**
     * 设置按钮点击的回调
     * @author zhy
     *
     */
    public interface FOneBtnClickListener
    {
        void onFOneBtnClick();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        mBtn = (Button) view.findViewById(R.id.id_fragment_one_btn);
        mBtn.setOnClickListener(this);
        return view;
    }

    /**
     * 交给宿主Activity处理，如果它希望处理
     */
    @Override
    public void onClick(View v)
    {
        if (getActivity() instanceof FOneBtnClickListener)
        {
            ((FOneBtnClickListener) getActivity()).onFOneBtnClick();
        }
    }

}


```
可以看到现在的FragmentOne不和任何Activity耦合，任何Activity都可以使用；并且我们声明了一个接口，来回调其点击事件，想要管理其点击事件的Activity实现此接口就即可。可以看到我们在onClick中首先判断了当前绑定的Activity是否实现了该接口，如果实现了则调用。
再看FragmentTwo
```
package com.zhy.zhy_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentTwo extends Fragment implements OnClickListener
{

    
    private Button mBtn ;
    
    private FTwoBtnClickListener fTwoBtnClickListener ;
    
    public interface FTwoBtnClickListener
    {
        void onFTwoBtnClick();
    }
    //设置回调接口
    public void setfTwoBtnClickListener(FTwoBtnClickListener fTwoBtnClickListener)
    {
        this.fTwoBtnClickListener = fTwoBtnClickListener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        mBtn = (Button) view.findViewById(R.id.id_fragment_two_btn);
        mBtn.setOnClickListener(this);
        return view ; 
    }
    @Override
    public void onClick(View v)
    {
        if(fTwoBtnClickListener != null)
        {
            fTwoBtnClickListener.onFTwoBtnClick();
        }
    }

}

```
与FragmentOne极其类似，但是我们提供了setListener这样的方法，意味着Activity不仅需要实现该接口，还必须显示调用mFTwo.setfTwoBtnClickListener(this)。
最后看Activity :
```

package com.zhy.zhy_fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

import com.zhy.zhy_fragments.FragmentOne.FOneBtnClickListener;
import com.zhy.zhy_fragments.FragmentTwo.FTwoBtnClickListener;

public class MainActivity extends Activity implements FOneBtnClickListener,
        FTwoBtnClickListener
{

    private FragmentOne mFOne;
    private FragmentTwo mFTwo;
    private FragmentThree mFThree;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mFOne = new FragmentOne();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.add(R.id.id_content, mFOne, "ONE");
        tx.commit();
    }

    /**
     * FragmentOne 按钮点击时的回调
     */
    @Override
    public void onFOneBtnClick()
    {

        if (mFTwo == null)
        {
            mFTwo = new FragmentTwo();
            mFTwo.setfTwoBtnClickListener(this);
        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.replace(R.id.id_content, mFTwo, "TWO");
        tx.addToBackStack(null);
        tx.commit();
    }

    /**
     * FragmentTwo 按钮点击时的回调
     */
    @Override
    public void onFTwoBtnClick()
    {
        if (mFThree == null)
        {
            mFThree = new FragmentThree();

        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.hide(mFTwo);
        tx.add(R.id.id_content, mFThree, "THREE");
        // tx.replace(R.id.id_content, fThree, "THREE");
        tx.addToBackStack(null);
        tx.commit();
    }

}


```
代码重构结束，与开始的效果一模一样。上面两种通信方式都是值得推荐的，随便选择一种自己喜欢的。这里再提一下：虽然Fragment和Activity可以通过getActivity与findFragmentByTag或者findFragmentById，进行任何操作，甚至在Fragment里面操作另外的Fragment，但是没有特殊理由是绝对不提倡的。Activity担任的是Fragment间类似总线一样的角色，应当由它决定Fragment如何操作。另外虽然Fragment不能响应Intent打开，但是Activity可以，Activity可以接收Intent，然后根据参数判断显示哪个Fragment。
####4、如何处理运行时配置发生变化
运行时配置发生变化，最常见的就是屏幕发生旋转，如果你不知道如何处理屏幕变化可以参考：Android 屏幕旋转 处理 AsyncTask 和 ProgressDialog 的最佳方案
这里提一下：很多人觉得强制设置屏幕的方向就可以了，但是有一点，当你的应用被至于后台（例如用户点击了home），长时间没有返回的时候，你的应用也会被重新启动。比如上例：如果你把上面的例子你至于FragmentThree界面，然后处于后台状态，长时间后你会发现当你再次通过home打开时，上面FragmentThree与FragmentOne叠加在一起，这就是因为你的Activity重新启动，在原来的FragmentThree上又绘制了一个FragmentOne。
好了，下面看一段代码：
Activity:
```
package com.zhy.zhy_fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity

{
    private FragmentOne mFOne;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mFOne = new FragmentOne();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.add(R.id.id_content, mFOne, "ONE");
        tx.commit();

    }

}

```
Fragment
```
package com.zhy.zhy_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentOne extends Fragment
{
    private static final String TAG = "FragmentOne";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        Log.e(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate");
    }

    @Override
    public void onDestroyView()
    {
        // TODO Auto-generated method stub
        super.onDestroyView();
        Log.e(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

}

```
很简单的代码，当你运行之后，不断的旋转屏幕，你会发现每旋转一次屏幕，屏幕上就多了一个FragmentOne的实例，并且后台log会打印出许多套生命周期的回调。
类似
```
07-20 08:18:46.651: E/FragmentOne(1633): onCreate  
07-20 08:18:46.651: E/FragmentOne(1633): onCreate  
07-20 08:18:46.651: E/FragmentOne(1633): onCreate  
07-20 08:18:46.681: E/FragmentOne(1633): onCreateView  
07-20 08:18:46.831: E/FragmentOne(1633): onCreateView  
07-20 08:18:46.891: E/FragmentOne(1633): onCreateView 
```
这是为什么呢，因为当屏幕发生旋转，Activity发生重新启动，默认的Activity中的Fragment也会跟着Activity重新创建；这样造成当旋转的时候，本身存在的Fragment会重新启动，然后当执行Activity的onCreate时，又会再次实例化一个新的Fragment，这就是出现的原因。
那么如何解决呢：
其实通过检查onCreate的参数Bundle savedInstanceState就可以判断，当前是否发生Activity的重新创建：
默认的savedInstanceState会存储一些数据，包括Fragment的实例：通过打印可以看出：
```

07-20 08:23:12.952: E/FragmentOne(1782): Bundle[{android:fragments=android.app.FragmentManagerState@40d0b7b8, android:viewHierarchyState=Bundle[{android:focusedViewId=2131230721, android:views=android.util.SparseArray@40d0af68}]}]
```
所以，我们简单改一下代码，只有在savedInstanceState==null时，才进行创建Fragment实例：
```
package com.zhy.zhy_fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class MainActivity extends Activity

{
    private static final String TAG = "FragmentOne";
    private FragmentOne mFOne;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Log.e(TAG, savedInstanceState+"");
        
        if(savedInstanceState == null)
        {
            mFOne = new FragmentOne();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction tx = fm.beginTransaction();
            tx.add(R.id.id_content, mFOne, "ONE");
            tx.commit();
        }
        
        

    }

}

```
现在无论进行多次旋转都只会有一个Fragment实例在Activity中。
现在还存在一个问题，就是重新绘制时，Fragment发生重建，原本的数据如何保持？
其实和Activity类似，Fragment也有onSaveInstanceState的方法，在此方法中进行保存数据，然后在onCreate或者onCreateView或者onActivityCreated进行恢复都可以。
由于篇幅原因，就不贴测试代码了。
####5、Fragmeny与ActionBar和MenuItem集成
Fragment可以添加自己的MenuItem到Activity的ActionBar或者可选菜单中。
</br>**a、在Fragment的onCreate中调用 setHasOptionsMenu(true);**</br>
</br>**b、然后在Fragment子类中实现onCreateOptionsMenu**</br>
</br>**c、如果希望在Fragment中处理MenuItem的点击，也可以实现onOptionsItemSelected；当然了Activity也可以直接处理该MenuItem的点击事件。**</br>
代码：
```
package com.zhy.zhy_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FragmentOne extends Fragment
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case R.id.id_menu_fra_test:
            Toast.makeText(getActivity(), "FragmentMenuItem1", Toast.LENGTH_SHORT).show();
            break;
        }
        return true;
    }

}

```
Activity
```
package com.zhy.zhy_fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity

{
    private static final String TAG = "FragmentOne";
    private FragmentOne mFOne;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Log.e(TAG, savedInstanceState + "");

        if (savedInstanceState == null)
        {
            mFOne = new FragmentOne();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction tx = fm.beginTransaction();
            tx.add(R.id.id_content, mFOne, "ONE");
            tx.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case R.id.action_settings:
            Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
            return true;
        default:
            //如果希望Fragment自己处理MenuItem点击事件，一定不要忘了调用super.xxx
            return super.onOptionsItemSelected(item);
        }
    }

}

```
效果图：
</br>![](http://img.blog.csdn.net/20140720164313421)</br>
好了，可以很好的看到，Fragment可以添加MenuItem，也可以自己处理点击~~~
####6、没有布局的Fragment的作用
没有布局文件Fragment实际上是为了保存，当Activity重启时，保存大量数据准备的
请参考博客：Android 屏幕旋转 处理 AsyncTask 和 ProgressDialog 的最佳方案

####7、使用Fragment创建对话框
这是Google推荐的方式，我也单独写过博客介绍，请参考：Android 官方推荐 : <a href="http://blog.csdn.net/lmj623565791/article/details/42628537">DialogFragment 创建对话框

好了，终于把Fragment相关的联系到一起了，上述基本包含了Fragment所有的用法~~~相信大家如果能够看完，一定有不少的收获~~~

有任何问题，欢迎留言~~~
两篇结束，相信你对Fragment已经有了一定的了解，那么在项目中的最佳实践是什么呢？请移步：Android Fragment 你应该知道的一切