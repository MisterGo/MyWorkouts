package com.mistergo.myworkouts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

public class AddMonthActivity extends Activity {
    Activity activity = this;
    final String ML = "myLogs";
    DBDataSource dataSource;
    DatePicker datePicker;
    EditText txt;
    Button btn;
    float cost;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmonth);
        dataSource = new DBDataSource(activity);
        dataSource.open();

        activity.setTitle("Add Month");

        txt = (EditText) findViewById(R.id.addMonthCost);
        btn = (Button) findViewById(R.id.addMonthSaveButton);
        datePicker = (DatePicker) findViewById(R.id.addMonthDate);

        TextView tv = (TextView) findViewById(R.id.temporaryText);

        Intent intent = getIntent();
        int mId = intent.getIntExtra("mId", -1);
        int mYear = intent.getIntExtra("mYear", -1);
        int mMonth = intent.getIntExtra("mMonth", -1);
        Log.d(ML, "--- mId = " + mId + ", mYear = " + mYear + ", mMonth = " + mMonth);

        DatePicker.OnDateChangedListener lPicker = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
                setDefaultCost(datePicker.getYear(), datePicker.getMonth() + 1);
            }
        };

        if (mId != -1) {
            datePicker.init(mYear, mMonth - 1, 1, lPicker);
            setDefaultCost(mYear,  mMonth);
        }

        // уберем день из DatePicker, чтобы выбирать только месяц и год
        findAndHideField(datePicker, "mDaySpinner");
        findAndHideField(datePicker, "mDayPicker");

        /*
        try {
            Field f[] = datePicker.getClass().getDeclaredFields();
            String ss = "~";
            for (Field field : f) {
                //Log.d(ML, "--FIELD: " + field.getName());
                ss = ss + field.getName() + "; ";
                if (field.getName().equals("mDaySpinner") || (field.getName().equals("mDayPicker"))) {
                    field.setAccessible(true);
                    Object dayPicker = new Object();
                    dayPicker = field.get(datePicker);
                    ((View) dayPicker).setVisibility(View.GONE);
                }
            }
            tv.setText(ss);
        } catch (SecurityException e) {
            Log.d(ML, "----SECURITY EXCEPTION---- : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            Log.d(ML, "----ILLEGAL ARGUMENTS EXCEPTION---- : " + e.getMessage());
        } catch (IllegalAccessException e) {
            Log.d(ML, "----ILLEGAL ACCESS EXCEPTION---- : " + e.getMessage());
        }
        */

        View.OnClickListener lBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insMonth();
                activity.finish();
            }
        };
        btn.setOnClickListener(lBtn);
    }

    /** find a member field by given name and hide it */
    private void findAndHideField(DatePicker datepicker, String name) {
        try {
            Field field = DatePicker.class.getDeclaredField(name);
            field.setAccessible(true);
            View fieldInstance = (View) field.get(datepicker);
            fieldInstance.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDefaultCost(int y, int m) {
        Cursor c = dataSource.getMyMonth(null, "yearNum = " + y + " and monthNum = " + m);
        if (c.getCount() > 0) {
            c.moveToFirst();
            Log.d(ML, "-- mm = " + c.getInt(2) + ", cc = " + c.getFloat(3));
            txt.setText(String.valueOf(c.getFloat(3)), TextView.BufferType.EDITABLE);
        } else {
            txt.setText("0", TextView.BufferType.EDITABLE);
        }
    }

    private void insMonth() {
        int y = datePicker.getYear();
        int m = datePicker.getMonth() + 1;

        String str = txt.getText().toString();
        if (str.equals("")) str = "0";
        cost = Float.parseFloat(str);

        Cursor c = dataSource.getMyMonth(null, "yearNum = " + y + " and monthNum = " + m);
        if (c.getCount() > 0) {
            c.moveToFirst();
            MyMonth myMonth = new MyMonth(c.getInt(0), c.getInt(1), c.getInt(2), cost);
            dataSource.updateMyMonth(myMonth);
        } else {
            dataSource.createMyMonth(datePicker.getYear(), datePicker.getMonth() + 1, cost);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        dataSource.close();
    }
}