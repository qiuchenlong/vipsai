package com.vs.vipsai.balance.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vs.vipsai.R;
import com.vs.vipsai.balance.adapter.BalanceDetailAdapter;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.base.fragments.BaseFragment;
import com.vs.vipsai.bean.PageBean;
import com.vs.vipsai.bean.SubBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: cynid
 * Created on 4/3/18 1:33 PM
 * Description:
 */

public class BalanceDetailFragment extends BaseFragment {

    @BindView(R.id.fragment_balace_detail_recyclerview)
    RecyclerView mRecyclerView;

    protected BalanceDetailAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_balance_detail;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);


        mAdapter = new BalanceDetailAdapter(getContext());

        PageBean mBean = new PageBean<>();

        List items = new ArrayList();

        for (int i = 0; i < 2; i++) {
            SubBean sb = new SubBean();
            sb.setBody("111");
            items.add(sb);
        }

        mBean.setItems(items);

        mAdapter.addAll(mBean.getItems());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
