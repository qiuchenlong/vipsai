package com.vs.vipsai.settings.help;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.vs.vipsai.R;
import com.vs.vipsai.base.fragments.BaseFragment;
import com.vs.vipsai.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: cynid
 * Created on 3/23/18 3:35 PM
 * Description:
 *
 * 帮助中心
 */

public class HelpCenterFragment extends BaseFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(getActivity());

    }

    @Override
    protected void initWidget(View root) {


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_help_center;
    }


    @OnClick({R.id.fragment_help_center_qa, R.id.fragment_help_center_get_success, R.id.fragment_help_center_what, R.id.fragment_help_center_customer_service})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }
}
