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
import java.util.Arrays;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private List<AnnouncementData> announcements;
    private RecyclerView mRecyclerView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_announcement:
                    return true;
                case R.id.navigation_keep:
                    Intent keepIntent = new Intent(FeedActivity.this, KeepActivity.class);
                    startActivity(keepIntent);
                    return true;
                case R.id.navigation_health:
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

        mRecyclerView = (RecyclerView) findViewById(R.id.announcement_rv);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        initializeData();

        TextView announcementNumber = (TextView) findViewById(R.id.announcement_number);
        announcementNumber.setText(Integer.toString(announcements.size()));
        CardAdapter mAdapter = new CardAdapter(announcements);
        mRecyclerView.setAdapter(mAdapter);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_announcement);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initializeData(){
        announcements = new ArrayList<>();
        List<String> animalAttributes = Arrays.asList("Labrador", "Gentil", "Docile");

        for(int l = 20, i = 0; i < l; i++) {
            announcements.add(
                    new AnnouncementData(
                            "Pinpin le pingouin",
                            3,
                            "56",
                            "15",
                            animalAttributes,
                            "Aurélien Marrast",
                            "Chambre double et simple dans une belle maison sont disponibles à Baron's Court." +
                                    " À distance de marche de la Tamise à Hammersmith avec de grands pubs anglais traditionnels," +
                                    " du théâtre et des restaurants. La station de métro se trouve à 7 minutes à pied, à 16 minutes en " +
                                    "métro de Piccadilly Circus (Westend / Soho) à 8-10 minutes de Knightsbridge (Harrods) et de Hyde Park.",
                            "hCyTyXDkZ9M",
                            R.drawable.penguin,
                            "penguin")
            );
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }
}
