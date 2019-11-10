package cn.edu.bistu.cs.se.myapplication;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    public static final int WORD_DIR = 0;

    public static final int WORD_ITEM = 1;

    public static final String AUTHORITY = "cn.edu.bistu.cs.se.myapplication.provider";

    private static UriMatcher uriMatcher;

    private MyDataBaseHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "dict", WORD_DIR);
        uriMatcher.addURI(AUTHORITY, "dict/#", WORD_ITEM);

    }




    public MyContentProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
                deletedRows = db.delete("dict", selection, selectionArgs);
                break;
            case WORD_ITEM:
                String bookId = uri.getPathSegments().get(1);
                deletedRows = db.delete("dict", "_id = ?", new String[] { bookId });
                break;

            default:
                break;
        }
        return deletedRows;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
            case WORD_ITEM:
                long newBookId = db.insert("dict", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/dict/" + newBookId);
                break;
            default:
                break;
        }
        return uriReturn;

    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper = new MyDataBaseHelper(getContext(), "myDict.db3",  1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=null;
        switch (uriMatcher.match(uri)){
            case WORD_DIR:
                cursor = db.query("dict", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case WORD_ITEM:
                String bookId = uri.getPathSegments().get(1);
                cursor = db.query("dict", projection, "_id = ?", new String[] { bookId }, null, null, sortOrder);

                break;
        }
        return cursor;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        // 更新数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
                updatedRows = db.update("dict", values, selection, selectionArgs);
                break;
            case WORD_ITEM:
                String bookId = uri.getPathSegments().get(1);
                updatedRows = db.update("dict", values, "_id = ?", new String[] { bookId });
                break;

            default:
                break;
        }
        return updatedRows;
    }
}
