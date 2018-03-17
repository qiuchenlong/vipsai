package com.vs.vipsai.ui.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.xtablayout.XTabLayout;
import com.google.gson.reflect.TypeToken;
import com.vs.vipsai.OnTabReselectListener;
import com.vs.vipsai.R;
import com.vs.vipsai.account.activity.LoginActivity;
import com.vs.vipsai.base.fragments.BaseFragment;
import com.vs.vipsai.base.fragments.BaseTitleFragment;
import com.vs.vipsai.bean.News;
import com.vs.vipsai.bean.SubTab;
import com.vs.vipsai.main.recommend.SubFragment;
import com.vs.vipsai.media.SelectImageActivity;
import com.vs.vipsai.media.config.SelectOptions;
import com.vs.vipsai.notice.NoticeBean;
import com.vs.vipsai.notice.NoticeManager;
import com.vs.vipsai.util.BlurUtil;
import com.vs.vipsai.util.DialogHelper;
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.util.UIHelper;
import com.vs.vipsai.widget.FragmentPagerAdapter;
import com.vs.vipsai.widget.PortraitView;
import com.vs.vipsai.widget.SolarSystemView;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: cynid
 * Created on 3/12/18 6:06 PM
 * Description:
 *
 * 用户个人界面
 */

public class UserInfoFragment extends BaseFragment implements View.OnClickListener,
         NoticeManager.NoticeNotify, OnTabReselectListener { //EasyPermissions.PermissionCallbacks,

//    @BindView(R.id.iv_logo_setting)
//    ImageView mIvLogoSetting;
//    @BindView(R.id.iv_logo_zxing)
//    ImageView mIvLogoZxing;
//    @BindView(R.id.user_info_head_container)
//    FrameLayout mFlUserInfoHeadContainer;
//
//    @BindView(R.id.iv_portrait)
//    PortraitView mPortrait;
//    @BindView(R.id.iv_gender)
//    ImageView mIvGander;
//    @BindView(R.id.user_info_icon_container)
//    FrameLayout mFlUserInfoIconContainer;
//
//    @BindView(R.id.tv_nick)
//    TextView mTvName;
//    @BindView(R.id.tv_avail_score)
//    TextView mTextAvailScore;
//    @BindView(R.id.tv_active_score)
//    TextView mTextActiveScore;
//    @BindView(R.id.user_view_solar_system)
//    SolarSystemView mSolarSystem;
//    @BindView(R.id.rl_show_my_info)
//    LinearLayout mRlShowInfo;


//    @BindView(R.id.about_line)
//    View mAboutLine;
//
//    @BindView(R.id.lay_about_info)
//    LinearLayout mLayAboutCount;
//    @BindView(R.id.tv_tweet)
//    TextView mTvTweetCount;
//    @BindView(R.id.tv_favorite)
//    TextView mTvFavoriteCount;
//    @BindView(R.id.tv_following)
//    TextView mTvFollowCount;
//    @BindView(R.id.tv_follower)
//    TextView mTvFollowerCount;
//
//    @BindView(R.id.user_info_notice_message)
//    TextView mMesView;
//
//    @BindView(R.id.user_info_notice_fans)
//    TextView mFansView;

    private boolean mIsUploadIcon;
    private ProgressDialog mDialog;

    private int mMaxRadius;
    private int mR;
    private float mPx;
    private float mPy;

    private File mCacheFile;



    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.head_layout)
    LinearLayout headLayout;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.main_vp_container)
    ViewPager viewPager;

    @BindView(R.id.toolbar_tab)
    XTabLayout mLayoutTab;


    // avatar
    @BindView(R.id.fragment_main_user_home_portrait)
    PortraitView portraitView;


    private FragmentPagerAdapter mAdapter;
    private Fragment mCurFragment;
    List<SubTab> tabs;

//    private User mUserInfo;

