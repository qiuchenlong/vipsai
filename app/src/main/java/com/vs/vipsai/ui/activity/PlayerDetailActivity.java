package com.vs.vipsai.ui.activity;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
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
import com.vs.vipsai.base.activities.BaseActivity;
import com.vs.vipsai.bean.PlayerComment;
import com.vs.vipsai.bean.ResultBean;
import com.vs.vipsai.bean.Tweet;
import com.vs.vipsai.behavior.CommentBar;
import com.vs.vipsai.tweet.contract.TweetDetailContract;
import com.vs.vipsai.ui.fragment.PlayerDetailViewPagerFragment;
import com.vs.vipsai.widget.ObservableScrollView;
//import com.vs.vipsai.ui.fragment.PlayerDetailViewPagerFragment;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

/**
 * Author: cynid
 * Created on 3/21/18 4:41 PM
 * Description:
 *
 * 比赛详情页面
 */

public class PlayerDetailActivity extends BackActivity implements TweetDetailContract.Operator {

    public static final String BUNDLE_KEY_TWEET = "BUNDLE_KEY_TWEET";

//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;

    @BindView(R.id.layout_appbar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.layout_coordinator)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.layout_container)
    LinearLayout headLayout;


//    @BindView(R.id.layout_main_header_header)
//    RelativeLayout detail_header_layout;
    @BindView(R.id.layoutHeader)
    RelativeLayout detail_header_layout;
    @BindView(R.id.layout_btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.layout_btn_detail)
    RelativeLayout btnDetail;
    @BindView(R.id.layout_header_title)
    TextView headerTitle;


    // 带有滚动监听的scrollview
//    @BindView(R.id.activity_car_detail_observable_scrollview)
//    ObservableScrollView scrollView;


    private int imageHeight;


    private Tweet tweet;


    private TweetDetailContract.ICmnView mCmnViewImp;
    private TweetDetailContract.IAgencyView mAgencyViewImp;


    private CommentBar mDelegation;

    PlayerDetailViewPagerFragment mPagerFrag;


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
//        mToolbar.setTitle("比赛详情");
//        setSupportActionBar(mToolbar);

        imageHeight = 220;


//        headerLayout.setVisibility(View.INVISIBLE);

        detail_header_layout.setBackgroundResource(R.color.transparent);
        headerTitle.setVisibility(View.GONE);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



//        scrollView.setScrollViewListener(PlayerDetailActivity.this);




        mCoordinatorLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("aaa", "dddLin");
                return false;
            }
        });

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -headLayout.getHeight()) {
//                    collapsingToolbarLayout.setTitle("    谭松韵seven");
//                    headerLayout.setVisibility(View.VISIBLE);
                    mPagerFrag.setTabLayout(View.VISIBLE);
                    detail_header_layout.setBackgroundResource(R.color.gray);
                    headerTitle.setVisibility(View.VISIBLE);
                } else {
//                    collapsingToolbarLayout.setTitle(" ");
//                    headerLayout.setVisibility(View.INVISIBLE);
                    mPagerFrag.setTabLayout(View.INVISIBLE);
                    detail_header_layout.setBackgroundResource(R.color.transparent);
                    headerTitle.setVisibility(View.GONE);
                }
            }
        });


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

                Toast.makeText(PlayerDetailActivity.this, "--->" + content, Toast.LENGTH_SHORT).show();
                mDelegation.getBottomSheet().dismiss();

                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(PlayerDetailActivity.this, "请输入文字", Toast.LENGTH_SHORT).show();
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
        mPagerFrag = PlayerDetailViewPagerFragment.instantiate();
        mCmnViewImp = mPagerFrag.getCommentViewHandler();
//        mThumbupViewImp = mPagerFrag.getThumbupViewHandler();
        mAgencyViewImp = mPagerFrag.getAgencyViewHandler();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mPagerFrag)
                .commit();


//        mToolbar.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mCmnViewImp.onCommentSuccess(null);
//            }
//        }, 2000);


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
        Log.d("aaa", "ddd");
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


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("aaa", "ddd");
        return super.onTouchEvent(event);
    }

//    @Override
//    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
//        Log.d("aaa", "" + x + "," + "y");
//        if (y <= 0) {
//            headerLayout.setBackgroundColor(Color.argb((int) 0, 212, 58, 50));//AGB由相关工具获得，或者美工提供
//        } else if (y > 0 && y <= imageHeight) {
//            float scale = (float) y / imageHeight;
//            float alpha = (255 * scale);
//            // 只是layout背景透明(仿知乎滑动效果)
//            headerLayout.setBackgroundColor(Color.argb((int) alpha, 212, 58, 50));
////            backToTop.setVisibility(View.GONE);
//        } else {
//            headerLayout.setBackgroundColor(Color.argb((int) 255, 212, 58, 50));
////            backToTop.setVisibility(View.VISIBLE);
//        }
//    }
}
