package com.vs.vipsai.detail.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.vs.vipsai.AppContext;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.R;
import com.vs.vipsai.api.remote.VSApi;
import com.vs.vipsai.base.activities.BackActivity;
import com.vs.vipsai.bean.PlayerComment;
import com.vs.vipsai.bean.ResultBean;
import com.vs.vipsai.bean.Tweet;
import com.vs.vipsai.behavior.CommentBar;
import com.vs.vipsai.detail.viewpager.PlayerDetailViewPagerFragment;
import com.vs.vipsai.tweet.contract.TweetDetailContract;
import com.vs.vipsai.ui.activity.ReactNativeContainerActivity;
import com.vs.vipsai.ui.videoplayer.SampleVideo;
import com.vs.vipsai.ui.videoplayer.SwitchVideoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

//import com.vs.vipsai.ui.fragment.PlayerDetailViewPagerFragment;

/**
 * Author: cynid
 * Created on 3/21/18 4:41 PM
 * Description:
 *
 * 比赛详情基类
 */

public class BasePlayerDetailActivity extends BackActivity implements TweetDetailContract.Operator {

    public static final String BUNDLE_KEY_TWEET = "BUNDLE_KEY_TWEET";

//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;

    @BindView(R.id.layout_appbar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.layout_coordinator)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.layout_container)
    LinearLayout headLayout;


    @BindView(R.id.layoutHeader)
    RelativeLayout detail_header_layout;
    @BindView(R.id.layout_btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.layout_btn_detail)
    RelativeLayout btnDetail;
    @BindView(R.id.layout_header_title)
    TextView headerTitle;


    private Tweet tweet;


    public TweetDetailContract.ICmnView mCmnViewImp;
    public TweetDetailContract.IAgencyView mAgencyViewImp;


    public CommentBar mDelegation;





    @BindView(R.id.video_player)
    SampleVideo videoPlayer;



    @BindView(R.id.activity_player_detail_tab_nav)
    TabLayout mTabLayout;



    private onViewPagerSelectListener mListener;






    @Override
    protected int getContentView() {
        return R.layout.activity_player_detail;
    }

    @Override
    protected void initData() {

        VSApi.getTweetDetail(1, new TextHttpResponseHandler() {//tweet.getId()
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
//                Toast.makeText(PlayerDetailActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();

                responseString = "{\n" +
                        "  \"code\": 1,\n" +
                        "  \"message\": \"success\",\n" +
                        "  \"time\": \"1632565432\",\n" +
                        "  \"result\": {\n" +
                        "    \"id\": 1,\n" +
                        "    \"content\": \"hello world\",\n" +
                        "    \"appClient\": 1,\n" +
                        "    \"commentCount\": 200,\n" +
                        "    \"likeCount\": 301,\n" +
                        "    \"liked\": true,\n" +
                        "    \"pubDate\": \"2018-1-2\",\n" +
                        "    \"Code\": {\n" +
                        "      \"brush\": \"123\",\n" +
                        "      \"content\": \"112312515\"\n" +
                        "    },\n" +
                        "    \"href\": \"http://www.baidu.com\",\n" +
                        "    \"audio\": [\n" +
                        "      {\n" +
                        "        \"href\": \"http://www.google.com\",\n" +
                        "        \"timeSpan\": 101012101\n" +
                        "      }\n" +
                        "    ],\n" +
                        "    \"images\": [\n" +
                        "      {\n" +
                        "        \"thumb\": \"sss\",\n" +
                        "        \"href\": \"http://www.qq.com\",\n" +
                        "        \"name\": \"dsa\",\n" +
                        "        \"type\": 1,\n" +
                        "        \"w\": 32,\n" +
                        "        \"h\": 32\n" +
                        "      }\n" +
                        "    ],\n" +
                        "    \"statistics\": {\n" +
                        "      \"comment\": 12,\n" +
                        "      \"transmit\": 205,\n" +
                        "      \"like\": 256\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";

                ResultBean<Tweet> result = AppOperator.createGson().fromJson(
                        responseString, new TypeToken<ResultBean<Tweet>>() {
                        }.getType());
                if (result.isSuccess()) {
                    if (result.getResult() == null) {
                        AppContext.showToast("data null..."); //R.string.tweet_detail_data_null
                        finish();
                        return;
                    }
                    tweet = result.getResult();
                    mAgencyViewImp.resetCmnCount(tweet.getCommentCount());
                    mAgencyViewImp.resetLikeCount(tweet.getLikeCount());
                    setupDetailView();
                } else {
                    onFailure(500, headers, "妈的智障", null);
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ResultBean<Tweet> result = AppOperator.createGson().fromJson(
                        responseString, new TypeToken<ResultBean<Tweet>>() {
                        }.getType());
                if (result.isSuccess()) {
                    if (result.getResult() == null) {
                        AppContext.showToast("data null..."); //R.string.tweet_detail_data_null
                        finish();
                        return;
                    }
                    tweet = result.getResult();
//                    mAgencyViewImp.resetCmnCount(tweet.getCommentCount());
//                    mAgencyViewImp.resetLikeCount(tweet.getLikeCount());
                    setupDetailView();
                } else {
                    onFailure(500, headers, "妈的智障", null);
                }
            }
        });
    }

    @Override
    protected void initWidget() {
        setSwipeBackEnable(true);


//        startActivity(new Intent(this, ReactNativeContainerActivity.class));


        detail_header_layout.setBackgroundResource(R.color.transparent);
//        mPagerFrag.setTabLayout(View.INVISIBLE);
        headerTitle.setVisibility(View.GONE);
//        mTabLayout.setVisibility(View.GONE);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -headLayout.getHeight() / 2) {
//                    mPagerFrag.setTabLayout(View.VISIBLE);
//                    detail_header_layout.setBackgroundResource(R.color.gray);
                    headerTitle.setVisibility(View.VISIBLE);
//                    mTabLayout.setVisibility(View.VISIBLE);

                } else {
//                    mPagerFrag.setTabLayout(View.INVISIBLE);
//                    detail_header_layout.setBackgroundResource(R.color.transparent);
                    headerTitle.setVisibility(View.GONE);
//                    mTabLayout.setVisibility(View.GONE);

                    float scale = (float) verticalOffset / (-headLayout.getHeight() / 2);
                    float alpha = (255 * scale);
                    int color = Color.argb((int) alpha, 255, 255, 255); //212, 58, 50  (int) alpha, 33, 33, 33
                    detail_header_layout.setBackgroundColor(color);
//                    mPagerFrag.setTabLayoutBackgroundColor(color);


                    mTabLayout.setBackgroundColor(color);


                    int blackColor = Color.argb((int) alpha, 33, 33, 33); //212, 58, 50  (int) alpha, 33, 33, 33
                    int mackColor = Color.argb((int) alpha, 72, 170, 251); //212, 58, 50  (int) alpha, 33, 33, 33
                    mTabLayout.setSelectedTabIndicatorColor(mackColor);
                    mTabLayout.setTabTextColors(blackColor, mackColor);





                }
            }
        });



        String source1 = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
        String name = "普通";
        SwitchVideoModel switchVideoModel = new SwitchVideoModel(name, source1);

        String source2 = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
        String name2 = "清晰";
        SwitchVideoModel switchVideoModel2 = new SwitchVideoModel(name2, source2);

        List<SwitchVideoModel> list = new ArrayList<>();
        list.add(switchVideoModel);
        list.add(switchVideoModel2);

        videoPlayer.setUp(list, true, "");

        videoPlayer.getBackButton().setVisibility(View.GONE);






        mDelegation = CommentBar.delegation(this, mCoordinatorLayout);

        mDelegation.getBottomSheet().getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    handleKeyDel();
                }
                return false;
            }
        });

        mDelegation.hideShare();
        mDelegation.hideFav();

        mDelegation.getBottomSheet().setMentionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (AccountHelper.isLogin()) {
//                    UserSelectFriendsActivity.show(TweetDetailActivity.this, mDelegation.getBottomSheet().getEditText());
//                } else
//                    LoginActivity.show(TweetDetailActivity.this);
            }
        });