//    private TextHttpResponseHandler requestUserInfoHandler = new TextHttpResponseHandler() {
//
//        @Override
//        public void onStart() {
//            super.onStart();
//            if (mSolarSystem != null) mSolarSystem.accelerate();
//            if (mIsUploadIcon) {
//                showWaitDialog(R.string.title_updating_user_avatar);
//            }
//        }
//
//        @Override
//        public void onFailure(int statusCode, Header[] headers, String responseString
//                , Throwable throwable) {
//            if (mIsUploadIcon) {
//                Toast.makeText(getActivity(), R.string.title_update_fail_status, Toast.LENGTH_SHORT).show();
//                deleteCacheImage();
//            }
//        }
//
//        @Override
//        public void onSuccess(int statusCode, Header[] headers, String responseString) {
//            try {
//                Type type = new TypeToken<ResultBean<User>>() {
//                }.getType();
//
//                ResultBean resultBean = AppOperator.createGson().fromJson(responseString, type);
//                if (resultBean.isSuccess()) {
//                    User userInfo = (User) resultBean.getResult();
//                    updateView(userInfo);
//                    //缓存用户信息
//                    AccountHelper.updateUserCache(userInfo);
//                }
//                if (mIsUploadIcon) {
//                    deleteCacheImage();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                onFailure(statusCode, headers, responseString, e);
//            }
//        }
//
//        @Override
//        public void onFinish() {
////            super.onFinish();
////            if (mSolarSystem != null) mSolarSystem.decelerate();
////            if (mIsUploadIcon) mIsUploadIcon = false;
////            if (mDialog != null && mDialog.isShowing()) mDialog.dismiss();
//        }
//    };


    /**
     * delete the cache image file for upload action
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void deleteCacheImage() {
        File file = this.mCacheFile;
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_user_home;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        measureTitleBarHeight();

//        if (mFansView != null)
//            mFansView.setVisibility(View.INVISIBLE);

        initSolar();

        init();

    }

//    @Override
//    protected int getContentLayoutId() {
//        return R.layout.fragment_main_user_home;
//    }
//
//    @Override
//    protected int getTitleRes() {
//        return R.string.app_name;
//    }

    private void init() {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tmp_bg);
//        headLayout.setBackgroundDrawable(new BitmapDrawable(BlurUtil.fastblur(getContext(), bitmap, 180)));
//        collapsingToolbarLayout.setContentScrim(new BitmapDrawable(BlurUtil.fastblur(getContext(), bitmap, 180)));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -headLayout.getHeight() / 2) {
                    collapsingToolbarLayout.setTitle("    谭松韵seven");
                } else {
                    collapsingToolbarLayout.setTitle(" ");
                }
            }
        });


        List<SubTab> sts = new ArrayList<>();

        SubTab st = new SubTab();
        st.setName("最新");
        st.setType(News.TYPE_NEWEST);
//        st.setHref("https://www.oschina.net/action/apiv2/sub_list?token=d6112fa662bc4bf21084670a857fbd20");
//        st.setSubtype(1);
//        st.setToken("d6112fa662bc4bf21084670a857fbd20"); // cache key
//        st.setType(News.TYPE_ATTENTION);

        sts.add(st);

        SubTab st1 = new SubTab();
        st1.setName("获奖");
        st1.setType(News.TYPE_WINNER);
//        st1.setHref("https://www.oschina.net/action/apiv2/sub_list?token=d6112fa662bc4bf21084670a857fbd20");
//        st1.setSubtype(4);
//        st1.setToken("df985be3c5d5449f8dfb47e06e098ef9"); // cache key

        sts.add(st1);


        SubTab st2 = new SubTab();
        st2.setName("比赛");
        st2.setType(News.TYPE_GAME);
        sts.add(st2);

        SubTab st3 = new SubTab();
        st3.setName("全部");
        st3.setType(News.TYPE_ALL);
        sts.add(st3);



        tabs = new ArrayList<>();
        tabs.addAll(sts);
        for (SubTab tab : tabs) {
            mLayoutTab.addTab(mLayoutTab.newTab().setText(tab.getName()));
        }

//        SubTab st1 = new SubTab();
//        st1.setName("关注");
//        tabs.add(st1);
//
//        SubTab st2 = new SubTab();
//        st2.setName("热门");
//        tabs.add(st2);


        viewPager.setAdapter(mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return SubFragment.newInstance(getContext(), tabs.get(position));
//                return DefaultFragment.instantiate(getContext(), "");
            }

            @Override
            public int getCount() {
                return tabs.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabs.get(position).getName();
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);
                if (mCurFragment == null) {
                    commitUpdate();
                }
                mCurFragment = (Fragment) object;
            }

            //this is called when notifyDataSetChanged() is called
            @Override
            public int getItemPosition(Object object) {
                return PagerAdapter.POSITION_NONE;
            }

        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mAdapter.commitUpdate();
                }
            }
        });
        mLayoutTab.setupWithViewPager(viewPager);
        mLayoutTab.setSmoothScrollingEnabled(true);


        // set vertical divider
//        LinearLayout mLinearLayout = (LinearLayout) mLayoutTab.getChildAt(0);
//        // 在所有子控件的中间显示分割线（还可能只显示顶部、尾部和不显示分割线）
//        mLinearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
//        // 设置分割线的距离本身（LinearLayout）的内间距
//        mLinearLayout.setDividerPadding(20);
//        // 设置分割线的样式
//        mLinearLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_vertical));

    }





    @Override
    protected void initData() {
        super.initData();
        NoticeManager.bindNotify(this);
        requestUserCache();
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsUploadIcon = false;
//        if (AccountHelper.isLogin()) {
//            User user = AccountHelper.getUser();
//            updateView(user);
//        } else {
//            hideView();
//        }
    }

    /**
     * if user isLogin,request user cache,
     * And then request user info and update user info
     */
    private void requestUserCache() {
//        if (AccountHelper.isLogin()) {
//            User user = AccountHelper.getUser();
//            updateView(user);
//            sendRequestData();
//        } else {
//            hideView();
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        if (!AccountHelper.isLogin()) {
//            hideView();
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NoticeManager.unBindNotify(this);
    }

    /**
     * update the view
     *
     * @param userInfo userInfo
     */
//    private void updateView(User userInfo) {
//        mPortrait.setup(userInfo);
//        mPortrait.setVisibility(View.VISIBLE);
//
//        mTvName.setText(userInfo.getName());
//        mTvName.setVisibility(View.VISIBLE);
//        mTvName.setTextSize(20.0f);
//
//        switch (userInfo.getGender()) {
//            case 0:
//                mIvGander.setVisibility(View.INVISIBLE);
//                break;
//            case 1:
//                mIvGander.setVisibility(View.VISIBLE);
//                mIvGander.setImageResource(R.mipmap.ic_male);
//                break;
//            case 2:
//                mIvGander.setVisibility(View.VISIBLE);
//                mIvGander.setImageResource(R.mipmap.ic_female);
//                break;
//            default:
//                break;
//        }
//
////        mTextAvailScore.setText(String.format("技能积分 %s", userInfo.getStatistics().getHonorScore()));
////        mTextActiveScore.setText(String.format("活跃积分 %s", userInfo.getStatistics().getActiveScore()));
//        mTextAvailScore.setVisibility(View.VISIBLE);
//        mTextActiveScore.setVisibility(View.VISIBLE);
//        mAboutLine.setVisibility(View.VISIBLE);
//        mLayAboutCount.setVisibility(View.VISIBLE);
////        mTvTweetCount.setText(formatCount(userInfo.getStatistics().getTweet()));
////        mTvFavoriteCount.setText(formatCount(userInfo.getStatistics().getCollect()));
////        mTvFollowCount.setText(formatCount(userInfo.getStatistics().getFollow()));
////        mTvFollowerCount.setText(formatCount(userInfo.getStatistics().getFans()));
//
//        mUserInfo = userInfo;
//    }

    /**
     * format count
     *
     * @param count count
     * @return formatCount
     */
    private String formatCount(long count) {

        if (count > 1000) {
            int a = (int) (count / 100);
            int b = a % 10;
            int c = a / 10;
            String str;
            if (c <= 9 && b != 0)
                str = c + "." + b;
            else
                str = String.valueOf(c);

            return str + "k";
        } else {
            return String.valueOf(count);
        }

    }

    /**
     * requestData
     */
    private void sendRequestData() {
//        if (TDevice.hasInternet() && AccountHelper.isLogin())
//            OSChinaApi.getUserInfo(requestUserInfoHandler);
    }

    /**
     * init solar view
     */
    private void initSolar() {
//        View root = this.mRoot;
//        if (root != null) {
//            root.post(new Runnable() {
//                @Override
//                public void run() {
//
//                    if (mRlShowInfo == null) return;
//                    int width = mRlShowInfo.getWidth();
//                    float rlShowInfoX = mRlShowInfo.getX();
//
//                    int height = mFlUserInfoIconContainer.getHeight();
//                    float y1 = mFlUserInfoIconContainer.getY();
//
//                    float x = mPortrait.getX();
//                    float y = mPortrait.getY();
//                    int portraitWidth = mPortrait.getWidth();
//
//                    mPx = x + +rlShowInfoX + (width >> 1);
//                    mPy = y1 + y + (height - y) / 2;
//                    mMaxRadius = (int) (mSolarSystem.getHeight() - mPy + 250);
//                    mR = (portraitWidth >> 1);
//
//                    updateSolar(mPx, mPy);
//
//                }
//            });
//        }
    }

    /**
     * update solar
     *
     * @param px float
     * @param py float
     */
    private void updateSolar(float px, float py) {

//        SolarSystemView solarSystemView = mSolarSystem;
//        Random random = new Random(System.currentTimeMillis());
//        int maxRadius = mMaxRadius;
//        int r = mR;
//        solarSystemView.clear();
//        for (int i = 40, radius = r + i; radius <= maxRadius; i = (int) (i * 1.4), radius += i) {
//            SolarSystemView.Planet planet = new SolarSystemView.Planet();
//            planet.setClockwise(random.nextInt(10) % 2 == 0);
//            planet.setAngleRate((random.nextInt(35) + 1) / 1000.f);
//            planet.setRadius(radius);
//            solarSystemView.addPlanets(planet);
//
//        }
//        solarSystemView.setPivotPoint(px, py);
    }

    /**
     *
     */
    private void hideView() {
//        mPortrait.setImageResource(R.mipmap.widget_default_face);
////        mTvName.setText(R.string.user_hint_login);
//        mTvName.setTextSize(16.0f);
//        mIvGander.setVisibility(View.INVISIBLE);
//        mTextActiveScore.setVisibility(View.INVISIBLE);
//        mTextAvailScore.setVisibility(View.INVISIBLE);
////        mLayAboutCount.setVisibility(View.GONE);
////        mAboutLine.setVisibility(View.GONE);
    }

    /**
     * measureTitleBarHeight
     */
    private void measureTitleBarHeight() {
//        if (mRlShowInfo != null) {
////            mRlShowInfo.setPadding(mRlShowInfo.getLeft(),
////                    UiUtil.getStatusBarHeight(getContext()),
////                    mRlShowInfo.getRight(), mRlShowInfo.getBottom());
//        }
    }



//    , R.id.ly_tweet, R.id.ly_favorite,
//    R.id.ly_following, R.id.ly_follower, R.id.rl_message,
//    R.id.rl_blog, R.id.rl_data,
//    R.id.rl_info_question, R.id.rl_info_activities, R.id.rl_team

    @SuppressWarnings("deprecation")
    @OnClick({
//            R.id.iv_logo_setting, R.id.iv_logo_zxing, R.id.iv_portrait,
//            R.id.user_view_solar_system,
            R.id.fragment_main_user_home_portrait,
            R.id.fragment_main_user_home_btn_setting
    })
    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.fragment_main_user_home_portrait:
                //查看头像 or 更换头像
                showAvatarOperation();
                break;
            case R.id.fragment_main_user_home_btn_setting:
                SimplexToast.show(getContext(), "Setting...");
                UIHelper.showSetting(getActivity());
                break;

        }

