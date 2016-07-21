OKHttp使用简介
现在Android网络方面的第三方库很多，volley，Retrofit，OKHttp等，各有各自的特点，这边博客就来简单介绍下如何使用OKHttp。

###梗概

OKHttp是一款高效的HTTP客户端，支持连接同一地址的链接共享同一个socket，通过连接池来减小响应延迟，还有透明的GZIP压缩，请求缓存等优势 OKHttp官网

###配置环境

支持Android 2.3及其以上版本，要求Java JDK1.7以上

###jar包引入

可以通过下载jar包直接导入工程地址如下下载地址 
或者通过构建的方式导入 
MAVEN：
```
<dependency>
  <groupId>com.squareup.okhttp</groupId>
  <artifactId>okhttp</artifactId>
  <version>2.4.0</version>
</dependency>
```
GRADLE
```
compile 'com.squareup.okhttp:okhttp:2.4.0'
```
###用法

在向网络发起请求的时候，我们最常用的就是GET和POST，下面就来看看如何使用 
####1. GET 
在OKHttp，每次网络请求就是一个Request，我们在Request里填写我们需要的url，header等其他参数，再通过Request构造出Call，Call内部去请求参数，得到回复，并将结果告诉调用者。
```
package com.jackchan.test.okhttptest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;


public class TestActivity extends ActionBarActivity {

    private final static String TAG = "TestActivity";

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void execute() throws Exception {
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            System.out.println(response.code());
            System.out.println(response.body().string());
        }
    }
}
```
我们通过Request.Builder传入url，然后直接execute执行得到Response，通过Response可以得到code,message等信息。

这个是通过同步的方式去操作网络请求，而android本身是不允许在UI线程做网络请求操作的，因此我们需要自己开启一个线程。

当然，OKHttp也支持异步线程并且有回调返回，有了上面同步的基础，异步只要稍加改动即可
```
private void enqueue(){
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                //NOT UI Thread
                if(response.isSuccessful()){
                    System.out.println(response.code());
                    System.out.println(response.body().string());
                }
            }
        });
    }
```
就是在同步的基础上讲execute改成enqueue，并且传入回调接口，但接口回调回来的代码是在非UI线程的，因此如果有更新UI的操作记得用Handler或者其他方式。

####2、POST

说完GET该介绍些如何使用POST，POST情况下我们一般需要传入参数，甚至一些header，传入参数或者header 
比如传入header 
```
Request request = new Request.Builder() 
.url("https://api.github.com/repos/square/okhttp/issues") 
.header("User-Agent", "OkHttp Headers.java") 
.addHeader("Accept", "application/json; q=0.5") 
.addHeader("Accept", "application/vnd.github.v3+json") 
.build(); 
```
传入POST参数
```
RequestBody formBody = new FormEncodingBuilder()
    .add("platform", "android")
    .add("name", "bug")
    .add("subject", "XXXXXXXXXXXXXXX")
    .build();

    Request request = new Request.Builder()
      .url(url)
      .post(body)
      .build();
```
可以看出来，传入header或者post参数都是传到Request里面，因此最后的调用方式也和GET方式一样
```
Response response = client.newCall(request).execute();
    if (response.isSuccessful()) {
        return response.body().string();
    } else {
        throw new IOException("Unexpected code " + response);
    }
```
这个代码是同步网络请求，异步就改成enqueue就行了。
###请求缓存

