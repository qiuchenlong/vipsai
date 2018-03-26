package com.vs.vipsai.common.adapter;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Author: cynid
 * Created on 3/26/18 5:53 PM
 * Description:
 *
 * {@link android.widget.EditText} 的文字变化监听实现，
 * 进行文字变化监听时可传入当前类，避免每次都实现三个方法
 */

public abstract class TextWatcherAdapter implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

