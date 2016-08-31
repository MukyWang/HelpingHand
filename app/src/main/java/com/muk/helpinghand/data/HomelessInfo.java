package com.muk.helpinghand.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zelta on 8/05/16.
 */
public class HomelessInfo implements Parcelable{

    private String uid,location,need,note;
    private double latitude,longitude;

    public HomelessInfo(String uid, String location, String need, String note, double latitude, double longitude) {
        this.uid = uid;
        this.location = location;
        this.need = need;
        this.note = note;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected HomelessInfo (Parcel in){
        uid = in.readString();
        location = in.readString();
        need = in.readString();
        note = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public HomelessInfo() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public static final Creator<HomelessInfo> CREATOR = new Creator<HomelessInfo>() {
        @Override
        public HomelessInfo createFromParcel(Parcel in) {
            return new HomelessInfo(in);
        }

        @Override
        public HomelessInfo[] newArray(int size) {
            return new HomelessInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(uid);
        parcel.writeString(location);
        parcel.writeString(need);
        parcel.writeString(note);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);

    }
}
