package com.app.stefansjobs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper  {
    public static final String DB = "Jobs.db";
    public static final String TABLE_NAME = "jobs";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Name";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT, Salary DOUBLE)");

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(COL_2,"buy milk");

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(COL_2,"wash Car");

        ContentValues contentValues3 = new ContentValues();
        contentValues3.put(COL_2,"call plumber");

        sqLiteDatabase.insert(TABLE_NAME,null,contentValues1);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues2);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertTodo(String todo){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,todo);
        getWritableDatabase().insert(TABLE_NAME,null,contentValues);
        close();
    }

    public void delete(long id){
        String query =
                "DELETE FROM "+ TABLE_NAME+
                        " WHERE "+ COL_1 +" = "+id;
        getWritableDatabase().execSQL(query);
        close();
    }

    public  List<TodoJobs> getToDoList(){
        String query = "SELECT * FROM "+ TABLE_NAME;
        List<TodoJobs> todoJobsList = new ArrayList<TodoJobs>();

        Cursor cursor = getReadableDatabase().rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do
            {
                TodoJobs todoJobs = new TodoJobs();
                todoJobs.setId(cursor.getLong(0));
                todoJobs.setName(cursor.getString(1));
                todoJobsList.add(todoJobs);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return todoJobsList;
    }
}
