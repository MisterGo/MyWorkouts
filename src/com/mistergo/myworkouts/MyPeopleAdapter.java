package com.mistergo.myworkouts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MyPeopleAdapter extends BaseAdapter {
    private ArrayList<MyPeople> objects;
    private Context context;
    LayoutInflater lInflater;

    public MyPeopleAdapter(Context context, ArrayList<MyPeople> objects) {
        //super(context, R.layout.mypeople, objects);
        this.context = context;
        this.objects = objects;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.mypeople, parent, false);
        }

        final MyPeople people = (MyPeople)getItem(position);
        ((TextView) view.findViewById(R.id.mypeoplelabel)).setText((people.getName().equals("")) ? "null name" : people.getName());
        //view.setId(people.getId());
        //Log.d("myLogs", "View ID = " + view.getId());
        CheckBox cb = (CheckBox) view.findViewById(R.id.mypeoplecheckbox);
        cb.setOnCheckedChangeListener(myCheck);
        cb.setTag(position);
        cb.setChecked(people.getActual());
        /*
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("myLogs", "OLOLO!!!!");
                delRecord(people);
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        */
        return view;
    }

    CompoundButton.OnCheckedChangeListener myCheck = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            MyPeople people = (MyPeople) getItem((Integer) compoundButton.getTag());
            people.setActual(isChecked);
        }
    };

}
