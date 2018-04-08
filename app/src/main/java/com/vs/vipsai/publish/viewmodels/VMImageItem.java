package com.vs.vipsai.publish.viewmodels;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.view.View;

import com.vs.vipsai.R;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  可选图像列表项,绑定R.layout.list_item_image
 */
public class VMImageItem {

//    public int placeHolder = R.mipmap.ic_default_image;

    public ObservableField<Boolean> selected = new ObservableField<>(false);

    public String url;

    public String localPath;

    public String description = "";

    @BindingAdapter("backgroundSelector")
    public static void bindSelected(View view, ObservableField<Boolean> value) {
        if(value.get()) {
            view.setBackgroundResource(R.drawable.bg_frame_green_shape);
        }else {
            view.setBackgroundResource(R.color.transparent);
        }
    }
}
