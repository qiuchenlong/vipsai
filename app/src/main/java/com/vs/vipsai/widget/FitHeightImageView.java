package com.vs.vipsai.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class FitHeightImageView extends GlidImageView {

    private float mRatio = 1;

    public FitHeightImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.FIT_XY);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        if(drawable != null) {
            mRatio = drawable.getIntrinsicWidth() * 1f / drawable.getIntrinsicHeight();
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(getDrawable() != null && getMeasuredWidth() > 0 && getMeasuredHeight() > 0) {
            int height = (int)(getMeasuredWidth() / mRatio);
            setMeasuredDimension(getMeasuredWidth(), height);
        }
    }
}
