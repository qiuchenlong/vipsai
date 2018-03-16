package com.vs.vipsai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vs.vipsai.R;

/**
 * Author: cynid
 * Created on 3/12/18 2:57 PM
 * Description:
 */

public class DefaultFragment extends Fragment {

    private TextView tv_content;
    private String arg;

    public DefaultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            arg = arguments.getString("key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_default, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_content = (TextView) view.findViewById(R.id.tv_content);

        if(!TextUtils.isEmpty(arg)){
            tv_content.setText(arg);
        }

    }

}
