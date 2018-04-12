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
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.vs.library.widget.FunctionBar;
import com.vs.vipsai.AppConfig;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.BR;
import com.vs.vipsai.BaseApplication;
import com.vs.vipsai.R;
import com.vs.vipsai.bean.AwardBean;
import com.vs.vipsai.constants.Constants;
import com.vs.vipsai.media.SelectImageActivity;
import com.vs.vipsai.media.config.SelectOptions;
import com.vs.vipsai.publish.TournamentCollector;
import com.vs.vipsai.publish.layoutcontroller.BaseListAdapterController;
import com.vs.vipsai.publish.layoutcontroller.InputBarController;
import com.vs.vipsai.publish.viewmodels.VMAwardItem;
import com.vs.vipsai.publish.viewmodels.VMInputBar;
import com.vs.vipsai.settings.SettingsFragment;
import com.vs.vipsai.util.JsonUtil;
import com.vs.vipsai.util.TDevice;
import com.vs.vipsai.widget.dialog.AlertWheelDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  编辑奖项,需要配合TournamentCollector使用
 */
public class EditAwardActivity extends ToolbarActivity {

    private AwardList mList;

    private FunctionBar mRules;

    private File mLocalDir;

    public static void openForResult(Activity context, int requestCode) {
        Intent intent = new Intent(context, EditAwardActivity.class);
        context.startActivityForResult(intent,requestCode);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setMenuButton(R.string.pickerview_submit);

        ListView listView = (ListView)findViewById(R.id.list_view);
        mList = new AwardList().wrap(listView);

        ViewDataBinding footer = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_edit_award_footer,
                                        null, false);
        mRules = footer.getRoot().findViewById(R.id.award_method);
        footer.setVariable(BR.EditAwardActivity, this);
        listView.addFooterView(footer.getRoot());

        mLocalDir = new File(getFilesDir() + AppConfig.PUBLISH_AWARD_PIC_DIR);
        if(!mLocalDir.exists()) {
            mLocalDir.mkdir();
        }
    }

    @Override
    protected void initData() {
        super.initData();

        List<AwardBean> data = TournamentCollector.get().getAwards();

        if(data == null || data.size() <= 0) {
            data = new ArrayList<>();
            AwardBean first = new AwardBean();
            first.setImageNullVisiable(0, View.VISIBLE);
            first.setDefaultImage(R.drawable.bg_add_btn);
            first.setOnImageItemClick(mList);
            data.add(first);
        }

        mList.setData(data);

        mRules.setText(TournamentCollector.get().getAwardMethod());

        setResult(RESULT_CANCELED);
    }

    //界面绑定
    public void addAwardItem(View view) {
        AwardBean item = new AwardBean();
        item.setImageNullVisiable(0, View.VISIBLE);
        item.setDefaultImage(R.drawable.bg_add_btn);
        item.setOnImageItemClick(mList);

        mList.appendData(item, true);
        mList.getAbsListView().setSelection(0);
    }

    private class AwardList extends BaseListAdapterController<AwardBean> implements
            VMAwardItem.OnImageItemClick{

        /**
         * 检查数据有效性
         * @return true 有效 or false 无效
         */
        public boolean checkInputValid() {

            int count = getDataSize();
            for(int i = 0; i < count ; i++) {
                if(!getData(i).isDataValid()) {
                    getAbsListView().setSelection(i);
                    return false;
                }
            }

            return true;
        }

        @Override
        protected View onGetView(AwardBean data, int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                    R.layout.list_item_award_setting, parent, false);

                Holder holder = new Holder(binding);

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

        @Override
        public void onImageItemClick(View view, int index, Object data) {
            SelectOptions.Callback callback = new SelectAwardPic(AwardList.this, index, (AwardBean)data);
            SelectImageActivity.show(view.getContext(), new SelectOptions.Builder()
                    .setHasCam(false)
                    .setCrop(360, 360)
                    .setCallback(callback).build());
        }
    }

    private class SelectAwardPic implements SelectOptions.Callback {

        AwardList mList;
        int mImageIndex;
        AwardBean mData;

        public SelectAwardPic(AwardList list, int index, AwardBean data) {
            mList = list;
            mImageIndex = index;
            mData = data;
        }

        @Override
        public void doSelected(String[] images) {
            if(images != null && images.length > 0) {

                File tmp = new File(mLocalDir, String.valueOf(System.currentTimeMillis()) + ".jpg");

                if(new File(images[0]).renameTo(tmp)) {
                    mData.addLocalPath(tmp.getAbsolutePath());
                    mData.setImageNullVisiable(mImageIndex + 1, View.VISIBLE);
                    mData.setImageClickable(mImageIndex, false);
                    mList.notifyDataChanged();
                }

            }
        }
    }

    private class Holder {
        ViewDataBinding mBinding;
        FunctionBar mType, mRandings;

        public Holder(ViewDataBinding binding){
            //类别
            mType = binding.getRoot().findViewById(R.id.inputbar_type);
            mType.setOnClickListener(mPickType);
            //名次
            mRandings = binding.getRoot().findViewById(R.id.inputbar_rankings);
            mRandings.setOnClickListener(mPickRankings);

            mBinding = binding;
        }

        public void bind(AwardBean award) {
            mBinding.setVariable(BR.VMAwardItem, award);
            mBinding.executePendingBindings();
        }

        private View.OnClickListener mPickType = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TDevice.hideSoftInputFromWindow((Activity)v.getContext());

                final ArrayList<String> mList = new ArrayList<String>();
                // 单项选择
                mList.add(v.getContext().getString(R.string.cash));
                mList.add(v.getContext().getString(R.string.present));

                AlertWheelDialog.alertBottomWheelOption(v.getContext(), mList, new SettingsFragment.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        String emailNotifyStr = mList.get(postion);
                        mType.setText(emailNotifyStr);
                    }
                });
            }
        };

        private View.OnClickListener mPickRankings = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TDevice.hideSoftInputFromWindow((Activity)v.getContext());

                AlertWheelDialog.showRankingsWheel(v.getContext(), new AlertWheelDialog.OnWheelPickListener() {
                    @Override
                    public void onWheelPick(String data) {
                        mRandings.setText(data);
                    }
                });
            }
        };
    }

    @Override
    protected void onMenuBtnClick(View view) {
        if(!mList.checkInputValid()) {
            Toast.makeText(this, R.string.input_hint1, Toast.LENGTH_SHORT).show();
            return;
        }

        if(!mRules.checkValidateAndToast(R.string.input_hint1)) {
            mList.getAbsListView().setSelection(mList.getDataSize());
            return;
        }

        CheckBox checkBox = (CheckBox)findViewById(R.id.checkbox);
        if(!checkBox.isChecked()) {
            Toast.makeText(this, R.string.agree_continue, Toast.LENGTH_SHORT).show();
            checkBox.requestFocus();
            return;
        }

        TournamentCollector.get().setAwards(mList.getData());
        TournamentCollector.get().setAwardMethod(mRules.getText());
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_award;
    }
}