//        if (id == R.id.iv_logo_setting) {
//            UIHelper.showSetting(getActivity());
//        } else {
//
//            if (!AccountHelper.isLogin()) {
//                LoginActivity.show(getActivity());
//                return;
//            }
//
//            switch (id) {
//                case R.id.iv_logo_zxing:
//                    MyQRCodeDialog dialog = new MyQRCodeDialog(getActivity());
//                    dialog.show();
//                    break;
//                case R.id.iv_portrait:
//                    //查看头像 or 更换头像
//                    showAvatarOperation();
//                    break;
//                case R.id.user_view_solar_system:
//                    //显示我的资料
//                    if (mUserInfo != null) {
//                        UserDataActivity.show(mContext, mUserInfo);
//                    }
//                    break;
//                case R.id.ly_tweet:
//                    UserTweetActivity.show(getActivity(), AccountHelper.getUserId());
//                    break;
//                case R.id.ly_favorite:
//                    UserCollectionActivity.show(getActivity());
//                    break;
//                case R.id.ly_following:
//                    UserFollowsActivity.show(getActivity(), AccountHelper.getUserId());
//                    break;
//                case R.id.ly_follower:
//                    UserFansActivity.show(getActivity(), AccountHelper.getUserId());
//                    break;
//                case R.id.rl_message:
//                    UserMessageActivity.show(getActivity());
//                    break;
//                case R.id.rl_blog:
//                    UIHelper.showUserBlog(getActivity(), AccountHelper.getUserId());
//                    break;
//                case R.id.rl_info_question:
//                    UIHelper.showUserQuestion(getActivity(), AccountHelper.getUserId());
//                    break;
//                case R.id.rl_info_activities:
//                    UserEventActivity.show(mContext, AccountHelper.getUserId(), "");
//                    break;
//                case R.id.rl_team:
//                    UIHelper.showTeamMainActivity(getActivity());
//                    break;
//                case R.id.rl_data:
//                    MyDataActivity.show(mContext, mUserInfo);
//                    break;
//                default:
//                    break;
//            }
//        }
    }

    /**
     * 更换头像 or 查看头像
     */
    private void showAvatarOperation() {
//        if (!AccountHelper.isLogin()) {
            LoginActivity.show(getActivity());
//        } else {
//            DialogHelper.getSelectDialog(getActivity(),
//                    getString(R.string.action_select),
//                    getResources().getStringArray(R.array.avatar_option), "取消",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            switch (i) {
//                                case 0:
//                                    SelectImageActivity.show(getContext(), new SelectOptions.Builder()
//                                            .setSelectCount(1)
//                                            .setHasCam(true)
//                                            .setCrop(700, 700)
//                                            .setCallback(new SelectOptions.Callback() {
//                                                @Override
//                                                public void doSelected(String[] images) {
//                                                    String path = images[0];
//                                                    uploadNewPhoto(new File(path));
//                                                }
//                                            }).build());
//                                    break;
//
//                                case 1:
////                                    if (mUserInfo == null
////                                            || TextUtils.isEmpty(mUserInfo.getPortrait())) return;
////                                    UIHelper.showUserAvatar(getActivity(), mUserInfo.getPortrait());
//                                    break;
//                            }
//                        }
//                    }).show();
//        }
    }

    /**
     * take photo
     */
    private void startTakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent,
