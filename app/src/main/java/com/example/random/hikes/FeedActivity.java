package com.example.random.hikes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private List<AnnouncementData> announcements;
    private RecyclerView mRecyclerView;
    private FirebaseAnalytics mFirebaseAnalytics;

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

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        bindDoneSearchEvent();
    }


    private void bindDoneSearchEvent() {
        EditText searchInput = (EditText) findViewById(R.id.search_input);

        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    Bundle bundle = new Bundle();
                    bundle.putString("text_content_research", v.getText().toString());
                    mFirebaseAnalytics.logEvent("content_research", bundle);
                    v.clearFocus();
                    Context mContext = getApplicationContext();
                    InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
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
}
