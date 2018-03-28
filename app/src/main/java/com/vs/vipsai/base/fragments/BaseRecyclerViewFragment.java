package com.vs.vipsai.base.fragments;

import android.graphics.Rect;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.loopj.android.http.TextHttpResponseHandler;
import com.vs.vipsai.AppConfig;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.R;
import com.vs.vipsai.adapter.PlayerBonusAdapter;
import com.vs.vipsai.adapter.PlayerCommentAdapter;
import com.vs.vipsai.adapter.PlayerDetailAdapter;
import com.vs.vipsai.base.adapter.BaseGeneralRecyclerAdapter;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.bean.PageBean;
import com.vs.vipsai.bean.ResultBean;
import com.vs.vipsai.main.my.AllSubAdapter;
import com.vs.vipsai.main.my.DividerItemDecoration;
import com.vs.vipsai.main.my.WinnerSubAdapter;
import com.vs.vipsai.main.past.ChampionWorkSubAdapter;
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
 *
 * RecyclerViewFragment的基类，定义通用的数据请求流程
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
    protected TextHttpResponseHandler mHandler111;
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
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.lay_16);
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
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(getLayoutManager());
        }
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


        // 手动 判断 泛型
//        if (mAdapter instanceof PlayerDetailAdapter ||
//                mAdapter instanceof PlayerCommentAdapter ||
//                mAdapter instanceof PlayerBonusAdapter) {

