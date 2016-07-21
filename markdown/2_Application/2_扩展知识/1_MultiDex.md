实现MultiDex的方法有很多种.这里我就介绍一种我自己经常使用的吧
步骤
>1.配置app的build.gradle
>2.继承MultiDexApplication

###1.配置build.gradle
```
android{
     dexOptions {
        incremental = true;//使用增量模式构建
        preDexLibraries = false//true保留两个一样的库.false不保持
        javaMaxHeapSize "4g" // 设置java最大的堆size
    }
    //设个地方设置支持multiDex配置
    defaultConfig{
        multiDexEnabled true
    }
}
dependencies{
    //增加库支持
     compile 'com.android.support:multidex:1.0.0'
}
```
###2.继承MultiDexApplication
```
public class LingChatApplication extends MultiDexApplication {
      protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
```
总得来说不难.希望能帮到大家