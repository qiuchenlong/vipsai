package com.vs.vipsai.publish.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vs.vipsai.R;
import com.vs.vipsai.bean.TournamentBean;
import com.vs.vipsai.publish.TournamentCollector;
import com.vs.vipsai.publish.fragments.PubTemplatePickAward;
import com.vs.vipsai.publish.fragments.PubTemplatePickCover;
import com.vs.vipsai.publish.fragments.UserListFragment;
import com.vs.vipsai.publish.fragments.PubTemplatePickSubject;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  创建样板比赛
 */
public class PubTemplateActivity extends ToolbarActivity {

    private static final String EXTRA_PRESET_TOURNAMENT = "EXTRA_PRESET_TOURNAMENT";

    private static final int STEP_PICK_SUBJECT = 0;
    private static final int STEP_PICK_AWARD = STEP_PICK_SUBJECT + 1;
    private static final int STEP_PICK_PARTICIPANT = STEP_PICK_AWARD + 1;
    private static final int STEP_LAST = STEP_PICK_PARTICIPANT + 1;

    private Fragment[] mFragments = new Fragment[4];
    private int mStep = STEP_PICK_SUBJECT;

    /**
     * 打开赛事编辑器
     * @param context
     * @param preset 需要编辑的赛事， 如果为null 则创建新赛事
     */
    public static void open(Context context, TournamentBean preset) {
        Intent intent = new Intent(context, PubTemplateActivity.class);
        if(preset != null) {
            intent.putExtra(EXTRA_PRESET_TOURNAMENT, preset);
        }
        context.startActivity(intent);
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

    /**
     * 跳下一步
     * @param nextStep
     */
    private synchronized void move2Step(int nextStep) {
        Fragment fragment = mFragments[nextStep];
        switch (nextStep) {
            case STEP_PICK_SUBJECT:

                if (fragment == null) {
                    fragment = new PubTemplatePickSubject();
                    mFragments[nextStep] = fragment;
                }

                setTitle(R.string.actionbar_title_subject);

                break;
            case STEP_PICK_AWARD:

                if (fragment == null) {
                    fragment = new PubTemplatePickAward();
                    mFragments[nextStep] = fragment;
                }

                setTitle(R.string.pick_award_title);

                break;
            case STEP_PICK_PARTICIPANT:

                if (fragment == null) {
                    fragment = new UserListFragment();
                    mFragments[nextStep] = fragment;
                }

                setTitle(R.string.invite_to_tournament);

                break;
            case STEP_LAST:

                if(TournamentCollector.get().getUserCount() <= 0) {
                    Toast.makeText(this, R.string.pick_user_first, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (fragment == null) {
                    fragment = new PubTemplatePickCover();
                    mFragments[nextStep] = fragment;
                }

                setTitle(R.string.reset_pwd_submit);

                break;
            default:throw new IndexOutOfBoundsException();
        }

        mStep = nextStep;

        showToolbarTitle(nextStep == STEP_PICK_SUBJECT ? 0 : R.string.pre_step);
        setMenuButton(nextStep == STEP_LAST ? R.string.image_select_opt_done : R.string.retrieve_pwd_step_hint);

        addFragment(container.getId(), fragment);
    }



    @Override
    protected void onMenuBtnClick(View view) {
        if(mStep == STEP_LAST) {
            submitTournament();
        }else {
            move2Step(Math.min(mStep + 1, STEP_LAST));
        }
    }

    private void submitTournament() {
        //TODO:submit tournament
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(mStep > STEP_PICK_SUBJECT) {
            move2Step(Math.max(mStep - 1, 0));
            return true;
        }
        return super.onSupportNavigateUp();
    }

    @Override
    protected void bindContent(ViewGroup parent) {
        move2Step(mStep);
    }

    @Override
    protected void onDestroy() {
        TournamentCollector.release();
        super.onDestroy();
    }
}
