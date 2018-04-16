package com.vs.vipsai.publish;

import android.databinding.ObservableField;

import com.vs.vipsai.bean.AwardBean;
import com.vs.vipsai.bean.TournamentBean;
import com.vs.vipsai.bean.User;
import com.vs.vipsai.publish.viewmodels.VMImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  赛事收集器，创建赛事时收集信息
 *  使用时需先build()
 *  使用完后要release()
 */
public class TournamentCollector {

    //布局绑定
    public String title;
    public ObservableField<String> time = new ObservableField<>("");
    private boolean mImmediate = true;
    public String description = "";
    public String rule = "";
    public ObservableField<String> localCover = new ObservableField<>("");
    public ObservableField<String> type = new ObservableField<>("");

    private static TournamentCollector mInstance;

    private TournamentBean mTournament;

    private boolean mRuleChecked;

    public VMImageItem cover;

    private List<Long> mUsers = new ArrayList<>();

    private List<AwardBean> mAwards;
    private String mAwardMethod = "";

    /**
     * 创建赛事收集器
     * @param tournament
     */
    public static void build(TournamentBean tournament) {
        if(mInstance == null) {
            synchronized (TournamentCollector.class) {
                if(mInstance == null) {
                    mInstance = new TournamentCollector(tournament);
                }
            }
        }
    }

    public List<AwardBean> getAwards() {
        return mAwards;
    }

    public void setAwards(List<AwardBean> awards) {
        if(mAwards == null) {
            mAwards = new ArrayList<>();
        }else {
            mAwards.clear();
        }
        if(awards != null) {
            mAwards.addAll(awards);
        }
    }

    public String getAwardMethod() {
        return mAwardMethod;
    }

    public void setAwardMethod(String awardMethod) {
        mAwardMethod = awardMethod;
    }

    public void setCoverData(VMImageItem cover) {
        this.cover = cover;
    }

    /**
     * 获取赛事收集器
     * @return Tournament or null: 赛事收集器还未被创建，需要先build
     */
    public static TournamentCollector get() {
        synchronized (TournamentCollector.class) {
            return mInstance;
        }
    }

    public void setSubjectId(long id) {
        if(mTournament != null) {
            mTournament.setSubjectId(id);
        }
    }

    public void removeAwardId(long id) {
        if(mTournament != null) {
            mTournament.removeAwardId(id);
        }
    }

    public void appendAwardId(long id) {
        if(mTournament != null) {
            mTournament.appendAwardId(id);
        }
    }

    public TournamentBean getTournament() {
        return mTournament;
    }

    public void changedPlayer(User user, boolean append) {
        if(append) {
            mUsers.add(user.getId());
        }else {
            mUsers.remove(user.getId());
        }
    }

    public int getUserCount() {return mUsers.size();}

    public void setRuleChecked(boolean checked) {
        mRuleChecked = checked;
    }

    private TournamentCollector(TournamentBean tournament){
        mTournament = tournament;
    }

    public static void release(){
        mInstance = null;
    }
}
