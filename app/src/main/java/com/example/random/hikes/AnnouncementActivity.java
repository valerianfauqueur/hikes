package com.example.random.hikes;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.analytics.FirebaseAnalytics;

public class AnnouncementActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, AppCompatCallback {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private CardView card;
    private AnnouncementInfo announcementInfo;
    private AppCompatDelegate delegate;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        delegate.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_announcement);
        initNavigateBack();

        announcementInfo = getIntent().getExtras().getParcelable("announcement");
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);

        card = (CardView) findViewById(R.id.card);

        //Adapt card style
        cardAdaptStyle();

        // Add dynamic announcement data
        initAnnouncementInfo();

        bindGuardClick();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    private void initAnnouncementInfo() {

        CardView card = (CardView) findViewById(R.id.card);
        ImageView announcementImage = (ImageView) findViewById(R.id.announcement_image);
        TextView cardTitle = (TextView) findViewById(R.id.card_title);
        RatingBar cardRating = (RatingBar) findViewById(R.id.card_rating);
        TextView cardDist = (TextView) findViewById(R.id.card_dist);
        TextView cardPrice = (TextView) findViewById(R.id.card_price);
        TextView animalAttributes1 = (TextView) findViewById(R.id.animal_attributes1);
        TextView animalAttributes2 = (TextView) findViewById(R.id.animal_attributes2);
        TextView animalAttributes3 = (TextView) findViewById(R.id.animal_attributes3);
        TextView masterName = (TextView) findViewById(R.id.master_name);
        TextView masterDescription = (TextView) findViewById(R.id.master_desc);
        YouTubePlayerView youtubePlayer = (YouTubePlayerView) findViewById(R.id.youtube_view);
        TextView guardPrice = (TextView) findViewById(R.id.guard_price);
        RatingBar bottomRating = (RatingBar) findViewById(R.id.bottom_rating);

        cardTitle.setText(announcementInfo.title);
        cardRating.setRating(announcementInfo.rating);
        bottomRating.setRating(announcementInfo.rating);
        cardDist.setText(announcementInfo.distance);
        cardPrice.setText(announcementInfo.price);
        guardPrice.setText(announcementInfo.price +" €");
        animalAttributes1.setText(announcementInfo.attributes.get(0));
        animalAttributes2.setText(announcementInfo.attributes.get(1));
        animalAttributes3.setText(announcementInfo.attributes.get(2));
        masterName.setText(announcementInfo.master);
        masterDescription.setText(announcementInfo.content);
        announcementImage.setBackgroundResource(getApplicationContext().getResources().getIdentifier(announcementInfo.animal, "drawable", "com.example.random.hikes"));
    }

    private void initNavigateBack() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        delegate.setSupportActionBar(toolbar);
        delegate.getSupportActionBar().setTitle("");
        delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        delegate.getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void cardAdaptStyle() {
        card.setCardElevation(0);
        card.setRadius(0);
        int layoutHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 226, getResources().getDisplayMetrics());
        int cardMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -3, getResources().getDisplayMetrics());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, layoutHeight);
        layoutParams.setMargins(cardMargin,cardMargin,cardMargin,0);
        card.setLayoutParams(layoutParams);
    }

    private void bindGuardClick() {
        Button guardBtn = findViewById(R.id.action_btn_guard);

        guardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title_annonce", announcementInfo.title);
                bundle.putString("price_day_annonce", announcementInfo.price);
                bundle.putString("distance_map_annonce", announcementInfo.distance);
                bundle.putString("stars_annonce", Integer.toString(announcementInfo.rating));

                mFirebaseAnalytics.logEvent("checkout_cart_annonce", bundle);

                Toast.makeText(getApplicationContext(), "Votre demande de garde a bien été envoyé", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(announcementInfo.youtube);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}
