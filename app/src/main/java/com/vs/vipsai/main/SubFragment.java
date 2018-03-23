package com.vs.vipsai.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.reflect.TypeToken;
import com.vs.vipsai.AppConfig;
import com.vs.vipsai.AppContext;
import com.vs.vipsai.api.remote.VSApi;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.base.fragments.BaseGeneralRecyclerFragment;
import com.vs.vipsai.bean.News;
import com.vs.vipsai.bean.PageBean;
import com.vs.vipsai.bean.ResultBean;
import com.vs.vipsai.bean.SubBean;
import com.vs.vipsai.bean.SubTab;
import com.vs.vipsai.main.competition.OpenSubAdapter;
import com.vs.vipsai.main.competition.QualifyingSubAdapter;
import com.vs.vipsai.main.my.AllSubAdapter;
import com.vs.vipsai.main.my.GameSubAdapter;
import com.vs.vipsai.main.my.MeSubAdapter;
import com.vs.vipsai.main.my.WinnerSubAdapter;
import com.vs.vipsai.main.past.ChampionWorkSubAdapter;
import com.vs.vipsai.main.past.PastWonderfulSubAdapter;
import com.vs.vipsai.main.recommend.AttentionSubAdapter;
import com.vs.vipsai.main.recommend.PopularSubAdapter;
import com.vs.vipsai.ui.activity.PlayerDetailActivity;
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.util.TDevice;

import java.lang.reflect.Type;

/**
 * Author: cynid
 * Created on 3/13/18 2:46 PM
 * Description:
 */

public class SubFragment extends BaseGeneralRecyclerFragment<SubBean> {

    private SubTab mTab;
//    private HeaderView mHeaderView;
//    private OSCApplication.ReadState mReadState;

    public static SubFragment newInstance(Context context, SubTab subTab) {
        SubFragment fragment = new SubFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", subTab);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        mTab = (SubTab) bundle.getSerializable("sub_tab");
        CACHE_NAME = mTab.getToken();
    }

    @Override
    public void initData() {
//        mReadState = OSCApplication.getReadState("sub_list");
//        if (mTab.getBanner() != null) {
//            mHeaderView = mTab.getBanner().getCatalog() == SubTab.BANNER_CATEGORY_NEWS ?
//                    new NewsHeaderView(mContext, getImgLoader(), mTab.getBanner().getHref(), mTab.getToken() + "banner" + mTab.getType()) :
//                    new EventHeaderView(mContext, getImgLoader(), mTab.getBanner().getHref(), mTab.getToken() + "banner" + mTab.getType());
//        }
        super.initData();
//        mAdapter.setHeaderView(mHeaderView);
        mAdapter.setSystemTime(AppConfig.getAppConfig(getActivity()).get("system_time"));
        if (mAdapter instanceof PopularSubAdapter) {
            ((PopularSubAdapter) mAdapter).setTab(mTab);
        }
    }

