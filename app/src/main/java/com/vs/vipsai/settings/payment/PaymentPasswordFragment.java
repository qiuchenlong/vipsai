package com.vs.vipsai.settings.payment;

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
 */

public class PaymentPasswordFragment extends BaseFragment {

    @BindView(R.id.rl_payment_modify_password)
    RelativeLayout modify_payment_password;

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
        return R.layout.fragment_payment_password;
    }


    @OnClick({R.id.rl_payment_modify_password, R.id.rl_payment_forgot_password, R.id.rl_payment_open_fingerprint})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_payment_modify_password:
                UIHelper.showPaymentModifyPassword(getContext());
                break;
            case R.id.rl_payment_forgot_password:
                UIHelper.showPaymentForgotPassword(getContext());
                break;
            case R.id.rl_payment_open_fingerprint:
                UIHelper.showPaymentOpenFingerprint(getContext());
                break;
            default:
                break;
        }
    }
}
