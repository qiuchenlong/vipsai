package com.vs.vipsai.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * * Author: chends
 * Created on 3/30/18 5:33 PM
 * Description:
 *
 *  赛事数据bean
 */
public class TournamentBean implements Parcelable{

    public TournamentBean(){}

    protected TournamentBean(Parcel in) {}

    public static final Creator<TournamentBean> CREATOR = new Creator<TournamentBean>() {
        @Override
        public TournamentBean createFromParcel(Parcel in) {
            return new TournamentBean(in);
        }

        @Override
        public TournamentBean[] newArray(int size) {
            return new TournamentBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
