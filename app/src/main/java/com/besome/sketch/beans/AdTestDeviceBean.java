package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class AdTestDeviceBean implements Parcelable {
    public static final Parcelable.Creator<AdTestDeviceBean> CREATOR = new Parcelable.Creator<AdTestDeviceBean>() {
        @Override
        public AdTestDeviceBean createFromParcel(Parcel parcel) {
            return new AdTestDeviceBean(parcel);
        }

        @Override
        public AdTestDeviceBean[] newArray(int i) {
            return new AdTestDeviceBean[i];
        }
    };
    @Expose
    public String deviceId;

    public AdTestDeviceBean() {
        this("");
    }

    public AdTestDeviceBean(String str) {
        deviceId = str;
    }

    public AdTestDeviceBean(Parcel parcel) {
        deviceId = parcel.readString();
    }

    public static Parcelable.Creator<AdTestDeviceBean> getCreator() {
        return CREATOR;
    }

    public void copy(AdTestDeviceBean adTestDeviceBean) {
        deviceId = adTestDeviceBean.deviceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void print() {
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.deviceId);
    }

    @Override
    public AdTestDeviceBean clone() {
        AdTestDeviceBean adTestDeviceBean = new AdTestDeviceBean();
        adTestDeviceBean.copy(this);
        return adTestDeviceBean;
    }
}
