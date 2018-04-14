package com.vs.vipsai.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseLongArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * * Author: chends
 * Created on 3/30/18 5:33 PM
 * Description:
 *
 *  赛事数据bean
 */
public class TournamentBean implements Parcelable{

    private long mSubjectId;
    private List<Long> mAwardId = new ArrayList<>();

    public boolean startImmediate;
    public String startTime;
    public String startEntries;
    public String openDuration;
    public String qualifyDuration;
    public boolean enableUpload;
    public String entryDuration;

    protected TournamentBean(Parcel in) {
        mSubjectId = in.readLong();
        mAwardId = new ArrayList<>();
        int idCount = in.readInt();
        if(idCount > 0) {
            long[] awardId = new long[idCount];
            in.readLongArray(awardId);
            for (long item : awardId) {
                mAwardId.add(item);
            }
        }

        startImmediate = in.readInt() == 1 ? true : false;
        startTime = in.readString();
        startEntries = in.readString();
        openDuration = in.readString();
        qualifyDuration = in.readString();
        enableUpload = in.readInt() == 1 ? true : false;
        entryDuration = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mSubjectId);
        dest.writeInt(mAwardId.size());
        if(mAwardId.size() > 0) {
            long[] ids = new long[mAwardId.size()];
            for(int i = 0; i < mAwardId.size();i++) {
                ids[i] = mAwardId.get(i);
            }

            dest.writeLongArray(ids);
        }
        dest.writeInt(startImmediate ? 1 : 0);
        dest.writeString(startTime);
        dest.writeString(startEntries);
        dest.writeString(openDuration);
        dest.writeString(qualifyDuration);
        dest.writeInt(enableUpload ? 1 : 0);
        dest.writeString(entryDuration);
    }

    public long getSubjectId() {
        return mSubjectId;
    }

    public void setSubjectId(long subjectId) {
        mSubjectId = subjectId;
    }

    public void appendAwardId(long awardId) {
        mAwardId.add(awardId);
    }

    public void removeAwardId(long awardId) {
        mAwardId.remove(awardId);
    }

    public TournamentBean(){}

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

}
