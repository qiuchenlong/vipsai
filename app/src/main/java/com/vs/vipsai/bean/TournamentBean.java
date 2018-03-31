package com.vs.vipsai.bean;

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

    protected TournamentBean(Parcel in) {
        mSubjectId = in.readLong();
        int idCount = in.readInt();
        long[] awardId = new long[idCount];
        in.readLongArray(awardId);
        mAwardId = new ArrayList<>();
        for (long item : awardId) {
            mAwardId.add(item);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mSubjectId);
        dest.writeInt(mAwardId.size());
        if(mAwardId. size() > 0) {
            long[] ids = new long[mAwardId.size()];
            for(int i = 0; i < mAwardId.size();i++) {
                ids[i] = mAwardId.get(i);
            }

            dest.writeLongArray(ids);
        }
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
