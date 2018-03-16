package com.vs.vipsai.widget.loading;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.vs.vipsai.R;

/**
 * Author: cynid
 * Created on 3/12/18 10:38 AM
 * Description:
 */

public class LoadingView extends DialogFragment {

    public LoadingView() {
    }

    Animation operatingAnim;

    Dialog mDialog;

    View mouse;

    GraduallyTextView mGraduallyTextView;

    String text;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mDialog == null) {
            mDialog = new Dialog(getActivity(), R.style.loading_dialog);
            mDialog.setContentView(R.layout.view_loading);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.getWindow().setGravity(Gravity.CENTER);

            operatingAnim = new RotateAnimation(360f, 0f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            operatingAnim.setRepeatCount(Animation.INFINITE);
            operatingAnim.setDuration(2000);

            View view = mDialog.getWindow().getDecorView();

            mouse = view.findViewById(R.id.mouse);

            mGraduallyTextView = (GraduallyTextView) view.findViewById(R.id.graduallyTextView);

            if (!TextUtils.isEmpty(text)) {
                mGraduallyTextView.setText(text);
            }

            operatingAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        return mDialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        mouse.setAnimation(operatingAnim);
        mGraduallyTextView.startLoading();
    }

    @Override
    public void onPause() {
        super.onPause();

        operatingAnim.reset();
        mouse.clearAnimation();
        mGraduallyTextView.stopLoading();
    }

    public void setText(String str) {
        text = str;
    }

    @Override
    public void dismiss() {
        if (getDialog() != null && getDialog().isShowing())
            super.dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mDialog = null;
        System.gc();
    }

}
