###数据存储之Sharepreference
这个方法一般是为了存储具有Key-Value特征的数据。

我们可以从一个上下文对象中获取共享应用，他提供一个框架在保存和获取一些K-V的持久化数据。
具体步骤如下：
###1.获取SharedPreferences
```
SharedPreferences sharedPreferences = context.getSharedPreferences(  
                "userinfo", Context.MODE_PRIVATE);  

```
###2.获取一个编辑器：
```
SharedPreferences.Editor editor =   sharedPreferences.edit();  
```
###3.通过编辑器放入数据：
```
editor.putString("name", name);  
editor.putString("pswd", pswd);  
```
###4.最后需要提交
```
editor.commit();   
```

一个完整的代码如下：
```
public boolean saveMessage(String name, String pswd) {  
        boolean flag = false;  
        SharedPreferences sharedPreferences = context.getSharedPreferences(  
                "userinfo", Context.MODE_PRIVATE);  
        //对数据进行编辑   
        SharedPreferences.Editor editor =   sharedPreferences.edit();  
        editor.putString("name", name);  
        editor.putString("pswd", pswd);  
        flag = editor.commit();  //将数据持久化到存储介质  
        return flag;  
    }  
```
如果是读数据，那么需要Map类型来存储：</br>
1.生成一个HashMap对象：
```
Map<String,Object> map = new HashMap<String,Object>();  
```
2.获取共享引用对象：
```
SharedPreferences sharedPreferences  =context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);  
```
3.取出数据并放入Map中：
```
String name = sharedPreferences.getString("name", null);  
        String pswd = sharedPreferences.getString("pswd", null);  
        map.put("name",name);  
        map.put("pswd", pswd);  
```
完整代码如下：
```
public Map<String,Object> getMessage(){  
        Map<String,Object> map = new HashMap<String,Object>();  
        SharedPreferences sharedPreferences  =context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);  
        String name = sharedPreferences.getString("name", null);  
        String pswd = sharedPreferences.getString("pswd", null);  
        map.put("name",name);  
        map.put("pswd", pswd);  
        return map;  
    }  
```
