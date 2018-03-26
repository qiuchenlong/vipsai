package com.vs.vipsai.settings.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vs.vipsai.R;
import com.vs.vipsai.Setting;
import com.vs.vipsai.base.BaseFragment;
import com.vs.vipsai.util.TDevice;
import com.vs.vipsai.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: cynid
 * Created on 3/17/18 1:50 PM
 * Description:
 *
 * 关于我们
 */

public class AboutVSFragment extends BaseFragment {

    @BindView(R.id.tv_version_name)
    TextView mTvVersionName;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.tv_grade).setOnClickListener(this);
        view.findViewById(R.id.tv_oscsite).setOnClickListener(this);
        view.findViewById(R.id.tv_knowmore).setOnClickListener(this);
    }

    @Override
    public void initData() {
        mTvVersionName.setText(TDevice.getVersionName());
    }

    @Override
    @OnClick(R.id.img_portrait)
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.tv_grade:
                TDevice.openAppInMarket(getActivity());
                break;
            case R.id.tv_oscsite:
                UIHelper.openInternalBrowser(getActivity(), "https://channelfix.com/");
                break;
            case R.id.tv_knowmore:
                UIHelper.openInternalBrowser(getActivity(),
                        "https://channelfix.com/about");
                break;
            case R.id.img_portrait:
//                Boss.verifyApp(getContext());
                Setting.updateSystemConfigTimeStamp(getContext());
                break;
            default:
                break;
        }
    }

}
