package com.mistergo.myworkouts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class SummaryListActivity extends Activity {
    final Activity myActivity = this;
    ListView myListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity.setContentView(R.layout.summarylist);
        myListView  = (ListView) findViewById(R.id.summaryList);

        refreshList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshList();
    }

    private void refreshList() {
        Cursor cursor = Global.dataSource.getMyMonthsSummary();
        final String[] summaryList = new String[cursor.getCount()];
        Global.myLog("Count = " + cursor.getCount());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Global.myLog("IDX = " + cursor.getPosition() + ", ID = " + cursor.getInt(0) + ", LENGTH = " + summaryList.length);
            summaryList[cursor.getPosition()] = cursor.getString(0) + "/" + cursor.getString(1);
            cursor.moveToNext();
        }
        //myListView.setAdapter(new MyMonthArrayAdapter(this, summaryList));
        myListView.setAdapter(new SummaryAdapter(this, summaryList));

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Global.myLog("i = " + i + ", str = " + summaryList[i]);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, R.string.label_settings);
        //menu.add(1, 1, 2, "Summary");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Global.myLog(Integer.toString(item.getOrder()));
        if (item.getOrder() == 1) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        //} else {
        //    Intent intent = new Intent(this, SummaryListActivity.class);
        //    startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}