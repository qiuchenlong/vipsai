package com.vs.vipsai.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseLongArray;

import com.vs.vipsai.publish.TournamentCollector;

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

    public boolean startImmediate = true;
    public String startTime;
    public String startEntries;
    public String openDuration;
    public String qualifyDuration;
    public boolean enableUpload;
    public String entryDuration;

    public TournamentBean(Parcel in) {
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

    public void setTimeTheme(TournamentBean in) {
        setStartImmediate(in.isStartImmediate());
        setStartTime(in.getStartTime());
        setStartEntries(in.getStartEntries());
        setOpenDuration(in.getOpenDuration());
        setQualifyDuration(in.getQualifyDuration());
        setEnableUpload(in.isEnableUpload());
        setEntryDuration(in.getEntryDuration());
    }

    public boolean isStartImmediate() {
        return startImmediate;
    }

    public void setStartImmediate(boolean startImmediate) {
        this.startImmediate = startImmediate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartEntries() {
        return startEntries;
    }

    public void setStartEntries(String startEntries) {
        this.startEntries = startEntries;
    }

    public String getOpenDuration() {
        return openDuration;
    }

    public void setOpenDuration(String openDuration) {
        this.openDuration = openDuration;
    }

    public String getQualifyDuration() {
        return qualifyDuration;
    }

    public void setQualifyDuration(String qualifyDuration) {
        this.qualifyDuration = qualifyDuration;
    }

    public boolean isEnableUpload() {
        return enableUpload;
    }

    public void setEnableUpload(boolean enableUpload) {
        this.enableUpload = enableUpload;
    }

    public String getEntryDuration() {
        return entryDuration;
    }

    public void setEntryDuration(String entryDuration) {
        this.entryDuration = entryDuration;
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
