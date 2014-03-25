package com.mistergo.myworkouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

public class PutEventAdapter extends ArrayAdapter<Map<String, String>>{

    private List<Map<String, String>> objects;
    private Context context;
    LayoutInflater inflater;

    public PutEventAdapter(Context context, int textViewResourceId, java.util.List<Map<String, String>> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Map<String, String> getItem(int position) {
        return objects.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewGroup parent2 = parent;

        final Map<String, String> currentData;
        currentData = objects.get(position);

        if (view == null) {
            view = inflater.inflate(R.layout.woperson, parent, false);
        }

        final CheckedTextView ctv = (CheckedTextView) view.findViewById(R.id.woPerson);
        ctv.setText(currentData.get("name"));

        /** хз почему так, прочитал на StackOverflow */
        ((ListView)parent).setItemChecked(position, !currentData.get("visit").equals("0"));

        View.OnClickListener myCheck = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentData.get("visit").equals("0")) {
                    currentData.put("visit", "1");
                } else {
                    currentData.put("visit", "0");
                }
                //ctv.setChecked(!ctv.isChecked());
                //boolean isChecked =((ListView)parent).isItemChecked(position);
                //((ListView)parent).setItemChecked(position, !isChecked);
            }
        };

        //ctv.setOnClickListener(myCheck);

        return view;
    }

}
