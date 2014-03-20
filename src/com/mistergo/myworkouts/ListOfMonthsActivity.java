package com.mistergo.myworkouts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ListOfMonthsActivity extends Activity {
    //final String ML = "myLogs";
    ListView myListView;
    //DBDataSource dataSource;
    Cursor cursor;
    String[] strFrom = {"yearNum", "monthNum", "cost"};
    int[] strTo = {R.id.listOfMonthsYearText, R.id.listOfMonthsMonthText, R.id.listOfMonthsCostText};

    @Override
    protected void onRestart() {
        super.onRestart();
        this.onCreate(null);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity myActivity = this;
        Log.d(Global.ML, "SetContentView");
        this.setContentView(R.layout.listofmonths);

        Log.d(Global.ML, "Try to get myListView");
        myListView = (ListView) findViewById(R.id.myListOfMonths);
        /*
        TextView mv = (TextView) findViewById(R.id.listOfMonthsMonthText);
        int ms = Integer.parseInt(mv.getText().toString());
        Log.d(ML, "-- ms = " + ms + ", formatted");
        //mv.setText(String.format("%02d", ms));
        */
        Log.d(Global.ML, "new DBDataSource(this)");
        //dataSource = new DBDataSource(this);
        //dataSource.open();
        Log.d(Global.ML, "new cursor");
        cursor = Global.dataSource.getMyMonth(null, null);
        //startManagingCursor(cursor);

        Log.d(Global.ML, "Get Adapter");
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.monthcost, cursor, strFrom, strTo);


        Log.d(Global.ML, "setListAdapter");
        myListView.setAdapter(adapter);
        //setListAdapter(adapter);

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor.moveToPosition(i);
                MyMonth mm = new MyMonth(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getFloat(3));
                delRecord(mm);
                return true;
            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(myActivity, AddMonthActivity.class);
                intent.putExtra("mId", cursor.getInt(0));
                intent.putExtra("mYear", cursor.getInt(1));
                intent.putExtra("mMonth", cursor.getInt(2));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        //dataSource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "Добавить запись");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, AddMonthActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void delRecord(MyMonth month) {
        final MyMonth m = month;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Удаление");
        builder.setMessage("Удалить запись?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Global.dataSource.deleteMyMonth(m);
                Log.d(Global.ML, "Deleted item: " + m.getMonth());
                dialog.dismiss();
                cursor.requery();
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