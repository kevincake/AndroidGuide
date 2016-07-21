IntentService其实就是一个Handler和thread的封装
```
public abstract class IntentService extends Service {
    private volatile Looper mServiceLooper;
    private volatile ServiceHandler mServiceHandler;
    private String mName;
    private boolean mRedelivery;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            onHandleIntent((Intent)msg.obj);
            stopSelf(msg.arg1);
        }
    }

 
     //调用service的构造函数
    public IntentService(String name) {
        super();
        mName = name;
    }

    //是否允许重启
    public void setIntentRedelivery(boolean enabled) {
        mRedelivery = enabled;
    }

    //创建一个thread.一个handler来处理线程逻辑.IntentService和service主要的逻辑其实就是Handler和thread
    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("IntentService[" + mName + "]");
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    //接受参数
    @Override
    public void onStart(Intent intent, int startId) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        mServiceHandler.sendMessage(msg);
    }

//START_NOT_STICKY 如果服务进程在它启动后(从onStartCommand()返回后)被kill掉, 并且没有新启动的intent传给他, 
//那么将服务移出启动状态并且不重新生成, 直到再次显式调用Context.startService().

//START_REDELIVER_INTENT 如果服务进程在它启动后(从onStartCommand()返回后)被kill掉, 那么它将会被重启, 
//并且最后传给它的intent会被重新传给它, 通过onStartCommand(Intent, int, int).
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent, startId);
        return mRedelivery ? START_REDELIVER_INTENT : START_NOT_STICKY;
    }
    //onDestroy退出looper
    @Override
    public void onDestroy() {
        mServiceLooper.quit();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //需要你实现的onHandleIntent
    protected abstract void onHandleIntent(Intent intent);
}
```