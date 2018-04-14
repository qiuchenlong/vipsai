package com.vs.vipsai.publish.activitys;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.vs.vipsai.R;
import com.vs.vipsai.BR;
import com.vs.vipsai.bean.TournamentBean;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  设置时间, 和TournamentCollector一起使用
 */
public class PubCustomSetTimeActivity extends ToolbarActivity {

    public static final String EXTRA_RESULT = "EXTRA_RESULT";

    public ObservableField<Boolean> immediate = new ObservableField<>(true);

    private TournamentBean mData = new TournamentBean();

    public static void openForResult(Activity context, int requestCode) {
        Intent intent = new Intent(context, PubCustomSetTimeActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setMenuButton(R.string.pickerview_submit);
    }

    @Override
    protected void initData() {
        super.initData();
        mData.startImmediate = true;
    }

    @Override
    protected void bindContent(ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_pub_custom_settime, parent, true);
        binding.setVariable(BR.PubCustomSetTime, this);
        binding.setVariable(BR.tournament, mData);
    }

    //布局绑定，是否立即开始
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        immediate.set(isChecked);
    }

    //布局绑定，选择时间
    public void selectTime(View view) {
        Toast.makeText(this, "asdasd", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onMenuBtnClick(View view) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT, mData);
        setResult(RESULT_OK, intent);
        finish();
    }
}
