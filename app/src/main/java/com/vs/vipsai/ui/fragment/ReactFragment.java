package com.vs.vipsai.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.vs.vipsai.VSApplication;
import com.vs.vipsai.detail.activity.BasePlayerDetailActivity;

/**
 * Author: cynid
 * Created on 4/12/18 4:31 PM
 * Description:
 */

public abstract class ReactFragment extends Fragment {

    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    // This method returns the name of our top-level component to show
    public abstract String getMainComponentName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mReactRootView = new ReactRootView(context);
        mReactInstanceManager = ((VSApplication) getActivity().getApplication()).getReactNativeHost().getReactInstanceManager();
    }


    @Override
    public ReactRootView onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return mReactRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mReactRootView.startReactApplication(
                mReactInstanceManager,
                getMainComponentName(),
                null
        );
    }

}
