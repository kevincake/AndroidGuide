###前言

　　上一篇文章介绍了使用HttpURLConnection来完成对于HTTP协议的支持。现在介绍一个新的方式来访问Web站点，那就是HttpClient。

　　HttpClient是Apache开源组织提供的一个开源的项目，从名字上就可以看出，它是一个简单的HTTP客户端（并不是浏览器），可以发送HTTP请求，接受HTTP响应。但是不会缓存服务器的响应，不能执行HTTP页面中签入嵌入的JS代码，自然也不会对页面内容进行任何解析、处理，这些都是需要开发人员来完成的。

　　现在Android已经成功集成了HttpClient，所以开发人员在Android项目中可以直接使用HttpClient来想Web站点提交请求以及接受响应，如果使用其他的Java项目，需要引入进相应的Jar包。HttpClient可以在官网上下载。

　　

###HttpClient

　　HttpClient其实是一个interface类型，HttpClient封装了对象需要执行的Http请求、身份验证、连接管理和其它特性。从文档上看，HttpClient有三个已知的实现类分别是：AbstractHttpClient, AndroidHttpClient, DefaultHttpClient，会发现有一个专门为Android应用准备的实现类AndroidHttpClient，当然使用常规的DefaultHttpClient也可以实现功能，但是既然开发的是Android应用程序，还是使用Android专有的实现类，一定有其优势。

　　从两个类包所有在位置就可以看出区别，AndroidHttpClient定义在android.net.http.AndroidHttpClient包下，属于Android原生的http访问，而DefaultHttpClient定义在org.apache.http.impl.client.DefaultHttpClient包下，属于对apche项目的支持。而AndroidHttpClient没有公开的构造函数，只能通过静态方法newInstance()方法来获得AndroidHttpClient对象。

　　AndroidHttpClient对于DefaultHttpClient做了一些改进，使其更使用用于Android项目：

关掉过期检查，自连接可以打破所有的时间限制。
可以设置ConnectionTimeOut（连接超时）和SoTimeout（读取数据超时）。
关掉重定向。
使用一个Session缓冲用于SSL Sockets。
如果服务器支持，使用gzip压缩方式用于在服务端和客户端传递的数据。
默认情况下不保留Cookie。　　　　
　　简单来说，用HttpClient发送请求、接收响应都很简单，只需要几个步骤即可：

###创建HttpClient对象。
创建对应的发送请求的对象，如果需要发送GET请求，则创建HttpGet对象，如果需要发送POST请求，则创建HttpPost对象。
对于发送请求的参数，GET和POST使用的方式不同，GET方式可以使用拼接字符串的方式，把参数拼接在URL结尾；POST方式需要使用setEntity(HttpEntity entity)方法来设置请求参数。
调用HttpClient对象的execute（HttpUriRequest request）发送请求，执行该方法返回一个HttpResponse对象。
调用HttpResponse的对应方法获取服务器的响应头、响应内容等。
DefaultHttpClient

　　先看看使用DefaultHttpClient方式发送Web站点请求，上面已经简要说明了步骤，在这里简要说明一个参数的传递问题，对于GET方式，只需要拼接字符串就在URL结尾即可，但是对于POST方式，需要传递HttpEntity对象，HttpEntity为一个接口，有多个实现类，可以使用其间接子继承，UrlEncodedFormEntity类来保存请求参数，并传递给HttpPost。

　　此例子简单实现了在Android客户端使用DefaultHttpClient实现一个Http站点登陆的实现，使用的是POST传递，其传递值只需要传递username+password即可，当传递的数据为admin+123则认为登陆成功。Web站点使用.net的架构，一个一般处理程序，简单的比对账户密码，这里就不在此讲解。

　　因为Android4.0之后对使用网络有特殊要求，已经无法再在主线程中访问网络了，必须使用多线程访问的模式，其他的一些信息在代码注释中已经说明。

###DefaultHttpClient-Code

```
 package com.bgxt.httpUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class httpClientUtils implements Runnable {
    /**
     * 对于Android4.0之上的环境下，不能在主线程中访问网络 所以这里另新建了一个实现了Runnable接口的Http访问类
     */
    private String username;
    private String password;

    public httpClientUtils(String username, String password) {
        // 初始化用户名和密码
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        // 设置访问的Web站点
        String path = "http://192.168.1.103:1231/loginas.ashx";
        // 设置Http请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        String result = sendHttpClientPost(path, params, "utf-8");
        // 把返回的接口输出
        System.out.println(result);
    }

    /**
     * 发送Http请求到Web站点
     * 
     * @param path
     *            Web站点请求地址
     * @param map
     *            Http请求参数
     * @param encode
     *            编码格式
     * @return Web站点响应的字符串
     */
    private String sendHttpClientPost(String path, Map<String, String> map,
            String encode) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                // 解析Map传递的参数，使用一个键值对对象BasicNameValuePair保存。
                list.add(new BasicNameValuePair(entry.getKey(), entry
                        .getValue()));
            }
        }
        try {
            // 实现将请求 的参数封装封装到HttpEntity中。
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encode);
            // 使用HttpPost请求方式
            HttpPost httpPost = new HttpPost(path);
            // 设置请求参数到Form中。
            httpPost.setEntity(entity);
            // 实例化一个默认的Http客户端
            DefaultHttpClient client = new DefaultHttpClient();
            // 执行请求，并获得响应数据
            HttpResponse httpResponse = client.execute(httpPost);
            // 判断是否请求成功，为200时表示成功，其他均问有问题。
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 通过HttpEntity获得响应流
                InputStream inputStream = httpResponse.getEntity().getContent();
                return changeInputStream(inputStream, encode);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 把Web站点返回的响应流转换为字符串格式
     * 
     * @param inputStream
     *            响应流
     * @param encode
     *            编码格式
     * @return 转换后的字符串
     */
    private String changeInputStream(InputStream inputStream, String encode) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, len);
                }
                result = new String(outputStream.toByteArray(), encode);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
```
###AndroidHttpClient

