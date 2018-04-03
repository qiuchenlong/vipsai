package com.vs.vipsai.balance.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vs.vipsai.R;
import com.vs.vipsai.bean.SubTab;

/**
 * Author: cynid
 * Created on 3/13/18 2:46 PM
 * Description:
 *
 * 微币 卡片
 */

public class VASHCoinFragment extends AccountBalanceBaseFragment {


    public static VASHCoinFragment newInstance(Context context, SubTab subTab) {
        VASHCoinFragment fragment = new VASHCoinFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", subTab);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vash_coin;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);

    }

    @Override
    public void initData() {
        super.initData();
    }



    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

    }




    //    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        TextView labelMenu = getActivity().findViewById(R.id.activity_account_balacne_label_menu);
//        labelMenu.setText("兑换码");
//    }


}
