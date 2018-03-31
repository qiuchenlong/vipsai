package com.vs.vipsai.publish.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vs.vipsai.BR;
import com.vs.vipsai.R;
import com.vs.vipsai.base.fragments.BaseFragment;
import com.vs.vipsai.publish.viewmodels.VMPublishPickSubject;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  创建样板比赛-选取封面
 */
public class PubTemplatePickCover extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (mRoot != null) {
//            ViewGroup parent = (ViewGroup) mRoot.getParent();
//            if (parent != null)
//                parent.removeView(mRoot);
//        } else {
//
//            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.vm_tab_fragment,
//                                                container, false);
//            binding.setVariable(BR.VMTabFragment, new VMPublishPickSubject(getChildFragmentManager(), binding.getRoot()));
//            mRoot = binding.getRoot();
//
//            // Do something
//            onBindViewBefore(mRoot);
//            // Get savedInstanceState
//            if (savedInstanceState != null)
//                onRestartInstance(savedInstanceState);
//            // Init
//            initWidget(mRoot);
//            initData();
//        }
//        return mRoot;
        TextView result = new TextView(container.getContext());
        result.setText("Pick cover");
        return result;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}