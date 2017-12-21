package com.example.random.hikes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class AnnouncementData implements Parcelable {
    String cardTitle;
    Integer cardRating;
    String cardDist;
    String cardPrice;
    List<String> animalAttributes;
    String masterName;
    String masterDescription;
    String youtubeLink;
    Integer cardPicture;
    String animal;

    AnnouncementData(String cardTitle,
                     int cardRating,
                     String cardDist,
                     String cardPrice,
                     List<String> animalAttributes,
                     String masterName,
                     String masterDescription,
                     String youtubeLink,
                     int cardPicture,
                     String animal)
    {
        this.cardTitle = cardTitle;
        this.cardRating = cardRating;
        this.cardDist = cardDist;
        this.cardPrice = cardPrice;
        this.animalAttributes = animalAttributes;
        this.masterName = masterName;
        this.masterDescription = masterDescription;
        this.youtubeLink = youtubeLink;
        this.cardPicture = cardPicture;
        this.animal = animal;
    }

    protected AnnouncementData(Parcel in) {
        cardTitle = in.readString();
        if (in.readByte() == 0) {
            cardRating = null;
        } else {
            cardRating = in.readInt();
        }
        cardDist = in.readString();
        cardPrice = in.readString();
        animalAttributes = in.createStringArrayList();
        masterName = in.readString();
        masterDescription = in.readString();
        youtubeLink = in.readString();
        if (in.readByte() == 0) {
            cardPicture = null;
        } else {
            cardPicture = in.readInt();
        }
        animal = in.readString();
    }

    public static final Creator<AnnouncementData> CREATOR = new Creator<AnnouncementData>() {
        @Override
        public AnnouncementData createFromParcel(Parcel in) {
            return new AnnouncementData(in);
        }

        @Override
        public AnnouncementData[] newArray(int size) {
            return new AnnouncementData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cardTitle);
        if (cardRating == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(cardRating);
        }
        parcel.writeString(cardDist);
        parcel.writeString(cardPrice);
        parcel.writeStringList(animalAttributes);
        parcel.writeString(masterName);
        parcel.writeString(masterDescription);
        parcel.writeString(youtubeLink);
        if (cardPicture == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(cardPicture);
        }
        parcel.writeString(animal);
    }
}