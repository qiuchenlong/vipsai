package com.vs.vipsai.publish.viewmodels;

import android.databinding.ObservableField;
import android.view.Gravity;

import com.vs.vipsai.R;

public class VMInputBar {

    public ObservableField<Integer> inputHint = new ObservableField<>(R.string.please_input);
    public String inputTitle;
    public ObservableField<String> inputContent = new ObservableField<>("");
    public ObservableField<Integer> gravity = new ObservableField<>(Gravity.LEFT  | Gravity.CENTER_VERTICAL);
    public ObservableField<Integer> rightIndicator = new ObservableField<>(R.mipmap.ic_unsubscribe);
    public ObservableField<Boolean> rightArrowVisible = new ObservableField<>(false);
}
