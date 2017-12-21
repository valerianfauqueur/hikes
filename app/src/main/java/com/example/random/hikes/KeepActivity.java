package com.example.random.hikes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KeepActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_keep);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            // Firebase
            Bundle bundle = new Bundle();

            bundle.putString("nav_bar_title", item.getTitle().toString());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);

            bundle.putString("date_time", currentDate);
            mFirebaseAnalytics.logEvent("nav_bar", bundle);
            // Firebase
            
            switch (item.getItemId()) {
                case R.id.navigation_announcement:
                    Intent feedIntent = new Intent(KeepActivity.this, FeedActivity.class);
                    startActivity(feedIntent);
                    return true;
                case R.id.navigation_keep:
                    return true;
                case R.id.navigation_health:
                    Intent healthIntent = new Intent(KeepActivity.this, HealthActivity.class);
                    startActivity(healthIntent);
                    return true;
            }

            return false;
        }
    };
}
