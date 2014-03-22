package com.mistergo.myworkouts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * Created by mistergo on 21.03.14.
 */
public class SummaryListActivity extends Activity {
    final Activity myActivity = this;
    ListView myListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity.setContentView(R.layout.summarylist);
        myListView  = (ListView) findViewById(R.id.summaryList);

        RefreshList();
    }

    private void RefreshList() {
        Cursor cursor = Global.dataSource.getMyMonth(null, null);
        MyMonth[] summaryList = new MyMonth[cursor.getCount()];
        Log.d(Global.ML, "Count = " + cursor.getCount());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(Global.ML, "IDX = " + cursor.getPosition() + ", ID = " + cursor.getInt(0) + ", LENGTH = " + summaryList.length);
            summaryList[cursor.getPosition()] = new MyMonth(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getFloat(3));
            cursor.moveToNext();
        }
        myListView.setAdapter(new MyMonthArrayAdapter(this, summaryList));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, R.string.label_settings);
        menu.add(1, 1, 2, "Summary");
        return super.onCreateOptionsMenu(menu);
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
        return super.onOptionsItemSelected(item);
    }
}