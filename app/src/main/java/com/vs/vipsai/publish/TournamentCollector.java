package com.vs.vipsai.publish;

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

    private static TournamentCollector mInstance;

    private TournamentBean mTournament;

    private boolean mRuleChecked;

    private VMImageItem mCover;

    private List<Long> mUsers = new ArrayList<>();

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

    public void setCover(VMImageItem cover) {
        mCover = cover;
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
