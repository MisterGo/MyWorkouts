package com.mistergo.myworkouts;

import android.util.Log;
import com.mistergo.myworkouts.DBDataSource;

public class Global {
    public static DBDataSource dataSource;
    public static String ML1 = "myLogs";
    //public static String[] mainActivityList = {"Настройки", "Тренировки", "Тестовая схема"};

    public static void myLog(String s) {
        Log.d(ML1, s);
    }

}