//                ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
    }

    public ProgressDialog showWaitDialog(int messageId) {
        String message = getResources().getString(messageId);
        if (mDialog == null) {
//            mDialog = DialogHelper.getProgressDialog(getActivity(), message);
        }

        mDialog.setMessage(message);
        mDialog.show();

        return mDialog;
    }

    /**
     * update the new picture
     */
    private void uploadNewPhoto(File file) {
        // 获取头像缩略图
        if (file == null || !file.exists() || file.length() == 0) {
//            AppContext.showToast(getString(R.string.title_icon_null));
        } else {
            mIsUploadIcon = true;
            this.mCacheFile = file;
//            OSChinaApi.updateUserIcon(file, requestUserInfoHandler);
        }

    }

//    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        try {
            startTakePhoto();
        } catch (Exception e) {
//            Toast.makeText(this.getContext(), R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
        }
    }

//    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        Toast.makeText(this.getContext(), R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onNoticeArrived(NoticeBean bean) {
//        if (mMesView != null) {
//            int allCount = bean.getReview() + bean.getLetter() + bean.getMention();
//            mMesView.setVisibility(allCount > 0 ? View.VISIBLE : View.GONE);
//            mMesView.setText(String.valueOf(allCount));
//        }
//        if (mFansView != null) {
//            int fans = bean.getFans();
//            mFansView.setVisibility(fans > 0 ? View.VISIBLE : View.GONE);
//            mFansView.setText(String.valueOf(fans));
//        }
    }


    @Override
    public void onTabReselect() {
        sendRequestData();
    }

}
