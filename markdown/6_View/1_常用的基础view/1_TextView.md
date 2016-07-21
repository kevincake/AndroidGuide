TextView的使用
###1.常用的方法
```
setText();                          //设置文本内容，同xml中的android:text

　　setTextSize();                    //设置文本字体大小，同xml中的android:textSize

　　setTextColor();                   //设置文本颜色，同xml中的android:textColor

　　setBackgroundColor();         //设置背景颜色，同xml中的android:background

　　此外，还可以在xml中设置一些TextView的属性，如下：

　　android:autoLink                 //设置是否显示为可点击的链接。可选值(none/web/email/phone/map/all)

　　android:drawableBottom      //在text的下方输出一个drawable(图片)

　　android:drawableLeft           //在text的左边输出一个drawable(图片)

　　android:drawableRight         //在text的右边输出一个drawable(图片)

　　android:drawableTop           //在text的正上方输出一个drawable(图片)

　　android:drawablePadding     //设置text与drawable(图片)的间隔，与drawableLeft、drawableRight、drawableTop、drawableBottom一起使用，可设置为负数，单独使用没有效果

　　android:ellipsize                 //设置当文字过长时，该控件该如何显示。可设置如下属性值："start"省略号显示在开头;"end”省略号显示在结尾;"middle"省略号显示在中间; "marquee" 以跑马灯的方式显示(动画横向移动)

　　android:gravity                   //设置文本位置，设置成"center"，文本将居中显示

　　android:linksClickable          //设置点击时是否链接，即使设置了autoLink

　　android:marqueeRepeatLimit     //在ellipsize设定为marquee时，设置重复滚动的次数，设置为marquee_forever时表示无限次。

　　android:lines                      //设置文本的行数，设置两行就显示两行，即使第二行没有数据

　　android:shadowRadius         //设置阴影的半径。设置为0.1就变成字体的颜色了，一般设置为3.0的效果比较好

　　android:shadowColor           //指定文本阴影的颜色，需要与shadowRadius一起使用

　　android:singleLine               //设置单行显示

　　android:textColorLink           //设置文字链接的颜色

　　android:textScaleX              //设置文字之间间隔，默认为1.0f

　　android:textStyle                //设置字形 bold(粗体) 0, italic(斜体) 1, bolditalic(又粗又斜) 2, 可以设置一个或多个，用“|”隔开

　　android:typeface                 //设置文本字体，必须是以下常量值之一：normal 0, sans 1, serif 2, monospace(等宽字体) 3
```

###2.TextView显示URL文本
　在TextView中预定义了一些类似HTML的标签，通过这些标签可以使TextView控件显示不同的颜色、大小和字体的文字。HTML的常用标签有以下一些：
```
　　<font>设置文本的字体、字体颜色、字体尺寸，如：<font size ="3" color = "red">This is Some Text!</font>

　　<big>设置大号字体效果

　　<small>设置小号字体效果

　　<i>设置斜体文本效果

　　<b>设置粗体文本效果

　　<a>通过使用href属性，创建指向另外一个文档的链接（超链接），如：<a href = http://www.baidu.com>百度</a>

　　<br>插入一个简单的换行符，注意：在HTML中，<br>标签没有结束标签

　　<p>自定在其前后创建一些空白

　　<img>向网页中嵌入一幅图像，该标签有两个必须的属性：src和alt，如：<img src = "/i/tulip.jpg" alt = "郁金香" />

　　
```
更多关于HTML中标签的使用，请参考：http://www.w3school.com.cn/tags/tag_font.asp

　　使用这些标签时，可以用Html.fromHtml()方法将这些标签的字符串转换成CharSequence对象，然后在TextView中通过setText()方法将CharSequence对象显示出来。


###3.TextView中显示图片

