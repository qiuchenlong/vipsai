package com.vs.vipsai.ui.nav;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.vs.vipsai.R;
import com.vs.vipsai.base.fragments.BaseFragment;
import com.vs.vipsai.common.widget.BorderShape;
import com.vs.vipsai.notice.NoticeBean;
import com.vs.vipsai.notice.NoticeManager;
import com.vs.vipsai.ui.fragment.tab.PastTabFragment;
import com.vs.vipsai.ui.fragment.tab.CompetitionTabFragment;
import com.vs.vipsai.ui.fragment.tab.RecommendTabFragment;
import com.vs.vipsai.ui.fragment.tab.UserInfoFragment;
import com.vs.vipsai.ui.fragment.tab.VoteTabFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Author: cynid
 * Created on 3/12/18 4:23 PM
 * Description:
 *
 * 导航栏控件
 */

public class NavFragment extends BaseFragment implements View.OnClickListener, NoticeManager.NoticeNotify,View.OnLongClickListener {

    @BindView(R.id.nav_item_recommend)
    NavigationButton mNavRecommend;
    @BindView(R.id.nav_item_competition)
    NavigationButton mNavCompetition;
    @BindView(R.id.nav_item_vote)
    NavigationButton mNavVote;
    @BindView(R.id.nav_item_past)
    NavigationButton mNavPast;
    @BindView(R.id.nav_item_me)
    NavigationButton mNavMe;
    @BindView(R.id.nav_item_tweet_pub)
    ImageView mNavPub;
    private Context mContext;
    private int mContainerId;
    private FragmentManager mFragmentManager;
    private NavigationButton mCurrentNavButton;
    private OnNavigationReselectListener mOnNavigationReselectListener;

    public NavFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nav;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        ShapeDrawable lineDrawable = new ShapeDrawable(new BorderShape(new RectF(0, 1, 0, 0)));
        lineDrawable.getPaint().setColor(getResources().getColor(R.color.default_pic));
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{
                new ColorDrawable(getResources().getColor(R.color.color_white)),
                lineDrawable
        });
        root.setBackgroundDrawable(layerDrawable);

        mNavRecommend.init(R.drawable.tab_icon_recommend,
                R.string.main_tab_name_recommend,
                RecommendTabFragment.class);

        mNavCompetition.init(R.drawable.tab_icon_competition,
                R.string.main_tab_name_competition,
                CompetitionTabFragment.class);

        mNavVote.init(R.drawable.tab_icon_vote,
                R.string.main_tab_name_vote,
                VoteTabFragment.class);

        mNavPast.init(R.drawable.tab_icon_past,
                R.string.main_tab_name_past,
                PastTabFragment.class);

        mNavMe.init(R.drawable.tab_icon_me,
                R.string.main_tab_name_my,
                UserInfoFragment.class);

        // 假装设置一个小红点
        mNavMe.showRedDot(2);


    }

    @OnClick({R.id.nav_item_recommend, R.id.nav_item_competition,
            R.id.nav_item_vote, R.id.nav_item_past,
            R.id.nav_item_me})
    @Override
    public void onClick(View v) {
        if (v instanceof NavigationButton) {
            NavigationButton nav = (NavigationButton) v;
            doSelect(nav);
        } else if (v.getId() == R.id.nav_item_tweet_pub) {
//            PubActivity.show(getContext());
            //TweetPublishActivity.show(getContext(), mRoot.findViewById(R.id.nav_item_tweet_pub));
        }
    }

    @OnLongClick({R.id.nav_item_tweet_pub})
    @Override
    public boolean onLongClick(View v) {
//        TweetPublishActivity.show(getContext(), mRoot.findViewById(R.id.nav_item_tweet_pub));
        return false;
    }

    public void setup(Context context, FragmentManager fragmentManager, int contentId, OnNavigationReselectListener listener) {
        mContext = context;
        mFragmentManager = fragmentManager;
        mContainerId = contentId;
        mOnNavigationReselectListener = listener;

        // do clear
        clearOldFragment();
        // do select first
        doSelect(mNavRecommend);
    }

    public void select(int index) {
        if (mNavMe != null)
            doSelect(mNavMe);
    }

    @SuppressWarnings("RestrictedApi")
    private void clearOldFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (transaction == null || fragments == null || fragments.size() == 0)
            return;
        boolean doCommit = false;
        for (Fragment fragment : fragments) {
            if (fragment != this && fragment != null) {
                transaction.remove(fragment);
                doCommit = true;
            }
        }
        if (doCommit)
            transaction.commitNow();
    }

    private void doSelect(NavigationButton newNavButton) {
        // If the new navigation is me info fragment, we intercept it
        /*
        if (newNavButton == mNavMe) {
            if (interceptMessageSkip())
                return;
        }
        */

        NavigationButton oldNavButton = null;
        if (mCurrentNavButton != null) {
            oldNavButton = mCurrentNavButton;
            if (oldNavButton == newNavButton) {
                onReselect(oldNavButton);
                return;
            }
            oldNavButton.setSelected(false);
            oldNavButton.setUnSelectedTitleColor();
        }
        newNavButton.setSelected(true);
        // 设置选中nav的文字颜色
        newNavButton.setSelectedTitleColor();

        doTabChanged(oldNavButton, newNavButton);
        mCurrentNavButton = newNavButton;
    }

    private void doTabChanged(NavigationButton oldNavButton, NavigationButton newNavButton) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (oldNavButton != null) {
            if (oldNavButton.getFragment() != null) {
                ft.detach(oldNavButton.getFragment());
            }
        }
        if (newNavButton != null) {
            if (newNavButton.getFragment() == null) {
                Fragment fragment = Fragment.instantiate(mContext,
                        newNavButton.getClx().getName(), null);
                ft.add(mContainerId, fragment, newNavButton.getTag());
                newNavButton.setFragment(fragment);
            } else {
                ft.attach(newNavButton.getFragment());
            }
        }
        ft.commit();
    }

    /**
     * 拦截底部点击，当点击个人按钮时进行消息跳转
     */
    private boolean interceptMessageSkip() {
        NoticeBean bean = NoticeManager.getNotice();
        if (bean.getAllCount() > 0) {
//            if (bean.getLetter() + bean.getMention() + bean.getReview() > 0)
//                UserMessageActivity.show(getActivity());
//            else
//                UserFansActivity.show(getActivity(), AccountHelper.getUserId());
            return true;
        }
        return false;
    }

    private void onReselect(NavigationButton navigationButton) {
        OnNavigationReselectListener listener = mOnNavigationReselectListener;
        if (listener != null) {
            listener.onReselect(navigationButton);
        }
    }

    @Override
    public void onNoticeArrived(NoticeBean bean) {
        mNavMe.showRedDot(bean.getAllCount());
    }

    public interface OnNavigationReselectListener {
        void onReselect(NavigationButton navigationButton);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NoticeManager.unBindNotify(this);
    }

    @Override
    public void initData() {
        super.initData();
        NoticeManager.bindNotify(this);
    }

}
