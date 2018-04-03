package com.vs.vipsai.widget;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.vs.library.widget.RoundImageView;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.util.ImageLoader;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  封装glid的ImageView
 */
public class GlidImageView extends RoundImageView {

    private RequestManager mGlide;

    public GlidImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mGlide = Glide.with(getContext());
    }

    public void setImageUrl(String url, int placeHolder) {
        if(TextUtils.isEmpty(url)) {
            setVisibility(View.INVISIBLE);
            return;
        }else {
            setVisibility(View.VISIBLE);
        }

        ImageLoader.loadImage(mGlide, this, url, placeHolder);
    }

    @BindingAdapter({"bindImage","placeHolder"})
    public static void bindImageView(GlidImageView view, String url, int placeHolder) {
        view.setImageUrl(url, placeHolder);
    }
}
