package com.vs.vipsai.publish.viewmodels;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.TextUtils;
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

    public View.OnClickListener onDelClick;

    public ObservableField<Boolean> selected = new ObservableField<>(false);

    public String url;

    public ObservableField<String> localPath = new ObservableField<>();

    @BindingAdapter("backgroundSelector")
    public static void bindSelected(View view, ObservableField<Boolean> value) {
        if(value.get()) {
            view.setBackgroundResource(R.drawable.bg_frame_green_shape);
        }else {
            view.setBackgroundResource(R.color.transparent);
        }
    }

    /**
     * 布局绑定，删除按钮是否可见
     * @return
     */
    public int delBtnVisiable() {
        return !TextUtils.isEmpty(url) || !TextUtils.isEmpty(localPath.get()) ?
                View.VISIBLE : View.GONE;
    }
}
