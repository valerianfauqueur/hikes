package com.example.random.hikes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HealthActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_health);
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
                    Intent feedIntent = new Intent(HealthActivity.this, FeedActivity.class);
                    startActivity(feedIntent);
                    return true;
                case R.id.navigation_keep:
                    Intent keepIntent = new Intent(HealthActivity.this, KeepActivity.class);
                    startActivity(keepIntent);
                    return true;
                case R.id.navigation_health:
                    return true;
            }

            return false;
        }
    };
}
