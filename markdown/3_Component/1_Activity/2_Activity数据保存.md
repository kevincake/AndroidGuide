Android中的activity通过onSaveInstanceState()方法保存activity的状态，在onCreate或者onRestoreInstanceState方法恢复状态，如果是通过onRestart方法前端运行的话，只会执行onSaveinstanceState方法。直接看代码：
```
package com.hebaijun.savestate;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SaveStateActivity extends Activity {
    public static final String TAG = "SaveStateActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState!=null) {
            Log.v(TAG, savedInstanceState.getString("data"));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.v(TAG, "onCreate");
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.v(TAG, savedInstanceState.getString("data"));
        super.onRestoreInstanceState(savedInstanceState);
        Log.v(TAG, "onRestoreInstanceState");
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("data", "example");
        super.onSaveInstanceState(outState);
        Log.v(TAG, "onSaveInstanceState");
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TAG, "onRestart");
    }
}
```

main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical" >

    <EditText
        android:id="@+id/editText1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" >
    </EditText>

</LinearLayout>
```

1. 运行程序后我们按HOME键，然后我们重新进入程序，Log如图：
![](http://my.csdn.net/uploads/201205/14/1336983822_5983.png)
我们可以看出onSaveInstanceState方法在onPause方法前执行。
程序恢复执行的时候调用了onRestart方法，并没有调用 onRestoreInstanceState方法。

2. 运行程序后按BACK键，Log如图：
![](http://my.csdn.net/uploads/201205/14/1336983859_8101.png)
    1. 程序并没有调用onSaveInstanceState方法，因为按BACK键表明用户已经明确退出，所以不会执行保存状态的操作。就是说 onSaveInstanceState能不能被执行到是不确定的，要保存永久的数据话还是在onPause方法中用其他方式保存。
    2. 如果在edittext中输入字符的话，重新执行也不会重新出现。

3. 运行程序，在EditText中输入字符串，切换横竖屏。Log如图：
![](http://my.csdn.net/uploads/201205/14/1336983886_5476.png)
    1. 运行界面没有截图，但是可以看到，EditText中的字符有保存下来，说明UI是自动保存和恢复的。前提是这个View要赋给id: android:id="@+id/editText1"
    2. onRestoreInstanceState方法在onStart后面onResume前面执行。
    3. 可以附加其他你想保存的状态，在 onCreate或者onRestoreInstanceState方法中都可以恢复。
作者：lixiang0522  url:<a href="http://blog.csdn.net/lixiang0522/article/details/7565401">http://blog.csdn.net/lixiang0522/article/details/7565401</a>
