package com.mistergo.myworkouts;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by mistergo on 01.04.14.
 */
public class SummaryAdapter extends TwoLineArrayAdapter<String> {
    Context context;

    public SummaryAdapter(Context context, String[] strings) {
        super(context, strings);
        this.context = context;
    }

    @Override
    public String lineOneText(String s) {
        return  s;
    }

    @Override
    public String lineTwoText(String s) {
        Cursor cursor1 = Global.dataSource.getMonthHours(s.substring(0, 2));
        cursor1.moveToFirst();

        Cursor cursor2 = Global.dataSource.getMyMonth(null, "monthNum = " + s.substring(0, 2));
        float cost;
        if (cursor2.getCount() == 0) {
            cost = 0;
        } else {
            cursor2.moveToFirst();
            cost = cursor2.getFloat(3);
        }

        return context.getResources().getString(R.string.summary_text1) +  ": " + cursor1.getInt(0) + "     " +
                context.getResources().getString(R.string.summary_text2) + ": " + cost;
    }
}