//        mDelegation.getBottomSheet().getEditText().setOnKeyArrivedListener(new OnKeyArrivedListenerAdapterV2(this));
        mDelegation.getBottomSheet().showEmoji();
        mDelegation.getBottomSheet().hideSyncAction();
        mDelegation.getBottomSheet().setCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mDelegation.getBottomSheet().getCommentText().replaceAll("[\\s\\n]+", " ");

                Toast.makeText(BasePlayerDetailActivity.this, "--->" + content, Toast.LENGTH_SHORT).show();
                mDelegation.getBottomSheet().dismiss();

                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(BasePlayerDetailActivity.this, "请输入文字", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (!AccountHelper.isLogin()) {
//                    UIHelper.showLoginActivity(TweetDetailActivity.this);
//                    return;
//                }
//                if (replies.size() > 0)
//                    content = mDelegation.getBottomSheet().getEditText().getHint() + ": " + content;
//                OSChinaApi.pubTweetComment(tweet.getId(), content, 0, publishCommentHandler);
            }
        });




        setupDetailView();


        // viewpager fragment
//        mPagerFrag = PlayerDetailViewPagerFragment.instantiate();
//        mCmnViewImp = mPagerFrag.getCommentViewHandler();
////        mThumbupViewImp = mPagerFrag.getThumbupViewHandler();
//        mAgencyViewImp = mPagerFrag.getAgencyViewHandler();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, mPagerFrag)
//                .commit();


