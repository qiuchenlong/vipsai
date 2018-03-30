package com.vs.vipsai.publish.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vs.vipsai.BR;
import com.vs.vipsai.R;
import com.vs.vipsai.publish.viewmodels.VMPublishPickSubject;
import com.vs.vipsai.publish.viewmodels.VMTabFragment;
import com.vs.vipsai.util.SimplexToast;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  创建样板比赛
 */
public class PubTemplateActivity extends ToolbarActivity {

    public static void open(Context context) {
        Intent intent = new Intent(context, PubTemplateActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setMenuButton(R.string.retrieve_pwd_step_hint);
    }

    @Override
    protected void onMenuBtnClick(View view) {
        SimplexToast.show(this,"next");
    }

//    @Override
//    protected int getContentView() {
//        VmTabFragmentBinding binding = DataBindingUtil.setContentView(this, R.layout.vm_tab_fragment);
//        binding.setVMTabFragment(new VMTabFragment());
//
//        return R.layout.vm_tab_fragment;
//    }

    @Override
    protected void bindContent(ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.vm_tab_fragment, parent, true);
        binding.setVariable(BR.VMTabFragment, new VMPublishPickSubject(getSupportFragmentManager(), binding.getRoot()));
    }
}
