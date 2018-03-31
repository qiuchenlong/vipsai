package com.vs.vipsai.publish;

import com.vs.vipsai.bean.TournamentBean;

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

    /**
     * 获取赛事收集器
     * @return Tournament or null: 赛事收集器还未被创建，需要先build
     */
    public static TournamentCollector get() {
        synchronized (TournamentCollector.class) {
            return mInstance;
        }
    }

    private TournamentCollector(TournamentBean tournament){
        mTournament = tournament;
    }

    public static void release(){
        mInstance = null;
    }
}
