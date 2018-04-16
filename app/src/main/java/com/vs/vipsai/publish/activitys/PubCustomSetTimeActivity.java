package com.vs.vipsai.publish.activitys;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.vs.library.OnViewClickListener;
import com.vs.library.utils.DateUtil;
import com.vs.library.utils.SystemUtil;
import com.vs.library.widget.FunctionBar;
import com.vs.vipsai.R;
import com.vs.vipsai.BR;
import com.vs.vipsai.bean.TournamentBean;
import com.vs.vipsai.ui.dialog.DialogFactory;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  设置时间, 和TournamentCollector一起使用
 */
public class PubCustomSetTimeActivity extends ToolbarActivity {

    public static final String EXTRA_RESULT = "EXTRA_RESULT";
    private static final String EXTRA_PRESET = "EXTRA_PRESET";

    public ObservableField<Boolean> immediate = new ObservableField<>(true);

    private TournamentBean mData;
    private ViewDataBinding mBinding;

    public static void openForResult(Activity context, int requestCode, TournamentBean preset) {
        Intent intent = new Intent(context, PubCustomSetTimeActivity.class);
        if(preset != null) {
            intent.putExtra(EXTRA_PRESET, preset);
        }
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected boolean initBundle(Bundle bundle) {
        mData = bundle.getParcelable(EXTRA_PRESET);
        if(mData == null) {
            mData = new TournamentBean();
        }
        immediate.set(mData.startImmediate);
        return super.initBundle(bundle);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setMenuButton(R.string.pickerview_submit);
        ((FunctionBar)mBinding.getRoot().findViewById(R.id.upload_entry)).setText(
                mData.enableUpload ? getString(R.string.yes) : getString(R.string.no));
    }

    @Override
    protected void bindContent(ViewGroup parent) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_pub_custom_settime, parent, true);
        mBinding.setVariable(BR.PubCustomSetTime, this);
        mBinding.setVariable(BR.tournament, mData);
    }

    //布局绑定，是否立即开始
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        immediate.set(isChecked);
    }

    //布局绑定，选择时间
    public void selectTime(View view) {
        DialogFactory.showDateTimePicker(view.getContext(), new OnViewClickListener<Long>() {
            @Override
            public void onViewClick(View view, Long entity) {
                mData.startTime = DateUtil.format(entity, DateUtil.DEFAULT_FORMAT);
                mBinding.setVariable(BR.tournament, mData);
            }
        });
    }

    //布局绑定，输入开放阶段时长
    public void setOpenDuration(View view) {
        DialogFactory.showTimeDurationDialog(view.getContext(), R.string.stage_open, new OnViewClickListener<String>() {
            @Override
            public void onViewClick(View view, String entity) {
                mData.openDuration = entity;
                mBinding.setVariable(BR.tournament, mData);
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBinding.getRoot().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SystemUtil.setSoftInputVisibility(PubCustomSetTimeActivity.this, false);
                    }
                }, 100);
            }
        });
    }

    //布局绑定，输入资格赛阶段时长
    public void setQualifyDuration(View view) {
        DialogFactory.showTimeDurationDialog(view.getContext(), R.string.stage_qualify, new OnViewClickListener<String>() {
            @Override
            public void onViewClick(View view, String entity) {
                mData.qualifyDuration = entity;
                mBinding.setVariable(BR.tournament, mData);
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBinding.getRoot().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SystemUtil.setSoftInputVisibility(PubCustomSetTimeActivity.this, false);
                    }
                }, 100);
            }
        });
    }

    //布局绑定，选择资格赛是否允许上传作品
    public void setUploadEntry(View view) {
        DialogFactory.showYesOrNoDialog(view.getContext(), new OnViewClickListener<Boolean>() {
            @Override
            public void onViewClick(View view,  Boolean entity) {
                mData.enableUpload = entity == null ? false : entity;
                ((FunctionBar)mBinding.getRoot().findViewById(R.id.upload_entry)).setText(
                        mData.enableUpload ? getString(R.string.yes) : getString(R.string.no));
                mBinding.setVariable(BR.tournament, mData);
            }
        });
    }

    //布局绑定，输入上传作品时长
    public void setEntryDuration(View view) {
        DialogFactory.showTimeDurationDialog(view.getContext(), R.string.entry_duration, new OnViewClickListener<String>() {
            @Override
            public void onViewClick(View view,  String entity) {
                mData.entryDuration = entity;
                mBinding.setVariable(BR.tournament, mData);
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBinding.getRoot().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SystemUtil.setSoftInputVisibility(PubCustomSetTimeActivity.this, false);
                    }
                }, 100);
            }
        });
    }

    @Override
    protected void onMenuBtnClick(View view) {

        if(!mData.startImmediate) {
            if(!((FunctionBar)mBinding.getRoot().findViewById(R.id.start_time)).checkValidateAndToast(0) &&
                    !((FunctionBar)mBinding.getRoot().findViewById(R.id.start_entry_num)).checkValidateAndToast(0)) {
                Toast.makeText(view.getContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if(!((FunctionBar)mBinding.getRoot().findViewById(R.id.open_duration)).checkValidateAndToast(R.string.invalid_input)) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT, mData);
        setResult(RESULT_OK, intent);
        finish();
    }
}