//        mToolbar.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mCmnViewImp.onCommentSuccess(null);
//            }
//        }, 2000);




        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mListener != null) {
                    mListener.SetViewPagerSelectListener(tab.getPosition());
                    setCommentBarVisible(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public Tweet getTweetDetail() {
        return tweet;
    }

    @Override
    public void toReply(PlayerComment comment) {

    }

    @Override
    public void onScroll() {

    }

    private void setupDetailView() {
        // 有可能传入的tweet只有id这一个值
        if (tweet == null || isDestroy())
            return;
//        Author author = tweet.getAuthor();
//        mIdentityView.setup(author);
//        if (author != null) {
//            ivPortrait.setup(author);
//            ivPortrait.setOnClickListener(getOnPortraitClickListener());
//            tvNick.setText(author.getName());
//        } else {
//            ivPortrait.setup(0, "匿名用户", "");
//            tvNick.setText("匿名用户");
//        }
//        if (!TextUtils.isEmpty(tweet.getPubDate()))
//            tvTime.setText(StringUtils.formatSomeAgo(tweet.getPubDate()));
//        PlatfromUtil.setPlatFromString(tvClient, tweet.getAppClient());
//        if (tweet.isLiked()) {
//            ivThumbup.setSelected(true);
//        } else {
//            ivThumbup.setSelected(false);
//        }
//        if (!TextUtils.isEmpty(tweet.getContent())) {
//            String content = tweet.getContent().replaceAll("[\n\\s]+", " ");
//            mContent.setText(TweetParser.getInstance().parse(this, content));
//            mContent.setMovementMethod(LinkMovementMethod.getInstance());
//        }
//
//        mLayoutGrid.setImage(tweet.getImages());
//
//        /* -- about reference -- */
//        if (tweet.getAbout() != null) {
//            mLayoutRef.setVisibility(View.VISIBLE);
//            About about = tweet.getAbout();
//            mLayoutRefImages.setImage(about.getImages());
//
//            if (!About.check(about)) {
//                mViewRefTitle.setVisibility(View.VISIBLE);
//                mViewRefTitle.setText("不存在或已删除的内容");
//                mViewRefContent.setText("抱歉，该内容不存在或已被删除");
//            } else {
//                if (about.getType() == OSChinaApi.COMMENT_TWEET) {
//                    mViewRefTitle.setVisibility(View.GONE);
//                    String aName = "@" + about.getTitle();
//                    String cnt = about.getContent();
//                    Spannable spannable = TweetParser.getInstance().parse(this, cnt);
//                    SpannableStringBuilder builder = new SpannableStringBuilder();
//                    builder.append(aName).append(": ");
//                    builder.append(spannable);
//                    ForegroundColorSpan span = new ForegroundColorSpan(
//                            getResources().getColor(R.color.day_colorPrimary));
//                    builder.setSpan(span, 0, aName.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                    mViewRefContent.setText(builder);
//                } else {
//                    mViewRefTitle.setVisibility(View.VISIBLE);
//                    mViewRefTitle.setText(about.getTitle());
//                    mViewRefContent.setText(about.getContent());
//                }
//            }
//        } else {
//            mLayoutRef.setVisibility(View.GONE);
//        }
    }


    public void setCommentBarVisible(int position){

    }



    public interface onViewPagerSelectListener {
        void SetViewPagerSelectListener(int index);
    }

    public void setOnViewPagerSelectListener(onViewPagerSelectListener onViewPagerSelectListener) {
        this.mListener = onViewPagerSelectListener;
    }



}
