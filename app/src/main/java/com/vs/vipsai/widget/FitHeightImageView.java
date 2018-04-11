package com.vs.vipsai.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;

public class FitHeightImageView extends GlidImageView {

    private float mRatio = 1;

    public FitHeightImageView(Context context) {
        super(context);
    }

    public FitHeightImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        if(drawable != null) {
            float old = mRatio;
            mRatio = drawable.getIntrinsicWidth() * 1f / drawable.getIntrinsicHeight();
            if(old != mRatio) {
                requestLayout();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = (int) (getMeasuredWidth() / mRatio);
        setMeasuredDimension(getMeasuredWidth(), Math.max(height, getMinimumHeight()));
    }
}
