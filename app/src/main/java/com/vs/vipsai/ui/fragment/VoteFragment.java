package com.vs.vipsai.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.vs.vipsai.OnTabReselectListener;
import com.vs.vipsai.R;
import com.vs.vipsai.base.fragments.BaseTitleFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: cynid
 * Created on 3/13/18 11:17 AM
 * Description:
 */

public class VoteFragment extends BaseTitleFragment implements View.OnClickListener, OnTabReselectListener {

//    @BindView(R.id.rl_soft)
//    View mRlActive;
//
//    @BindView(R.id.rl_scan)
//    View mScan;
//
//    @BindView(R.id.iv_has_location)
//    ImageView mIvLocated;

    @Override
    protected int getIconRes() {
        return R.mipmap.btn_search_normal;
    }

    @Override
    protected View.OnClickListener getIconClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SearchActivity.show(getContext());
            }
        };
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_explore;
    }

    @Override
    protected int getTitleRes() {
        return R.string.app_name;
    }

    @Override
    public void onResume() {
        super.onResume();
        hasLocation();
    }

//    @OnClick({R.id.rl_git})
    /**
     , R.id.rl_gits,
     R.id.rl_soft, R.id.rl_scan,
     R.id.rl_shake, R.id.layout_events,
     R.id.layout_nearby*/
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
//            case R.id.rl_git:
////                FeatureActivity.show(getActivity());
//                break;
//            case R.id.rl_gits:
////                GistActivity.show(mContext);
//                break;
//            case R.id.rl_soft: //开源软件
////                UIHelper.showSimpleBack(getActivity(),
////                        SimpleBackPage.OPEN_SOURCE_SOFTWARE);
//                break;
//            case R.id.rl_scan: //扫一扫
////                UIHelper.showScanActivity(getActivity());
//                break;
//            case R.id.rl_shake: //摇一摇
//                showShake();
//                break;
//            case R.id.layout_events: //线下活动
////                SubTab tab = new SubTab();
////
////                SubTab.Banner banner = tab.new Banner();
////                banner.setCatalog(3);
////                banner.setHref("https://121.29.18.27/action/apiv2/banner?catalog=3");
////                tab.setBanner(banner);
////
////                tab.setName("线下活动");
////                tab.setFixed(false);
////                tab.setHref("https://www.oschina.net/action/apiv2/sub_list?token=727d77c15b2ca641fff392b779658512");
////                tab.setNeedLogin(false);
////                tab.setSubtype(1);
////                tab.setOrder(74);
////                tab.setToken("727d77c15b2ca641fff392b779658512");
////                tab.setType(5);
////
////                Bundle bundle = new Bundle();
////                bundle.putSerializable("sub_tab", tab);
////
////                UIHelper.showSimpleBack(getContext(), SimpleBackPage.OUTLINE_EVENTS, bundle);
//                break;
//            case R.id.layout_nearby:
////                if (!AccountHelper.isLogin()) {
////                    LoginActivity.show(getContext());
////                    break;
////                }
////                NearbyActivity.show(getContext());
//                break;
            default:
                break;
        }
    }

    @Override
    public void onTabReselect() {
        hasLocation();
    }

    private void showShake() {
//        ShakePresentActivity.show(getActivity());
    }

    private void hasLocation() {
//        boolean hasLocation = Setting.hasLocation(getContext());
//        if (hasLocation) {
//            mIvLocated.setVisibility(View.VISIBLE);
//        } else {
//            mIvLocated.setVisibility(View.GONE);
//        }
    }

}
