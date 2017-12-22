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

    private final Context context;
    private List<AnnouncementInfo> announcements;
    private RecyclerView mRecyclerView;

    public static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
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
        }
    }

    CardAdapter(List<AnnouncementInfo> announcements, Context context){
        this.announcements = announcements;
        this.context = context;
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
                AnnouncementInfo announcement = announcements.get(itemPosition);
                Context mContext = (Context) v.getContext();
                Intent announcementIntent = new Intent(mContext, AnnouncementActivity.class);
                announcementIntent.putExtra("announcement", announcement);

                FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
                Bundle bundle = new Bundle();
                bundle.putString("title_annonce", announcement.title);
                bundle.putString("price_day_annonce", announcement.price);
                bundle.putString("distance_map_annonce", announcement.distance);
                bundle.putString("stars_annonce", Integer.toString(announcement.rating));

                mFirebaseAnalytics.logEvent("annonce_content", bundle);
                mContext.startActivity(announcementIntent);
            }
        });
        return new AnnouncementViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AnnouncementViewHolder cardViewHolder, int i) {
        cardViewHolder.cardTitle.setText(announcements.get(i).title);
        cardViewHolder.cardPrice.setText(announcements.get(i).price);
        cardViewHolder.cardRating.setRating(announcements.get(i).rating);
        cardViewHolder.cardDist.setText(announcements.get(i).distance);
        cardViewHolder.cardImage.setBackgroundResource(context.getResources().getIdentifier(announcements.get(i).animal, "drawable", "com.example.random.hikes"));
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }
}