//        }

        // 默认线性布局
        if (mAdapter instanceof ChampionWorkSubAdapter) {
            setRecyclerStaggeredGridLayoutStyle(2);
        } else if (mAdapter instanceof WinnerSubAdapter ||
            mAdapter instanceof AllSubAdapter) {
            setRecyclerStaggeredGridLayoutStyle(3);
        } else {
            mRecyclerView.setLayoutManager(getLayoutManager());
        }


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


        mHandler111 = new TextHttpResponseHandler() {
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

                responseString = "{\"code\":1,\"message\":\"SUCCESS\",\"result\":{\"items\":[{\"author\":{\"id\":864999,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"AeroZen\",\"portrait\":\"http://static.oschina.net/uploads/user/432/864999_50.jpg?t=1352358674000\",\"relation\":0},\"pubDate\":\"2018-03-22 09:26:16\"},{\"author\":{\"id\":2521898,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"_奇洛\",\"portrait\":\"http://static.oschina.net/uploads/user/1260/2521898_50.jpg?t=1454550683000\",\"relation\":0},\"pubDate\":\"2018-03-22 09:19:42\"},{\"author\":{\"id\":2366516,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"归园田居\",\"portrait\":\"http://static.oschina.net/uploads/user/1183/2366516_50.jpg?t=1434336507000\",\"relation\":0},\"pubDate\":\"2018-03-22 08:47:04\"},{\"author\":{\"id\":239863,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"文耳兑心\",\"portrait\":\"http://static.oschina.net/uploads/user/119/239863_50.jpg?t=1382934209000\",\"relation\":0},\"pubDate\":\"2018-03-22 07:57:00\"},{\"author\":{\"id\":347223,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"clouddyy\",\"portrait\":\"http://static.oschina.net/uploads/user/173/347223_50.jpeg?t=1477362397000\",\"relation\":0},\"pubDate\":\"2018-03-22 07:11:26\"},{\"author\":{\"id\":3186485,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"lovejar\",\"portrait\":\"http://static.oschina.net/uploads/user/1593/3186485_50.jpg?t=1482920236000\",\"relation\":0},\"pubDate\":\"2018-03-21 22:31:27\"},{\"author\":{\"id\":731312,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"obaniu\",\"portrait\":\"http://static.oschina.net/uploads/user/365/731312_50.jpg?t=1492759957000\",\"relation\":0},\"pubDate\":\"2018-03-21 20:44:36\"},{\"author\":{\"id\":1779409,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"Fenying\",\"portrait\":\"http://static.oschina.net/uploads/user/889/1779409_50.jpg?t=1501767998000\",\"relation\":0},\"pubDate\":\"2018-03-21 19:49:50\"},{\"author\":{\"id\":3785538,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"OSC_UbyuvY\",\"portrait\":\"http://static.oschina.net/uploads/user/1892/3785538_50.jpg?t=1519649386000\",\"relation\":0},\"pubDate\":\"2018-03-21 17:05:24\"},{\"author\":{\"id\":2858361,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"锦年\",\"portrait\":\"http://static.oschina.net/uploads/user/1429/2858361_50.jpg?t=1469500444000\",\"relation\":0},\"pubDate\":\"2018-03-21 12:04:12\"},{\"author\":{\"id\":3794450,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"OSC_oiyaAP\",\"portrait\":\"http://static.oschina.net/uploads/user/1897/3794450_50.jpg?t=1520679950000\",\"relation\":0},\"pubDate\":\"2018-03-21 11:32:15\"},{\"author\":{\"id\":2884911,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"-jar\",\"portrait\":\"http://static.oschina.net/uploads/user/1442/2884911_50.jpg?t=1512433395000\",\"relation\":0},\"pubDate\":\"2018-03-21 10:59:55\"},{\"author\":{\"id\":991837,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"Coffee_M\",\"portrait\":\"http://static.oschina.net/uploads/user/495/991837_50.jpg?t=1468749327000\",\"relation\":0},\"pubDate\":\"2018-03-21 10:51:27\"},{\"author\":{\"id\":1248135,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"leiboo\",\"portrait\":\"http://static.oschina.net/uploads/user/624/1248135_50.jpg?t=1429062958000\",\"relation\":0},\"pubDate\":\"2018-03-21 09:45:35\"},{\"author\":{\"id\":111634,\"identity\":{\"officialMember\":false,\"softwareAuthor\":true},\"name\":\"理工男海哥\",\"portrait\":\"http://static.oschina.net/uploads/user/55/111634_50.jpeg?t=1521633080000\",\"relation\":0},\"pubDate\":\"2018-03-21 09:43:41\"}],\"nextPageToken\":\"DBA816934CD0AA59\",\"prevPageToken\":\"0997C855C600E421\",\"requestCount\":20,\"responseCount\":15,\"totalResults\":15},\"time\":\"2018-03-22 10:28:27\"}";


                // Commend
                responseString = "{\"code\":1,\"message\":\"SUCCESS\",\"result\":{\"items\":[{\"appClient\":0,\"author\":{\"id\":1863616,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"387\",\"portrait\":\"http://static.oschina.net/uploads/user/931/1863616_50.jpeg?t=1489504536000\",\"relation\":0},\"content\":\"我不入地域，谁入？\",\"id\":16779452,\"pubDate\":\"2018-03-22 12:13:46\"},{\"appClient\":0,\"author\":{\"id\":254844,\"identity\":{\"officialMember\":false,\"softwareAuthor\":true},\"name\":\"sjzjams\",\"portrait\":\"http://static.oschina.net/uploads/user/127/254844_50.jpg?t=1370076010000\",\"relation\":0},\"content\":\"必须升级到java10\",\"id\":16779036,\"pubDate\":\"2018-03-22 11:33:09\"},{\"appClient\":0,\"author\":{\"id\":2973425,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"玖越\",\"portrait\":\"http://static.oschina.net/uploads/user/1486/2973425_50.jpeg?t=1477058862000\",\"relation\":0},\"content\":\"我反对这门亲事\",\"id\":16779034,\"pubDate\":\"2018-03-22 11:32:58\"},{\"appClient\":0,\"author\":{\"id\":3698843,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"宅女喵\",\"portrait\":\"http://static.oschina.net/uploads/user/1849/3698843_50.jpg?t=1506499505000\",\"relation\":0},\"content\":\"全面屏么？\",\"id\":16778895,\"pubDate\":\"2018-03-22 11:20:42\"},{\"appClient\":0,\"author\":{\"id\":220983,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"eggbucket\",\"portrait\":\"http://static.oschina.net/uploads/user/110/220983_50.jpg?t=1378226978000\",\"relation\":0},\"content\":\"日后再说\",\"id\":16778115,\"pubDate\":\"2018-03-22 10:29:55\"},{\"appClient\":0,\"author\":{\"id\":147489,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"木狼\",\"portrait\":\"http://static.oschina.net/uploads/user/73/147489_50.jpeg?t=1509413198000\",\"relation\":0},\"content\":\"你只需要一句话,他就得忙上一整月, 他当然不干\",\"id\":16778043,\"pubDate\":\"2018-03-22 10:24:51\"},{\"appClient\":0,\"author\":{\"id\":1010876,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"bedrock32\",\"portrait\":\"http://static.oschina.net/uploads/user/505/1010876_50.jpeg?t=1477099002000\",\"relation\":0},\"content\":\"日后再说\",\"id\":16777870,\"pubDate\":\"2018-03-22 10:11:48\"},{\"appClient\":3,\"author\":{\"id\":2646627,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"Tomato丶\",\"portrait\":\"http://static.oschina.net/uploads/user/1323/2646627_50.jpeg?t=1495444537000\",\"relation\":0},\"content\":\"干他\",\"id\":16777570,\"pubDate\":\"2018-03-22 09:50:15\"},{\"appClient\":0,\"author\":{\"id\":565401,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"ubuntuvim\",\"portrait\":\"http://static.oschina.net/uploads/user/282/565401_50.jpg?t=1421517275000\",\"relation\":0},\"content\":\"日后再说\",\"id\":16777202,\"pubDate\":\"2018-03-22 09:27:35\"},{\"appClient\":0,\"author\":{\"id\":864999,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"AeroZen\",\"portrait\":\"http://static.oschina.net/uploads/user/432/864999_50.jpg?t=1352358674000\",\"relation\":0},\"content\":\"日后再说\",\"id\":16777186,\"pubDate\":\"2018-03-22 09:26:24\"},{\"appClient\":0,\"author\":{\"id\":2822400,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"曾经蜡笔没有小新\",\"portrait\":\"http://static.oschina.net/uploads/user/1411/2822400_50.jpeg?t=1474607315000\",\"relation\":0},\"content\":\"日后再说吧\",\"id\":16777142,\"pubDate\":\"2018-03-22 09:22:08\"},{\"appClient\":0,\"author\":{\"id\":2524511,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"五毛钱特效\",\"portrait\":\"http://static.oschina.net/uploads/user/1262/2524511_50.jpeg?t=1503048446000\",\"relation\":0},\"content\":\"日后再说\",\"id\":16777040,\"pubDate\":\"2018-03-22 09:13:17\"},{\"appClient\":0,\"author\":{\"id\":988298,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"麦琪\",\"portrait\":\"http://static.oschina.net/uploads/user/494/988298_50.png?t=1400725084000\",\"relation\":0},\"content\":\"坚决支持你开他的菊。\",\"id\":16777036,\"pubDate\":\"2018-03-22 09:13:05\"},{\"appClient\":0,\"author\":{\"id\":2671788,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"火薯\",\"portrait\":\"http://static.oschina.net/uploads/user/1335/2671788_50.jpeg?t=1521106613000\",\"relation\":0},\"content\":\"墙裂支持红薯！！\",\"id\":16777031,\"pubDate\":\"2018-03-22 09:12:31\"},{\"appClient\":0,\"author\":{\"id\":1768789,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"Roadlu\",\"portrait\":\"http://static.oschina.net/uploads/user/884/1768789_50.jpg?t=1399612310000\",\"relation\":0},\"content\":\"从后面开，没毛病\",\"id\":16777009,\"pubDate\":\"2018-03-22 09:10:26\"},{\"appClient\":0,\"author\":{\"id\":188666,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"沙海\",\"portrait\":\"http://static.oschina.net/uploads/user/94/188666_50.jpg?t=1370831766000\",\"relation\":0},\"content\":\"开吧\",\"id\":16776966,\"pubDate\":\"2018-03-22 09:05:04\"},{\"appClient\":0,\"author\":{\"id\":3716038,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"开源中国老鸨\",\"portrait\":\"http://static.oschina.net/uploads/user/1858/3716038_50.jpeg?t=1509075626000\",\"relation\":0},\"content\":\"开\",\"id\":16776960,\"pubDate\":\"2018-03-22 09:04:01\"},{\"appClient\":3,\"author\":{\"id\":1392885,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"叼烟斗的纤夫\",\"portrait\":\"http://static.oschina.net/uploads/user/696/1392885_50.jpg?t=1383454249000\",\"relation\":0},\"content\":\"开吧\",\"id\":16776949,\"pubDate\":\"2018-03-22 09:03:01\"},{\"appClient\":3,\"author\":{\"id\":919487,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"Big_BoBo\",\"portrait\":\"http://static.oschina.net/uploads/user/459/919487_50.png?t=1402203237000\",\"relation\":0},\"content\":\"不用易语言了吗\",\"id\":16776932,\"pubDate\":\"2018-03-22 09:01:40\"},{\"appClient\":0,\"author\":{\"id\":2448166,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"无畏地主\",\"portrait\":\"http://static.oschina.net/uploads/user/1224/2448166_50.jpeg?t=1520383554000\",\"relation\":0},\"content\":\"原来薯薯也是java\",\"id\":16776867,\"pubDate\":\"2018-03-22 08:56:04\"}],\"nextPageToken\":\"DBA816934CD0AA59\",\"prevPageToken\":\"0997C855C600E421\",\"requestCount\":20,\"responseCount\":20,\"totalResults\":82},\"time\":\"2018-03-22 12:29:51\"}";


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



//                responseString = "{\"code\":1,\"message\":\"SUCCESS\",\"result\":{\"items\":[{\"author\":{\"id\":864999,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"AeroZen\",\"portrait\":\"http://static.oschina.net/uploads/user/432/864999_50.jpg?t=1352358674000\",\"relation\":0},\"pubDate\":\"2018-03-22 09:26:16\"},{\"author\":{\"id\":2521898,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"_奇洛\",\"portrait\":\"http://static.oschina.net/uploads/user/1260/2521898_50.jpg?t=1454550683000\",\"relation\":0},\"pubDate\":\"2018-03-22 09:19:42\"},{\"author\":{\"id\":2366516,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"归园田居\",\"portrait\":\"http://static.oschina.net/uploads/user/1183/2366516_50.jpg?t=1434336507000\",\"relation\":0},\"pubDate\":\"2018-03-22 08:47:04\"},{\"author\":{\"id\":239863,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"文耳兑心\",\"portrait\":\"http://static.oschina.net/uploads/user/119/239863_50.jpg?t=1382934209000\",\"relation\":0},\"pubDate\":\"2018-03-22 07:57:00\"},{\"author\":{\"id\":347223,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"clouddyy\",\"portrait\":\"http://static.oschina.net/uploads/user/173/347223_50.jpeg?t=1477362397000\",\"relation\":0},\"pubDate\":\"2018-03-22 07:11:26\"},{\"author\":{\"id\":3186485,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"lovejar\",\"portrait\":\"http://static.oschina.net/uploads/user/1593/3186485_50.jpg?t=1482920236000\",\"relation\":0},\"pubDate\":\"2018-03-21 22:31:27\"},{\"author\":{\"id\":731312,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"obaniu\",\"portrait\":\"http://static.oschina.net/uploads/user/365/731312_50.jpg?t=1492759957000\",\"relation\":0},\"pubDate\":\"2018-03-21 20:44:36\"},{\"author\":{\"id\":1779409,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"Fenying\",\"portrait\":\"http://static.oschina.net/uploads/user/889/1779409_50.jpg?t=1501767998000\",\"relation\":0},\"pubDate\":\"2018-03-21 19:49:50\"},{\"author\":{\"id\":3785538,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"OSC_UbyuvY\",\"portrait\":\"http://static.oschina.net/uploads/user/1892/3785538_50.jpg?t=1519649386000\",\"relation\":0},\"pubDate\":\"2018-03-21 17:05:24\"},{\"author\":{\"id\":2858361,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"锦年\",\"portrait\":\"http://static.oschina.net/uploads/user/1429/2858361_50.jpg?t=1469500444000\",\"relation\":0},\"pubDate\":\"2018-03-21 12:04:12\"},{\"author\":{\"id\":3794450,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"OSC_oiyaAP\",\"portrait\":\"http://static.oschina.net/uploads/user/1897/3794450_50.jpg?t=1520679950000\",\"relation\":0},\"pubDate\":\"2018-03-21 11:32:15\"},{\"author\":{\"id\":2884911,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"-jar\",\"portrait\":\"http://static.oschina.net/uploads/user/1442/2884911_50.jpg?t=1512433395000\",\"relation\":0},\"pubDate\":\"2018-03-21 10:59:55\"},{\"author\":{\"id\":991837,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"Coffee_M\",\"portrait\":\"http://static.oschina.net/uploads/user/495/991837_50.jpg?t=1468749327000\",\"relation\":0},\"pubDate\":\"2018-03-21 10:51:27\"},{\"author\":{\"id\":1248135,\"identity\":{\"officialMember\":false,\"softwareAuthor\":false},\"name\":\"leiboo\",\"portrait\":\"http://static.oschina.net/uploads/user/624/1248135_50.jpg?t=1429062958000\",\"relation\":0},\"pubDate\":\"2018-03-21 09:45:35\"},{\"author\":{\"id\":111634,\"identity\":{\"officialMember\":false,\"softwareAuthor\":true},\"name\":\"理工男海哥\",\"portrait\":\"http://static.oschina.net/uploads/user/55/111634_50.jpeg?t=1521633080000\",\"relation\":0},\"pubDate\":\"2018-03-21 09:43:41\"}],\"nextPageToken\":\"DBA816934CD0AA59\",\"prevPageToken\":\"0997C855C600E421\",\"requestCount\":20,\"responseCount\":15,\"totalResults\":15},\"time\":\"2018-03-22 10:28:27\"}";


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




