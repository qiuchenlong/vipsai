package com.vs.vipsai.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;

public class FitHeightImageView extends GlidImageView {

    public FitHeightImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.FIT_XY);
    }

    @Override
    public void setLayoutParams(LayoutParams params) {
        params.width = LayoutParams.MATCH_PARENT;
        super.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),  getMeasuredWidth());
    }
}
