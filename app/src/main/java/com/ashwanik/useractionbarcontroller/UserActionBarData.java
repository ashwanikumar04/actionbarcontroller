package com.ashwanik.useractionbarcontroller;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class UserActionBarData implements Parcelable {
    public static final Parcelable.Creator<UserActionBarData> CREATOR = new Parcelable.Creator<UserActionBarData>() {

        @Override
        public UserActionBarData createFromParcel(Parcel source) {
            Bundle bundle = source.readBundle();
            return new UserActionBarData(bundle.getString("message"));
        }

        @Override
        public UserActionBarData[] newArray(int size) {
            return new UserActionBarData[size];
        }
    };
    private String message;

    public UserActionBarData(String sMessage) {
        this.message = sMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        parcel.writeBundle(bundle);
    }
}

