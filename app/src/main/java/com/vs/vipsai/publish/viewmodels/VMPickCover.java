package com.vs.vipsai.publish.viewmodels;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.vs.vipsai.R;

public class VMPickCover {

    public int placeHolder = R.mipmap.ic_default_image;

    public MovementMethod scrollingMovementMethod = ScrollingMovementMethod.getInstance();

    public ObservableField<String> cover = new ObservableField<>("");

    public VMPickCover() {
        cover.set("http://img5.imgtn.bdimg.com/it/u=2765039492,3915530203&fm=200&gp=0.jpg");
    }

    @BindingAdapter("bindMovementMethod")
    public static void scrollTextview(TextView view, MovementMethod method) {
        view.setMovementMethod(method);
    }
}
