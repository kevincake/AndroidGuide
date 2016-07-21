ContentProvider是用于将数据共享给其他应用。例如在同一部手机里面，有两个APP，第一个APP要访问第二个APP的数据。此时，第二个APP就需要设置ContentProvider。这样，第一个APP就能通过Uri访问第二个APP的数据。
ContentProvider是用于将数据共享给其他应用。例如在同一部手机里面，有两个APP，第一个APP要访问第二个APP的数据。此时，第二个APP就需要设置ContentProvider。这样，第一个APP就能通过Uri访问第二个APP的数据。
第二部手机的设置，首先需要一个类来继承ContentProvider这个类，继承后需要实现onCreate，query，getType，insert，delete，update这个几个方法，如其名，其作用是给第一个APP调用的，至于第一个APP是如何调用的，下面会提到。
给出一个继承ContentProvider的例子：
1. 先是提供的数据类型等数据的类。
```
package org.juetion.cp;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * 提供的数据类型等数据。
 * Created by juetionke on 13-12-21.
 */
public class MyProviderMetaData {


    public static final String AUTHORIY = "org.juetion.cp.MyContentProvider";
    /**
     * 数据库名称
     */
    public static final String DATABASE_NAME = "MyProvider.db";
    /**
     * 数据库版本
     */
    public static final int DATABASE_VERSION = 1;
    /**
     * 表名
     */
    public static final String USERS_TABLE_NAME = "users";

    /**
     * 继承了BaseColumns，所以已经有了_ID
     */
    public static final class UserTableMetaData implements BaseColumns {
        /**
         * 表名
         */
        public static final String TABLE_NAME = "users";
        /**
         * 访问该ContentProvider的URI
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORIY + "/users");
        /**
         * 该ContentProvider所返回的数据类型定义
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/org.juetion.user";
        public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/org.juetion.user";
        /**
         * 列名
         */
        public static final String USER_NAME = "name";
        public static final String USER_AGE = "age";
        /**
         * 默认的排序方法
         */
        public static final String DEFAULT_SORT_ORDER = "_id desc";
    }
}

```
2. 继承ContentProvider的类：
```
package org.juetion.cp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.juetion.sqlite3.DatabaseHelper;

import java.util.HashMap;

/**
 * Created by juetionke on 13-12-21.
 */
public class MyContentProvider extends ContentProvider {

    /**
     * 定义规则
     */
    public static final UriMatcher uriMatcher;
    public static final int USERS_COLLECTION = 1;//用于标记
    public static final int USERS_SINGLE = 2;//用于标记
    private DatabaseHelper databaseHelper;//这里的数据共享是共享Sqlite里的数据，当然，可以试用其他，如文本数据共享。
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);//试用一个没有规则的Uri。然后下面自己匹配。
        uriMatcher.addURI(MyProviderMetaData.AUTHORIY,"/users",USERS_COLLECTION);//自己定义的规则，有点像路由器，是uri匹配的方案。
        uriMatcher.addURI(MyProviderMetaData.AUTHORIY,"/users/#",USERS_SINGLE);//同上。
    }

    /**
     * 为列定义别名
     */
    public static HashMap<String,String> usersMap;
    static {
        usersMap = new HashMap<String, String>();
        usersMap.put(MyProviderMetaData.UserTableMetaData._ID, MyProviderMetaData.UserTableMetaData._ID);
        usersMap.put(MyProviderMetaData.UserTableMetaData.USER_NAME, MyProviderMetaData.UserTableMetaData.USER_NAME);
        usersMap.put(MyProviderMetaData.UserTableMetaData.USER_AGE, MyProviderMetaData.UserTableMetaData.USER_AGE);
    }


    @Override
    public boolean onCreate() {
        Log.i("juetion","onCreate");
        databaseHelper = new DatabaseHelper(getContext(), MyProviderMetaData.DATABASE_NAME);//这里的实现，常见前篇关于Sqlite的文章。
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i("juetion","query");
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();//写入查询条件，有点像Hibernate。
        switch (uriMatcher.match(uri)) {//判断查询的是单个数据还是多个数据。
            case USERS_SINGLE:
                sqLiteQueryBuilder.setTables(MyProviderMetaData.UserTableMetaData.TABLE_NAME);//需要查询的表
                sqLiteQueryBuilder.setProjectionMap(usersMap);//列的别名定义
                sqLiteQueryBuilder.appendWhere(MyProviderMetaData.UserTableMetaData._ID + "=" + uri.getPathSegments().get(1));
                //查询条件，uri.getPathSegments().get(1)，getPathSegments是将内容根据／划分成list。
                break;
            case USERS_COLLECTION:
                sqLiteQueryBuilder.setTables(MyProviderMetaData.UserTableMetaData.TABLE_NAME);
                sqLiteQueryBuilder.setProjectionMap(usersMap);
                break;
        }
        String orderBy;//判断sortOrder是否为空，加入默认。
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = MyProviderMetaData.UserTableMetaData.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder);//可以使用下面的方法，不过此时sqLiteDatabase将会没有用。
        //Cursor cursor = sqLiteDatabase.query(MyProviderMetaData.UserTableMetaData.TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    /**
     * 根据传入的URI，返回URI说表示的数据类型
     * @param uri
     * @return
     */
    @Override
    public String getType(Uri uri) {
        Log.i("juetion","getType");
        switch (uriMatcher.match(uri)) {//匹配uri的规则
            case USERS_COLLECTION:
                return MyProviderMetaData.UserTableMetaData.CONTENT_TYPE;
            case USERS_SINGLE:
                return MyProviderMetaData.UserTableMetaData.CONTENT_TYPE_ITEM;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i("juetion","insert");
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        long rowId = sqLiteDatabase.insert(MyProviderMetaData.UserTableMetaData.TABLE_NAME, null, values);
        if (rowId > 0) {
            Uri insertUserUri = ContentUris.withAppendedId(MyProviderMetaData.UserTableMetaData.CONTENT_URI, rowId);//简单来说就是字符串拼凑一下。只不过是uri专用的。
            //通知监听器
            getContext().getContentResolver().notifyChange(insertUserUri,null);
            return insertUserUri;
        }else
            throw new IllegalArgumentException("Failed to insert row into" + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i("juetion","delete");
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i("juetion","update");
        return 0;
    }
}

```

