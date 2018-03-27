package com.vs.vipsai.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.vs.mvp.cache.SharedPref;
import com.vs.mvp.router.Router;
import com.vs.vipsai.R;
import com.vs.vipsai.constants.Constants;
import com.vs.vipsai.widget.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: cynid
 * Created on 3/12/18 1:49 PM
 * Description:
 *
 * 引导页面
 */

public class GuidePageActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    private List<View> viewList;

    @Override
    public void initData(Bundle savedInstanceState) {
        SharedPref.getInstance(context).putBoolean(Constants.IS_FIRST, true);
        viewList = new ArrayList<>();
        View view1 = new View(this);
        view1.setBackgroundResource(R.mipmap.guide_4_img);
        View view2 = new View(this);
        view2.setBackgroundResource(R.mipmap.guide_2_img);
        View view3 = new View(this);
        view3.setBackgroundResource(R.mipmap.guide_3_img);
        final View view4 = new View(this);
        view4.setBackgroundResource(R.mipmap.guide_1_img);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewpager.setAdapter(pagerAdapter);

        indicator.setViewPager(viewpager);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //判断是不是要跳转下页一个标记位
            private boolean flag;

            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        //拖的时候才进入下一页
                        flag = false;

                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        flag = true;
                        break;

                    case ViewPager.SCROLL_STATE_IDLE:
                        /**
                         * 判断是不是最后一页，同是是不是拖的状态
                         */
                        if (viewpager.getCurrentItem() == pagerAdapter.getCount() - 1 && !flag) {
                            //LoginActivity.launch(context);
                            toMain();
                        }
                        flag = true;

                        break;
                }
            }

            @Override
            public void onPageSelected(int arg0) {

                if(arg0==3){
                    view4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            toMain();
                        }
                    });
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
        });

    }

    public void toMain() {
        MainActivity.launch(this);
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide_page;
    }

    @Override
    public Object newP() {
        return null;
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(GuidePageActivity.class)
                .launch();
    }

    PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public int getCount() {

            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(viewList.get(position));

        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return "title";
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));

            return viewList.get(position);
        }

    };

}
