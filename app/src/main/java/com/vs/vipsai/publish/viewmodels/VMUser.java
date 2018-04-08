package com.vs.vipsai.publish.viewmodels;

import android.databinding.ObservableField;
import android.view.View;

import com.vs.vipsai.R;
import com.vs.vipsai.bean.User;

import java.lang.ref.SoftReference;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  用户的profile model
 */
public class VMUser extends User {

    /**是否显示选择框*/
    public ObservableField<Boolean> pickMode = new ObservableField<>(false);
    public ObservableField<Boolean> checked = new ObservableField<>(false);

    private OnCheckedListener mListener;

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkbox:
                checked.set(!checked.get());
                if(mListener != null) {
                    mListener.onCheckedChange(VMUser.this, checked.get());
                }
                break;
        }
    }

    public void setOnCheckedChangedLisener(OnCheckedListener listener) {
        mListener = listener;
    }

    /**赋选框改变监听器*/
    public interface OnCheckedListener {
        public void onCheckedChange(VMUser user, boolean selected);
    }
}
