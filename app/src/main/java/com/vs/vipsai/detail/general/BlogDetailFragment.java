package com.vs.vipsai.detail.general;

import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vs.vipsai.R;
import com.vs.vipsai.api.remote.VSApi;
import com.vs.vipsai.bean.SubBean;
import com.vs.vipsai.detail.DetailFragment;
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.util.StringUtils;
import com.vs.vipsai.widget.IdentityView;
import com.vs.vipsai.widget.PortraitView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Author: cynid
 * Created on 3/20/18 9:29 AM
 * Description:
 */

public class BlogDetailFragment extends DetailFragment {

    @BindView(R.id.iv_label_today)
    ImageView mImageToday;

    @BindView(R.id.iv_label_recommend)
    ImageView mImageRecommend;

    @BindView(R.id.iv_label_originate)
    ImageView mImageOriginate;

    @BindView(R.id.iv_label_reprint)
    ImageView mImageReprint;

    @BindView(R.id.iv_avatar)
    PortraitView mImageAvatar;

    @BindView(R.id.identityView)
    IdentityView mIdentityView;

    @BindView(R.id.tv_name)
    TextView mTextName;

    @BindView(R.id.tv_pub_date)
    TextView mTextPubDate;

    @BindView(R.id.tv_title)
    TextView mTextTitle;

    @BindView(R.id.tv_detail_abstract)
    TextView mTextAbstract;

    @BindView(R.id.btn_relation)
    Button mBtnRelation;

    @BindView(R.id.lay_nsv)
    NestedScrollView mViewScroller;

//    private PayDialog mDialog;


    public static BlogDetailFragment newInstance() {
        return new BlogDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_blog_detail_v2;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mBtnRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mBean.getAuthor() != null) {
//                    mPresenter.addUserRelation(mBean.getAuthor().getId());
//                }
            }
        });
//        mDetailAboutView.setTitle("相关文章");
        mImageAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mBean != null && mBean.getAuthor() != null) {
//                    OtherUserHomeActivity.show(mContext, mBean.getAuthor());
//                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
//        CACHE_CATALOG = OSChinaApi.CATALOG_BLOG;
    }

    @OnClick({R.id.btn_pay})
    @Override
    public void onClick(View v) {
//        if(!AccountHelper.isLogin()){
//            LoginActivity.show(this,1);
//            return;
//        }
//        if (mDialog == null) {
//            mDialog = new PayDialog(mContext, mBean);
//            mDialog.setOnPayListener(new PayDialog.OnPayListener() {
//                @Override
//                public void onPay(float money, int type) {
//                    mPresenter.payDonate(mBean.getAuthor().getId(), mBean.getId(), money, type);
//                }
//            });
//        }
//        mDialog.show();
    }

//    @Override
//    public void showGetDetailSuccess(SubBean bean) {
////        super.showGetDetailSuccess(bean);
//        if (mContext == null) return;
////        Author author = bean.getAuthor();
//        mIdentityView.setup(author);
////        if (author != null) {
////            mTextName.setText(author.getName());
////            mImageAvatar.setup(author);
////        }
//        mTextPubDate.setText(StringUtils.formatYearMonthDay(bean.getPubDate()));
//        mTextTitle.setText(bean.getTitle());
//        mTextAbstract.setText(bean.getSummary());
//        if (TextUtils.isEmpty(bean.getSummary())) {
//            mRoot.findViewById(R.id.line).setVisibility(View.GONE);
//            mRoot.findViewById(R.id.line1).setVisibility(View.GONE);
//            mTextAbstract.setVisibility(View.GONE);
//        }
////        mBtnRelation.setText(bean.getAuthor().getRelation() < UserRelation.RELATION_ONLY_HER
////                ? "已关注" : "关注");
//        mImageRecommend.setVisibility(mBean.isRecommend() ? View.VISIBLE : View.GONE);
//        mImageOriginate.setVisibility(mBean.isOriginal() ? View.VISIBLE : View.GONE);
//        mImageReprint.setVisibility(mImageOriginate.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//        mImageToday.setVisibility(StringUtils.isToday(mBean.getPubDate()) ? View.VISIBLE : View.GONE);
//    }

//    @Override
//    public void showAddRelationSuccess(boolean isRelation, int strId) {
//        mBtnRelation.setText(isRelation ? "已关注" : "关注");
//        SimplexToast.show(mContext, strId);
//    }

    @Override
    protected int getCommentOrder() {
        return VSApi.COMMENT_NEW_ORDER;
    }

    @OnLongClick(R.id.tv_title)
    boolean onLongClickTitle() {
        showCopyTitle();
        return true;
    }

}
