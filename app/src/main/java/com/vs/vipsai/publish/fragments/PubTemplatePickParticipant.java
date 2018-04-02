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
 *  创建样板比赛 - 选择参赛者
 */
public class PubTemplatePickParticipant extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView result = new TextView(container.getContext());
        result.setText("Pick Participant");
        return result;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