这两个类里面都有的对应的注释，都能看懂吧？
还有重要的一点，再第二个APP的AndroidManifest.xml里面需要添加
```
<!-- provider name填写ContentProvider那个类的全称，authorities填写MyProviderMetaData里的AUTHORIY -->  
        <provider  
            android:authorities="org.juetion.cp.MyContentProvider"  
            android:name="org.juetion.cp.MyContentProvider"/>  
```
以上代码都是在第二个APP里面的。
－－－－－－－－我是可爱的分界线－－－－－－－－－－－－
以下代码是在第一个APP里面的。
关于使用。只需要将uri的string提供给第一个APP。
例如在第一个APP的Activity调用数据插入：
```
ContentValues contentValues = new ContentValues();  
                    contentValues.put("name","zhangsan");  
                    contentValues.put("age",19);  
                    Uri uri = getContentResolver().insert(Uri.parse("content://org.juetion.cp.MyContentProvider/users"),contentValues);  
                    Log.i("juetion", "insert uri-->" + uri.toString());  
```
例如在第一个APP的Activity调用数据的查询：
```
Cursor cursor = getContentResolver().query(Uri.parse("content://org.juetion.cp.MyContentProvider/users"),
                            new String[]{"name", "age"},
                            null, null, null);
                    while (cursor.moveToNext()) {
                        Log.i("juetion", cursor.getString(cursor.getColumnIndex("name")));
                    }
```
正如上面的例子，使用的时候是在Activity里面调用getContentResolver（）的。
代码下载链接：http://download.csdn.net/detail/juetion/6752125
出处:http://blog.csdn.net/juetion/article/details/17481039