    @Override
    public void onItemClick(int position, long itemId) {
        if(!TDevice.hasWebView(mContext))
            return;
        SubBean sub = mAdapter.getItem(position);
        if (sub == null)
            return;
        switch (sub.getType()) { // type的值 由请求来的数据决定，目前为模拟数据
            case News.TYPE_ATTENTION:
                SimplexToast.show(getContext(), "position:" + position);

//                Intent intent = new Intent(AppContext.getContext(), CityListActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                AppContext.getInstance().startActivity(intent);


                Intent intent = new Intent(AppContext.getContext(), PlayerDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AppContext.getInstance().startActivity(intent);


                break;
            case News.TYPE_NEWEST:
                SimplexToast.show(getContext(), "21" + sub.getBody());
                break;
//            case News.TYPE_SOFTWARE:
//                //SoftwareDetailActivity.show(mContext, sub.getId());
//                net.oschina.app.improve.detail.general.SoftwareDetailActivity.show(mContext, sub);
//                break;
//            case News.TYPE_QUESTION:
//                //QuestionDetailActivity.show(mContext, sub.getId());
//                net.oschina.app.improve.detail.general.QuestionDetailActivity.show(mContext, sub);
//                break;
//            case News.TYPE_ATTENTION:
//                //BlogDetailActivity.show(mContext, sub.getId());
//                net.oschina.app.improve.detail.general.BlogDetailActivity.show(mContext, sub);
//                break;
//            case News.TYPE_TRANSLATE:
//                //TranslateDetailActivity.show(mContext, sub.getId());
//                net.oschina.app.improve.detail.general.NewsDetailActivity.show(mContext, sub);
//                break;
//            case News.TYPE_EVENT:
//                //EventDetailActivity.show(mContext, sub.getId());
//                net.oschina.app.improve.detail.general.EventDetailActivity.show(mContext, sub);
//                break;
//            case News.TYPE_NEWS:
//                //NewsDetailActivity.show(mContext, sub.getId());
//                net.oschina.app.improve.detail.general.NewsDetailActivity.show(mContext, sub);
//                break;
//            default:
//                UIHelper.showUrlRedirect(mContext, sub.getHref());
//                break;
        }

//        mReadState.put(sub.getKey());
        mAdapter.updateItem(position);
    }


    @Override
    public void onRefreshing() {
        super.onRefreshing();
//        if (mHeaderView != null)
//            mHeaderView.requestBanner();
    }

    @Override
    protected void requestData() {
        VSApi.getSubscription(mTab.getHref(), isRefreshing ? null : mBean.getNextPageToken(), mHandler);
    }

    @Override
    protected void setListData(ResultBean<PageBean<SubBean>> resultBean) {
        super.setListData(resultBean);
        mAdapter.setSystemTime(resultBean.getTime());
    }

    @Override
    protected BaseRecyclerAdapter<SubBean> getRecyclerAdapter() {
//        int mode = mHeaderView != null ? BaseRecyclerAdapter.BOTH_HEADER_FOOTER : BaseRecyclerAdapter.ONLY_FOOTER;
        int mode = BaseRecyclerAdapter.ONLY_FOOTER;
        if (mTab.getType() == News.TYPE_ATTENTION) {
            setItemDecoration();
            setRecyclerLinearLayoutStyle();
            return new AttentionSubAdapter(getActivity(), mode, getActivity());
        }
//        else if (mTab.getType() == News.TYPE_EVENT)
//            return new EventSubAdapter(this, mode);
//        else if (mTab.getType() == News.TYPE_QUESTION)
//            return new QuestionSubAdapter(this, mode);

        else if (mTab.getType() == News.TYPE_POPULAR) {
            setItemDecoration();
            setRecyclerLinearLayoutStyle();
            return new PopularSubAdapter(getActivity(), mode);
        }

        /**
         * competition tab
         */
        else if (mTab.getType() == News.TYPE_OPEN) {
            setItemDecoration();
            setRecyclerLinearLayoutStyle();
            return new OpenSubAdapter(getActivity(), mode, getActivity());
        }
        else if (mTab.getType() == News.TYPE_QUALIFYING) {
            setItemDecoration();
            setRecyclerLinearLayoutStyle();
            return new QualifyingSubAdapter(getActivity(), mode, getActivity());
        }




        /**
         * past tab
         */

        else if (mTab.getType() == News.TYPE_PAST_WONDERFUL) {
            setItemDecoration();
            setRecyclerLinearLayoutStyle();
            return new PastWonderfulSubAdapter(getActivity(), mode, getActivity());
        }
        else if (mTab.getType() == News.TYPE_CHAMPIONWORK) {
            setRecyclerBackgroundColor(); // set bg color
            setRecyclerStaggeredGridLayoutStyle(2); // StaggeredGridLayout
            return new ChampionWorkSubAdapter(getActivity(), mode);
        }




        /**
         * my tab
         */
        else if (mTab.getType() == News.TYPE_NEWEST) {
            setaddItemDecorationOfMySelf(); // time line
            setRecyclerBackgroundColor(); // set bg color
            setRecyclerLinearLayoutStyle(); // linearlayout
            return new MeSubAdapter(getActivity(), mode);
        }
        else if (mTab.getType() == News.TYPE_WINNER) {
            setRecyclerBackgroundColor(); // set bg color
            setRecyclerStaggeredGridLayoutStyle(3); // StaggeredGridLayout
            return new WinnerSubAdapter(getActivity(), mode);
        }
        else if (mTab.getType() == News.TYPE_GAME) {
            setRecyclerBackgroundColor(); // set bg color
            setRecyclerLinearLayoutStyle(); // linearlayout
            return new GameSubAdapter(getActivity(), mode);
        }
        else if (mTab.getType() == News.TYPE_ALL) {
            setRecyclerBackgroundColor(); // set bg color
            setRecyclerStaggeredGridLayoutStyle(3); // StaggeredGridLayout
            return new AllSubAdapter(getActivity(), mode);
        }

        return new PopularSubAdapter(getActivity(), mode);
//        return new BlogSubAdapter(getActivity(), mode);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<SubBean>>>() {
        }.getType();
    }

    @Override
    protected Class<SubBean> getCacheClass() {
        return SubBean.class;
    }

}
