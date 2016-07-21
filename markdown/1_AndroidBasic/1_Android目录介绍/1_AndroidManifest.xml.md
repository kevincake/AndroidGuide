作用:
>1.注册四大组件
>2.权限
>3.meta-data元数据
>4.配置包名
>5.配置libary

1.注册四大组件
Activity
```
<activity android:name=".MainActivity"/>
```
BroadReceiver
```
 <receiver android:name="MainReceiver"/>
```
service
```
<service android:name="MainService"/>
```
ContentProvider
```
    <provider
            android:authorities="uri"
            android:name="MainProvider"/>
```


2.权限.这里简单罗列几个常用的
```
 <!--网络状态-->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <!--WIFI状态-->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <!--允许联网-->
    <uses-permission android:name="android.permission.INTERNET"/>
    <!--读取电话的状态-->
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <!--写SD卡-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <!--读SD卡-->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```
3.meta-data元数据
```
通常都是放AppKey和Scecret放在里面
放一些渠道Id统计使用
```
4.配置包名
```
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="cn.ifreedomer.com.androidguide">
```
5.配置libary
```
<uses-library
  android:name="string"
  android:required=["true" | "false"] />
```
