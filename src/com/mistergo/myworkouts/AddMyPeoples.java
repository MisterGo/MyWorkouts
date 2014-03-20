package com.mistergo.myworkouts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMyPeoples extends Activity {
    Activity activity = this;
    EditText txt;
    Button btn;
    private DBDataSource dataSource;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpeople);

        txt = (EditText) findViewById(R.id.addPeopleName);
        btn = (Button) findViewById(R.id.addPeopleButton);
        dataSource = new DBDataSource(this);

        View.OnClickListener btnLs = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = ((txt.getText().toString()).equals("")) ? "null name" : txt.getText().toString();
                dataSource.open();
                dataSource.createMyPeople(newName);
                dataSource.close();
                activity.finish();
            }
        };
        btn.setOnClickListener(btnLs);
        //this.finish();
    }

}