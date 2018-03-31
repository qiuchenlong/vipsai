package com.vs.vipsai.publish.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vs.vipsai.AppContext;
import com.vs.vipsai.R;
import com.vs.vipsai.BR;
import com.vs.vipsai.bean.AwardBean;

import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.publish.recyclerview.VHDatabinding;
import com.vs.vipsai.publish.viewmodels.VMAwardItem;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  创建样板比赛 - 奖项列表适配器
 */
public class AwardListAdapter extends BaseRecyclerAdapter<AwardBean> {

    public AwardListAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        VHDatabinding vh = null;
        switch (type) {
            default:
                ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.list_item_award_cash, parent, false);
                vh = new VHDatabinding<VMAwardItem>(binding, binding.getRoot());
        }
        return vh;
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, AwardBean item, int position) {
        VHDatabinding vh = (VHDatabinding)holder;
        VMAwardItem model = new VMAwardItem(AppContext.getContext(), item);
        vh.setModel(model);
        vh.getBinding().setVariable(BR.VMAwardItem, model);
        vh.getBinding().executePendingBindings();

    }

    @Override
    public int getItemViewType(int position) {
        int type = super.getItemViewType(position);
        if(type == VIEW_TYPE_NORMAL) {
            AwardBean award = getItem(position);
            type = award.getAwardType();
        }
        return type;
    }
}
