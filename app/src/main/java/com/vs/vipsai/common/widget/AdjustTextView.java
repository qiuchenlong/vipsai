package com.vs.vipsai.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.vs.vipsai.R;

/**
 * Author: cynid
 * Created on 3/13/18 2:57 PM
 * Description:
 *
 * 自动调节字体大小的TextView,
 * 注意：暂时并不支持多行
 */

public class AdjustTextView extends android.support.v7.widget.AppCompatTextView {

    private int mMinTextSize = 1;
    private float mDefaultTextSize;
    private int mContentWidth;

    public AdjustTextView(Context context) {
        this(context, null);
    }

    public AdjustTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdjustTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final Context context = getContext();

        mContentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        mDefaultTextSize = getTextSize();

        // Load attributes
        if (attrs == null) {
            return;
        }

        // Load attributes
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.AdjustTextView, defStyle, 0);

        mMinTextSize = a.getDimensionPixelSize(R.styleable.AdjustTextView_oscMinTextSize, mMinTextSize);
        a.recycle();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        mContentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        mContentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private void resize(CharSequence charSequence) {
        String text = charSequence.toString();
        if (TextUtils.isEmpty(text) || mContentWidth <= 0) return;

        TextPaint paint = new TextPaint(getPaint());
        paint.setTextSize(mDefaultTextSize);

        float ts = mDefaultTextSize;
        for (float mw = paint.measureText(text); ts > mMinTextSize && mw > mContentWidth; ) {
            paint.setTextSize(--ts);
            mw = paint.measureText(text);
        }
        setTextSize(TypedValue.COMPLEX_UNIT_PX, ts);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mContentWidth = w - getPaddingLeft() - getPaddingRight();
        resize(getText());
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        resize(text);
    }

}
