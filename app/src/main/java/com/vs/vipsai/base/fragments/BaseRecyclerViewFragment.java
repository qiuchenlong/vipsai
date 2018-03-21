package com.vs.vipsai.base.fragments;

import android.graphics.Rect;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.loopj.android.http.TextHttpResponseHandler;
import com.vs.vipsai.AppConfig;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.R;
import com.vs.vipsai.base.adapter.BaseGeneralRecyclerAdapter;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.bean.PageBean;
import com.vs.vipsai.bean.ResultBean;
import com.vs.vipsai.main.my.DividerItemDecoration;
import com.vs.vipsai.ui.empty.EmptyLayout;
import com.vs.vipsai.util.CacheManager;
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.util.TDevice;
import com.vs.vipsai.util.TLog;
import com.vs.vipsai.widget.RecyclerRefreshLayout;
import com.vs.vipsai.widget.swipemenu.SwipeItemLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Author: cynid
 * Created on 3/13/18 1:52 PM
 * Description:
 */

public abstract class BaseRecyclerViewFragment<T> extends BaseFragment implements
        RecyclerRefreshLayout.SuperRefreshLayoutListener,
        BaseRecyclerAdapter.OnItemClickListener,
        View.OnClickListener,
        BaseGeneralRecyclerAdapter.Callback {

    private final String TAG = this.getClass().getSimpleName();
    protected BaseRecyclerAdapter<T> mAdapter;
    protected RecyclerView mRecyclerView;
    protected RecyclerRefreshLayout mRefreshLayout;
    protected boolean isRefreshing;
    protected TextHttpResponseHandler mHandler;
    protected PageBean<T> mBean;
    protected String CACHE_NAME = getClass().getName();
    protected EmptyLayout mErrorLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_recycler_view;
    }

    @Override
    protected void initWidget(View root) {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        mRefreshLayout = (RecyclerRefreshLayout) root.findViewById(R.id.refreshLayout);
        mErrorLayout = (EmptyLayout) root.findViewById(R.id.error_layout);
    }


    /**
     * 设置背景颜色
     */
    public void setRecyclerBackgroundColor() {
        mRecyclerView.setBackgroundResource(R.color.white); // background color : white
    }


    public void setaddItemDecorationOfMySelf() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
    }

    /**
     * 设置分割间距
     */
    public void setItemDecoration() {
        // set item decoration
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.lay_32);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
    }

    /**
     * 设置瀑布流布局
     */
    public void setRecyclerStaggeredGridLayoutStyle(int spanCount) {
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        // 设置分割线
//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.lay_20);
//        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        // 设置RecyclerView布局管理器为3列垂直排布
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL));
    }


    /**
     * 设置线性布局
     */
    public void setRecyclerLinearLayoutStyle() {
        mRecyclerView.setLayoutManager(getLayoutManager());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initData() {
        mBean = new PageBean<>();
        mAdapter = getRecyclerAdapter();
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mErrorLayout.setOnLayoutClickListener(this);
        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);

//        mRecyclerView.setLayoutManager(getLayoutManager());

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (RecyclerView.SCROLL_STATE_DRAGGING == newState && getActivity() != null
                        && getActivity().getCurrentFocus() != null) {
                    TDevice.hideSoftKeyboard(getActivity().getCurrentFocus());
                }
            }
        });


        // 添加滑动手势
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));


        mRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);


        mHandler = new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                log("HttpResponseHandler:onStart");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onRequestError();
                log("HttpResponseHandler:onFailure responseString:" + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                log("HttpResponseHandler:onSuccess responseString:" + responseString);
                // 模拟数据
                responseString = "{\n" +
                        "  code: 1,\n" +
                        "  message: \"SUCCESS\",\n" +
                        "  notice: {\n" +
                        "    newsCount: 35\n" +
                        "  },\n" +
                        "  result: {\n" +
                        "    items: [\n" +
                        "      {\n" +
                        "        \"fixed\": true,\n" +
                        "        \"href\": \"https://www.oschina.net/action/apiv2/sub_list?token=df985be3c5d5449f8dfb47e06e098ef9\",\n" +
                        "        \"title\": \"热门\",\n" +
                        "        \"body\": \"hello world...\",\n" +
                        "        \"needLogin\": false,\n" +
                        "        \"isActived\": true,\n" +
                        "        \"order\": 3,\n" +
                        "        \"subtype\": 4,\n" +
                        "        \"token\": \"df985be3c5d5449f8dfb47e06e098ef9\",\n" +
                        "        \"type\": 3\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"fixed\": true,\n" +
                        "        \"href\": \"https://www.oschina.net/action/apiv2/sub_list?token=df985be3c5d5449f8dfb47e06e098ef9\",\n" +
                        "        \"title\": \"hot\",\n" +
                        "        \"body\": \"123\",\n" +
                        "        \"needLogin\": false,\n" +
                        "        \"isActived\": true,\n" +
                        "        \"order\": 3,\n" +
                        "        \"subtype\": 4,\n" +
                        "        \"token\": \"df985be3c5d5449f8dfb47e06e098ef9\",\n" +
                        "        \"type\": 21\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"fixed\": true,\n" +
                        "        \"href\": \"https://www.oschina.net/action/apiv2/sub_list?token=df985be3c5d5449f8dfb47e06e098ef9\",\n" +
                        "        \"title\": \"hot\",\n" +
                        "        \"body\": \"abc\",\n" +
                        "        \"needLogin\": false,\n" +
                        "        \"isActived\": true,\n" +
                        "        \"order\": 3,\n" +
                        "        \"subtype\": 4,\n" +
                        "        \"token\": \"df985be3c5d5449f8dfb47e06e098ef9\",\n" +
                        "        \"type\": 21\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  time: \"2018-03-14 14:26:37\"\n" +
                        "}";
                try {
                    ResultBean<PageBean<T>> resultBean = AppOperator.createGson().fromJson(responseString, getType());
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
                log("HttpResponseHandler:onFinish");
            }

            @Override
            public void onCancel() {
                super.onCancel();
                onRequestFinish();
            }
        };

        boolean isNeedEmptyView = isNeedEmptyView();
        if (isNeedEmptyView) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            mRefreshLayout.setVisibility(View.GONE);
            mBean = new PageBean<>();

            List<T> items = isNeedCache()
                    ? (List<T>) CacheManager.readListJson(getActivity(), CACHE_NAME, getCacheClass())
                    : null;

