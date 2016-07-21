android中定义的dimension单位有以下这些 
- px: pixel(像素) 
  in:inches(英寸) 
  mm:millimeter(毫米) 
  pt:point(点) 
  dp:density密度 
  sp:刻度 
1.定义dimension可定义string一样 
```
<resources>  
  <dimen name="mysize_in_pixels">1px</dimen>  
  <dimen name="mysize_in_dp">1dp</dimen>  
  <dimen name="medium_size">100sp</dimen>  
  
</resources>  
```

2.在UI中调用dimen中的数值
```
this.getResources().getDimension(R.dimen.mysize_in_pixels); 
```

3.在layout中使用dimen
```
<TextView  android:text="@string/target_string"  
    android:id="@+id/text3"   
    android:layout_width="wrap_content"   
    android:textSize="@dimen/medium_size"   android:layout_height="wrap_content"/>
```

4.开发常用知识
- 字体用sp
- 其他都用dp方便适配

5.dp和px的换算
```
public static int dip2px(Context context, float dipValue){
final float scale = context.getResources().getDisplayMetrics().density;
return (int)(dipValue * scale + 0.5f);
}
public static int px2dip(Context context, float pxValue){
final float scale = context.getResources().getDisplayMetrics().density;
return (int)(pxValue / scale + 0.5f);
}
```

6.扩张知识，Androd适配
>我们项目想在的适配的时候，通常有一套UI图。按照UI图做的所有大小都放在dimen里面.后面用一个工具直接计算生成其他分辨率dimen
1920x1080 10dp
960x540 5dp

更多适配方案看hongyang<url>http://blog.csdn.net/lmj623565791/article/details/45460089</url>>



