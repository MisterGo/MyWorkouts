package com.mistergo.myworkouts;

import android.content.Context;

/**
 * Created by mistergo on 21.03.14.
 */
public class MyMonthArrayAdapter extends TwoLineArrayAdapter<MyMonth> {

    public MyMonthArrayAdapter(Context context, MyMonth[] myMonths) {
        super(context, myMonths);
    }

    @Override
    public String lineOneText(MyMonth myMonth) {
        return Integer.toString(myMonth.getMonth());
    }

    @Override
    public String lineTwoText(MyMonth myMonth) {
        return Integer.toString(myMonth.getYear());
    }
}
