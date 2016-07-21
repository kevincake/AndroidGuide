 Android文件操作模式
###一、基本概念
```
 private Context context;  
  
public FileService(Context context)  
{  
    super();  
    this.context = context;  
}  
  
// 保存文件方法  
public void save(String filename, String fileContent) throws Exception  
{  
    FileOutputStream fos = context.openFileOutput(filename, context.MODE_PRIVATE);  
    fos.write(fileContent.getBytes("UTF-8"));  
    fos.close();  
}  
```
####私有模式
①只能被创建这个文件的当前应用访问
②若文件不存在会创建文件；若创建的文件已存在则会覆盖掉原来的文件
Context.MODE_PRIVATE = 0;
 
####追加模式
①私有的
②若文件不存在会创建文件；若文件存在则在文件的末尾进行追加内容
Context.MODE_APPEND = 32768;
 
####可读模式
①创建出来的文件可以被其他应用所读取
Context.MODE_WORLD_READABLE=1;
 
####可写模式
①允许其他应用对其进行写入。
Context.MODE_WORLD_WRITEABLE=2
 
以上文件操作模式均针对保存在手机自带存储空间的文件。若文件存储在SDCard上，则不受读写控制。
 
 
###二、组合使用
```
FileOutputStream outStream = this.openFileOutput("xy.txt",Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
```
允许其他应用读写，并默认覆盖
```
FileOutputStream outStream = this.openFileOutput("xy.txt",Context.MODE_APPEND+Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
```
追加模式，但允许其他应用读写
出处:<a href="http://blog.csdn.net/woshixuye/article/details/8250101#">http://blog.csdn.net/woshixuye/article/details/8250101#</a>