　　使用AndroidHttpClient的方式和DefaultHttpClient差不多，不多的几点区别上面已经说明，但是在此例子中没有体现。有一点需要注意的是，AndroidHttpClient是一个final类，也没有公开的构造函数，所以无法使用new的形式对其进行实例化，必须使用AndroidHttpClient.newInstance()方法获得AndroidHttpClient对象。

　　示例中依然是使用POST请求，实现的功能和DefaultHttpClient示例一样。细节部分已经在注释中体现，直接看代码即可。
```
package com.bgxt.httpUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.net.http.AndroidHttpClient;

public class AndroidHttpClientUtils implements Runnable {

    private String username;
    private String password;

    public AndroidHttpClientUtils(String username, String password) {
        // 初始化用户名和密码
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        // 设置访问的Web站点
        String path = "http://192.168.1.103:1231/loginas.ashx";
        //设置Http请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        String result = sendHttpClientPost(path, params, "utf-8");
        //把返回的接口输出
        System.out.println(result);
    }
    /**
     * 发送Http请求到Web站点
     * @param path Web站点请求地址
     * @param map Http请求参数
     * @param encode 编码格式
     * @return Web站点响应的字符串
     */
    private String sendHttpClientPost(String path,Map<String, String> map,String encode)
    {
        List<NameValuePair> list=new ArrayList<NameValuePair>();
        if(map!=null&&!map.isEmpty())
        {
            for(Map.Entry<String, String> entry:map.entrySet())
            {
                //解析Map传递的参数，使用一个键值对对象BasicNameValuePair保存。
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        try {
            //实现将请求 的参数封装封装到HttpEntity中。
            UrlEncodedFormEntity entity=new UrlEncodedFormEntity(list, encode);
            //使用HttpPost请求方式
            HttpPost httpPost=new HttpPost(path);
            //设置请求参数到Form中。
            httpPost.setEntity(entity);
            //实例化一个默认的Http客户端，使用的是AndroidHttpClient
            HttpClient client=AndroidHttpClient.newInstance("");
            //执行请求，并获得响应数据
            HttpResponse httpResponse= client.execute(httpPost);
            //判断是否请求成功，为200时表示成功，其他均问有问题。
            if(httpResponse.getStatusLine().getStatusCode()==200)
            {
                //通过HttpEntity获得响应流
                InputStream inputStream=httpResponse.getEntity().getContent();
                return changeInputStream(inputStream,encode);
            }
            
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return "";
    }                    
    /**
     * 把Web站点返回的响应流转换为字符串格式
     * @param inputStream 响应流
     * @param encode 编码格式
     * @return 转换后的字符串
     */
    private  String changeInputStream(InputStream inputStream,
            String encode) { 
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result="";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    outputStream.write(data,0,len);                    
                }
                result=new String(outputStream.toByteArray(),encode);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

```
在本文的示例中，环境是使用的Android项目，可以对其进行简单的界面布局，如图：
![](http://images.cnitblog.com/blog/234895/201306/22203237-984a7dc8972f4a75b79783bc43202261.x-png)</br>
如果输入用户和密码为：admin+123，则可以再LogCat中查看到登录成功。
![](http://images.cnitblog.com/blog/234895/201306/22203527-865ea6d5cf714e7fb4694bc4c77cd5e2.x-png)</br>

###总结

　　最近的两次博客中，已经分别介绍了HttpUrlConnection和HttpClient两种方式，通过Http协议对Web站点的访问。如果还不了解HttpURLConnection的读者，可以看看Android--Http协议。

　　根据官方文档上说的显示，Android包括两个Http客户端：HttpURLConnection和Apache HttpClient。并且都支持HTTPS，流媒体上传下载，并且可配置超时以及支持IPv6和连接池技术。但是因为移动设备的局限性，HttpURLConnection会是比Apache Http更好的选择，因为其API简单，运行消耗内存小，并且具有公开化的压缩算法，以及响应缓存，能更好的减少网络使用，提供运行速度和节省电池。

　　但是也不能否认Apache HttpClient，它有大量的灵活的API，实现比较稳定，少有Bug，可造成的问题就是很难在不影响其兼容性的情况下对其进行改进了。现在Android开发者已经慢慢放弃Apache HttpClient的使用，转而使用HttpURLConnection。但是对于Android2.2之前的版本，HttpURLConnection具有一个致命的BUG，在响应输入流InputStream中调用.Close()方法将会阻碍连接池，因为这个BUG，只能放弃连接池的使用，但是Apache HttpClient不存在这个问题，当然Android2.3之后的版本中，HttpURLConnection已经解决了这个BUG，可以放心使用。


