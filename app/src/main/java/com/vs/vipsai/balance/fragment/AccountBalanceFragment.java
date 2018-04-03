package com.vs.vipsai.balance.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vs.vipsai.R;
import com.vs.vipsai.balance.LineLayout;
import com.vs.vipsai.balance.adapter.BalanceSubAdapter;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.bean.PageBean;
import com.vs.vipsai.bean.SubBean;
import com.vs.vipsai.bean.SubTab;
import com.vs.vipsai.ui.empty.EmptyLayout;
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.util.TDevice;
import com.vs.vipsai.util.UIHelper;
import com.vs.vipsai.widget.popupwindow.BlurPopupWindow;
import com.vs.vipsai.widget.popupwindow.PopupListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.vs.vipsai.widget.popupwindow.BlurPopupWindow.KEYWORD_LOCATION_CLICK_LEFT;

/**
 * Author: cynid
 * Created on 3/13/18 2:46 PM
 * Description:
 *
 * 账户余额 卡片
 */

public class AccountBalanceFragment extends AccountBalanceBaseFragment {

    private SubTab mTab;

    @BindView(R.id.fragment_account_balance_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.error_layout)
    EmptyLayout mErrorLayout;


    protected BalanceSubAdapter mAdapter;







    public static AccountBalanceFragment newInstance(Context context, SubTab subTab) {
        AccountBalanceFragment fragment = new AccountBalanceFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", subTab);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account_balance;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        mTab = (SubTab) bundle.getSerializable("sub_tab");

    }

    @Override
    public void initData() {
        super.initData();

        mAdapter = new BalanceSubAdapter(getContext(), BaseRecyclerAdapter.ONLY_FOOTER, getActivity());

        PageBean mBean = new PageBean<>();

        List items = new ArrayList();

        for (int i = 0; i < 10; i++) {
            SubBean sb = new SubBean();
            sb.setBody("111");
            items.add(sb);
        }

        mBean.setItems(items);

        mAdapter.addAll(mBean.getItems());
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);
        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(this);
        mErrorLayout.setOnLayoutClickListener(this);
//        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (canLoad() && mCanLoadMore) {
                    loadData();
                    SimplexToast.show(getContext(), "loadData()...");
                }
            }
        });


    }

    private boolean mCanLoadMore = true;
    /**
     * 是否可以加载更多, 条件是到了最底部
     *
     * @return isCanLoad
     */
    private boolean canLoad() {
        return isScrollBottom(); // && !mIsOnLoading && isPullUp() && mHasMore;
    }


    /**
     * 判断是否到了最底部
     */
    private boolean isScrollBottom() {
        return (mRecyclerView != null && mRecyclerView.getAdapter() != null)
                && getLastVisiblePosition() == (mRecyclerView.getAdapter().getItemCount() - 1);
    }

    /**
     * 获取RecyclerView可见的最后一项
     *
     * @return 可见的最后一项position
     */
    public int getLastVisiblePosition() {
        int position;
        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) mRecyclerView.getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = mRecyclerView.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获得最大的位置
     *
     * @param positions 获得最大的位置
     * @return 获得最大的位置
     */
    private int getMaxPosition(int[] positions) {
        int maxPosition = Integer.MIN_VALUE;
        for (int position : positions) {
            maxPosition = Math.max(maxPosition, position);
        }
        return maxPosition;
    }


    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
//        if (listener != null) {
//            setOnLoading(true);
//            listener.onLoadMore();
//        }
        isRefreshing = true;

        onLoadMore();
    }

    protected boolean isRefreshing;

    public void onLoadMore() {
        mAdapter.setState(isRefreshing ? BaseRecyclerAdapter.STATE_HIDE : BaseRecyclerAdapter.STATE_LOADING, true);
//        requestData();

        mAdapter.setState(BaseRecyclerAdapter.STATE_LOADING, true);


        PageBean mBean = new PageBean<>();
        List items = new ArrayList();
        for (int i = 0; i < 10; i++) {
            SubBean sb = new SubBean();
            sb.setBody("111");
            items.add(sb);
        }
        mBean.setItems(items);
        mAdapter.addAll(mBean.getItems());


        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, true);

//        mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
    }













    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        initPopupWindow(getActivity());

    }


    @OnClick(R.id.fragment_account_balance_option_all)
    void onClickAll(View view) {
        blurPopupWindow.displayPopupWindow(view, KEYWORD_LOCATION_CLICK_LEFT);
    }



    private BlurPopupWindow blurPopupWindow;

    private void initPopupWindow(Activity context) {
        final List<Map<String, Object>> list_popup = new ArrayList<>();
        final PopupListView lv_popup = new PopupListView(context);


        final String[] titles = new String[]{"全部", "赢取", "取现", "购买微币", "购买商品"};



        // 自定义布局容器控件
        LineLayout view = (LineLayout) View.inflate(context, R.layout.popup_all_option_item, null);


        for (int i=0; i<titles.length; i++) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("title", titles[i]);
//            list_popup.add(map);


            TextView tv = new TextView(getContext());
            tv.setGravity(Gravity.CENTER);
            tv.setPadding((int) TDevice.dp2px(10), (int) TDevice.dp2px(8), (int) TDevice.dp2px(10), (int) TDevice.dp2px(8));
            tv.setBackgroundResource(R.drawable.shape_search_view_white);
            tv.setSingleLine(true);
            tv.setText(titles[i]);
            tv.setTextColor(Color.argb(255, 51, 51, 51));
            LinearLayout ll = new LinearLayout(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(20, 20, 20, 20);
            ll.setLayoutParams(lp);
            ll.addView(tv);
            view.addView(ll);
        }

        view.setBackgroundResource(R.drawable.shape_search_view_white);




        for (int i = 0; i < view.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) view.getChildAt(i);
            final int finalI = i;
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimplexToast.show(getActivity(), "" + titles[finalI]);
                }
            });
        }



//        lv_popup.setDivider(new ColorDrawable(Color.GRAY));
//        lv_popup.setDividerHeight(1);
//        lv_popup.setAdapter(new AllOptionPopupListAdapter(context, list_popup));
//        lv_popup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(blurPopupWindow != null) {
//                    blurPopupWindow.dismissPopupWindow();
//                }
//            }
//        });



//        ImageView iv_popup_top = new ImageView(context);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
//                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        iv_popup_top.setLayoutParams(params);
        blurPopupWindow = new BlurPopupWindow(context, view, KEYWORD_LOCATION_CLICK_LEFT);
        blurPopupWindow.setOnPopupStateListener(new BlurPopupWindow.OnPopupStateListener() {
            @Override
            public void onDisplay(boolean isDisplay) {
//                TitleBar.this.isDisplay = isDisplay;
            }

            @Override
            public void onDismiss(boolean isDisplay) {
//                TitleBar.this.isDisplay = isDisplay;
//                dismissAnim();
            }
        });
    }





    @OnClick(R.id.fragment_account_balance_withdraw_money)
    void onClickWithDrawMoney() {
        UIHelper.showBalanceWithDrawMoney(mContext);
    }


    @OnClick(R.id.fragment_account_balance_buy_coin)
    void onClickBuyCoin() {

    }




}
