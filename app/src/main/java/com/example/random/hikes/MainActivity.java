package com.example.random.hikes;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.random.hikes.Utils;


import com.example.random.hikes.Utils;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    boolean isUserFirstTime;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(MainActivity.this, PREF_USER_FIRST_TIME, "true"));
        Intent introIntent = new Intent(MainActivity.this, OnboardingActivity.class);
        introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);
        Intent feedIntent = new Intent(MainActivity.this, FeedActivity.class);


        // Firebase
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        String osVersion = Build.VERSION.RELEASE;
        String appVersion = BuildConfig.VERSION_NAME;

        mFirebaseAnalytics.setUserProperty("osType", "Android");
        mFirebaseAnalytics.setUserProperty("appVersion", appVersion);
        mFirebaseAnalytics.setUserProperty("osVersion", osVersion);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        Bundle bundle = new Bundle();
        bundle.putString("date_time", currentDate);
        bundle.putString("osType", "Android");
        bundle.putString("appVersion", appVersion);
        bundle.putString("osVersion", osVersion);

       mFirebaseAnalytics.logEvent("open_app", bundle);
       //Firebase


        if (isUserFirstTime)
            startActivity(introIntent);

        if(!isUserFirstTime)
            startActivity(feedIntent);
    }
}