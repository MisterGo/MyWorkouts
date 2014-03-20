package com.mistergo.myworkouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity{
    final Activity myActivity = this;
    Button myButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Global.dataSource = new DBDataSource(this);
        Global.dataSource.open();

        myActivity.setContentView(R.layout.mainactivity);
        myButton = (Button) findViewById(R.id.button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myActivity, WOListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Global.dataSource.close();
    }

}
