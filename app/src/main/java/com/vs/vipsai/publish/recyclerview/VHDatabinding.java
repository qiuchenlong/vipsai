package com.vs.vipsai.publish.recyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.SoftReference;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  RecyclerView.ViewHolder 的Databinding 基类
 */
public class VHDatabinding extends RecyclerView.ViewHolder{

    private ViewDataBinding mBinding;

    public VHDatabinding(ViewDataBinding binding, View itemView) {
        super(itemView);
        mBinding = binding;
    }

    public ViewDataBinding getBinding() {
        return mBinding;
    }
}
