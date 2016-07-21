Activity生命周期


![官方的一张图](http://androidguide.oss-cn-shanghai.aliyuncs.com/activity/activity_life_recycle.png)


###图的意思就是.分三条主线
1.第一次创建
```
onCreate -> onStart -> onResume
```
2.销毁
```
onPause -> onStop -> onDestroy
```
3.返回操作 A -> B(A没有destroy)   B -> A
```
onRestart -> onResume
```

###以打拨号界面为例子
1.打开拨号界面
```
onCreate -> onStart -> onResume
```
2.突然来了一个电话
```
onPause -> onStop
```
3.挂掉电话
```
onRestart -> onResume
```
4.退出拨号.
```
onPause -> onStop -> onDestroy
```