　　要在TextView中显示图片，首先需要将要显示的图片存放在res目录下的drawable-hdpi目录下，在R.java文件中则会自动生成图片资源的映射信息。然后就可以使用getField()方法通过图片资源id的变量名来获取图片资源了。具体实现方法如下：
```
获取图片资源id
    public int getResourceId(String name) {                          //获取福娃图片资源id
        try {            
            Field field = R.drawable.class.getField(name);                   //根据资源id的变量名获得Field对象，使用反射机制来实现            
            return Integer.parseInt(field.get(null).toString());          //取得并返回资源id的字段值（静态变量）
        }
        catch (Exception e) {
            //TODO
        }
        return 0;
    }
```
获得了图片资源id后，就可以通过Drawable drawable = getResources().getDrawable(getResourceId(source))方法来获得图片资源信息进行描画了。具体实现方法如下：
```
显示福娃图片
    public void showFuWaImage() {                                            //显示福娃图片        
        String html_fuwa = "<img src = 'beibei' />" +
                                                 "<img src = 'jingjing' />" +
                                                 "<a href = 'http://www.baidu.com'><img src = 'huanhuan' /></a>" +      //超链接“欢欢”图片到百度
                                                 "<img src = 'yingying' />" + 
                                                 "<img src = 'nini' />";    
        
        CharSequence charSequence_fuwa = Html.fromHtml(html_fuwa, new ImageGetter() {              //将图片加载到字符序列中
               public Drawable getDrawable (String source) {                 
                   Drawable drawable = getResources().getDrawable(getResourceId(source));                   //获得图片资源信息                   
                   if (source.equals("beibei") || source.equals("nini")) {                             //将图片“贝贝”和“妮妮”压缩到原图片尺寸的80%
                       drawable.setBounds(0, 0, drawable.getIntrinsicWidth()*4/5, drawable.getIntrinsicHeight()*4/5);
                   } else if (source.equals("huanhuan")) {                                                           //将图片“欢欢”放大到原图片尺寸的120%
                       drawable.setBounds(0, 0, drawable.getIntrinsicWidth()*5/4, drawable.getIntrinsicHeight()*5/4);
                   } else {                                                                                                                               //其他福娃图片保持原图片尺寸
                       drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                   }
                   return drawable;
               }             
        }, null);    
        
        textView_fuwaImage.setText(charSequence_fuwa);                   
        //将字符序列加载到textView控件中
        textView_fuwaImage.setMovementMethod(LinkMovementMethod.getInstance());
    }
```
　如上，便可以在TextView控件中显示图片了。其中，使用到了drawable.setBounds()方法进行图片的缩放操作，将图片“贝贝”和“妮妮”压缩到原图片尺寸的80%，将图片“欢欢”放大到原图片尺寸的120%。并通过`<a href = 'http://www.baidu.com'><img src = 'huanhuan' /></a>方法在图片“欢欢”上添加了超链接。最后，使用textView_fuwaImage.setMovementMethod(LinkMovementMethod.getInstance())`方法使点击时产生链接效果。

###4.TextView中显示跑马灯效果
####要在TextView控件中显示跑马灯效果，需要进行以下几项必须的设置：
```
　　android:singleLine="true"                                       
//设置显示的行数
　　android:ellipsize="marquee"                                   
//设置以跑马灯形式显示
　　android:marqueeRepeatLimit="marquee_forever"       
//设置跑马灯显示的次数
　　android:focusable="true"                                       
 //设置聚焦
　　android:focusableInTouchMode="true"                     
 //
　　android:linksClickable="true"                                   //
```
在跑马灯中设置了超链接时，设置了此属性才能产生链接效果
###5.TextView应用实例

　　本实例中，在MainActivity.java中定义了4个TextView控件：textView_marquee，textView_fuwaImage，textView_fuwaText，textView_showMyActivity，分别用来显示跑马灯，福娃图片，描述福娃的一段文字，以及一个实现跳转到MyActivity页面的标签。

