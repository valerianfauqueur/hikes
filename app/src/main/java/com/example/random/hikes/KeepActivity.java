package com.example.random.hikes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class KeepActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep);

        LinearLayout keepClick = (LinearLayout) findViewById(R.id.keep_click);

        keepClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context mContext = (Context) view.getContext();
                Intent monitoringIntent = new Intent(mContext, MonitoringActivity.class);
                mContext.startActivity(monitoringIntent);
            }
        });
    }
}
