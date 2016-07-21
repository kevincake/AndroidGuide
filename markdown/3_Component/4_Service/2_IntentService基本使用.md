一、IntentService简介 
IntentService是Service的子类，比普通的Service增加了额外的功能。先看Service本身存在两个问题：  
Service不会专门启动一条单独的进程，Service与它所在应用位于同一个进程中；  
Service也不是专门一条新线程，因此不应该在Service中直接处理耗时的任务；  
  
二、IntentService特征 
会创建独立的worker线程来处理所有的Intent请求；  
会创建独立的worker线程来处理onHandleIntent()方法实现的代码，无需处理多线程问题；  
所有请求处理完成后，IntentService会自动停止，无需调用stopSelf()方法停止Service；  
为Service的onBind()提供默认实现，返回null；  
为Service的onStartCommand提供默认实现，将请求Intent添加到队列中； 
 
三、使用步骤(详情参考Service项目) 
继承IntentService类，并重写onHandleIntent()方法即可； 
MainActivity.Java文件 

```
public class MainActivity extends Activity { 
 
    @Override 
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_main); 
    } 
 
    public void startService(View source) { 
        // 创建所需要启动的Service的Intent 
        Intent intent = new Intent(this, MyService.class); 
        startService(intent); 
    } 
 
    public void startIntentService(View source) { 
        // 创建需要启动的IntentService的Intent 
        Intent intent = new Intent(this, MyIntentService.class); 
        startService(intent); 
    } 
} 
```
MyIntentService.java文件 
```
public class MyIntentService extends IntentService { 
 
    public MyIntentService() { 
        super("MyIntentService"); 
    } 
 
    @Override 
    protected void onHandleIntent(Intent intent) { 
        // IntentService会使用单独的线程来执行该方法的代码 
        // 该方法内执行耗时任务，比如下载文件，此处只是让线程等待20秒 
        long endTime = System.currentTimeMillis() + 20 * 1000; 
        System.out.println("onStart"); 
        while (System.currentTimeMillis() < endTime) { 
            synchronized (this) { 
                try { 
                    wait(endTime - System.currentTimeMillis()); 
                } catch (InterruptedException e) { 
                    e.printStackTrace(); 
                } 
            } 
        } 
        System.out.println("----耗时任务执行完成---"); 
    } 
} 
```

MyService.java文件 
```
public class MyService extends Service { 
 
    @Override 
    public IBinder onBind(Intent arg0) { 
        return null; 
    } 
 
    @Override 
    public int onStartCommand(Intent intent, int flags, int startId) { 
        // 该方法内执行耗时任务可能导致ANR(Application Not Responding)异常 
        long endTime = System.currentTimeMillis() + 20 * 1000; 
        System.out.println("onStart"); 
        while (System.currentTimeMillis() < endTime) { 
            synchronized (this) { 
                try { 
                    wait(endTime - System.currentTimeMillis()); 
                } catch (InterruptedException e) { 
                    e.printStackTrace(); 
                } 
            } 
        } 
        System.out.println("----耗时任务执行完成---"); 
        return START_STICKY; 
    } 
} 
```
运行上述代码，启动MyIntentService的会使用单独的worker线程，因此不会阻塞前台的UI线程；而MyService会。