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
        InputBarController mReward;
        FunctionBar mType;
        AwardBean mData;

//        boolean mSaveDate;

        public Holder(Context context, ViewDataBinding binding){
            int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_horizontal);
            int paddingDiv = context.getResources().getDimensionPixelSize(R.dimen.divider);
            //名次
//            mRanking= new InputBarController()
//                    .wrapper((ViewGroup)binding.getRoot().findViewById(R.id.inputbar_ranking))
//                    .setBarBackground(R.drawable.bg_frame_bottom_white)
//                    .setBarPadding(0,0,padding, paddingDiv)
//                    .enableClearBtn(false);
//
//            mRanking.rightArrowVisible.set(true);
//            mRanking.inputTitle = context.getString(R.string.ranking);
//            mRanking.inputHint.set(R.string.input_ranking_hint);
//            mRanking.gravity.set(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
//            mRanking.rightIndicator.set(R.mipmap.ic_brows_forward);
//            mRanking.inputContent.addOnPropertyChangedCallback(mRankingCallback);

            //类别
            mType = (FunctionBar) binding.getRoot().findViewById(R.id.inputbar_type);
            mType.setOnClickListener(this);
//            mType = new InputBarController()
//                    .wrapper(view)
//                    .setBarBackground(R.drawable.bg_frame_bottom_white)
//                    .setBarPadding(0,0,padding, paddingDiv)
//                    .setEditable(false);
//
//            mType.rightArrowVisible.set(true);
//            mType.gravity.set(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
//            mType.inputTitle = context.getString(R.string.type);
//            mType.inputHint.set(R.string.select_hint);
//            mType.rightIndicator.set(R.mipmap.ic_brows_forward);
//            mType.inputContent.addOnPropertyChangedCallback(mTypeCallback);

            //奖金
            mReward = new InputBarController()
                    .wrapper((ViewGroup)binding.getRoot().findViewById(R.id.inputbar_reward))
                    .setBarBackground(R.drawable.bg_frame_bottom_white)
                    .setBarPadding(0,0,padding, paddingDiv)
                    .setType(InputBarController.Type.FLOAT)
                    .setNumericPrecision(2)
                    .enableClearBtn(false);

            mReward.rightArrowVisible.set(true);
            mReward.gravity.set(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            mReward.inputTitle = context.getString(R.string.reward_option);
            mReward.inputHint.set(R.string.input_hint1);
            mReward.rightIndicator.set(R.mipmap.ic_brows_forward);
            mReward.inputContent.addOnPropertyChangedCallback(mRewardCallback);

            mBinding = binding;

//            mBinding.setVariable(BR.ranking, mRanking);
//            mBinding.setVariable(BR.type, mType);
            mBinding.setVariable(BR.reward, mReward);
        }

        public void bind(AwardBean award) {
            //初始化或绑定同个数据可以回写数据
//            mSaveDate = mData == null || mData == award;

            mData = award;

            //界面绑定新数据
//            mRanking.inputContent.set(award.getRankings());
//            mType.inputContent.set(award.awardType);
            if(award.getReward() > 0) {
                mReward.inputContent.set(String.valueOf(award.getReward()));
            }else {
                mReward.inputContent.set("");
            }

            mBinding.setVariable(BR.VMAwardItem, award);

            mBinding.executePendingBindings();

            //绑定期间可以回写
//            mSaveDate = true;
        }

//        private Observable.OnPropertyChangedCallback mRankingCallback = new Observable.OnPropertyChangedCallback() {
//            @Override
//            public void onPropertyChanged(Observable sender, int propertyId) {
//                if(mData != null) {
//                    mData.setRankings(((ObservableField<String>)sender).get());
//                }
//            }
//        };

//        private Observable.OnPropertyChangedCallback mTypeCallback = new Observable.OnPropertyChangedCallback() {
//            @Override
//            public void onPropertyChanged(Observable sender, int propertyId) {
//                if(mData != null) {
//                    mData.awardType = ((ObservableField<String>)sender).get();
//                }
//            }
//        };

        private Observable.OnPropertyChangedCallback mRewardCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(mData != null) {
                    try {
                        float reward = Float.parseFloat(((ObservableField<String>)sender).get());
                        if(reward > 0) {
                            mData.setReward(reward);
                        }

                    }catch (NumberFormatException e){}
                }
            }
        };

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
