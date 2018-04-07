package com.vs.vipsai.publish.fragments;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.vs.vipsai.AppConfig;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.R;
import com.vs.vipsai.api.remote.VSApi;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.base.fragments.BaseGeneralRecyclerFragment;
import com.vs.vipsai.bean.AwardBean;
import com.vs.vipsai.bean.PageBean;
import com.vs.vipsai.bean.ResultBean;
import com.vs.vipsai.publish.TournamentCollector;
import com.vs.vipsai.publish.adapters.AwardListAdapter;
import com.vs.vipsai.publish.recyclerview.PaddingItemDecoration;
import com.vs.vipsai.publish.recyclerview.VHDatabinding;
import com.vs.vipsai.util.SimplexToast;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  创建样板比赛 - 选择奖项
 */
public class PubTemplatePickAward extends BaseGeneralRecyclerFragment<AwardBean> {

    @Override
    public void initData() {
        super.initData();

        //测试
        mHandler = new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onRequestError();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                try {
                    ResultBean<PageBean<AwardBean>> resultBean = AppOperator.createGson().fromJson(AwardBean.getTestData(), getType());
                    if (resultBean != null && resultBean.isSuccess() && resultBean.getResult().getItems() != null) {
                        setListData(resultBean);
                        onRequestSuccess(resultBean.getCode());
                    } else {
                        if (resultBean.getCode() == ResultBean.RESULT_TOKEN_ERROR) {
                            SimplexToast.show(getActivity(), resultBean.getMessage());
                        }
                        mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onFailure(statusCode, headers, responseString, e);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                onRequestFinish();
            }

            @Override
            public void onCancel() {
                super.onCancel();
                onRequestFinish();
            }
        };

        mAdapter.setSystemTime(AppConfig.getAppConfig(getActivity()).get("system_time"));
    }

    @Override
    public void onItemClick(int position, long itemId) {

        VHDatabinding holder = (VHDatabinding)mRecyclerView.findViewHolderForAdapterPosition(position);
        AwardBean model = mAdapter.getItem(position);
        if(model != null) {
            model.selected.set(!model.selected.get());
        }

        TournamentCollector c = TournamentCollector.get();
        if(c != null) {
            if(model.selected.get()) {
                c.appendAwardId(model.getId());
            }else {
                c.removeAwardId(model.getId());
            }
        }
    }


    @Override
    protected void requestData() {
        VSApi.getSubscription("https://www.oschina.net/action/apiv2/sub_list?token=df985be3c5d5449f8dfb47e06e098ef9", isRefreshing ? null : mBean.getNextPageToken(), mHandler);
    }

    @Override
    protected void setListData(ResultBean<PageBean<AwardBean>> resultBean) {
        super.setListData(resultBean);
        mAdapter.setSystemTime(resultBean.getTime());
    }

    @Override
    protected BaseRecyclerAdapter<AwardBean> getRecyclerAdapter() {
        int padding = getContext().getResources().getDimensionPixelSize(R.dimen.padding_12);
        mRecyclerView.addItemDecoration(new PaddingItemDecoration(padding, padding, padding, padding));
        return new AwardListAdapter(getContext(), BaseRecyclerAdapter.ONLY_FOOTER);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<AwardBean>>>() {}.getType();
    }

    @Override
    protected Class<AwardBean> getCacheClass() {
        return AwardBean.class;
    }

}
