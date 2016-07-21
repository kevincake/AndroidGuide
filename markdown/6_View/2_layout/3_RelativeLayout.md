RelativeLayout
RelativeLayout用到的一些重要的属性：

###第一类:属性值为true或false
android:layout_centerHrizontal 水平居中
android:layout_centerVertical 垂直居中
android:layout_centerInparent 相对于父元素完全居中
android:layout_alignParentBottom 贴紧父元素的下边缘
android:layout_alignParentLeft 贴紧父元素的左边缘
android:layout_alignParentRight 贴紧父元素的右边缘
android:layout_alignParentTop 贴紧父元素的上边缘
android:layout_alignWithParentIfMissing 如果对应的兄弟元素找不到的话就以父元素做参照物

###第二类：属性值必须为id的引用名“@id/id-name”
android:layout_below 在某元素的下方
android:layout_above 在某元素的的上方
android:layout_toLeftOf 在某元素的左边
android:layout_toRightOf 在某元素的右边

android:layout_alignTop 本元素的上边缘和某元素的的上边缘对齐
android:layout_alignLeft 本元素的左边缘和某元素的的左边缘对齐
android:layout_alignBottom 本元素的下边缘和某元素的的下边缘对齐
android:layout_alignRight 本元素的右边缘和某元素的的右边缘对齐

###第三类：属性值为具体的像素值，如30dip，40px
android:layout_marginBottom 离某元素底边缘的距离
android:layout_marginLeft 离某元素左边缘的距离
android:layout_marginRight 离某元素右边缘的距离
android:layout_marginTop 离某元素上边缘的距离

示例代码
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".RelativeLayoutActivity">
    <!-- alignParent  相对父控件的位置属性 -->
    <RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:id="@+id/id1">
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="#23acaa"
            android:text="按钮1"/>
    </RelativeLayout>
    <!-- alignParent  相对有id的控件的位置属性 -->
    <RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_toRightOf="@+id/id1"
        android:layout_above="@+id/id1"
        android:id="@+id/id2">
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="#23acaa"
            android:text="按钮2"/>
    </RelativeLayout>

    <!-- alignParent  margin定义间距 -->
    <RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_toRightOf="@+id/id2"
        android:layout_marginLeft="20dp"
        android:layout_marginBottom="20dp"
        android:layout_above="@+id/id2">
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="#23acaa"
            android:text="按钮3"/>
    </RelativeLayout>

    <!-- alignParent  center定位居中 -->
    <RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_centerVertical="true"
        android:id="@+id/id4">
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="#23acaa"
            android:text="按钮4"/>
    </RelativeLayout>
</RelativeLayout>
```
![](http://images2015.cnblogs.com/blog/602164/201604/602164-20160418202504226-1004165563.png)