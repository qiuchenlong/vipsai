package com.vs.vipsai.balance.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.View;

import com.vs.vipsai.R;
import com.vs.vipsai.base.fragments.BaseFragment;
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.widget.edit.CashierInputFilter;
import com.vs.vipsai.widget.edit.EditWithDelView;
import com.vs.vipsai.widget.paymentedit.CustomerKeyboard;
import com.vs.vipsai.widget.paymentedit.PasswordEditText;
import com.vs.vipsai.widget.paymentedit.dialog.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: cynid
 * Created on 4/3/18 1:33 PM
 * Description:
 *
 * 取现
 */

public class BalanceWithDrawMoneyFragment extends BaseFragment implements CustomerKeyboard.CustomerKeyboardClickListener, PasswordEditText.PasswordFullListener, View.OnClickListener {


    @BindView(R.id.fragment_balance_withdraw_money_input_edit)
    EditWithDelView inputMoney;


    private CustomerKeyboard mCustomerKeyboard;
    private PasswordEditText mPasswordEt;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_balance_withdraw_money;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        // 只能为金额格式
        InputFilter[] filters = { new CashierInputFilter() };
        inputMoney.setFilters(filters);
    }




    @OnClick(R.id.fragment_balance_withdraw_money_btn_withdraw_money)
    void onClickWithDrawMoney() {
        // 弹出dialog  从底部并且带动画
        CommonDialog.Builder builder = new CommonDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_customer_keyboard).fromBottom().fullWidth().create().show();
        mPasswordEt =  builder.getView(R.id.password_edit_text);
        mCustomerKeyboard = builder.getView(R.id.custom_key_board);
        mCustomerKeyboard.setOnCustomerKeyboardClickListener(this);
        mPasswordEt.setEnabled(false);
        mPasswordEt.setOnPasswordFullListener(this);
    }


    @Override
    public void click(String number) {
        mPasswordEt.addPassword(number);
    }

    @Override
    public void delete() {
        mPasswordEt.deleteLastPassword();
    }

    @Override
    public void passwordFull(String password) {
        SimplexToast.show(getContext(), "密码输入完毕->"+password);
    }
}
