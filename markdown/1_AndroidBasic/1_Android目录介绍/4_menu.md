Menu放menu文件.
####1、分类:Android系统中menu的分为下面三类：
- OptionMenu和Toolbar
- ContextMenu和ActionMode：ContextMenu是一个浮动的窗口形式展现一个选项列表</br>
- Popup Menu：PopupMenu是固定在View上的模态菜单，以弹出的方式显示(3.0hihou)

####2、定义:
- `<menu>`：定义一个Menu，是一个菜单资源文件的根节点，里面可以包含一个或者多个`<item>`和`<group>`元素。

- `<item>`：创建一个`MenuItem`，代表了菜单中一个选项。`<item>`元素除了常规的id、icon、title属性的支持，还有一个重要的属性：`android:showAsAction`，这个属性是起兼容性的，描述了在Android的高版本中，菜单项何时以何种方式加入到ActionBar中

- `<group>`：对菜单项进行分组，可以以组的形式操作菜单项。分组后的菜单显示效果并没有区别，唯
的区别在于可以针对菜单组进行操作，这样对于分类的菜单项，操作起来更方便，提供如下的操作：
>Menu.setGroupCheckable()：菜单组内的菜单是否都可选。</br>
>Menu.setGroupVisible()：是否隐藏菜单组的所有菜单。</br>
>Menu.setGroupEnabled()：菜单组的菜单是否有用。</br>

####3、OptionMenu
OptionMenu，选项菜单，单击手机上的菜单键(MENU)出现，必须设备具有菜单按钮才可以触发。因为屏幕的限制，最多只能展示六个菜单项，如果定义的菜单项超出了六个，其他的菜单项将被隐藏，第六个菜单将会显示“更多”，点击展开更多的菜单。虽说在Android3.0之后不再推荐使用选项菜单，但是如果使用了，在Android3.0之后的设备上，选项菜单项将被默认转移到ActionBar中，这个可以通过android:showAsAction属性控制。
>（1）重写Activity的onCreateOptionMenu(Menu menu)方法，当菜单第一次被加载时调用

>（2）调用Menu 的add( )方法添加菜单项(MenuItem)，同时可以调用MenuItem的setIcon()方法为菜单项设置图标（注：Android 3.0之后，即使添加了图标也不会显示）

>（3）重写Activity的OptionsItemSelected(MenuItem item)来响应菜单项(MenuItem)的点击事件

