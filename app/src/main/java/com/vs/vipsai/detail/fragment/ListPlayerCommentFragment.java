package com.vs.vipsai.detail.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.vs.vipsai.AppConfig;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.R;
import com.vs.vipsai.detail.adapter.PlayerCommentAdapter;
import com.vs.vipsai.api.remote.VSApi;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.base.fragments.BaseRecyclerViewFragment;
import com.vs.vipsai.bean.PageBean;
import com.vs.vipsai.bean.PlayerComment;
import com.vs.vipsai.bean.ResultBean;
import com.vs.vipsai.tweet.contract.TweetDetailContract;
import com.vs.vipsai.ui.empty.EmptyLayout;
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.util.TDevice;
import com.vs.vipsai.widget.DivergeView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Author: cynid
 * Created on 3/21/18 5:45 PM
 * Description:
 *
 * 评论
 */

public class ListPlayerCommentFragment extends BaseRecyclerViewFragment<PlayerComment>
        implements TweetDetailContract.ICmnView, BaseRecyclerAdapter.OnItemLongClickListener, PlayerCommentAdapter.OnGoodClickListener { //PlayerComment

    private TweetDetailContract.Operator mOperator;
    private TweetDetailContract.IAgencyView mAgencyView;
    private int mDeleteIndex = 0;

    private DivergeView mDivergeView;
    private ArrayList<Bitmap> mList;
    private int mIndex = 0;

    private FrameLayout container;


    public static ListPlayerCommentFragment instantiate(TweetDetailContract.Operator operator, TweetDetailContract.IAgencyView mAgencyView) { //
        ListPlayerCommentFragment fragment = new ListPlayerCommentFragment();
        fragment.mOperator = operator;
        fragment.mAgencyView = mAgencyView;
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOperator = (TweetDetailContract.Operator) context;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mOperator.onScroll();
                }
            }
        });


        mList = new ArrayList<>();
        mList.add(((BitmapDrawable) ResourcesCompat.getDrawable(mContext.getResources(), R.mipmap.ic_praise_sm1, null)).getBitmap());
        mList.add(((BitmapDrawable) ResourcesCompat.getDrawable(mContext.getResources(),R.mipmap.ic_praise_sm2,null)).getBitmap());
        mList.add(((BitmapDrawable) ResourcesCompat.getDrawable(mContext.getResources(),R.mipmap.ic_praise_sm3,null)).getBitmap());
        mList.add(((BitmapDrawable) ResourcesCompat.getDrawable(mContext.getResources(),R.mipmap.ic_praise_sm4,null)).getBitmap());
        mList.add(((BitmapDrawable) ResourcesCompat.getDrawable(mContext.getResources(),R.mipmap.ic_praise_sm5,null)).getBitmap());
        mList.add(((BitmapDrawable) ResourcesCompat.getDrawable(mContext.getResources(), R.mipmap.ic_praise_sm6, null)).getBitmap());


        mDivergeView = new DivergeView(mContext);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDivergeView.setLayoutParams(lp);


        // 获取PlayerActivity的根布局
        container = getActivity().findViewById(R.id.root_container); //root.findViewById(R.id.layout_list_container);
        container.addView(mDivergeView);

        mDivergeView.post(new Runnable() {
            @Override
            public void run() {
//                mDivergeView.setEndPoint(new PointF(mDivergeView.getMeasuredWidth() / 2.0f, 0));
                mDivergeView.setDivergeViewProvider(new Provider());
            }
        });



    }

    @SuppressLint("Range")
    @Override
    public void OnGoodClickListener(View view, int position) {


        int[] points = new int[2];
        view.getLocationInWindow(points);

        if(mIndex >= 5){
            mIndex = 0 ;
        }

        mDivergeView.setStartPoint(new PointF(points[0], points[1] - TDevice.dp2px(20)));

        mDivergeView.setEndPoint(new PointF(points[0], 0));

        while (mIndex < 5) {
            mDivergeView.startDiverges(mIndex);
            mIndex++;
        }


    }


    class Provider implements DivergeView.DivergeViewProvider{

        @Override
        public Bitmap getBitmap(Object obj) {
            return mList == null ? null : (Bitmap) mList.get((int) obj);
        }
    }




    @Override
    protected BaseRecyclerAdapter<PlayerComment> getRecyclerAdapter() { //PlayerComment
        PlayerCommentAdapter adapter = new PlayerCommentAdapter(getContext());
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        // 注册点赞监听事件
        adapter.setOnGoodClickListener(this);
        return adapter;
    }


    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<PlayerComment>>>() {
        }.getType();
    }

    @Override
    protected void onRequestSuccess(int code) {
        super.onRequestSuccess(code);
        Log.d("Fragment", "--->" + mAdapter.getCount());
//        mRecyclerView
        if (mAdapter.getCount() < 20 && mAgencyView != null)
            mAgencyView.resetCmnCount(mAdapter.getCount());
    }

    @Override
    protected void requestData() {
        String token = isRefreshing ? null : mBean.getNextPageToken();
        token = "12334";
        VSApi.getTweetCommentList(1, token, mHandler111); //mOperator.getTweetDetail().getId()
    }

    @Override
    protected boolean isNeedCache() {
        return false;
    }

    @Override
    protected boolean isNeedEmptyView() {
        return false;
    }

    @Override
    public void onItemClick(int position, long itemId) {
        super.onItemClick(position, itemId);
        PlayerComment item = mAdapter.getItem(position);
        if (item != null)
            mOperator.toReply(item);
    }

    @Override
    public void onLongClick(int position, long itemId) {
        final PlayerComment comment = mAdapter.getItem(position);
//        if (comment == null) return;
//        mDeleteIndex = position;
//
//        QuickOptionDialogHelper.with(getContext())
//                .addCopy(HTMLUtil.delHTMLTag(comment.getContent()))
//                .addOther(comment.getAuthor().getId() == AccountHelper.getUserId(),
//                        R.string.delete, new Runnable() {
//                            @Override
//                            public void run() {
//                                handleDeleteComment(comment);
//                            }
//                        }).show();

    }


    @Override
    public void onCommentSuccess(PlayerComment comment) {
        isRefreshing = true;
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, true);
        VSApi.getTweetCommentList(1, null, new TextHttpResponseHandler() { //mOperator.getTweetDetail().getId()
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if(mContext==null)
                    return;
                onRequestError();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if(mContext==null)
                    return;

                responseString = "{\"code\":1,\"message\":\"SUCCESS\",\"result\":{\"items\":[{\"author\":{\"id\":864999,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"AeroZen\",\"portrait\":\"http://static.oschina.net/uploads/user/432/864999_50.jpg?t=1352358674000\",\"relation\":0},\"pubDate\":\"2018-03-22 09:26:16\"},{\"author\":{\"id\":2521898,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"_奇洛\",\"portrait\":\"http://static.oschina.net/uploads/user/1260/2521898_50.jpg?t=1454550683000\",\"relation\":0},\"pubDate\":\"2018-03-22 09:19:42\"},{\"author\":{\"id\":2366516,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"归园田居\",\"portrait\":\"http://static.oschina.net/uploads/user/1183/2366516_50.jpg?t=1434336507000\",\"relation\":0},\"pubDate\":\"2018-03-22 08:47:04\"},{\"author\":{\"id\":239863,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"文耳兑心\",\"portrait\":\"http://static.oschina.net/uploads/user/119/239863_50.jpg?t=1382934209000\",\"relation\":0},\"pubDate\":\"2018-03-22 07:57:00\"},{\"author\":{\"id\":347223,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"clouddyy\",\"portrait\":\"http://static.oschina.net/uploads/user/173/347223_50.jpeg?t=1477362397000\",\"relation\":0},\"pubDate\":\"2018-03-22 07:11:26\"},{\"author\":{\"id\":3186485,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"lovejar\",\"portrait\":\"http://static.oschina.net/uploads/user/1593/3186485_50.jpg?t=1482920236000\",\"relation\":0},\"pubDate\":\"2018-03-21 22:31:27\"},{\"author\":{\"id\":731312,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"obaniu\",\"portrait\":\"http://static.oschina.net/uploads/user/365/731312_50.jpg?t=1492759957000\",\"relation\":0},\"pubDate\":\"2018-03-21 20:44:36\"},{\"author\":{\"id\":1779409,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"Fenying\",\"portrait\":\"http://static.oschina.net/uploads/user/889/1779409_50.jpg?t=1501767998000\",\"relation\":0},\"pubDate\":\"2018-03-21 19:49:50\"},{\"author\":{\"id\":3785538,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"OSC_UbyuvY\",\"portrait\":\"http://static.oschina.net/uploads/user/1892/3785538_50.jpg?t=1519649386000\",\"relation\":0},\"pubDate\":\"2018-03-21 17:05:24\"},{\"author\":{\"id\":2858361,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"锦年\",\"portrait\":\"http://static.oschina.net/uploads/user/1429/2858361_50.jpg?t=1469500444000\",\"relation\":0},\"pubDate\":\"2018-03-21 12:04:12\"},{\"author\":{\"id\":3794450,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"OSC_oiyaAP\",\"portrait\":\"http://static.oschina.net/uploads/user/1897/3794450_50.jpg?t=1520679950000\",\"relation\":0},\"pubDate\":\"2018-03-21 11:32:15\"},{\"author\":{\"id\":2884911,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"-jar\",\"portrait\":\"http://static.oschina.net/uploads/user/1442/2884911_50.jpg?t=1512433395000\",\"relation\":0},\"pubDate\":\"2018-03-21 10:59:55\"},{\"author\":{\"id\":991837,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"Coffee_M\",\"portrait\":\"http://static.oschina.net/uploads/user/495/991837_50.jpg?t=1468749327000\",\"relation\":0},\"pubDate\":\"2018-03-21 10:51:27\"},{\"author\":{\"id\":1248135,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"leiboo\",\"portrait\":\"http://static.oschina.net/uploads/user/624/1248135_50.jpg?t=1429062958000\",\"relation\":0},\"pubDate\":\"2018-03-21 09:45:35\"},{\"author\":{\"id\":111634,\"identity\":{\"officialMember\":false,\"softwareAuthor\":true},\"name\":\"理工男海哥\",\"portrait\":\"http://static.oschina.net/uploads/user/55/111634_50.jpeg?t=1521633080000\",\"relation\":0},\"pubDate\":\"2018-03-21 09:43:41\"}],\"nextPageToken\":\"DBA816934CD0AA59\",\"prevPageToken\":\"0997C855C600E421\",\"requestCount\":20,\"responseCount\":15,\"totalResults\":15},\"time\":\"2018-03-22 10:28:27\"}";


                try {
                    ResultBean<PageBean<PlayerComment>> resultBean = AppOperator.createGson().fromJson(responseString, getType());
                    if (resultBean != null && resultBean.isSuccess() && resultBean.getResult().getItems() != null) {
                        showRefreshSuccess(resultBean);
                        onRequestSuccess(resultBean.getCode());
                    } else {
                        if (resultBean != null && resultBean.getCode() == ResultBean.RESULT_TOKEN_ERROR) {
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
                if(mContext==null)
                    return;
                onRequestFinish();
            }

            @Override
            public void onCancel() {
                super.onCancel();
                if(mContext==null)
                    return;
                onRequestFinish();
            }
        });
    }

    private void showRefreshSuccess(ResultBean<PageBean<PlayerComment>> resultBean) {
        mBean.setNextPageToken(resultBean.getResult().getNextPageToken());
        AppConfig.getAppConfig(getActivity()).set("system_time", resultBean.getTime());
        mBean.setItems(resultBean.getResult().getItems());
        mAdapter.clear();
        mAdapter.addAll(mBean.getItems());
        mBean.setPrevPageToken(resultBean.getResult().getPrevPageToken());
        mRefreshLayout.setCanLoadMore(true);
        if (resultBean.getResult().getItems() == null
                || resultBean.getResult().getItems().size() < 20)
            mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
        if (mAdapter.getItems().size() > 0) {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mErrorLayout.setErrorType(
                    isNeedEmptyView()
                            ? EmptyLayout.NODATA
                            : EmptyLayout.HIDE_LAYOUT);
        }
    }

}
