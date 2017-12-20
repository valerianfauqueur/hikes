package com.example.random.hikes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.AnnouncementViewHolder> {


    public static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
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
            context = (Context) itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent;
                    intent =  new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    List<AnnouncementCard> announcements;

    CardAdapter(List<AnnouncementCard> announcements){
        this.announcements = announcements;
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    @Override
    public AnnouncementViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        return new AnnouncementViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AnnouncementViewHolder cardViewHolder, int i) {
        cardViewHolder.cardTitle.setText(announcements.get(i).cardTitle);
        cardViewHolder.cardPrice.setText(announcements.get(i).cardPrice);
        cardViewHolder.cardRating.setRating(announcements.get(i).cardRating);
        cardViewHolder.cardDist.setText(announcements.get(i).cardDist);
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
