package com.vs.vipsai.publish.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.vs.library.widget.FunctionBar;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.BR;
import com.vs.vipsai.BaseApplication;
import com.vs.vipsai.R;
import com.vs.vipsai.bean.AwardBean;
import com.vs.vipsai.constants.Constants;
import com.vs.vipsai.publish.layoutcontroller.BaseListAdapterController;
import com.vs.vipsai.publish.layoutcontroller.InputBarController;
import com.vs.vipsai.publish.viewmodels.VMInputBar;
import com.vs.vipsai.settings.SettingsFragment;
import com.vs.vipsai.util.JsonUtil;
import com.vs.vipsai.util.TDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  编辑奖项
 */
public class EditAwardActivity extends ToolbarActivity {

    private static final String EXTRA_PRESET = "EXTRA_PRESET";

    private AwardList mList;

    public String awardMethod;

    public static void open(Context context, List<AwardBean> preset) {
        Intent intent = new Intent(context, EditAwardActivity.class);
        if(preset != null) {
            intent.putExtra(EXTRA_PRESET, JsonUtil.toJsonArrayString(AppOperator.getGson(), preset));
        }
        context.startActivity(intent);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setMenuButton(R.string.pickerview_submit);

        ListView listView = (ListView)findViewById(R.id.list_view);
        mList = new AwardList().wrap(listView);

        ViewDataBinding footer = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_edit_award_footer,
                                        null, false);
        footer.setVariable(BR.EditAwardActivity, this);
        listView.addFooterView(footer.getRoot());
    }

    @Override
    protected void initData() {
        super.initData();

        List<AwardBean> data = null;
        String presetStr = getIntent().getStringExtra(EXTRA_PRESET);
        if(!TextUtils.isEmpty(presetStr)) {
            data = JsonUtil.fromJsonArrayString(AppOperator.getGson(), presetStr);
        }

        if(data == null || data.size() <= 0) {
            data = new ArrayList<>();
            AwardBean first = new AwardBean();
            data.add(first);
        }

        mList.setData(data);
    }

    public void addAwardItem(View view) {
        mList.appendData(new AwardBean(), true);
        mList.getAbsListView().setSelection(0);
    }

    private class AwardList extends BaseListAdapterController<AwardBean> {

        @Override
        protected View onGetView(AwardBean data, int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                    R.layout.list_item_award_setting, parent, false);

                Holder holder = new Holder(parent.getContext(), binding);

                convertView = binding.getRoot();
                convertView.setTag(holder);
            }
            Holder holder = (Holder) convertView.getTag();
            holder.bind(data);
            return convertView;
        }

        @Override
        protected AbsListView getAbsListView() {
            ListView listView = (ListView)mRoot;
            listView.setDivider(new ColorDrawable(TDevice.getColor(getResources(), R.color.transparent)));
            listView.setDividerHeight(getResources().getDimensionPixelSize(R.dimen.padding_8));
            return listView;
        }

        @Override
        protected int getLayoutId() {
            return 0;
        }
    }

    private class Holder implements View.OnClickListener {
        ViewDataBinding mBinding;
        FunctionBar mType;

        public Holder(Context context, ViewDataBinding binding){
            //类别
            mType = (FunctionBar) binding.getRoot().findViewById(R.id.inputbar_type);
            mType.setOnClickListener(this);

            mBinding = binding;
        }

        public void bind(AwardBean award) {
            mBinding.setVariable(BR.VMAwardItem, award);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {

            TDevice.hideSoftInputFromWindow((Activity)v.getContext());

            final ArrayList<String> mList = new ArrayList<String>();
            // 单项选择
            mList.add(v.getContext().getString(R.string.cash));
            mList.add(v.getContext().getString(R.string.present));

            SettingsFragment.alertBottomWheelOption(v.getContext(), mList, new SettingsFragment.OnWheelViewClick() {
                @Override
                public void onClick(View view, int postion) {
                    String emailNotifyStr = mList.get(postion);
                    mType.setText(emailNotifyStr);
                }
            });
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_award;
    }
}
