package com.vs.vipsai.publish.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.vs.library.widget.HeaderGridView;
import com.vs.vipsai.BR;
import com.vs.vipsai.R;
import com.vs.vipsai.bean.SubjectBean;
import com.vs.vipsai.bean.TournamentBean;
import com.vs.vipsai.media.SelectImageActivity;
import com.vs.vipsai.media.config.SelectOptions;
import com.vs.vipsai.publish.RequestCode;
import com.vs.vipsai.publish.TournamentCollector;
import com.vs.vipsai.publish.layoutcontroller.BaseListAdapterController;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  创建私人比赛
 */
public class PubCustomActivity extends ToolbarActivity{

    private static final String EXTRA_PRESET_TOURNAMENT = "EXTRA_PRESET_TOURNAMENT";

    private RecommendedSubjectList mRecommendSubjects;

    /**
     * 打开赛事编辑器
     * @param context
     * @param preset 需要编辑的赛事， 如果为null 则创建新赛事
     */
    public static void open(Context context, TournamentBean preset) {
        Intent intent = new Intent(context, PubCustomActivity.class);
        if(preset != null) {
            intent.putExtra(EXTRA_PRESET_TOURNAMENT, preset);
        }
        context.startActivity(intent);
    }

    @Override
    protected void bindContent(ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.activity_pub_custom, parent, true);
        binding.setVariable(BR.PubCustomActivity, this);

        GridView gridView = binding.getRoot().findViewById(R.id.grid_view);
        mRecommendSubjects = new RecommendedSubjectList().wrap(gridView);

        ViewDataBinding headBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_pub_custom_header,
                                    null, false);
        headBinding.setVariable(BR.TournamentCollector, TournamentCollector.get());
        headBinding.setVariable(BR.PubCustomActivity, this);
        mRecommendSubjects.addHeaderView(headBinding.getRoot(), null, false);

        gridView.setAdapter(mRecommendSubjects.getAdapter());

        setMenuButton(R.string.public_txt);
    }

    @Override
    protected boolean initBundle(Bundle bundle) {
        TournamentBean tournament = null;
        if(bundle != null && bundle.containsKey(EXTRA_PRESET_TOURNAMENT)) {
            tournament = bundle.getParcelable(EXTRA_PRESET_TOURNAMENT);
        }

        if(tournament == null) {
            tournament = new TournamentBean();
        }

        //比赛信息收集器
        TournamentCollector.build(tournament);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RequestCode.REQUEST_PICK_TOURNAMENT_TYPE == requestCode && RESULT_OK == resultCode && data != null) {
            SubjectBean subject = (SubjectBean)data.getSerializableExtra(TournamentTypeListActivity.EXTRA_SUBJECT);
            TournamentCollector c = TournamentCollector.get();
            if(subject != null && c != null) {
                c.type.set(subject.title);
            }
        }
    }

    /**设置时间*/
    public void setTime(View view){
        Toast.makeText(this, TournamentCollector.get().title, Toast.LENGTH_SHORT).show();
    }

    public void pickCover(View view) {
        SelectImageActivity.show(view.getContext(), new SelectOptions.Builder()
                .setHasCam(true)
                .setCrop(700, 350)
                .setCallback(new SelectOptions.Callback() {
                    @Override
                    public void doSelected(String[] images) {
                        TournamentCollector c = TournamentCollector.get();
                        if(c != null && images != null && images.length > 0) {
                            c.localCover.set(images[0]);
                        }else {
                            c.localCover.set("");
                        }

                        mRecommendSubjects.notifyDataChanged();
                    }
                }).build());
    }

    public void removeCover(View view) {
        TournamentCollector c = TournamentCollector.get();
        if(c != null) {
            c.localCover.set("");
        }
    }

    public void pickType(View view) {
        TournamentTypeListActivity.openForResult(this, RequestCode.REQUEST_PICK_TOURNAMENT_TYPE);
    }

    public void setAward(View view) {
        EditAwardActivity.open(this, null);
    }

    private class RecommendedSubjectList extends BaseListAdapterController<SubjectBean> {

        public void addHeaderView(View view, Object data, boolean selectable){
            ((HeaderGridView)mRoot).addHeaderView(view, data, selectable);
        }

        @Override
        protected int getLayoutId() {
            return 0;
        }
    }

    @Override
    protected void onDestroy() {
        TournamentCollector.release();
        super.onDestroy();
    }

}