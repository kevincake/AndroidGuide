FrameLayout
昨天学习了LinearLayout，今天来学习FrameLayout布局，其实FrameLayout布局就是在屏幕上开辟一个区域来填充所有的组件，但是所有的组件都是从左上角开始显示（默认显示位置），而且都是层叠显示的，也就是说后面放的叠在前一个上面，具体效果等下看例子，这里同样先看下这个布局的定义（http://developer.android.com/reference/android/widget/FrameLayout.html）：
![](http://img.blog.csdn.net/20130813164113500)
和昨天LinearLayout结构一样，下面来写今天的例子。
效果如下：
![](http://img.blog.csdn.net/20130813165001234)</br>
main.xml：
```
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/FrameLayout1"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
  >

    <ImageView
        android:id="@+id/img"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="@drawable/img_2" />

    <EditText
        android:id="@+id/edt"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="我在button的下面，看见了吗">
    </EditText>

    <Button
        android:id="@+id/but"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="我在最上面" />

</FrameLayout>

```
上面程序定义了三个组件：ImageView、EditText和Button，可以看见在FrameLayout的作用下，组件起始位置都在左上角，最先定义的组件就在最小面，所有布局中（FrameLayout）的组件均层叠显示，这就是FrameLayout的效果。

刚才在看FrameLayout的定义时说过它的定义和LinearLayout一样，所以FrameLayout也可以在Java文件中定义，下面就通过java代码来实现上面一样的效果。
MianActivity.java：
```
package com.example.framelayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      setContentView(R.layout.activity_main);
        //新建FrameLayout布局
        FrameLayout frameLayout = new FrameLayout(this);
        //定义布局管理器高和宽
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //三个view的高宽都是wrap_content
        FrameLayout.LayoutParams viewParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //新建自己需要的三个组件
        ImageView img = new ImageView(this);
        EditText edt = new EditText(this);
        Button but = new Button(this);
        //设置和xml文件一样属性
        img.setBackgroundResource(R.drawable.img_2);
        edt.setText("我在button的下面，看见了吗");
        but.setText("我在最上面");
        //将组件加入frameLayout中,按xml中的顺序加入ImageView->EditText->Button
        frameLayout.addView(img, viewParams);
        frameLayout.addView(edt, viewParams);
        frameLayout.addView(but, viewParams);
        //显示布局
        super.setContentView(frameLayout,params);
    }
}

```
运行效果和前面用xml定义的效果是一样的，今天就说到这里了。