Android开发之Http通信HttpURLConnection接口
###HttpURLConnection接口
Http通信协议中，使用的最多的就是Get和Post。Get请求可以获取静态页面，也可以把参数放在字串后面，传递给服务器。Post与Get不同的是Post的参数不是放在URL字串的里面，而是放在http请求数据中。
HttpURLConnection是Java的标准类，继承自URLConnection类；
HttpURLConnection和URLConnection类都是抽象类，无法直接实例化对象。
其对象主要是通过URL的openConnection方法获得。
###构造url
实例定义代码:
```
//构造一个URL对象
url = new URL(httpUrl);
//使用HttpURLConnection打开链接，urlConn就是实例对象
HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
```

openConnection方法只是创建了一个HttpURLConnection或者URLConnection的实例，并不进行真正的链接操作。
每次openConnection的时候都将创建一个新的实例。
因此在连接之前可以对该对象的属性进行设置。
```
//设置输入（输出）流
				urlConn.setDoOutput(true);
				urlConn.setDoInput(true);
				//设置以POST方式
				urlConn.setRequestMethod("POST");
				//POST请求不能使用缓存
				urlConn.setUseCaches(false);
//在连接完成之后可以关闭这个连接
				urlConn.disconnect();

```
###利用Get和Post方式来获取一个网页内容。
HttpURLConnection默认使用Get方式，如果要使用Post方式，则需要setRequestMethod设置。然后将我们要传递的参数内容通过weiteBytes方法写入数据流。
Get方式访问无参数的代码:
```
/*
 *  HttpURLConnectionActivity02.java
 *  北京Android俱乐部群：167839253
 *  Created on: 2012-5-9
 *  Author: blueeagle
 *  Email: liujiaxiang@gmail.com
 */

public class HttpURLConnectionActivity02 extends Activity {
    /** Called when the activity is first created. */
	
	private final String DEBUG_TAG = "HttpURLConnectionActivityActivity";
    @Override
    public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);		
		TextView mTextView = (TextView)this.findViewById(R.id.myTextView);
		//http地址
		String httpUrl = "http://10.1.69.34/http1.jsp";
		//获得的数据
		String resultData = "";
		URL url = null;
		try
		{
			//构造一个URL对象
			url = new URL(httpUrl); 
		}
		catch (MalformedURLException e)
		{
			Log.e(DEBUG_TAG, "MalformedURLException");
		}
		if (url != null)
		{
			try
			{
				//使用HttpURLConnection打开连接
				HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
				//得到读取的内容(流)
				InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
				// 为输出创建BufferedReader
				BufferedReader buffer = new BufferedReader(in);
				String inputLine = null;
				//使用循环来读取获得的数据
				while (((inputLine = buffer.readLine()) != null))
				{
					//我们在每一行后面加上一个"\n"来换行
					resultData += inputLine + "\n";

				}	
				
				if ( !resultData.equals("") )
				{
					mTextView.setText(resultData);
				}
				else
				{
					mTextView.setText("读取的内容为NULL");
				}
				//关闭InputStreamReader
				in.close();
				//关闭http连接
				urlConn.disconnect();
				//设置显示取得的内容

			}
			catch (IOException e)
			{
				Log.e(DEBUG_TAG, "IOException");
			}
		}
		else
		{
			Log.e(DEBUG_TAG, "Url NULL");
		}
		//设置按键事件监听
		Button button_Back = (Button) findViewById(R.id.Button_back);
		/* 监听button的事件信息 */
		button_Back.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick(View v)
			{
				/* 新建一个Intent对象 */
				Intent intent = new Intent();
				/* 指定intent要启动的类 */
				intent.setClass(HttpURLConnectionActivity02.this, HttpURLConnectionActivity.class);
				/* 启动一个新的Activity */
				startActivity(intent);
				/* 关闭当前的Activity */
				HttpURLConnectionActivity02.this.finish();
			}
		});
	}     
}


```
###POST方式访问服务器
访问服务器端图片并显示在客户端。
代码如下：
```
/*
 *  HttpURLConnectionActivity02.java
 *  北京Android俱乐部群：167839253
 *  Created on: 2012-5-9
 *  Author: blueeagle
 *  Email: liujiaxiang@gmail.com
 */

public class HttpURLConnectionActivity03 extends Activity {
    /** Called when the activity is first created. */
	private final String DEBUG_TAG = "Activity03";
	private Bitmap bmp;
    @Override
    public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);		
		TextView mTextView = (TextView)this.findViewById(R.id.myTextView);
		ImageView mImageView = (ImageView)this.findViewById(R.id.bmp);
		//http地址
		String httpUrl = "http://10.1.69.34/http1.jsp";
		//获得的数据
		String resultData = "";
		URL url = null;
		try
		{
			//构造一个URL对象
			url = new URL(httpUrl); 
		}
		catch (MalformedURLException e)
		{
			Log.e(DEBUG_TAG, "MalformedURLException");
		}
		if (url != null)
		{
			try
			{
				//使用HttpURLConnection打开链接
				HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
//********************************Post方式不同的地方*************************************//
				//因为这个是post请求，需要设置为true
				urlConn.setDoOutput(true);
				urlConn.setDoInput(true);
				//设置以POST方式
				urlConn.setRequestMethod("POST");
				//POST请求不能使用缓存
				urlConn.setUseCaches(false);
				urlConn.setInstanceFollowRedirects(true);
				
				//配置本次连接的Content_type,配置为application/x-www-form-urlencoded
				urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				//连接，从postUrl.OpenConnection()至此的配置必须要在connect之前完成。
				//要注意的是connection.getOutputStream会隐含地进行connect.
//********************************Post方式不同的地方*************************************//
				urlConn.connect();
				//DataOutputStream流。
				DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
				//要上传的参数
				String content = "par=" + URLEncoder.encode("ABCDEF","gb2312");
				//将要上传的内容写入流中
				out.writeBytes(content);
				//刷新、关闭
				out.flush();
				out.close();
				//获取数据
				BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
				String inputLine = null;
				
				//---///得到读取的内容(流)
				//---InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
				//---// 为输出创建BufferedReader
				//---BufferedReader buffer = new BufferedReader(in);
				//---String inputLine = null;
				//---//使用循环来读取获得的数据
				while (((inputLine = reader.readLine()) != null))
				{
					//我们在每一行后面加上一个"\n"来换行
					resultData += inputLine + "\n";

				}	
				reader.close();
				//关闭http链接
				urlConn.disconnect();
				//设置显示取得的内容
				
				if ( !resultData.equals("") )
				{
					mTextView.setText(resultData);
					bmp = this.GetNetBitmap("http://10.1.69.34/0.jpg");
					mImageView.setImageBitmap(bmp);
				}
				else
				{
					mTextView.setText("读取的内容为空");
				}
				//关闭InputStreamReader
				reader.close();
				//关闭http连接
				urlConn.disconnect();
				//设置显示取得的内容

			}
			catch (IOException e)
			{
				Log.e(DEBUG_TAG, "IOException");
			}
		}
		else
		{
			Log.e(DEBUG_TAG, "Url NULL");
		}
		//设置按键事件监听
		Button button_Back = (Button) findViewById(R.id.Button_back);
		/* 监听button的事件信息 */
		button_Back.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick(View v)
			{
				/* 新建一个Intent对象 */
				Intent intent = new Intent();
				/* 指定intent要启动的类 */
				intent.setClass(HttpURLConnectionActivity03.this, HttpURLConnectionActivity.class);
				/* 启动一个新的Activity */
				startActivity(intent);
				/* 关闭当前的Activity */
				HttpURLConnectionActivity03.this.finish();
			}
		});
	}
  //********************************获取网络图片(支持bmp,jpg,png,gif等格式，但是bmp格式支持的比较小)*************************************//    
    public Bitmap GetNetBitmap(String url){
    	URL imageUrl = null;
    	Bitmap bitmap = null;
    	try{
    		imageUrl = new URL(url);
    	}
    	catch(MalformedURLException e){
    		Log.e(DEBUG_TAG, e.getMessage());
    	}
    	try{
    		HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
    		conn.setDoInput(true);
    		conn.connect();
    		//将得到的数据转换成InputStream
    		InputStream is = conn.getInputStream();
    		//将InputStream 转换成Bitmap
    		bitmap = BitmapFactory.decodeStream(is);
    		is.close();
    	}
    	catch(IOException e){
    		Log.e(DEBUG_TAG, e.getMessage());
    	}
		return bitmap;
    	
    }
}

```

###总结：
针对HTTP协议，简单来说:
GET方式是通过把参数键值对附加在url后面来传递的，是文本方式的。
在服务器端可以从'QUERY_STRING'这个变量中直接读取，效率较高，但缺乏安全性，也无法来处理复杂的数据，长度有限制。主要用于传递简单的参数。
POST方式：就传输方式讲参数会被打包在http报头中传输，可以是二进制的。
从CONTENT_LENGTH这个环境变量中读取，便于传送较大一些的数据，同时因为不暴露数据在浏览器的地址栏中，安全性相对较高，但这样的处理效率会受到影响。