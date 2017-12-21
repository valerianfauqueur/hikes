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

public class AnnouncementActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, AppCompatCallback {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private CardView card;
    private AnnouncementData announcementData;
    private AppCompatDelegate delegate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        delegate.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_announcement);
        initNavigateBack();

        announcementData = getIntent().getExtras().getParcelable("announcement");
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);

        card = (CardView) findViewById(R.id.card);

        //Adapt card style
        cardAdaptStyle();

        // Add dynamic announcement data
        initAnnouncementData();

    }

    private void initAnnouncementData() {

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

        announcementImage.setBackgroundResource(announcementData.cardPicture);
        cardTitle.setText(announcementData.cardTitle);
        cardRating.setRating(announcementData.cardRating);
        bottomRating.setRating(announcementData.cardRating);
        cardDist.setText(announcementData.cardDist);
        cardPrice.setText(announcementData.cardPrice);
        guardPrice.setText(announcementData.cardPrice +" €");
        animalAttributes1.setText(announcementData.animalAttributes.get(0));
        animalAttributes2.setText(announcementData.animalAttributes.get(1));
        animalAttributes3.setText(announcementData.animalAttributes.get(2));
        masterName.setText(announcementData.masterName);
        masterDescription.setText(announcementData.masterDescription);
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

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(announcementData.youtubeLink);
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
