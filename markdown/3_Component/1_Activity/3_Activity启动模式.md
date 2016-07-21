Activity启动模式

Activity的启动模式有四种.
>Standard
>SingleTop
>SingleInstance
>SingleTask

我们可以在AndroidManifest.xml里面activity节点Android:launchMode属性为以上四种之一即可.默认为Standard

###1.Standard
这个没什么好说的。每次启动一个新是实例
![官方的一张图](http://androidguide.oss-cn-shanghai.aliyuncs.com/activity/standard.x-png)

###2.SingleTop
  启动一个Activity.如果在栈顶.不创建新实例.反之则创建
![官方的一张图](http://androidguide.oss-cn-shanghai.aliyuncs.com/activity/singleTop.x-png)

###3.SingleInstance
被标记为singleInstance启动模式的Activity，在启动的时候，会开启一个新的BackStack，这个BackStack里只有一个Activity的实例存在，并且把这个BackStack获得焦点。这是一种很极端的模式，它会导致整个设备的操作系统里，只会存在一个这个Activity示例，无论是从何处被启动的。
![官方的一张图](http://androidguide.oss-cn-shanghai.aliyuncs.com/activity/singleInstance.x-png)

###4.SingleTask
　开启一个Activity的时候，检查BackStack里面是否有这个Activity的实例存在，如果存在的话，Pop这个Activity上所有的其他Activity。   
![官方的一张图](http://androidguide.oss-cn-shanghai.aliyuncs.com/activity/singleTask.x-png)
