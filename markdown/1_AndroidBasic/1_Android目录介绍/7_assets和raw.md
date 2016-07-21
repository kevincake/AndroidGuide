####Android资源文件分类：
- 第一种是res目录下存放的可编译的资源文件：
这种资源文件系统会在R.Java里面自动生成该资源文件的ID，所以访问这种资源文件比较简单，通过R.XXX.ID即可；
- 第二种是assets目录下存放的原生资源文件：
Android系统为我们提供了一个AssetManager工具类。
查看官方API可知，AssetManager提供对应用程序的原始资源文件进行访问；这个类提供了一个低级别的API，它允许你以简单的字节流的形式打开和读取和应用程序绑定在一起的原始资源文件。

###一、AssetManager类
####1.概述：
       提供对应用程序的原始资源文件进行访问；这个类提供了一个低级别的API，它允许你以简单的字节流的形式打开和读取和应用程序绑定在一起的原始资源文件。通过getAssets()方法获取AssetManager对象。

####2.AssetManager类常用方法
```
final String[] list(String path)
返回指定路径下的所有文件及目录名。

final InputStream open(String fileName)
使用 ACCESS_STREAMING模式打开assets下的指定文件。.

final InputStream open(String fileName, int accessMode)
使用显示的访问模式打开assets下的指定文件.
```
####3、例子
(1).加载html
```
webView.loadUrl("file:///android_asset/win8_Demo/index.html");//js和html都会加载
```
####效果图
![](http://img.blog.csdn.net/20140803183257519?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZmVuZ3l1emhlbmdmYW4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

(2).访问assets目录下的资源文件
```
  AssetManager.open(String filename)，返回的是一个InputSteam类型的字节流，这里的filename必须是文件比如
（aa.txt；img/semll.jpg），而不能是文件夹。
```
(3).获取assets的文件及目录名：
```
//获取assets目录下的所有文件及目录名，content（当前的上下文如Activity，Service等ContexWrapper的子类的
都可以）
String fileNames[] =context.getAssets().list(path);  
```
(4).将assets下的文件复制到SD卡：
```
/** 
 *  从assets目录中复制整个文件夹内容 
 *  @param  context  Context 使用CopyFiles类的Activity
 *  @param  oldPath  String  原文件路径  如：/aa 
 *  @param  newPath  String  复制后路径  如：xx:/bb/cc 
 */ 
public void copyFilesFassets(Context context,String oldPath,String newPath) {                    
         try {
        String fileNames[] = context.getAssets().list(oldPath);//获取assets目录下的所有文件及目录名
        if (fileNames.length > 0) {//如果是目录
            File file = new File(newPath);
            file.mkdirs();//如果文件夹不存在，则递归
            for (String fileName : fileNames) {
               copyFilesFassets(context,oldPath + "/" + fileName,newPath+"/"+fileName);
            }
        } else {//如果是文件
            InputStream is = context.getAssets().open(oldPath);
            FileOutputStream fos = new FileOutputStream(new File(newPath));
            byte[] buffer = new byte[1024];
            int byteCount=0;               
            while((byteCount=is.read(buffer))!=-1) {//循环从输入流读取 buffer字节        
                fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
            }
            fos.flush();//刷新缓冲区
            is.close();
            fos.close();
        }
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        //如果捕捉到错误则通知UI线程
                   MainActivity.handler.sendEmptyMessage(COPY_FALSE);
    }                           
}

```
![](http://img.my.csdn.net/uploads/201408/03/1407062124_7147.gif)</br>
(5).使用assets目录下的图片资源
```
InputStream is=getAssets().open("wpics/0ZR424L-0.jpg");  
Bitmap bitmap=BitmapFactory.decodeStream(is);  
imgShow.setImageBitmap(bitmap); 
```
![](http://img.my.csdn.net/uploads/201408/03/1407062094_4710.gif)</br>
(6).播放assets目录下的音乐
- 首先，获取通过openFd()的方法获取asset目录下指定文件的AssetFileDescriptor对象。
- 其次，通过MediaPlayer对象的setDataSource (FileDescriptorfd, longoffset, long length)方法加载音乐文件。
- 最后，调用prepare方法准备音乐，start方法开始播放音乐。
原文:![](http://blog.csdn.net/fengyuzhengfan/article/details/38360017)