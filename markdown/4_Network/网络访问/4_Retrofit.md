 Retrofit 2.0 常用写法示例
由于Retrofit的官方文档实在是很难理解，我这里收集了一些常见的写法，供在实际使用中参考。

对URL的处理

###BASEURL

通常我们在定义retrofit的时候，会设置一个baseurl。
```
Retrofit.Builder builder = new Retrofit.Builder();
builder.baseUrl(mBaseUrl);
```
在2.0中，url的拼接规则与网页中的<a href="url"></a>相同。如果写过网页的同学，就不会太陌生。
![](http://img.blog.csdn.net/20160225135526127)<br/>
![](http://img.blog.csdn.net/20160225135536187)<br/>
![](http://img.blog.csdn.net/20160225135547986)</br>
为了书写时不易出错，推荐在baseurl的后面加上‘/’

###请求URL

我们知道，请求通常有GET或POST：
```
public interface AppListApi {

    @GET("applist/apps/")
    Call<ResponsePojo> getList();

    @POST("applist/mini/")
    Call<ResponsePojo> getMiniApp();
}
```
在2.0中，GET和POST中的地址可以是绝对路径，
```
@GET("http://www.baidu.com/applist/apps/")
  Call<ResponsePojo> getList();
```
当然，路径也可以是动态的，根据参数进行修改。
```
@GET("applist/apps/{appid}/detail")
Call<ResponsePojo> getDetail(@Path ("appid") String appid);
```
除此之外，还可以单独为每个请求动态传入URL地址
```
@GET("wallpapers/")
Call<WallPaperPojo> getWallPaper(@Url String url, @Query("offset") int offset, @Query("limit") int limit);
```
###GET

GET请求比较简单

固定参数
```
 @GET("applist/apps/detail?type=software")
  Call<ResponsePojo> getDetail();
```
动态参数
```
 @GET("applist/apps/detail")
  Call<ResponsePojo> getDetail(@Query ("type") String type);
```
对null的支持，如果在实际调用的时候传一个null, 系统也不会出错，会把这个参数当作没有。

当然如果有多个参数
```
 @GET("applist/apps/detail")
  Call<ResponsePojo> getDetail(@Query ("type") String type, @Query("appid") String appid);
```
对于参数名称不固定的情况可以使用Map
```
  @GET("applist/apps/detail")
  Call<ResponsePojo> getDetail(@QueryMap Map<String, String> param);
```
当然，还可以支持固定参数与动态参数的混用
```
 @GET("applist/apps/detail?type=detail")
  Call<ResponsePojo> getDetail(@Query("appid") String appid);
```
系统会自动按照规范串接在URL后面

###HEADER的修改

固定添加Header
```
 @Headers("Accept-Encoding: application/json") 
  @GET("applist/apps/detail?type=detail")
  Call<ResponsePojo> getDetail(@Query("appid") String appid);
```
动态添加Header
```
 @Headers("Accept-Encoding: application/json") 
  @GET("applist/apps/detail?type=detail")
  Call<ResponsePojo> getDetail(@Header ("Accept-Encoding") String appid);
```
多个Header
```
 @Headers({
    "X-Foo: Bar",
    "X-Ping: Pong"
  })
  @GET("applist/apps/detail?type=detail")
  Call<ResponsePojo> getDetail(@Header ("Accept-Encoding") String appid);
```
固定与动态的Header的混合
```
 @Headers("Accept-Encoding: application/json") 
  @GET("applist/apps/detail?type=detail")
  Call<ResponsePojo> getDetail(@Header ("Location") String appid);
```
###POST

自定义的上传的参数
```
  @POST("applist/apps/detail")
  Call<ResponsePojo> sendDetail(@Body RequestBody requestData);
```


使用RequestBody.create来创建自定义的数据体

对于表单形式的上传，使用注解FormUrlEncoded+Field
```
  @FormUrlEncoded
  @POST("applist/apps/detail")
  Call<ResponsePojo> sendDetail(@Field("name1") String name1);
```
当然，动态的表单可以使用Map
```
  @FormUrlEncoded
  @POST("applist/apps/detail")
  Call<ResponsePojo> sendDetail(@FieldMap Map<String, String> names);
```
MutilPart
```
  @Multipart
  @POST("applist/apps/detail")
  Call<ResponsePojo> sendDetail(@Part("name1") String name1,@Part("name2") String name2);
```
动态的写法
```
  @Multipart
  @POST("applist/apps/detail")
  Call<ResponsePojo> sendDetail(@PartMap Map<String, String> names);
```

上传文件
```
 //上传文件
    @POST("applist/apps/detail")
    Call<ResponseResult> sendFile(RequestBody fileRequest);
 


     appListApi.sendFile(RequestBody.create(MediaType.parse("text/html"), new File("/sdcard/test.html")));
``` 
```
上传多个文件示例
```
    @Multipart
    @POST("applist/apps/detail")
    Call<ResponseResult> sendFile(@Part("filename=test1") RequestBody fileRequest1 ,@Part("filename=test2") RequestBody fileRequest2);
```
//上传多个文件示例
```
     appListApi.sendFile(RequestBody.create(MediaType.parse("text/html"), new File("/sdcard/test.html")),RequestBody.create(MediaType.parse("text/html"), new File("/sdcard/test.html"));
```

