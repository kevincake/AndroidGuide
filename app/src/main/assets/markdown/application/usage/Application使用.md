Application的使用应掌握一下几点

>  1.全局唯一
>  2. 生命周期
>  3.其他方法

###1.全局唯一
通常情况下.如果你没有配置application.系统会给你默认一个.AndroidManifest代码是这样
```
   <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
    </application>
```
如果你想在application里面做点什么。那么你需要配置自己的application。AndroidManifest代码是这样
```
   <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:name="GuideApplication"//说那么多.其实就是修改name
        android:theme="@style/AppTheme">
    </application>
```
因为application里面在应用中常用到.经常我们会用一个全局的单例.于是便有了一下代码
```
public class GuideApplication extends Application {
    private static GuideApplication mContext;

    public static GuideApplication getInstance() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

}
```
###2.生命周期
```
onCreate //Application创建时使用.
```

```
onTerminate //当终止应用程序对象时调用，不保证一定被调用
```

###3.其他方法
```
OnTrimMemory //能获取到app是否出于不可见状态.可以用来释放内存
```


