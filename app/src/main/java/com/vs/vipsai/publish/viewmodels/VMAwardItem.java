package com.vs.vipsai.publish.viewmodels;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.ImageView;

import com.vs.vipsai.R;
import com.vs.vipsai.bean.AwardBean;
import com.vs.vipsai.widget.GlidImageView;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  奖项列表项数据模型
 */
public class VMAwardItem extends AwardBean{

    public int iconPlaceHolder = R.mipmap.icon_logo;

    public ObservableField<Boolean> selected = new ObservableField<>(false);
    private Context mAppContext;

    public VMAwardItem(Context context, AwardBean bean) {
        id = bean.getId();
        title = bean.title;
        awardType = bean.getAwardType();
        description = bean.description;
        icons = bean.getIcons();
        mAppContext = context.getApplicationContext();
    }

    //布局绑定
    public int getIconSize() {
        return icons == null ? 0 : icons.length;
    }

    //布局绑定
    public String getAwardTypeStr() {
        String result = "none";
        switch (awardType) {
            case TYPE_CASH:
                result = mAppContext.getString(R.string.cash);
                break;
            case TYPE_PRESENT:
                result = mAppContext.getString(R.string.present);
                break;
        }

        return result;
    }

    //布局绑定
    public String getIconUrl(int index) {
        if(icons != null && index >= 0 && index < icons.length) {
            return icons[index];
        }

        return "";
    }

    @BindingAdapter("selected")
    public static void bindCheckbox(View view, boolean selected) {
        view.setSelected(selected);
    }

    @BindingAdapter({"bindImage","placeHolder"})
    public static void bindImageView(GlidImageView view, String url, int placeHolder) {
        view.setImageUrl(url, placeHolder);
    }
}
