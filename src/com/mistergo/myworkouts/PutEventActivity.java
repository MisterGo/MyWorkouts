package com.mistergo.myworkouts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PutEventActivity extends Activity {
    Activity myActivity = this;
    Button saveBtn;
    DatePicker picker;
    ListView myListView;
    Cursor cursor;
    int pDay;
    int pMonth;
    int pYear;
    ArrayAdapter<Map<String, String>> adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.putevent);
        picker = (DatePicker) findViewById(R.id.putEvent_datePicker);
        saveBtn = (Button) findViewById(R.id.putEvent_button);
        myListView = (ListView) findViewById(R.id.putEvent_workout);
        //refreshList();

        Intent intent = getIntent();
        int mDay = intent.getIntExtra("mDay", -1);
        int mYear = intent.getIntExtra("mYear", -1);
        int mMonth = intent.getIntExtra("mMonth", -1);


        DatePicker.OnDateChangedListener lPicker = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
                refreshList();
                //Toast.makeText(myActivity, "Date changed!" + i + "/" + (i2+1) + "/" + i3, Toast.LENGTH_SHORT).show();
                //Log.d(Global.ML, "Date changed!" + i + "/" + i2 + "/" + i3);
            }
        };

        if (mDay != -1) {
            //Toast.makeText(myActivity, "Date changed!" + mDay + "/" + mMonth + "/" + mYear, Toast.LENGTH_SHORT).show();
            picker.init(mYear, mMonth, mDay, lPicker);
            refreshList();
        }
        myListView.setOnItemClickListener(itemLs);
        saveBtn.setOnClickListener(btnLs);
    }

    private void refreshList() {
        pDay = picker.getDayOfMonth();
        pMonth = picker.getMonth() + 1;
        pYear = picker.getYear();
        Map<String, String> m;
        ArrayList<Map<String, String>> myList = new ArrayList<Map<String, String>>();

//        Log.d(Global.ML, "--- RefreshList");
        cursor = Global.dataSource.getPeoplesForWorkoutNew(pDay, pMonth, pYear);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            m = new HashMap<String, String>();
            m.put("id",     cursor.getString(0));
            m.put("name",   cursor.getString(1));
            m.put("visit",  cursor.getString(2));
            myList.add(m);
//            Log.d(Global.ML, "--- " + m.toString());
            cursor.moveToNext();
        }
        adapter = new PutEventAdapter(myActivity, R.layout.woperson, myList);
        myListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        myListView.setAdapter(adapter);
    }

    ListView.OnItemClickListener itemLs = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (adapter.getItem(i).get("visit").equals("0")) {
                adapter.getItem(i).put("visit", "1");
            } else {
                adapter.getItem(i).put("visit", "0");
            }
        }
    };

    View.OnClickListener btnLs = new View.OnClickListener() {
        ArrayList<Map<String, String>> selectedItems = new ArrayList<Map<String, String>>();

        @Override
        public void onClick(View view) {
            SparseBooleanArray checked = myListView.getCheckedItemPositions();
            Log.d(Global.ML, "checked.size = " + checked.size());
            //ArrayList<Map<String, String>> selectedItems = new ArrayList<Map<String, String>>();
            for (int i=0; i<checked.size(); i++) {
                int pos = checked.keyAt(i);
                Log.d(Global.ML, "  pos = " + pos + ", valueat = " + checked.valueAt(i));
                if (checked.valueAt(i)) {
                    //Log.d(Global.ML, "  selectedItems.add" + adapter.getItem(pos));
                    selectedItems.add(adapter.getItem(pos));
                }
            }

            String pDate = String.format("%02d", pDay) + "/" + String.format("%02d", pMonth) + "/" + Integer.toString(pYear);

            /** удалим перед сохранением все записи за дату */
            /** таким образом можно и очистить текущую дату */
            Global.dataSource.deleteWorkoutsByDate(pDate);

//            Log.d(Global.ML, "---- pDate = " + pDate);
            for (int i=0; i<selectedItems.size(); i++) {
                Map<String, String> mm = selectedItems.get(i);
                Log.d(Global.ML, "----" + mm.toString());
                Global.dataSource.insertWorkout(mm.get("id"), pDate);
            }
            myActivity.finish();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);    //To change body of overridden methods use File | Settings | File Templates.
        if (requestCode == 1) {
            //Log.d(Global.ML, "requestCode = " + requestCode);
            finish();
        }
    }
}