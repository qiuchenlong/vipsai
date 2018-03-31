package com.vs.vipsai.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vs.vipsai.R;
import com.vs.vipsai.base.activities.BaseActivity;
import com.vs.vipsai.media.Util;
import com.vs.vipsai.publish.activitys.PubTemplateActivity;
import com.vs.vipsai.util.SimplexToast;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Author: cynid
 * Created on 3/16/18 4:15 PM
 * Description:
 *
 * 发布界面
 */

public class PubActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.btn_pub)
    ImageView mBtnPub;

    @BindViews({R.id.activity_pub_layout_ad, R.id.activity_pub_layout_private, R.id.activity_pub_layout_sample})
    LinearLayout[] mLays;

    public static void show(Context context) {
        context.startActivity(new Intent(context, PubActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_pub;
    }

    @Override
    protected void initWindow() {
        super.initWindow();
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mBtnPub.animate()
                .rotation(135.0f)
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
        show(0);
        show(1);
        show(2);
    }

    @OnClick({R.id.rl_main, R.id.activity_pub_layout_sample, R.id.activity_pub_layout_private, R.id.activity_pub_layout_ad})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_main:
                dismiss();
                break;
            case R.id.activity_pub_layout_sample:
//                if(!AccountHelper.isLogin()){
//                    LoginActivity.show(this);
//                    finish();
//                    return;
//                }
//                WriteActivity.show(this);
//                dismiss();
//                SimplexToast.show(PubActivity.this, "sample");
                PubTemplateActivity.open(v.getContext(), null);

                break;
            case R.id.activity_pub_layout_private:
//                if(!AccountHelper.isLogin()){
//                    LoginActivity.show(this);
//                    finish();
//                    return;
//                }
//                TweetPublishActivity.show(this, mBtnPub);
//                finish();
//                dismiss();
                SimplexToast.show(PubActivity.this, "private");
                break;
            case R.id.activity_pub_layout_ad:
//                if(!AccountHelper.isLogin()){
//                    LoginActivity.show(this);
//                    finish();
//                    return;
//                }
//                WriteActivity.show(this);
//                dismiss();
                SimplexToast.show(PubActivity.this, "ad");
                break;

        }
    }

    private void dismiss(){
        close();
        close(0);
        close(1);
    }

    private void close() {
        mBtnPub.clearAnimation();
        mBtnPub.animate()
                .rotation(0f)
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        finish();
                    }
                })
                .start();
    }

    private void show(int position) {
//        int angle = position == 0 ? 45 : 135;

        int angle = 0;
        int dpYValue = 0;
        if (position == 0) {
            angle = 30;
            dpYValue = 160;
        }
        if (position == 1) {
            angle = 90;
            dpYValue = 80;
        }
        if (position == 2) {
            angle = 150;
            dpYValue = 160;
        }




        float x = (float) Math.cos(angle * (Math.PI / 180)) * Util.dipTopx(this, 130); //80
        float y = (float) -Math.sin(angle * (Math.PI / 180)) * Util.dipTopx(this, dpYValue);
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(mLays[position], "translationX", 0, x);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(mLays[position], "translationY", 0, y);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(180);
        animatorSet.play(objectAnimatorX).with(objectAnimatorY);
        animatorSet.start();
    }

    private void close(int position) {
//        int angle = position == 0 ? 45 : 135;

        int angle = 0;
        int dpYValue = 0;
        if (position == 0) {
            angle = 30;
            dpYValue = 160;
        }
        if (position == 1) {
            angle = 90;
            dpYValue = 80;
        }
        if (position == 2) {
            angle = 150;
            dpYValue = 160;
        }

        float x = (float) Math.cos(angle * (Math.PI / 180)) * Util.dipTopx(this, 130);
        float y = (float) -Math.sin(angle * (Math.PI / 180)) * Util.dipTopx(this, dpYValue);
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(mLays[position], "translationX", x, 0);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(mLays[position], "translationY", y, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(180);
        animatorSet.play(objectAnimatorX).with(objectAnimatorY);
        animatorSet.start();
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

}
