package com.example.music_player.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MusicDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;

    public static final String create_table1 = "create table paper (id integer primary key, name text)";
    public static final String create_table2 = "create table song (name text, path text, idOfPaper int, primary key (name, idOfPaper))";

    public MusicDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table1);
        db.execSQL(create_table2);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
