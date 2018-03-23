package com.vs.vipsai.ui.fragment.tab;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.reflect.TypeToken;
import com.vs.vipsai.AppContext;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.OnTabReselectListener;
import com.vs.vipsai.R;
import com.vs.vipsai.base.fragments.BaseFragment;
import com.vs.vipsai.bean.News;
import com.vs.vipsai.bean.SubTab;
import com.vs.vipsai.common.utils.StreamUtil;
import com.vs.vipsai.main.SubFragment;
import com.vs.vipsai.notify.activity.NotifyActicity;
import com.vs.vipsai.ui.activity.MainActivity;
import com.vs.vipsai.ui.activity.PubActivity;
import com.vs.vipsai.util.TDevice;
import com.vs.vipsai.widget.FragmentPagerAdapter;
import com.vs.vipsai.widget.TabPickerView;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//import android.support.v4.app.FragmentPagerAdapter;

/**
 * Author: cynid
 * Created on 3/13/18 1:42 PM
 * Description:
 *
 * 投票界面 tabfragment
 */

public class VoteTabFragment extends BaseFragment implements OnTabReselectListener {

    @BindView(R.id.layout_tab)
    TabLayout mLayoutTab;
    @BindView(R.id.view_tab_picker)
    TabPickerView mViewTabPicker;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.recommend_tabfragment_btn_notification)
    ImageView mViewArrowDown;

    private MainActivity activity;
    private Fragment mCurFragment;
    private FragmentPagerAdapter mAdapter;
    private static TabPickerView.TabPickerDataManager mTabPickerDataManager;
    List<SubTab> tabs;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;

//        activity.addOnTurnBackListener(new MainActivity.TurnBackListener() {
//            @Override
//            public boolean onTurnBack() {
//                return mViewTabPicker != null && mViewTabPicker.onTurnBack();
//            }
//        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_competition_tab;
    }

    public static TabPickerView.TabPickerDataManager initTabPickerManager() {
        if (mTabPickerDataManager == null) {
            mTabPickerDataManager = new TabPickerView.TabPickerDataManager() {
                @Override
                public List<SubTab> setupActiveDataSet() {
                    FileReader reader = null;
                    List<SubTab> sts = new ArrayList<>();
                    try {
//                        File file = AppContext.getInstance().getFileStreamPath("sub_tab_active.json");
//                        if (!file.exists()) return null;
//                        reader = new FileReader(file);
////                        return AppOperator.getGson().fromJson(reader,
////                                new TypeToken<ArrayList<SubTab>>() {
////                                }.getType());
//                        Log.d("aa", "" + AppOperator.getGson().fromJson(reader,
//                                new TypeToken<ArrayList<SubTab>>() {
//                                }.getType()));

                        SubTab st = new SubTab();
                        st.setName("资格赛");
                        st.setHref("https://www.oschina.net/action/apiv2/sub_list?token=d6112fa662bc4bf21084670a857fbd20");
                        st.setSubtype(10);
                        st.setToken("d6112fa662bc4bf21084670a857fbd20"); // cache key
                        st.setType(News.TYPE_QUALIFYING); //3

                        sts.add(st);

                        SubTab st1 = new SubTab();
                        st1.setName("淘汰赛");
                        st1.setHref("https://www.oschina.net/action/apiv2/sub_list?token=df985be3c5d5449f8dfb47e06e098ef9");
                        st1.setSubtype(4);
                        st1.setToken("df985be3c5d5449f8dfb47e06e098ef9"); // cache key
                        st1.setType(News.TYPE_OPEN);

                        sts.add(st1);




                        return sts;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } finally {
                        StreamUtil.close(reader);
                    }
                    return null;
                }

                @Override
                public List<SubTab> setupOriginalDataSet() {
                    InputStreamReader reader = null;
                    try {
                        reader = new InputStreamReader(
                                AppContext.getInstance().getAssets().open("sub_tab_original.json")
                                , "UTF-8");
                        return AppOperator.getGson().<ArrayList<SubTab>>fromJson(reader,
                                new TypeToken<ArrayList<SubTab>>() {
                                }.getType());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        StreamUtil.close(reader);
                    }
                    return null;
                }

                @Override
                public void restoreActiveDataSet(List<SubTab> mActiveDataSet) {
                    OutputStreamWriter writer = null;
                    try {
                        writer = new OutputStreamWriter(
                                AppContext.getInstance().openFileOutput(
                                        "sub_tab_active.json", Context.MODE_PRIVATE)
                                , "UTF-8");
                        AppOperator.getGson().toJson(mActiveDataSet, writer);
                        AppContext.set("TabsMask", TDevice.getVersionCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        StreamUtil.close(writer);
                    }
                }
            };
        }
        return mTabPickerDataManager;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        mViewTabPicker.setTabPickerManager(initTabPickerManager());
        mViewTabPicker.setOnTabPickingListener(new TabPickerView.OnTabPickingListener() {

            private boolean isChangeIndex = false;

            @Override
            @SuppressWarnings("all")
            public void onSelected(final int position) {
                final int index = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(position);
                if (position == index) {
//                    mAdapter.commitUpdate();
                    // notifyDataSetChanged为什么会导致TabLayout位置偏移，而且需要延迟设置才能起效？？？
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mLayoutTab.getTabAt(position).select();
                        }
                    }, 50);
                }
            }

            @Override
            public void onRemove(int position, SubTab tab) {
                isChangeIndex = true;
            }

            @Override
            public void onInsert(SubTab tab) {
                isChangeIndex = true;
            }

            @Override
            public void onMove(int op, int np) {
                isChangeIndex = true;
            }

            @Override
            public void onRestore(final List<SubTab> mActiveDataSet) {
                if (!isChangeIndex) return;
                AppOperator.getExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        OutputStreamWriter writer = null;
                        try {
                            writer = new OutputStreamWriter(
                                    AppContext.getInstance().openFileOutput(
                                            "sub_tab_active.json", Context.MODE_PRIVATE)
                                    , "UTF-8");
                            AppOperator.getGson().toJson(mActiveDataSet, writer);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            StreamUtil.close(writer);
                        }

                        /*String json = AppOperator.getGson().toJson(mActiveDataSet);
                        FileOutputStream fos = null;
                        try {
                            fos = AppContext.getInstance().openFileOutput("sub_tab_active.json",
                                    Context.MODE_PRIVATE);
                            fos.write(json.getBytes("UTF-8"));
                            fos.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            StreamUtil.close(fos);
                        }*/
                    }
                });
                isChangeIndex = false;
                tabs.clear();
                tabs.addAll(mActiveDataSet);
                mAdapter.notifyDataSetChanged();
            }
        });

        mViewTabPicker.setOnShowAnimation(new TabPickerView.Action1<ViewPropertyAnimator>() {
            @Override
            public void call(ViewPropertyAnimator animator) {
                mViewArrowDown.setEnabled(false);
//                activity.toggleNavTabView(false);
                mViewArrowDown.animate()
                        .rotation(225)
                        .setDuration(380)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animator) {
                                super.onAnimationEnd(animator);
                                mViewArrowDown.setRotation(45);
                                mViewArrowDown.setEnabled(true);
                            }
                        }).start();

            }
        });

        mViewTabPicker.setOnHideAnimator(new TabPickerView.Action1<ViewPropertyAnimator>() {
            @Override
            public void call(ViewPropertyAnimator animator) {
                mViewArrowDown.setEnabled(false);
//                activity.toggleNavTabView(true);
                mViewArrowDown.animate()
                        .rotation(-180)
                        .setDuration(380)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animator) {
                                super.onAnimationEnd(animator);
                                mViewArrowDown.setRotation(0);
                                mViewArrowDown.setEnabled(true);
                            }
                        });
            }
        });

        tabs = new ArrayList<>();
        tabs.addAll(mViewTabPicker.getTabPickerManager().getActiveDataSet());
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


        mViewPager.setAdapter(mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
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
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mAdapter.commitUpdate();
                }
            }
        });
        mLayoutTab.setupWithViewPager(mViewPager);
        mLayoutTab.setSmoothScrollingEnabled(true);

        // set vertical divider
        LinearLayout mLinearLayout = (LinearLayout) mLayoutTab.getChildAt(0);
        // 在所有子控件的中间显示分割线（还可能只显示顶部、尾部和不显示分割线）
        mLinearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        // 设置分割线的距离本身（LinearLayout）的内间距
        mLinearLayout.setDividerPadding(20);
        // 设置分割线的样式
        mLinearLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_vertical));

    }

