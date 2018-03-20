package com.vs.vipsai.detail;

import com.vs.vipsai.bean.SubBean;

import org.w3c.dom.Comment;

import java.util.List;

/**
 * Author: cynid
 * Created on 3/20/18 9:09 AM
 * Description:
 */

@SuppressWarnings("unused")
public interface DetailContract {

    interface EmptyView {
        void hideEmptyLayout();

        void showErrorLayout(int errorType);

        void showGetDetailSuccess(SubBean bean);

        void showFavReverseSuccess(boolean isFav, int favCount, int strId);

        void showCommentSuccess(Comment comment);

        void showCommentError(String message);

        void showUploadBehaviorsSuccess(int index, String time);

        void showShareCommentView(Comment comment);
    }

//    interface View extends BaseView<Presenter> {
//        void showGetDetailSuccess(SubBean bean);
//
//        void showFavReverseSuccess(boolean isFav, int favCount, int strId);
//
//        void showFavError();
//
//        void showCommentSuccess(Comment comment);
//
//        void showCommentError(String message);
//
//        void showAddRelationSuccess(boolean isRelation, int strId);
//
//        void showAddRelationError();
//
//        void showScrollToTop();
//
//        void showPayDonateSuccess(int type, String sign, WeChatPay.PayResult result);
//
//        void showPayDonateError();
//    }
//
//    interface Presenter extends BasePresenter {
//
//        void getCache();
//
//        void getDetail();//获得详情
//
//        void favReverse();
//
//        void addComment(
//                long sourceId,
//                int type,
//                String content,
//                long referId,
//                long replyId,
//                long reAuthorId
//        );//添加评论
//
//        void uploadBehaviors(List<Behavior> behaviors);
//
//        void addUserRelation(long authorId);
//
//        void scrollToTop();
//
//        void shareComment(Comment comment);
//
//        /**
//         * 支付打赏接口信息拉取
//         * @param authorId  被打赏作者
//         * @param objId 文章id
//         * @param money 价格，支付宝单位元、微信单位分
//         * @param payType  支付类型 1 支付宝  2、微信支付  返回结果不一样
//         */
//        void payDonate( long authorId,long objId,float money,int payType);
//    }

}
