package com.vs.vipsai.tweet.contract;

import com.vs.vipsai.bean.PlayerComment;
import com.vs.vipsai.bean.Tweet;
import com.vs.vipsai.bean.User;

/**
 * Author: cynid
 * Created on 3/21/18 6:19 PM
 * Description:
 */

public interface TweetDetailContract {

    interface Operator {

        Tweet getTweetDetail();

        void toReply(PlayerComment comment);

        void onScroll();
    }

    interface ICmnView {
        void onCommentSuccess(PlayerComment comment);
    }

    interface IThumbupView {
        void onLikeSuccess(boolean isUp, User user);
    }

    interface IAgencyView {
        void resetLikeCount(int count);

        void resetCmnCount(int count);
    }

}