来看一下具体的代码实现：
```
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context="com.example.menutest.MainActivity" >

    <item
        android:id="@+id/start"
        android:orderInCategory="100"
        android:showAsAction="never"
        android:title="@string/start"/>
    
        <item
        android:id="@+id/over"
        android:orderInCategory="200"
        android:showAsAction="never"
        android:title="@string/over"/>

</menu>

```
Activity
```
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //重写onCreateOptionMenu(Menu menu)方法，当菜单第一次被加载时调用
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //填充选项菜单（读取XML文件、解析、加载到Menu组件上）
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //重写OptionsItemSelected(MenuItem item)来响应菜单项(MenuItem)的点击事件（根据id来区分是哪个item）
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
        case R.id.start:
            Toast.makeText(this, "开始游戏", Toast.LENGTH_SHORT).show();
            break;
        case R.id.over:
            Toast.makeText(this, "结束游戏", Toast.LENGTH_SHORT).show();
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
```
效果
![官方的一张图](http://images.cnitblog.com/blog/641601/201411/301625276689841.gif)
如果想让MenuItem变成ActionBar的形式，可以修改res/menu/main.xml中的android:showAsAction属性，它的属性值一共有下面几种：
![官方的一张图](http://images.cnitblog.com/blog/641601/201411/301625596062819.png)
其中，ifRoom表示：如果有空间，就显示出来。withText表示：只显示文本（如果配了图标的话）。如果将属性设置为always，效果如下：
![官方的一张图](http://images.cnitblog.com/blog/641601/201411/301626117628237.gif)
如果需要添加子菜单，可以修改menu.xml文件为如下所示：
```
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context="com.example.menutest.MainActivity" >

    <item
        android:id="@+id/start"
        android:orderInCategory="100"
        android:showAsAction="never"
        android:title="@string/start"/>
    <item
        android:id="@+id/over"
        android:orderInCategory="200"
        android:showAsAction="never"
        android:title="@string/over"/>
    
    <!-- 子菜单 -->
    <item
        android:id="@+id/setting"
        android:title="setting">
        <menu>
            <item
                android:id="@+id/setting1"
                android:orderInCategory="300"
                android:showAsAction="never"
                android:title="声音設置"/>
            <item
                android:id="@+id/setting2"
                android:orderInCategory="400"
                android:showAsAction="never"
                android:title="背景設置"/>
        </menu>
    </item>

</menu>
```
于是，子菜单的点击事件为：
```
public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
        case R.id.start:
            Toast.makeText(this, "开始游戏", Toast.LENGTH_SHORT).show();
            break;
        case R.id.over:
            Toast.makeText(this, "结束游戏", Toast.LENGTH_SHORT).show();
            break;
            
        case R.id.setting1:
            Toast.makeText(this, "声音設置", Toast.LENGTH_SHORT).show();
            break;
            
        case R.id.setting2:
            Toast.makeText(this, "背景設置", Toast.LENGTH_SHORT).show();
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }
```
运行效果如下：
![官方的一张图](http://images.cnitblog.com/blog/641601/201411/301629035435923.gif)
方式二:通过Java代码添加Menu选项
```
public class MainActivity extends Activity {

    
    private static final int START_ITEM = Menu.FIRST;  //Menu.FIRST的值就是1
    private static final int OVER_ITEM = Menu.FIRST+1;
    private static final int SET_ITEM1 = Menu.FIRST+2;
    private static final int SET_ITEM2 = Menu.FIRST+3;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //重写onCreateOptionMenu(Menu menu)方法，当菜单第一次被加载时调用
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //填充选项菜单（读取XML文件、解析、加载到Menu组件上）
       // getMenuInflater().inflate(R.menu.main, menu);
        
        //通过代码的方式来添加Menu
        //添加菜单项（组ID，菜单项ID，排序，标题）
        menu.add(0, START_ITEM, 100, "Start");
        menu.add(0, OVER_ITEM, 200, "Over");
        //添加子菜单
        SubMenu sub1 = menu.addSubMenu("setting");
        sub1.add(1, SET_ITEM1, 300, "声音设置");
        sub1.add(1, SET_ITEM2, 400, "背景设置");
        
        return true;
    }

    //重写OptionsItemSelected(MenuItem item)来响应菜单项(MenuItem)的点击事件（根据id来区分是哪个item）
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
        case START_ITEM:
            Toast.makeText(this, "开始游戏", Toast.LENGTH_SHORT).show();
            break;
        case OVER_ITEM:
            Toast.makeText(this, "结束游戏", Toast.LENGTH_SHORT).show();
            break;
            
        case SET_ITEM1:
            Toast.makeText(this, "声音設置", Toast.LENGTH_SHORT).show();
            break;
            
        case SET_ITEM2:
            Toast.makeText(this, "背景設置", Toast.LENGTH_SHORT).show();
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
```
运行程序，效果和上方gif图的效果是一样的。
####4、Context menu：上下文菜单
顾名思义 与上下文(环境)有关。操作时需要长时间按住某个item不放，就会弹出Context menu。效果如下：
![官方的一张图](http://images.cnitblog.com/blog/641601/201411/301632121681198.png)
![官方的一张图](http://images.cnitblog.com/blog/641601/201411/301632121681198.png)
创建上下文菜单的核心步骤:

>（1）覆盖Activity的onCreateContextMenu(Menu menu)方法，调用Menu的add()方法添加菜单项(MenuItem)

>（2）覆盖Activity的onContextItemSelected(MenuItem iitem)来响应事件

>（3）调用registerForContextMenu()方法来为视图注册上下文菜单。

代码步骤
重新建一个Android工程MenuTest02，步骤如下：
我们现在activity_main.xml中添加一个按钮button1，代码就不写了。然后继续：

（1）在res/menu/main.xml中定义菜单项。main.xml的代码如下：
```
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context="com.example.menutest02.MainActivity" >

    <item
        android:id="@+id/start"
        android:orderInCategory="100"
        android:showAsAction="never"
        android:title="@string/start"/>
    <item
        android:id="@+id/over"
        android:orderInCategory="200"
        android:showAsAction="never"
        android:title="@string/over"/>

</menu>
```
（2）MainActivity.java:
```
public class MainActivity extends Activity   {

    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button)findViewById(R.id.button1);
        //为按钮绑定上下文菜单（注意不是绑定监听器）
        registerForContextMenu(button1);
    }
    
    //创建上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        
        getMenuInflater().inflate(R.menu.main, menu);
    }
    
    //上下文菜单的触发事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.start:
            Toast.makeText(this, "开始···", Toast.LENGTH_SHORT).show();
            break;
            
        case R.id.over:
            Toast.makeText(this, "结束···", Toast.LENGTH_SHORT).show();
            break;

        default:
            break;
        }        
        
        return super.onContextItemSelected(item);
    }

}
```
核心代码是第22行：为按钮button1绑定上下文菜单。注意不是绑定监听器哦，不要一看到按钮就绑定监听器哈。

注：一个界面中只能有一个上下文菜单。

运行程序，长按button，效果如下：
![官方的一张图](http://images.cnitblog.com/blog/641601/201411/301634510122337.gif)
三、Popup menu：弹出式菜单

　PopupMenu，弹出菜单，一个模态形式展示的弹出风格的菜单，绑在在某个View上，一般出现在被绑定的View的下方（如果下方有空间）。

注意：弹出菜单是在API 11和更高版本上才有效的。

核心步骤：

>（1）通过PopupMenu的构造函数实例化一个PopupMenu对象，需要传递一个当前上下文对象以及绑定的View。

>（2）调用PopupMenu.setOnMenuItemClickListener()设置一个PopupMenu选项的选中事件。

>（3）使用MenuInflater.inflate()方法加载一个XML文件到PopupMenu.getMenu()中。

>（4）在需要的时候调用PopupMenu.show()方法显示。

现在通过代码来实现。重新新建一个工程文件MenuTest03。步骤如下：

先在布局文件activity_main.xml中加一个按钮，代码略。

（1）在res/menu/main.xml中定义菜单项。main.xml的代码如下：
```
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context="com.example.menutest03.MainActivity" >

    <item
        android:id="@+id/copy"
        android:orderInCategory="100"
        android:title="复制"/>
    
    <item
        android:id="@+id/delete"
        android:orderInCategory="100"
        android:title="粘贴"/>

</menu>
```
(2)MainActivity.java:
```
public class MainActivity extends Activity implements OnClickListener,OnMenuItemClickListener{

    
    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
    }

    //点击按钮后，加载弹出式菜单
    @Override
    public void onClick(View v) {
        //创建弹出式菜单对象（最低版本11）
        PopupMenu popup = new PopupMenu(this, v);//第二个参数是绑定的那个view
        //获取菜单填充器
        MenuInflater inflater = popup.getMenuInflater();
        //填充菜单
        inflater.inflate(R.menu.main, popup.getMenu());
        //绑定菜单项的点击事件
        popup.setOnMenuItemClickListener(this);
        popup.show(); //这一行代码不要忘记了
        
    }

    //弹出式菜单的单击事件处理
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
        case R.id.copy:
            Toast.makeText(this, "复制···", Toast.LENGTH_SHORT).show();
            break;

        case R.id.delete:
            Toast.makeText(this, "删除···", Toast.LENGTH_SHORT).show();
            break;
        default:
            break;
        }
        return false;
    }
    
}
```
注意14行代码绑定了两个监听器：OnClickListener和OnMenuItemClickListener。 在绑定OnMenuItemClickListener监听器时，选的是下面这个：
![官方的一张图](http://images.cnitblog.com/blog/641601/201411/301638005746925.png)
如果是在API 14及以上版本，32行34行可以合并为：popup.inflate(R.menu.main, popup.getMenu());

注意第37行代码不要忘记show。

运行程序，单击button，效果如下
![官方的一张图](http://images.cnitblog.com/blog/641601/201411/301638179023198.gif)
最后，附上整个文章的代码：

【工程文件】

链接：<url>http://pan.baidu.com/s/1eQ6EnUq </url>

密码：438o
原文:http://www.cnblogs.com/smyhvae/p/4133292.html (更多Android相关知识，大家都可以去看`生命壹号`的博客)
