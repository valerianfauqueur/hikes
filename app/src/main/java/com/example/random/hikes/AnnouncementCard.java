package com.example.random.hikes;

import android.os.Parcel;
import android.os.Parcelable;

public class AnnouncementCard implements Parcelable {
    String cardTitle;
    Integer cardRating;
    String cardDist;
    String cardPrice;


    AnnouncementCard(String cardTitle, int cardRating, String cardDist, String cardPrice) {
        this.cardTitle = cardTitle;
        this.cardRating = cardRating;
        this.cardDist = cardDist;
        this.cardPrice = cardPrice;
    }

    protected AnnouncementCard(Parcel in) {
        cardTitle = in.readString();
        if (in.readByte() == 0) {
            cardRating = null;
        } else {
            cardRating = in.readInt();
        }
        cardDist = in.readString();
        cardPrice = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cardTitle);
        if (cardRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(cardRating);
        }
        dest.writeString(cardDist);
        dest.writeString(cardPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnnouncementCard> CREATOR = new Creator<AnnouncementCard>() {
        @Override
        public AnnouncementCard createFromParcel(Parcel in) {
            return new AnnouncementCard(in);
        }

        @Override
        public AnnouncementCard[] newArray(int size) {
            return new AnnouncementCard[size];
        }
    };
}