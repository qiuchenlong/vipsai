package com.vs.vipsai.publish.viewmodels;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.TextView;
import android.widget.ImageView;


import com.vs.vipsai.R;
import com.vs.vipsai.bean.AwardBean;
import com.vs.vipsai.widget.GlidImageView;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  奖项列表项数据模型，绑定R.layout.list_item_award_cash
 */
public abstract class VMAwardItem {

    public ObservableField<Boolean> selected = new ObservableField<>(false);
    private Context mAppContext;

    /**布局绑定， 图片数量*/
    public abstract int getIconSize();

    /**布局绑定， 图片地址*/
    public abstract String getIconUrl(int index);

}