//    @Override
//    protected int getContentLayoutId() {
//        return R.layout.fragment_dynamic_tab;
//    }
//
//    @Override
//    protected int getTitleRes() {
////        return R.string.main_tab_name_news;
//        return R.string.app_name;
//    }


    @OnClick(R.id.recommend_tabfragment_btn_join)
    void onClickJoin(){
//        SimplexToast.show(getContext(), "join...");
        PubActivity.show(getContext());
//        TweetPublishActivity.show(getContext(), mRoot.findViewById(R.id.nav_item_tweet_pub));
    }

    @OnClick(R.id.recommend_tabfragment_btn_inbox)
    void onClickInBox(){
//        SimplexToast.show(getContext(), "inbox...");
        NotifyActicity.show(AppContext.getContext(), 0);
    }

    @OnClick(R.id.recommend_tabfragment_btn_notification)
    void onClickNotification() {
//        SimplexToast.show(getContext(), "notification...");
        NotifyActicity.show(AppContext.getContext(), 1);

//        if (mViewArrowDown.getRotation() != 0) {
////            mViewTabPicker.onTurnBack();
//        } else {
////            mViewTabPicker.show(mLayoutTab.getSelectedTabPosition());
//        }
    }

//    @Override
//    protected int getIconRes() {
//        return R.mipmap.btn_search_normal;
//    }
//
//    @Override
//    protected View.OnClickListener getIconClickListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                SearchActivity.show(getContext());
//                SimplexToast.show(getContext(), "onclick...");
//            }
//        };
//    }


    @Override
    public void onTabReselect() {
        if (mCurFragment != null && mCurFragment instanceof OnTabReselectListener) {
            ((OnTabReselectListener) mCurFragment).onTabReselect();
        }
    }


}
