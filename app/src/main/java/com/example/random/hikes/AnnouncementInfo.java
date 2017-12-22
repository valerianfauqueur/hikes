package com.example.random.hikes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class AnnouncementInfo implements Parcelable {
    public String title;
    public Integer rating;
    public String distance;
    public String price;
    public List<String> attributes;
    public String master;
    public String youtube;
    public String animal;
    public String content;

    protected AnnouncementInfo(Parcel in) {
        title = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readInt();
        }
        distance = in.readString();
        price = in.readString();
        attributes = in.createStringArrayList();
        master = in.readString();
        youtube = in.readString();
        animal = in.readString();
        content = in.readString();
    }

    public static final Creator<AnnouncementInfo> CREATOR = new Creator<AnnouncementInfo>() {
        @Override
        public AnnouncementInfo createFromParcel(Parcel in) {
            return new AnnouncementInfo(in);
        }

        @Override
        public AnnouncementInfo[] newArray(int size) {
            return new AnnouncementInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        if (rating == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(rating);
        }
        parcel.writeString(distance);
        parcel.writeString(price);
        parcel.writeStringList(attributes);
        parcel.writeString(master);
        parcel.writeString(youtube);
        parcel.writeString(animal);
        parcel.writeString(content);
    }
}
