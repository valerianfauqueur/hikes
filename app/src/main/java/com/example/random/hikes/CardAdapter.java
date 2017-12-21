package com.example.random.hikes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.AnnouncementViewHolder> {

    private List<AnnouncementData> announcements;
    private RecyclerView mRecyclerView;

    public static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        ImageView cardImage;
        CardView card;
        TextView cardTitle;
        RatingBar cardRating;
        TextView cardDist;
        TextView cardPrice;

        AnnouncementViewHolder(View itemView) {
            super(itemView);
            card = (CardView)itemView.findViewById(R.id.card);
            cardTitle = (TextView) itemView.findViewById(R.id.card_title);
            cardRating = (RatingBar)itemView.findViewById(R.id.card_rating);
            cardDist = (TextView)itemView.findViewById(R.id.card_dist);
            cardPrice = (TextView)itemView.findViewById(R.id.card_price);
            cardImage = (ImageView) itemView.findViewById(R.id.announcement_image);
            context = (Context) itemView.getContext();
        }
    }

    CardAdapter(List<AnnouncementData> announcements){
        this.announcements = announcements;
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    @Override
    public AnnouncementViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = mRecyclerView.getChildLayoutPosition(v);
                AnnouncementData announcement = announcements.get(itemPosition);
                Context mContext = (Context) v.getContext();
                Intent announcementIntent = new Intent(mContext, AnnouncementActivity.class);
                announcementIntent.putExtra("announcement", announcement);

                FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
                Bundle bundle = new Bundle();
                bundle.putString("title_annonce", announcement.cardTitle);
                bundle.putString("price_day_annonce", announcement.cardPrice);
                bundle.putString("distance_map_annonce", announcement.cardDist);
                bundle.putString("stars_annonce", Integer.toString(announcement.cardRating));

                mFirebaseAnalytics.logEvent("annonce_content", bundle);
                mContext.startActivity(announcementIntent);
            }
        });
        return new AnnouncementViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AnnouncementViewHolder cardViewHolder, int i) {
        cardViewHolder.cardTitle.setText(announcements.get(i).cardTitle);
        cardViewHolder.cardPrice.setText(announcements.get(i).cardPrice);
        cardViewHolder.cardRating.setRating(announcements.get(i).cardRating);
        cardViewHolder.cardDist.setText(announcements.get(i).cardDist);
        cardViewHolder.cardImage.setBackgroundResource(announcements.get(i).cardPicture);
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }
}
