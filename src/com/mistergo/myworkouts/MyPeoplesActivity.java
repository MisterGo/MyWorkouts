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
    private ArrayList<MyPeople> peoples;
    private ListView myListView;

    @Override
    protected void onRestart() {
        Global.myLog("MyPeoplesActivity onRestart");
        super.onRestart();
        this.onCreate(null);
    }

    @Override
    protected void onResume() {
        Global.myLog("MyPeoplesActivity onResume");
        super.onResume();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.listofpeoples);

        myListView = (ListView) findViewById(R.id.listOfPeoples);

        refreshList();

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Global.myLog("Position = " + ((MyPeople) myListView.getItemAtPosition(i)).getName());
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
        peoples = new ArrayList<MyPeople>();
        Cursor cursor = Global.dataSource.getMyPeople2(null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            boolean isActive = cursor.getInt(2) == 1;   //? true : false;
            peoples.add(new MyPeople(cursor.getInt(0), cursor.getString(1), isActive));
            cursor.moveToNext();
        }
        cursor.close();

        ListAdapter adapter = new MyPeopleAdapter(this, peoples);

        myListView.setAdapter(adapter);      //setListAdapter(adapter);
    }

    private void delRecord(MyPeople people) {
        final MyPeople p = people;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Удаление");
        builder.setMessage("Удалить человека из списка?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Global.dataSource.deleteMyPeople(p);
                Global.myLog("Deleted item: " + p.getName());
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