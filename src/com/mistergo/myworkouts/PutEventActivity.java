package com.mistergo.myworkouts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PutEventActivity extends Activity {
    Activity myActivity = this;
    Button saveBtn;
    //    TextView txt;
    DatePicker picker;
    ListView myListView;
    Cursor cursor;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.putevent);
        picker = (DatePicker) findViewById(R.id.putEvent_datePicker);
        saveBtn = (Button) findViewById(R.id.putEvent_button);
        myListView = (ListView) findViewById(R.id.putEvent_workout);
        refreshList();

        Intent intent = getIntent();
        int mDay = intent.getIntExtra("mDay", -1);
        int mYear = intent.getIntExtra("mYear", -1);
        int mMonth = intent.getIntExtra("mMonth", -1);

        DatePicker.OnDateChangedListener lPicker = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
                refreshList();
                Toast.makeText(myActivity, "Date changed!" + i + "/" + (i2+1) + "/" + i3, Toast.LENGTH_SHORT).show();
                Log.d(Global.ML, "Date changed!" + i + "/" + i2 + "/" + i3);
            }
        };

        if (mDay != -1) {
//            picker.init(2013, 11, 20, lPicker);
            Toast.makeText(myActivity, "Date changed!" + mDay + "/" + mMonth + "/" + mYear, Toast.LENGTH_SHORT).show();
            picker.init(mYear, mMonth - 1, mDay, lPicker);
            refreshList();
        }
//        btn.setOnClickListener(btnLs);
    }

    private void refreshList() {
        int pDay = picker.getDayOfMonth();
        int pMonth = picker.getMonth() + 1;
        int pYear = picker.getYear();

        cursor = Global.dataSource.getPeoplesForWorkout(pDay, pMonth, pYear);
        //ListAdapter adapter = new WorkoutAdapter(this, cursor, pDay + "/" + pMonth + "/" + pYear);
        //myListView.setAdapter(adapter);

    }

    View.OnClickListener btnLs = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Date date = new Date(picker.getYear() - 1900, picker.getMonth(), picker.getDayOfMonth());
            //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            //Intent intent = new Intent(myActivity, WorkoutActivity.class);
            Log.d(Global.ML, "----- " + picker.getDayOfMonth() + " " + picker.getMonth() + " " + picker.getYear());
            //intent.putExtra("day", String.format("%02d", picker.getDayOfMonth()));     //Integer.toString(picker.getDayOfMonth()));
            //intent.putExtra("month", String.format("%02d", picker.getMonth() + 1));       //Integer.toString(picker.getMonth() + 1));
            //intent.putExtra("year", Integer.toString(picker.getYear()));
            //startActivityForResult(intent, 1);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);    //To change body of overridden methods use File | Settings | File Templates.
        if (requestCode == 1) {
            Log.d(Global.ML, "requestCode = " + requestCode);
            finish();
        }
    }
}