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

import com.vs.library.widget.HeaderGridView;
import com.vs.vipsai.BR;
import com.vs.vipsai.R;
import com.vs.vipsai.bean.SubjectBean;
import com.vs.vipsai.bean.TournamentBean;
import com.vs.vipsai.publish.TournamentCollector;
import com.vs.vipsai.publish.layoutcontroller.BaseListAdapterController;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  创建私人比赛
 */
public class PubCustomActivity extends ToolbarActivity implements View.OnClickListener{

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
        binding.setVariable(BR.ClickImp, this);

        mRecommendSubjects = new RecommendedSubjectList().wrap((GridView)binding.getRoot().findViewById(R.id.grid_view));


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
    public void onClick(View view){

    }

    private class RecommendedSubjectList extends BaseListAdapterController<SubjectBean> {

        public void addHeaderView(View view){
            ((HeaderGridView)mRoot).addHeaderView(view);
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

    public interface Actions {

    }
}
