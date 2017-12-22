package com.example.random.hikes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private TextView mTextMessage;
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

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://gist.githubusercontent.com/anonymous/af5f008560cdec72a0e081b680dd9424/raw/cb4e764abb14a9cf58fe4c889eb84d3a36b1fc03/6737630a-e698-11e7-90ec-65715b0a3c26.json";
        JsonObjectRequest announcementsRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Gson gson = new Gson();
                            JSONArray jsonArray = response.optJSONArray("posts");
                            Log.d("json", jsonArray.toString());
                            AnnouncementInfo[] announcements = gson.fromJson(jsonArray.toString(), AnnouncementInfo[].class);
                            TextView announcementNumber = (TextView) findViewById(R.id.announcement_number);
                            announcementNumber.setText(Integer.toString(announcements.length));
                            CardAdapter mAdapter = new CardAdapter(Arrays.asList(announcements), getApplicationContext());
                            mRecyclerView.setAdapter(mAdapter);


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Request error", error.toString());
                    }
                }
        );

        queue.add(announcementsRequest);
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
