package com.mistergo.myworkouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBDataSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allMyPeople = {"_id", "name", "actual"};
    private String[] allMyMonth = {"_id", "yearNum", "monthNum", "cost"};
    private String[] columnsMyPeople = {"name", "actual"};
    private String[] allMyWorkouts = {"_id", "date", "peopleId"};

    public DBDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        //database.delete("myWorkouts", null, null);
        //dbHelper.onUpgrade(database, 0, 0);
        //dbHelper.onCreate(database);
        /*
        ContentValues values = new ContentValues();
        values.put("date", "21/11/2013");
        values.put("peopleId", 1);
        Log.d("myLogs", "Insert 1");
        database.insert("myWorkouts", null,  values);
        values.clear();
        values.put("date", "21/11/2013");
        values.put("peopleId", 2);
        database.insert("myWorkouts", null,  values);
        values.clear();
        values.put("date", "22/11/2013");
        values.put("peopleId", 2);
        database.insert("myWorkouts", null,  values);
        */
    }

    public void close() {
        dbHelper.close();
    }

    public MyPeople createMyPeople(String pName) {
        ContentValues values = new ContentValues();
        values.put("name", pName);
        values.put("actual", 1);
        long insertId = database.insert("myPeople", null, values);

        Cursor cursor = database.query("myPeople", allMyPeople, "_id = " + insertId, null, null, null, null);
        cursor.moveToFirst();

        MyPeople newPeople = new MyPeople(cursor.getInt(0), pName, true);

        cursor.close();
        return newPeople;
    }

    public void updateMyPeopleActual(MyPeople pPeople) {
        int act = pPeople.getActual() ? 1 : 0;
        ContentValues values = new ContentValues();
        values.put("actual", act);
        database.update("myPeople", values, "_id = " + pPeople.getId(), null);
    }

    public void deleteMyPeople(MyPeople pPeople) {
        int id = pPeople.getId();
        database.delete("myPeople", "_id = " + id, null);
    }

    public Cursor getMyPeople2(String[] pColumns, String pWhere) {
        if (pColumns == null) pColumns = allMyPeople;
        return database.query("myPeople", pColumns, pWhere, null, null, null, null);
    }

    public List<MyPeople> getMyPeople(String[] pColumns, String pWhere){
        List<MyPeople> peoples = new ArrayList<MyPeople>();
        boolean bool;

        if (pColumns == null) pColumns = allMyPeople;
        Cursor cursor = database.query("myPeople", pColumns, pWhere, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            //if (cursor.getInt(2) == 0) bool = false; else bool = true;
            bool = cursor.getInt(2) == 0;
            MyPeople people = new MyPeople(cursor.getInt(0), cursor.getString(1), bool);
            peoples.add(people);
            cursor.moveToNext();
        }
        cursor.close();
        return peoples;
    }

    public MyMonth createMyMonth(int year, int month, float cost) {
        ContentValues values = new ContentValues();
        values.put("yearNum", year);
        values.put("monthNum", month);
        values.put("cost", cost);

        long insertId = database.insert("myMonthCost", null, values);
        Cursor cursor = database.query("myMonthCost", allMyMonth, "_id=" + insertId, null, null, null, null);
        cursor.moveToFirst();

        MyMonth newMonth = new MyMonth(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getFloat(3));
        cursor.close();

        return newMonth;
    }

    public void updateMyMonth(MyMonth pMonth) {
        ContentValues values = new ContentValues();
        values.put("yearNum", pMonth.getYear());
        values.put("monthNum", pMonth.getMonth());
        values.put("cost", pMonth.getCost());
        database.update("myMonthCost", values, "_id = " + pMonth.getId(), null);
    }

    public void deleteMyMonth(MyMonth pMonth) {
        int id = pMonth.getId();
        database.delete("myMonthCost", "_id = " + id, null);
    }

    public Cursor getMyMonth(String[] pColumns, String pWhere) {
        if (pColumns == null) pColumns = allMyMonth;
        return database.query("myMonthCost", pColumns, pWhere, null, null, null, null);
    }

    public Cursor getListOfWorkouts() {    //(String[] pColumns, String pWhere, String pGrouping) {
        //String colArr[] = {"date", "count(*)"};
        //return database.query("myWorkouts", colArr, null, null, "date", null, null);
        return database.rawQuery("select date, count(*)" +
                "                   from myWorkouts" +
                "                   group by date"
                , null);
    }

    public Cursor getPeoplesForWorkout(String d, String m, String y) {
        String tables = "myPeople as MP left outer join myWorkouts MW on MW.peopleId = MP._id and MW.date = '" + d + "/" + m + "/" + y + "'";
        //String[] columns = {"MP._id as mp", "MP.name as name", "MP.actual as actual", "MW._id as mw"};
        String[] columns = {"MP.name", "MW._id", "MP._id"};
        String selection = "MP.actual = 1";// and MW.date = " + d + "/" + m + "/" + y;
        return database.query(tables, columns, selection, null, null, null, null);
        //return  database.query("myWorkouts", columns, null, null, null, null, null);
    }

    public Cursor getPeoplesForWorkout(int d, int m, int y) {
        String tables = "myPeople as MP left outer join myWorkouts MW on MW.peopleId = MP._id and MW.date = '" + d + "/" + m + "/" + y + "'";
        //String[] columns = {"MP._id as mp", "MP.name as name", "MP.actual as actual", "MW._id as mw"};
        String[] columns = {"MP.name", "MW._id", "MP._id"};
        String selection = "MP.actual = 1";// and MW.date = " + d + "/" + m + "/" + y;
        return database.query(tables, columns, selection, null, null, null, null);
        //return  database.query("myWorkouts", columns, null, null, null, null, null);
    }

    public Cursor getPeoplesForWorkoutNew(int d, int m, int y) {
        //String[] columns = {"MP.name", "select count(*) from "};
        //String tables = "myPeople as MP left outer join ";
        //String selection = "";
        //Log.d(Global.ML, "getPeoplesForWorkoutNew: " + String.format("%02d", d) + "/" + String.format("%02d", m) + "/" + y);
        return database.rawQuery("select MP._id, MP.name, " +
                "                       (select count(*) " +
                "                       from myWorkouts MV " +
                "                       where MV.date = '" + String.format("%02d", d) + "/" + String.format("%02d", m) + "/" + y + "'" +
                "                       and MV.peopleId = MP._id" +
                "                       limit 1" +
                "                       )" +
                "               from myPeople MP" +
                "               where MP.actual = 1", null);

    }

    public void insertWorkout(String pId, String pDate) {
        //String[] columns = {"date", "peopleId"};
        //String where = "peopleId = " + pId; //"date = " + pDate + " and peopleId = " + pId;
        //Log.d(Global.ML, ">>>>> Cursor WHERE is " + where + ", cols = " + columns.toString());
        //Cursor cursor = database.query("myWorkouts", columns, where, null, null, null, null);
        Cursor cursor = database.rawQuery("select date, peopleId" +
                "                           from myWorkouts" +
                "                           where date = '" + pDate + "' and peopleId = " + pId
                , null);
        Log.d(Global.ML, ">>>>> Cursor count = " + cursor.getCount());
        if (!(cursor.getCount() > 0)) {
            Log.d(Global.ML, ">>>>> Inserting: (" + pId + ", '" + pDate + "')");
            /*
            database.rawQuery("insert into myWorkouts" +
                    "           (peopleId, date)" +
                    "           values (" + pId + ", '" + pDate + "')"
                    , null);
*/
            ContentValues values = new ContentValues();
            values.put("peopleId", pId);
            values.put("date", pDate);
            database.insert("myWorkouts", null, values);
        }
    }

    public void deleteWorkout(int pId) {
        database.delete("myWorkouts", "_id = " + pId, null);
    }

    public void deleteWorkoutsByDate(String date) {
        database.delete("myWorkouts", "date = '" + date + "'", null);
    }
}
