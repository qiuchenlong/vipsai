package com.vs.vipsai.publish.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vs.vipsai.AppConfig;
import com.vs.vipsai.BR;
import com.vs.vipsai.R;
import com.vs.vipsai.base.fragments.BaseFragment;
import com.vs.vipsai.publish.viewmodels.VMPickCover;
import com.vs.vipsai.publish.viewmodels.VMPublishPickSubject;
import com.vs.vipsai.util.FileUtil;

import java.io.File;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  创建样板比赛-选取封面
 */
public class PubTemplatePickCover extends BaseFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //本地封面裁剪暂存文件夹
        File file = new File(getContext().getFilesDir() + AppConfig.PUBLISH_LOCAL_COVERS_DIR);
        if(!file.exists()) {
            file.mkdir();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {

            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_publish_pick_cover,
                                                container, false);
            binding.setVariable(BR.VMPickCover, new VMPickCover());
            mRoot = binding.getRoot();

            // Do something
            onBindViewBefore(mRoot);
            // Get savedInstanceState
            if (savedInstanceState != null)
                onRestartInstance(savedInstanceState);
            // Init
            initWidget(mRoot);
            initData();
        }
        return mRoot;
    }

    @Override
    public void onDestroy() {
        //清空封面裁剪暂存文件夹
        FileUtil.clearFileWithPath(getContext().getFilesDir() + AppConfig.PUBLISH_LOCAL_COVERS_DIR);
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
