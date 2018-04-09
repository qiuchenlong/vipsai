package com.vs.vipsai.detail.fragment;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.vs.vipsai.detail.adapter.PlayerDetailAdapter;
import com.vs.vipsai.api.remote.VSApi;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.base.fragments.BaseRecyclerViewFragment;
import com.vs.vipsai.bean.PageBean;
import com.vs.vipsai.bean.ResultBean;
import com.vs.vipsai.bean.TweetLike;


import java.lang.reflect.Type;

/**
 * Author: cynid
 * Created on 3/22/18 2:38 PM
 * Description:
 *
 * 详情页面的详情卡片
 */

public class ListPlayerDetailFragment extends BaseRecyclerViewFragment<TweetLike> {


    public static ListPlayerDetailFragment instantiate() {
        ListPlayerDetailFragment fragment = new ListPlayerDetailFragment();
        return fragment;
    }


    @Override
    protected BaseRecyclerAdapter<TweetLike> getRecyclerAdapter() {
        PlayerDetailAdapter adapter = new PlayerDetailAdapter(getContext());
//        super.setaddItemDecorationOfMySelf();
        return adapter;
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<TweetLike>>>() {
        }.getType();
    }

    @Override
    protected void onRequestSuccess(int code) {
        super.onRequestSuccess(code);
        Log.d("Fragment", "--->" + mAdapter.getCount());

    }

