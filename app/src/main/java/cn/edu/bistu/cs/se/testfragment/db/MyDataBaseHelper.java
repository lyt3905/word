package cn.edu.bistu.cs.se.testfragment.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myword.db";

    /**
     * 数据库的版本号，以后要升级数据库，修改版本号为 +1 即可
     */
    private static final int DATABASE_VERSION = 1;

    private static MyDataBaseHelper instance;

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    /**
     * 单例模式
     * @param context 传入上下文
     * @return 返回MySQLiteOpenHelper对象
     */
    public static MyDataBaseHelper getInstance(Context context) {
        if (null == instance) {
            synchronized (MyDataBaseHelper.class) {
                if (null == instance) {
                    instance = new MyDataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
                }
            }
        }
        return instance;
    }

    // 构造方法不对外暴露
    private MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);
        Log.d("shujuku",5+"");
    }

    // 构造方法不对外暴露
    private MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE dict(" +
                "_id INTEGER PRIMARY KEY autoincrement ," +
                "word," +
                "mean_word," +
                "example_sentence)";

        db.execSQL(sql);

        String sql2 = "CREATE TABLE new(" +
                "_id INTEGER PRIMARY KEY autoincrement ," +
                "word," +
                "mean_word," +
                "example_sentence)";
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