//            List<SubBean> iList = new ArrayList<>();
//            SubBean sb = new SubBean();
//            sb.setTitle("111");
//            sb.setBody("111122");
//            String [] strArray = {"aa", "bb"};
//            sb.setTags(strArray);
//            iList.add(sb);
//
//            SubBean sb1 = new SubBean();
//            sb1.setTitle("111");
//            sb1.setBody("11qqqq");
//            iList.add(sb1);
//
//            items = (List<T>) iList;

            mBean.setItems(items);
            //if is the first loading
            if (items == null) {
                mBean.setItems(new ArrayList<T>());
                onRefreshing();
            } else {
                mAdapter.addAll(mBean.getItems());
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                mRefreshLayout.setVisibility(View.VISIBLE);
                mRoot.post(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(true);
                        onRefreshing();
                    }
                });
            }
        } else {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                    onRefreshing();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        onRefreshing();
    }


    @Override
    public void onItemClick(int position, long itemId) {

    }

    @Override
    public void onRefreshing() {
        isRefreshing = true;
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, true);
        requestData();
    }

    @Override
    public void onLoadMore() {
        mAdapter.setState(isRefreshing ? BaseRecyclerAdapter.STATE_HIDE : BaseRecyclerAdapter.STATE_LOADING, true);
        requestData();
    }

    protected void requestData() {
    }

    protected void onRequestSuccess(int code) {

    }

    protected void onRequestFinish() {
        onComplete();
    }

    protected void onRequestError() {
        onComplete();
        if (mAdapter.getItems().size() == 0) {
            if (isNeedEmptyView()) mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
            mAdapter.setState(BaseRecyclerAdapter.STATE_LOAD_ERROR, true);
        }
    }

    protected void onComplete() {
        mRefreshLayout.onComplete();
        isRefreshing = false;
    }

    protected void setListData(ResultBean<PageBean<T>> resultBean) {
        mBean.setNextPageToken(resultBean.getResult().getNextPageToken());
        if (isRefreshing) {
            AppConfig.getAppConfig(getActivity()).set("system_time", resultBean.getTime());
            mBean.setItems(resultBean.getResult().getItems());
            mAdapter.clear();
            mAdapter.addAll(mBean.getItems());
            mBean.setPrevPageToken(resultBean.getResult().getPrevPageToken());
            mRefreshLayout.setCanLoadMore(true);
            if (isNeedCache()) {
                CacheManager.saveToJson(getActivity(), CACHE_NAME, mBean.getItems());
            }
        } else {
            mAdapter.addAll(resultBean.getResult().getItems());
        }

        if (resultBean.getResult().getItems() == null
                || resultBean.getResult().getItems().size() < 20)
            mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
//        mAdapter.setState(resultBean.getResult().getItems() == null
//                || resultBean.getResult().getItems().size() < 20
//                ? BaseRecyclerAdapter.STATE_NO_MORE
//                : BaseRecyclerAdapter.STATE_LOADING, true);

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

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    protected abstract BaseRecyclerAdapter<T> getRecyclerAdapter();

    protected abstract Type getType();

    /**
     * 获取缓存bean的class
     */
    protected Class<T> getCacheClass() {
        return null;
    }

    @Override
    public Date getSystemTime() {
        return new Date();
    }

    /**
     * 需要缓存
     *
     * @return isNeedCache
     */
    protected boolean isNeedCache() {
        return true;
    }

    /**
     * 需要空的View
     *
     * @return isNeedEmptyView
     */
    protected boolean isNeedEmptyView() {
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    private void log(String msg) {
        if (false)
            TLog.i(TAG, msg);
    }


    public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if(parent.getChildPosition(view) != 0)
                outRect.top = space;
        }
    }

}




