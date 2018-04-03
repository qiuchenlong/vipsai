package com.vs.vipsai.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.sina.weibo.sdk.register.mobile.LetterIndexBar;
import com.sina.weibo.sdk.utils.ResourceManager;
import com.vs.vipsai.R;

import java.util.Arrays;

/**
 * 字母索引
 */
public class LetterIndexView extends View {
    public static final int INDEX_COUNT_DEFAULT = 27;
    public static final String SEARCH_ICON_LETTER = "";
//    private int mItemHeight;
    private Paint mPaint = new Paint();
    private String[] mIndexLetter;
    private int count = 27;
    private int mIndex;
    private LetterIndexView.OnIndexChangeListener mListener;
    private boolean mTouching;
    private RectF mRect;
    private int mOrgTextSize;
    private Drawable mSeatchIcon;
    private int mTextColor;

    private float mGap;
    private float mOffset;

    public LetterIndexView(Context context) {
        super(context);
        this.init();
    }

    public LetterIndexView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    public LetterIndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init() {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(-10658467);

        setFontSize(13);

        mOffset = dp2px(getContext(), 3);

//        this.mItemPadding = dp2px(getContext(), 2);
    }

//    public void setIndexMark(int[] mark) {
//        if (mark != null) {
//            this.mNeedIndex = mark;
//            this.invalidate();
//        }
//    }

    public void setTextColor(int color) {
        mPaint.setColor(color);
    }

    public void setFontSize(int dp) {
        this.mOrgTextSize = dp2px(this.getContext(), dp);
        mPaint.setTextSize(mOrgTextSize);

        Paint.FontMetrics fm =  mPaint.getFontMetrics();
        mGap = (fm.bottom - fm.top) - (fm.descent - fm.ascent);

        requestLayout();
    }

    public void setIndexLetter(String[] letter) {
        if (letter != null) {
            this.mIndexLetter = letter;
            this.count = this.mIndexLetter.length;
            this.mIndex = -1;
            requestLayout();
        }
    }

    public int getCount(){return count;}

    public void setIndexChangeListener(LetterIndexView.OnIndexChangeListener listener) {
        this.mListener = listener;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mTouching) {
            int color = this.mPaint.getColor();
            this.mPaint.setColor(-2005436536);
            canvas.drawRoundRect(this.mRect, (float)(this.getMeasuredWidth() / 2), (float)(this.getMeasuredWidth() / 2), this.mPaint);
            this.mPaint.setColor(color);
        }

//        int textSize = this.mOrgTextSize;
//        if (textSize > this.mItemHeight) {
//            textSize = this.mItemHeight;
//        } else {
//            textSize = this.mOrgTextSize;
//        }
//
//        this.mPaint.setTextSize((float)textSize);
        int textWidth;
        int left;
        String title;
//        if (this.mIndexLetter == null) {
//            char letter = 'A';
//            for(int i = 0; i < this.count; ++i) {
//                top = this.mItemHeight * i + this.getPaddingTop() + textSize + this.mItemPadding;
//                if (this.mNeedIndex == null || this.mNeedIndex[i]) {
//                    if (i == this.count - 1) {
//                        title = "#";
//                    } else {
//                        title = String.valueOf(letter);
//                    }
//
//                    textWidth = (int)this.mPaint.measureText(title);
//                    left = (this.getMeasuredWidth() - textWidth) / 2;
//                    canvas.drawText(title, (float)left, (float)top, this.mPaint);
//                }
//                letter++;
//            }
//        } else {
//            for(int i = 0; i < this.count; ++i) {
//                top = this.mItemHeight * i + this.getPaddingTop() + textSize + this.mItemPadding;
//                if (this.mNeedIndex == null || this.mNeedIndex[i]) {
//                    title = this.mIndexLetter[i];
//                    if (title.equals("")) {
//                        textWidth = (int)this.mPaint.measureText("M");
//                        left = (this.getMeasuredWidth() - textWidth) / 2;
//                        this.mSeatchIcon.setBounds(left, top - left, textWidth + left, textWidth + top - left);
//                        this.mSeatchIcon.draw(canvas);
//                    } else {
//                        textWidth = (int)this.mPaint.measureText(title);
//                        left = (this.getMeasuredWidth() - textWidth) / 2;
//                        canvas.drawText(title, (float)left, (float)top, this.mPaint);
//                    }
//                }
//            }
//        }

        float itemHeight = mRect.height() / count;
        float padding = (itemHeight - mGap) / 2f;

        if(mIndexLetter != null) {
            for(int i = 0; i < mIndexLetter.length; i++) {
                float top = itemHeight * i + mRect.top + padding + mOffset;
                title = mIndexLetter[i];
                textWidth = (int)this.mPaint.measureText(title);
                left = (this.getMeasuredWidth() - textWidth) / 2;
                canvas.drawText(title, (float)left, top, this.mPaint);
            }

        }
//        else {
//            char letter = 'A';
//            for(int i = 0; i < count; ++i) {
//                float top = itemHeight * i + mRect.top + padding + mOffset;
//                if (i == this.count - 1) {
//                    title = "#";
//                } else {
//                    title = String.valueOf(letter);
//                }
//
//                textWidth = (int)this.mPaint.measureText(title);
//                left = (this.getMeasuredWidth() - textWidth) / 2;
//                canvas.drawText(title, (float)left, top, this.mPaint);
//                letter++;
//            }
//        }

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        int itemHeight = (int)(fontMetrics.bottom - fontMetrics.top);

        int contentHeight = itemHeight * count;

        if(height < contentHeight + getPaddingBottom() + getPaddingTop()) {
            contentHeight = height - getPaddingTop() - getPaddingBottom();
            mOrgTextSize = contentHeight / count;
            mPaint.setTextSize(mOrgTextSize);

        }

        int width = (int)mOrgTextSize + this.getPaddingLeft() + this.getPaddingRight();
//        this.setMeasuredDimension(width, heightMeasureSpec);
        setMeasuredDimension(width,  contentHeight + getPaddingTop() + getPaddingBottom());

//        this.mRect = new RectF(0.0F, (float)this.getPaddingTop(), (float)this.getMeasuredWidth(), (float)(height - this.getPaddingTop() - this.getPaddingBottom()));
        this.mRect = new RectF(0.0F, (float)this.getPaddingTop(), (float)this.getMeasuredWidth(), (float)(getMeasuredHeight() - getPaddingBottom()));
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch(action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                this.mTouching = true;
                int itemHeight = (int)(mRect.height() / count);
                int y = (int)event.getY();
                int index = (y - this.getPaddingTop()) / itemHeight;
//                if (index != this.mIndex && (this.mNeedIndex == null || this.mNeedIndex[index]) && index < this.count && index >= 0) {
                if (index != this.mIndex) {
                    this.mIndex = index;
                    if (this.mListener != null) {
                        this.mListener.onIndexChange(this.mIndex);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                this.mTouching = false;
        }

        this.invalidate();
        return true;
    }

    public static int dp2px(Context context, int dp) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int px = (int)((double)((float)dp * dm.density) + 0.5D);
        return px;
    }

    public interface OnIndexChangeListener {
        void onIndexChange(int index);
    }
}