在网络请求中，缓存技术是一项应用比较广泛的技术，需要对请求过的网络资源进行缓存，而okhttp也支持这一技术，也使用十分方便，前文涨经常出现的OkHttpclient这个时候就要派送用场了。看下面代码
```
package com.jackchan.test.okhttptest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;


public class TestActivity extends ActionBarActivity {

    private final static String TAG = "TestActivity";

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        File sdcache = getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        client.setCache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void execute() throws Exception {
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();

        Response response1 = client.newCall(request).execute();
        if (!response1.isSuccessful()) throw new IOException("Unexpected code " + response1);

        String response1Body = response1.body().string();
        System.out.println("Response 1 response:          " + response1);
        System.out.println("Response 1 cache response:    " + response1.cacheResponse());
        System.out.println("Response 1 network response:  " + response1.networkResponse());

        Response response2 = client.newCall(request).execute();
        if (!response2.isSuccessful()) throw new IOException("Unexpected code " + response2);

        String response2Body = response2.body().string();
        System.out.println("Response 2 response:          " + response2);
        System.out.println("Response 2 cache response:    " + response2.cacheResponse());
        System.out.println("Response 2 network response:  " + response2.networkResponse());

        System.out.println("Response 2 equals Response 1? " + response1Body.equals(response2Body));

    }
}
```
okhttpclient有点像Application的概念，统筹着整个okhttp的大功能，通过它设置缓存目录，我们执行上面的代码，得到的结果如下 
![](http://img.blog.csdn.net/20150723231332996)
response1 的结果在networkresponse，代表是从网络请求加载过来的，而response2的networkresponse 就为null，而cacheresponse有数据，因为我设置了缓存因此第二次请求时发现缓存里有就不再去走网络请求了。 
但有时候即使在有缓存的情况下我们依然需要去后台请求最新的资源（比如资源更新了）这个时候可以使用强制走网络来要求必须请求网络数据
```
public void execute() throws Exception {
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();

        Response response1 = client.newCall(request).execute();
        if (!response1.isSuccessful()) throw new IOException("Unexpected code " + response1);

        String response1Body = response1.body().string();
        System.out.println("Response 1 response:          " + response1);
        System.out.println("Response 1 cache response:    " + response1.cacheResponse());
        System.out.println("Response 1 network response:  " + response1.networkResponse());

        request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
        Response response2 = client.newCall(request).execute();
        if (!response2.isSuccessful()) throw new IOException("Unexpected code " + response2);

        String response2Body = response2.body().string();
        System.out.println("Response 2 response:          " + response2);
        System.out.println("Response 2 cache response:    " + response2.cacheResponse());
        System.out.println("Response 2 network response:  " + response2.networkResponse());

        System.out.println("Response 2 equals Response 1? " + response1Body.equals(response2Body));

    }
```
上面的代码中 
response2对应的request变成
```
request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
```
我们看看运行结果 
![](http://img.blog.csdn.net/20150724000536273)</br>
response2的cache response为null，network response依然有数据。
同样的我们可以使用 FORCE_CACHE 强制只要使用缓存的数据，但如果请求必须从网络获取才有数据，但又使用了FORCE_CACHE 策略就会返回504错误，代码如下，我们去okhttpclient的缓存，并设置request为FORCE_CACHE
```
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        File sdcache = getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        //client.setCache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage().toString());
                }
            }
        }).start();
    }

    public void execute() throws Exception {
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();

        Response response1 = client.newCall(request).execute();
        if (!response1.isSuccessful()) throw new IOException("Unexpected code " + response1);

        String response1Body = response1.body().string();
        System.out.println("Response 1 response:          " + response1);
        System.out.println("Response 1 cache response:    " + response1.cacheResponse());
        System.out.println("Response 1 network response:  " + response1.networkResponse());

        request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        Response response2 = client.newCall(request).execute();
        if (!response2.isSuccessful()) throw new IOException("Unexpected code " + response2);

        String response2Body = response2.body().string();
        System.out.println("Response 2 response:          " + response2);
        System.out.println("Response 2 cache response:    " + response2.cacheResponse());
        System.out.println("Response 2 network response:  " + response2.networkResponse());

        System.out.println("Response 2 equals Response 1? " + response1Body.equals(response2Body));

    }
```
![](http://img.blog.csdn.net/20150724001328192)</br>
###取消操作

网络操作中，经常会使用到对请求的cancel操作，okhttp的也提供了这方面的接口，call的cancel操作。使用Call.cancel()可以立即停止掉一个正在执行的call。如果一个线程正在写请求或者读响应，将会引发IOException，同时可以通过Request.Builder.tag(Object tag)给请求设置一个标签，并使用OkHttpClient.cancel(Object tag)来取消所有带有这个tag的call。但如果该请求已经在做读写操作的时候，cancel是无法成功的，会抛出IOException异常。
```
public void canceltest() throws Exception {
        Request request = new Request.Builder()
                .url("http://httpbin.org/delay/2") // This URL is served with a 2 second delay.
                .build();

        final long startNanos = System.nanoTime();
        final Call call = client.newCall(request);

        // Schedule a job to cancel the call in 1 second.
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.printf("%.2f Canceling call.%n", (System.nanoTime() - startNanos) / 1e9f);
                call.cancel();
                System.out.printf("%.2f Canceled call.%n", (System.nanoTime() - startNanos) / 1e9f);
            }
        }, 1, TimeUnit.SECONDS);

        try {
            System.out.printf("%.2f Executing call.%n", (System.nanoTime() - startNanos) / 1e9f);
            Response response = call.execute();
            System.out.printf("call is cancel:" + call.isCanceled() + "%n");
            System.out.printf("%.2f Call was expected to fail, but completed: %s%n",
                    (System.nanoTime() - startNanos) / 1e9f, response);
        } catch (IOException e) {
            System.out.printf("%.2f Call failed as expected: %s%n",
                    (System.nanoTime() - startNanos) / 1e9f, e);
        }
    }
```
成功取消 
![](http://img.blog.csdn.net/20150725122942428)<br/>
取消失败
![](http://img.blog.csdn.net/20150725123402761)<br/>
简单的对于OKHttp的使用就介绍到这里，下次将重点从源码角度介绍整个OKHttp是如何运转的。

出处:<a href="http://blog.csdn.net/chenzujie/article/details/46994073">http://blog.csdn.net/chenzujie/article/details/46994073</a>


