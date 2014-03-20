package com.mistergo.myworkouts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MyPeoplesActivity extends Activity {
    //final String ML = "myLogs";

    //    private DBDataSource dataSource;
    private ArrayList<MyPeople> peoples;
    private ListView myListView;

    @Override
    protected void onRestart() {
        Log.d(Global.ML, "MyPeoplesActivity onRestart");
        super.onRestart();
        this.onCreate(null);
    }

    @Override
    protected void onResume() {
        Log.d(Global.ML, "MyPeoplesActivity onResume");
        super.onResume();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.listofpeoples);

        Log.d(Global.ML, "Try to get myListView");
        myListView = (ListView) findViewById(R.id.listOfPeoples);

        //dataSource.createMyPeople("Anton");
        //dataSource.createMyPeople("Ivan");

        refreshList();

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(Global.ML, "Position = " + ((MyPeople) myListView.getItemAtPosition(i)).getName());
                Toast.makeText(MyPeoplesActivity.this, "Item in position " + i + " clicked", Toast.LENGTH_LONG).show();
                delRecord((MyPeople) myListView.getItemAtPosition(i));
                //refreshList();
                return true;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (MyPeople mp : peoples) {
            Global.dataSource.updateMyPeopleActual(mp);
        }
//        dataSource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, R.string.menu_add_people);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, AddMyPeoples.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void refreshList() {
//        dataSource = new DBDataSource(this);
//        Global.dataSource.open();

        peoples = new ArrayList<MyPeople>();
        Cursor cursor = Global.dataSource.getMyPeople2(null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            boolean isActive = cursor.getInt(2) == 1;   //? true : false;
            peoples.add(new MyPeople(cursor.getInt(0), cursor.getString(1), isActive));
            cursor.moveToNext();
        }
        Log.d(Global.ML, "Refresh list. Count = " + cursor.getCount());
        cursor.close();

        ListAdapter adapter = new MyPeopleAdapter(this, peoples);
        Log.d(Global.ML, "Adapter ready");

        myListView.setAdapter(adapter);      //setListAdapter(adapter);
        Log.d(Global.ML, "SetListAdapter");
    }

    private void delRecord(MyPeople people) {
        final MyPeople p = people;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Удаление");
        builder.setMessage("Удалить человека из списка?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Global.dataSource.deleteMyPeople(p);
                Log.d("myLogs", "Deleted item: " + p.getName());
                dialog.dismiss();
                refreshList();
            }

        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}