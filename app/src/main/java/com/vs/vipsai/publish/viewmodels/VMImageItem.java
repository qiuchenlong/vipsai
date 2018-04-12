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

    public int defaultImageRes = R.drawable.default_image;
    /**无图片时可视状态*/
    public int nullVisiable = View.INVISIBLE;

    public View.OnClickListener onClick;

    public ObservableField<Boolean> selected = new ObservableField<>(false);

    public String url;

    public ObservableField<String> localPath = new ObservableField<>();

    /**外部指定*/
    private boolean mClickable = true;

    public void setClickable(boolean clickable) {
        mClickable = clickable;
    }

    @BindingAdapter("backgroundSelector")
    public static void bindSelected(View view, ObservableField<Boolean> value) {
        if(value.get()) {
            view.setBackgroundResource(R.drawable.bg_frame_green_shape);
        }else {
            view.setBackgroundResource(R.color.transparent);
        }
    }

    public boolean isClickable() {
        return nullVisiable == View.VISIBLE && onClick != null && mClickable;
    }
}
