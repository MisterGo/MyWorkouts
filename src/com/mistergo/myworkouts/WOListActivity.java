package com.mistergo.myworkouts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class WOListActivity extends Activity {
    final Activity myActivity = this;
    ListView myListView;
    Button addButton;
    Cursor cursor;
    String[] strFrom = {"date", "count"};
    int[] strTo = {R.id.woListItemLeft, R.id.woListItemRight};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity.setContentView(R.layout.wolistlayout);
        myListView  = (ListView) findViewById(R.id.woList_listView);
        addButton   = (Button) findViewById(R.id.woList_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myActivity, PutEventActivity.class);
                Calendar cal = Calendar.getInstance();
                intent.putExtra("mDay", cal.get(Calendar.DAY_OF_MONTH));
                intent.putExtra("mMonth", cal.get(Calendar.MONTH));
                intent.putExtra("mYear", cal.get(Calendar.YEAR));
                startActivity(intent);
            }
        });
        refreshList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshList();
    }

    private void refreshList() {
        ArrayList<Map<String, String>> myList = new ArrayList<Map<String, String>>();
        Map<String, String> m;

//        ArrayAdapter.createFromResource()

        //dataSource = new DBDataSource(this);
        //dataSource.open();
        Log.d(Global.ML, "ListOfWorkoutsActivity: new cursor");
        cursor = Global.dataSource.getListOfWorkouts();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            m = new HashMap<String, String>();
            m.put("date", cursor.getString(0));
            m.put("count", cursor.getString(1));
            myList.add(m);
            Log.d(Global.ML, "ListOfWorkoutsActivity: date = " + m.get("date") + ", count = " + m.get("count"));
            cursor.moveToNext();
        }

        Log.d(Global.ML, "ListOfWorkoutsActivity: Get Adapter");
        SimpleAdapter adapter = new SimpleAdapter(this, myList, R.layout.wolistitem, strFrom, strTo);

        Log.d(Global.ML, "ListOfWorkoutsActivity: setListAdapter");
        myListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, R.string.label_settings);
        menu.add(1, 1, 2, R.string.label_summary_list);
        return super.onCreateOptionsMenu(menu);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(Global.ML, Integer.toString(item.getOrder()));
        if (item.getOrder() == 1) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SummaryListActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }

}