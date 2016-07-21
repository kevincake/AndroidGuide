String.xml一般用来存字的内容的.不过国际化的时候通常会用到多份不同的string-xx.xml.下面我来介绍string.xml的两个主要知识点
###一、.string.xml里面的转义符号怎么使用
我们在string.xml中用得最多的转义字符就是”\n”换行符了，其实还有很多可以在string.xml中使用的转义字符，下面是ANSII码表：
![官方的一张图](http://img.my.csdn.net/uploads/201412/06/1417841399_7128.jpg)
 其中下列字符是可以使用在string.xml文件中的(没有亲自测试的字符就不列出来了，使用的时候可以自己尝试，markdown也支持转义字符，只能截图了。。。)：
![官方的一张图](http://img.my.csdn.net/uploads/201412/06/1417842137_3894.jpg)
 比如我在string.xml文件中使用了空格转义字符
![官方的一张图](http://img.my.csdn.net/uploads/201412/06/1417842396_8911.jpg)
效果如下:
![](http://img.my.csdn.net/uploads/201412/06/1417842396_7108.jpg)

以上内容出自:
<a>http://zmywly8866.github.io/2014/12/06/android-use-ansii-in-string-xml.html</a>


###二、string.xml里面的国际化
internationalization （国际化）简称i18n,因为在i和n之间还有18个字符，<a>localization</a>（本地化 ），简称L10n。
一般用语言_地区的形式表示一种语言，如  zh_CN, zh_TW.
各国语言缩写 <url> http://www.loc.gov/standards/iso639-2/php/code_list.php<url>

国家和地区简写 http://www.iso.org/iso/en/prods-services/iso3166ma/02iso-3166-code-lists/list-en1.html

国家_地区语言速查表：http://www.cnblogs.com/Mien/archive/2008/08/22/1273950.html
常见的有：
zh_cn: 简体中文
zh_hk: 繁体中文(中国香港)  
zh_tw: 繁体中文(中国台湾地区)
en-hk: 英语(香港)
en_us: 英语(美国)
en_gb: 英语(英国)
en_ww: 英语(全球)
ja_jp: 日语(日本)
ko_kr: 韩文(韩国)

####(1)创建区域设置目录及字符串文件:
 为支持多国语言，在res/中创建一个额外的values目录以连字符和ISO国家代码结尾命名，比如values-es/是为语言代码为"es"的区域设置的简单的资源文件的目录。Android会在运行时根据设备的区域设置，加载相应的资源。
详见Providing Alternative Resources。
若决定支持某种语言，则需要创建资源子目录和字符串资源文件，例如:
```
YouProject/
        res/
        values/
        strings.xml
        values-es/
        strings.xml
        values-fr/
        strings.xml
```
####(2)添加不同区域语言的字符串值到相应的文件。
Android系统运行时会根据用户设备当前的区域设置，使用相应的字符串资源。
例如，下面列举了几个不同语言对应不同的字符串资源文件。
英语(默认区域语言)，/values/strings.xml:
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
<string name="title">My Application</string>
<string name="hello_world">Hello World!</string>
</resources>
```
法语，/values-fr/strings.xml:
使用字符资源
我们可以在源代码和其他XML文件中通过<string>元素的name属性来引用自己的字符串资源。
```
<TextView
    android:id="@+id/tv_title"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@string/title"
    android:textSize="16sp"/>
```
代码中使用string.xml的字
```
/ 从资源文件通过id获取你要的string资源
String tv_title= getResources().getString(R.string.title);
TextView textView = new TextView(this);
textView.setText(tv_title);
```
####(3)一键实现语言国际化
我们要装的插件是：<a>AndroidLocalizationer</a>>，从github上down后，集成到studio里面就好了，看图：
![官方的一张图](http://img.blog.csdn.net/20151116144636565?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
勾选你需要的国家
![官方的一张图](http://img.blog.csdn.net/20151116144902936?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)</br>
结果
![官方的一张图](http://img.blog.csdn.net/20151116145308549?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
打开日本的看
```
<resources>
   <string name="app_name">MyZhihuapp</string>
   <string name="hello_world">ハローワールド！</string>
   <string name="action_settings">設定</string>
   <string name="file_path">multidownload</string>
   <string name="image_data">imagedata を扱う</string>
</resources>
```
第二章节内容出自`Losileeya`大神的博客.欢迎访问
<a href="http://blog.csdn.net/u013278099/article/details/49865177">http://blog.csdn.net/u013278099/article/details/49865177</a>