    @Override
    protected void requestData() {
        String token = isRefreshing ? null : mBean.getNextPageToken();
        token = "12334";
        VSApi.getTweetCommentList(1, token, mHandler111); //mOperator.getTweetDetail().getId()
    }


//    @BindView(R.id.list_item_player_detail_time_line_layout)
//    RecyclerView time_line_layout;
//
//
//    public static ListPlayerDetailFragment instantiate() {
//        ListPlayerDetailFragment fragment = new ListPlayerDetailFragment();
//        return fragment;
//    }
//
//        @Override
//    protected int getLayoutId() {
//        return R.layout.list_item_player_detail;
//    }
//
////    private TweetDetailContract.Operator mOperator;
////    private TweetDetailContract.IAgencyView mAgencyView;
////
////    public static ListPlayerDetailFragment instantiate(TweetDetailContract.Operator operator, TweetDetailContract.IAgencyView mAgencyView) {
////        ListPlayerDetailFragment fragment = new ListPlayerDetailFragment();
////        fragment.mOperator = operator;
////        fragment.mAgencyView = mAgencyView;
////        return fragment;
////    }
////
////    @Override
////    public void onAttach(Context context) {
////        super.onAttach(context);
////        mOperator = (TweetDetailContract.Operator) context;
////    }
////
////    @Override
////    protected void initWidget(View root) {
////        super.initWidget(root);
////        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
////            @Override
////            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
////                super.onScrollStateChanged(recyclerView, newState);
////                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
////                    mOperator.onScroll();
////                }
////            }
////        });
////    }
////
////    @Override
////    protected BaseRecyclerAdapter<TweetLike> getRecyclerAdapter() {
////        return new PlayerDetailAdapter(getContext());
////    }
////
////    @Override
////    protected Type getType() {
////        return new TypeToken<ResultBean<PageBean<TweetLike>>>() {
////        }.getType();
////    }
////
////    @Override
////    protected boolean isNeedCache() {
////        return false;
////    }
////
////    @Override
////    protected boolean isNeedEmptyView() {
////        return false;
////    }
////
////    @Override
////    protected void requestData() {
////        String token = isRefreshing ? null : mBean.getNextPageToken();
////        VSApi.getTweetLikeList(16768658, token, mHandler); //mOperator.getTweetDetail().getId()
////    }
////
////    @Override
////    protected void onRequestSuccess(int code) {
////        super.onRequestSuccess(code);
////        if (mAdapter.getCount() < 20 && mAgencyView != null)
////            mAgencyView.resetLikeCount(mAdapter.getCount());
////    }
////
////    @Override
////    public void onItemClick(int position, long itemId) {
//////        super.onItemClick(position, itemId);
//////        TweetLike liker = mAdapter.getItem(position);
//////        if (liker == null) return;
//////        UIHelper.showUserCenter(getContext(), liker.getAuthor().getId(), liker.getAuthor().getName());
////    }
////
////    @Override
////    public void onLikeSuccess(boolean isUp, User user) {
////        onRefreshing();
////    }
//
//
//    @Override
//    public void initView(View view) {
//        super.initView(view);
//
//    }
//
//    private List mDatas;
//    public void initDatas()
//    {
//        mDatas = new ArrayList<String>();
//        for (int i = 'A'; i < 'z'; i++)
//        {
//            mDatas.add("" + (char) i);
//        }
//    }
//
//    @Override
//    protected void initWidget(View root) {
//        super.initWidget(root);
//
//        initDatas();
//        Log.d("TAG", "" + mDatas.size());
//        time_line_layout.addItemDecoration(new DividerItemDecoration(getContext()));
//        time_line_layout.setLayoutManager(new LinearLayoutManager(getActivity()));
//        time_line_layout.setAdapter(mAdapter = new PlayerDetailAdapter(mDatas));
//
//
//
//
//        setHeaderView(time_line_layout);
//
//    }
//
//    private void setHeaderView(RecyclerView view){
//        View header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_detail_header, view, false);
//        mAdapter.setHeaderView(header);
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//
//
//    private PlayerDetailAdapter mAdapter;
//    public class PlayerDetailAdapter extends RecyclerView.Adapter<PlayerDetailAdapter.ListHolder> {
//
////        @Override
////        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
////        {
////            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
////                    getActivity()).inflate(R.layout.item_list_sub_me_of_newest, parent,
////                    false));
////            return holder;
////        }
////
////
////        @Override
////        public void onBindViewHolder(MyViewHolder holder, int position)
////        {
////            holder.tv.setText("" + mDatas.get(position));
////        }
////
////        @Override
////        public int getItemCount()
////        {
////            return mDatas.size();
////        }
////
////        public class MyViewHolder extends RecyclerView.ViewHolder
////        {
////
////            TextView tv;
////
////            public MyViewHolder(View view)
////            {
////                super(view);
////                tv = (TextView) view.findViewById(R.id.item_list_sub_me_time);
////            }
////        }
//
//        public static final int TYPE_HEADER = 0;  //说明是带有Header的
//        public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
//        public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
//
//        //获取从Activity中传递过来每个item的数据集合
//        private List<String> mDatas;
//        //HeaderView, FooterView
//        private View mHeaderView;
//        private View mFooterView;
//
//        //构造函数
//        public PlayerDetailAdapter(List<String> list){
//            this.mDatas = list;
//        }
//
//        //HeaderView和FooterView的get和set函数
//        public View getHeaderView() {
//            return mHeaderView;
//        }
//        public void setHeaderView(View headerView) {
//            mHeaderView = headerView;
//            notifyItemInserted(0);
//        }
//        public View getFooterView() {
//            return mFooterView;
//        }
//        public void setFooterView(View footerView) {
//            mFooterView = footerView;
//            notifyItemInserted(getItemCount()-1);
//        }
//
//        /** 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    * */
//        @Override
//        public int getItemViewType(int position) {
//            if (mHeaderView == null && mFooterView == null){
//                return TYPE_NORMAL;
//            }
//            if (position == 0){
//                //第一个item应该加载Header
//                return TYPE_HEADER;
//            }
//            if (position == getItemCount()-1){
//                //最后一个,应该加载Footer
//                return TYPE_FOOTER;
//            }
//            return TYPE_NORMAL;
//        }
//
//        //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
//        @Override
//        public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            if(mHeaderView != null && viewType == TYPE_HEADER) {
//                return new ListHolder(mHeaderView);
//            }
//            if(mFooterView != null && viewType == TYPE_FOOTER){
//                return new ListHolder(mFooterView);
//            }
//            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_sub_me_of_newest, parent, false);
//            return new ListHolder(layout);
//        }
//
//        //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
//        @Override
//        public void onBindViewHolder(ListHolder holder, int position) {
//            if(getItemViewType(position) == TYPE_NORMAL){
//                if(holder instanceof ListHolder) {
//                    //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
//                    ((ListHolder) holder).tv.setText(mDatas.get(position-1));
//                    return;
//                }
//                return;
//            }else if(getItemViewType(position) == TYPE_HEADER){
//                return;
//            }else{
//                return;
//            }
//        }
//
//        //在这里面加载ListView中的每个item的布局
//        class ListHolder extends RecyclerView.ViewHolder{
//            TextView tv;
//            public ListHolder(View itemView) {
//                super(itemView);
//                //如果是headerview或者是footerview,直接返回
//                if (itemView == mHeaderView){
//                    return;
//                }
//                if (itemView == mFooterView){
//                    return;
//                }
//                tv = (TextView)itemView.findViewById(R.id.item_list_sub_me_time);
//            }
//        }
//
//        //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
//        @Override
//        public int getItemCount() {
//            if(mHeaderView == null && mFooterView == null){
//                return mDatas.size();
//            }else if(mHeaderView == null && mFooterView != null){
//                return mDatas.size() + 1;
//            }else if (mHeaderView != null && mFooterView == null){
//                return mDatas.size() + 1;
//            }else {
//                return mDatas.size() + 2;
//            }
//        }
//
//    }


}
