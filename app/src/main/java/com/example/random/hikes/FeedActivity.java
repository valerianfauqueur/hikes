package com.example.random.hikes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private List<AnnouncementCard> announcements;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_announcement:
                    mTextMessage.setText(R.string.announcements_nav_title);
                    return true;
                case R.id.navigation_keep:
                    mTextMessage.setText(R.string.keep_nav_title);
                    Intent keepIntent = new Intent(FeedActivity.this, KeepActivity.class);
                    startActivity(keepIntent);
                    return true;
                case R.id.navigation_health:
                    mTextMessage.setText(R.string.health_nav_title);
                    Intent healthIntent = new Intent(FeedActivity.this, HealthActivity.class);
                    startActivity(healthIntent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.announcement_rv);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        initializeData();
        CardAdapter mAdapter = new CardAdapter(announcements);
        mRecyclerView.setAdapter(mAdapter);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initializeData(){
        announcements = new ArrayList<>();


        for(int l = 20, i = 0; i < l; i++) {
            announcements.add(
                    new AnnouncementCard("Rodolf le cerf", 3, "56", "15")
            );
        }


       // Intent i = new Intent(this, MainActivity.class);
       // i.putExtra("qsdqsd", announcements.get(0));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

}