MainActivity页面的实现效果如图1所示。
![](http://pic002.cnblogs.com/images/2012/430074/2012112723490054.jpg)</br>
具体的MainActivity.java源代码如下。
```
MainActivity.java源码
package com.example.android_textview;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;

public class MainActivity extends Activity {
    
    private TextView textView_marquee;                            //跑马灯
    private TextView textView_fuwaImage;                       //福娃图片
    private TextView textView_fuwaText;                           //描述福娃的文字
    private TextView textView_showMyActivity;             //MyActivity跳转标签
    
    public int getResourceId(String name) {                          //获取福娃图片资源id
        try {            
            Field field = R.drawable.class.getField(name);                   //根据资源id的变量名获得Field对象，使用反射机制来实现            
            return Integer.parseInt(field.get(null).toString());          //取得并返回资源id的字段值（静态变量）
        }
        catch (Exception e) {
            //TODO
        }
        return 0;
    }

    public void showTextViewMarquee() {                               //显示跑马灯
        String html_marquee = 
                "万众瞩目的北京奥运会<a href = ‘http://www.baidu.com’>吉祥物</a>" +              //超链接“吉祥物”字段到百度
                "于北京时间11日20:18正式揭晓，" +
                "奥运吉祥物福娃：形象为鱼、熊猫、奥运圣火、藏羚羊、燕子，" +
                "名字是贝贝、晶晶、欢欢、迎迎、妮妮，即北京欢迎你";
        CharSequence charSequence_marquee = Html.fromHtml(html_marquee);
        textView_marquee.setText(charSequence_marquee);
        textView_marquee.setMovementMethod(LinkMovementMethod.getInstance());    //点击时产生超链接效果
    }
    
    public void showFuWaImage() {                                            //显示福娃图片        
        String html_fuwa = "<img src = 'beibei' />" +
                                                 "<img src = 'jingjing' />" +
                                                 "<a href = 'http://www.baidu.com'><img src = 'huanhuan' /></a>" +      //超链接“欢欢”图片到百度
                                                 "<img src = 'yingying' />" + 
                                                 "<img src = 'nini' />";    
        
        CharSequence charSequence_fuwa = Html.fromHtml(html_fuwa, new ImageGetter() {              //将图片加载到字符序列中
               public Drawable getDrawable (String source) {                 
                   Drawable drawable = getResources().getDrawable(getResourceId(source));                   //获得图片资源信息                   
                   if (source.equals("beibei") || source.equals("nini")) {                             //将图片“贝贝”和“妮妮”压缩到原图片尺寸的80%
                       drawable.setBounds(0, 0, drawable.getIntrinsicWidth()*4/5, drawable.getIntrinsicHeight()*4/5);
                   } else if (source.equals("huanhuan")) {                                                           //将图片“欢欢”放大到原图片尺寸的120%
                       drawable.setBounds(0, 0, drawable.getIntrinsicWidth()*5/4, drawable.getIntrinsicHeight()*5/4);
                   } else {                                                                                                                               //其他福娃图片保持原图片尺寸
                       drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                   }
                   return drawable;
               }             
        }, null);    
        
        textView_fuwaImage.setText(charSequence_fuwa);                   //将字符序列加载到textView控件中
        textView_fuwaImage.setMovementMethod(LinkMovementMethod.getInstance());
    }
    
    public void showFuWaText() {                                                                    //显示描述福娃的文字
        textView_fuwaText.setTextColor(Color.BLACK);
        textView_fuwaText.setBackgroundColor(Color.WHITE);
        textView_fuwaText.setTextSize(15);
        String string_fuwaText = 
                "         “福娃”是五个拟人化的娃娃，他们的原型和头饰蕴含着与海洋、森林、火、大地和天空的联系，" +
                "应用了中国传统艺术的表现方式，展现了灿烂的中国文化的博大精深。" +
                "北京奥运会吉祥物的每个娃娃都代表着一个美好的祝愿：" +
                "贝贝象征繁荣、晶晶象征欢乐、欢欢象征激情、迎迎象征健康、妮妮象征好运。" +
                "五个福娃分别叫“贝贝”、“晶晶”、“欢欢”、“迎迎”、“妮妮”。各取它们名字中的一个字有次序的组成了谐音“北京欢迎你”。";
        textView_fuwaText.setText(string_fuwaText);
    }
    
    public void showMyActivityPage() {                                                        //显示MyActivity页面
        String text_showMyActivity = "跳转到MyActivity页面......";
        SpannableString spannnableString = new SpannableString(text_showMyActivity);        //用来拆分字符串
        spannnableString.setSpan(new ClickableSpan() {                                             //设置点击时的触发范围为整个字符串
            public void onClick(View widget) {
                Intent intent = new Intent(MainActivity.this,MyActivity.class);         //从MainActivity页面跳转到MyActivity页面
                startActivity(intent);
            }
        }, 0, text_showMyActivity.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_showMyActivity.setText(spannnableString);
        textView_showMyActivity.setMovementMethod(LinkMovementMethod.getInstance());
    }
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView_marquee = (TextView)this.findViewById(R.id.textview_marquee);
        textView_fuwaImage = (TextView)this.findViewById(R.id.textview_fuwaImage);
        textView_fuwaText = (TextView)this.findViewById(R.id.textview_fuwaText);
        textView_showMyActivity = (TextView)this.findViewById(R.id.textview_showMyActivity);
              
        showTextViewMarquee();                       //显示跑马灯
        showFuWaImage();                                     //显示福娃图片
        showFuWaText();                                         //显示描述福娃的文字
        showMyActivityPage();                             //显示MyActivity页面
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
   
}
```
与MainActivity.java相对应的activity_main.xml文件如下。
```
activity_main.xml源码 
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >
 
    <!-- 跑马灯 -->
    <TextView 
        android:id="@+id/textview_marquee"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:singleLine="true"
        android:ellipsize="marquee"
        android:marqueeRepeatLimit="marquee_forever"
        android:focusable="true"
        android:focusableInTouchMode="true"
        android:linksClickable="true"
        android:background="#FFFFFF"
        android:textColor="#000000"
        android:layout_margin="10dp"
        android:padding="10dp"
        android:textSize="20dp"        />
    
    <!-- 福娃图片 -->
    <TextView 
        android:id="@+id/textview_fuwaImage"
        android:layout_marginLeft="30sp"
        android:layout_marginTop="10sp"
        android:layout_marginBottom="10sp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#FFFFFF"   />
    
    <!-- 描述福娃的文字 -->
    <TextView 
        android:id="@+id/textview_fuwaText"
        android:layout_margin="10dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"   />
    
    <!-- MyActivity页面 -->
    <TextView 
        android:id="@+id/textview_showMyActivity"
        android:layout_marginLeft="30sp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="15sp"        />
            
</LinearLayout>
```
　通过点击MainActivity页面中的“跳转到MyActivity页面......”，可以实现跳转到MyActivity页面的功能。在MyActivity页面定义了2个TextView控件：textView_androidText和textView_myinfo，分别用来显示一段描述Android的文字，和使用Html标签来实现的带有超链接功能的个人信息显示。

　　MyActivity页面的实现效果如图2所示。
![](http://pic002.cnblogs.com/images/2012/430074/2012112723574671.jpg)</br>
