package com.vs.vipsai.widget;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

    public GlidImageView(Context context) {
        super(context);
        mGlide = Glide.with(getContext());
    }

    public GlidImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mGlide = Glide.with(getContext());
    }

    public void setImageUrl(String url) {
        if(TextUtils.isEmpty(url)) {
            setVisibility(View.INVISIBLE);
            return;
        }else {
            setVisibility(View.VISIBLE);
        }

        mGlide.load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
                //和RoundImageView使用不能使用占位符,否则第一次无法显示，使用布局的src提供默认图片
//                .placeholder(placeHolder)
//                .error(placeHolder)
                .crossFade()
                .into(this);
    }

    @BindingAdapter("bindImage")
    public static void bindImageView(GlidImageView view, String url) {
        view.setImageUrl(url);
    }
}
