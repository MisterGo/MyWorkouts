package com.mistergo.myworkouts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //To change body of implemented methods use File | Settings | File Templates.
        //Log.d("myLogs", "DB OnCreate. Drop tables");
        //db.execSQL("drop table if exists myPeople");
        //db.execSQL("drop table if exists myWorkouts");

        Log.d("myLogs", "DB OnCreate. Create tables");
        db.execSQL("create table if not exists myPeople (_id integer primary key autoincrement, name text, actual integer default 1);");
        db.execSQL("create table if not exists myWorkouts (_id integer primary key autoincrement, date text, peopleId integer)");
        db.execSQL("create table if not exists myMonthCost (_id integer primary key autoincrement, yearNum integer, monthNum integer, cost real)");
        //db.execSQL("create table if not exists myEvents (_id integer primary key autoincrement, peopleId integer, )");

        /*
            Log.d("myLogs", "Delete table");
        db.delete("myWorkouts", null, null);
        ContentValues values = new ContentValues();
        values.put("date", "21/11/2013");
        values.put("peopleId", 1);
            Log.d("myLogs", "Insert 1");
        db.insert("myWorkouts", null,  values);
        values.clear();
        values.put("date", "22/11/2013");
        values.put("peopleId", 2);
            Log.d("myLogs", "Insert 2");
        db.insert("myWorkouts", null,  values);
        values.clear();
        values.put("date", "23/11/2013");
        values.put("peopleId", 3);
            Log.d("myLogs", "Insert 3");
        db.insert("myWorkouts", null,  values);
        values.clear();
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i1, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
        if (i1 != i2) {
            Log.d("myLogs", "DB OnUpdate");
            db.execSQL("drop table if exists myPeople");
            db.execSQL("drop table if exists myWorkouts");
            db.execSQL("drop table if exists myMonthCost");
            onCreate(db);
        }
    }
}
