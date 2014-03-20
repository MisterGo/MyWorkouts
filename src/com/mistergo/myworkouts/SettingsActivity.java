package com.mistergo.myworkouts;

//import android.*;
//import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.Toast;

public class SettingsActivity extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] listValues = new String[]{ "Люди", "Месяцы (стоимость)"};
        //setContentView(R.layout.Settings);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listValues);
        setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);    //To change body of overridden methods use File | Settings | File Templates.

        //String item = (String) getListAdapter().getItem(position);
        long ii = getListAdapter().getItemId(position);

        if (ii == 0) {
            //Toast.makeText(this, Long.toString(ii), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MyPeoplesActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ListOfMonthsActivity.class); //AddMonthActivity.class);//BlankActivity.class);
            startActivity(intent);
        }
    }
}

