package com.example.sy7;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {

    MyDatabaseHelper openHelper;
    SQLiteDatabase database;
    /**
     * 获得数据库
     * @return
     */
    @Override
    public boolean onCreate() {
        Log.d("--","provider create");
        openHelper = new MyDatabaseHelper(getContext(),"sy7.db", null, 1);
        database = openHelper.getWritableDatabase();
        if(database != null)
            return true;
        else
            return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return database.query("contacts",projection,selection,selectionArgs,null,null,sortOrder);
    }

    /**
     * 返回是一条数据还是多条数据
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType( @NonNull Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert( @NonNull Uri uri,  @Nullable ContentValues values) {
        database.insert("contacts",null,values);
        return uri;
    }

    @Override
    public int delete( @NonNull Uri uri,  @Nullable String selection,  @Nullable String[] selectionArgs) {
        return database.delete("contacts",selection,selectionArgs);
    }

    @Override
    public int update( @NonNull Uri uri,  @Nullable ContentValues values,  @Nullable String selection,  @Nullable String[] selectionArgs) {
        return database.update("contacts",values,selection,selectionArgs);
    }
}
