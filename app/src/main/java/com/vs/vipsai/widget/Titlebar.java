package com.vs.vipsai.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vs.vipsai.R;

/**
 * Author: cynid
 * Created on 3/12/18 10:34 AM
 * Description:
 */

public class Titlebar  extends RelativeLayout {

    private String mLeftButtonText;
    private int mLeftButtonTextColor;
    private float mLeftButtonSize;
    private Drawable mLeftButtonImage;
    private float mLeftButtonImageWidth;
    private float mLeftButtonImageHeight;
    private String mTitleButtonText;
    private int mTitleButtonTextColor;
    private float mTitleButtonSize;
    private String mRightButtonText;
    private int mRightButtonTextColor;
    private float mRightButtonSize;
    private Drawable mRightButtonImage;
    private float mRightButtonImageWidth;
    private float mRightButtonImageHeight;
    private TextView mLeftTextView;
    private ImageView mLeftButton;
    private TextView mRightTextView;
    private ImageView mRightButton;
    private TextView titleTextView;

    public Titlebar(Context context) {
        this(context, null);
    }

    public Titlebar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Titlebar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        initView(context);
    }

    public void setTitleText(String titleText) {
        titleTextView.setText(titleText);
    }

    public void setRightText(String text) {
        mRightTextView.setText(text);
    }

    public void setRightButtonText(String text) {
        mRightTextView.setText(text);
    }

    public void setRightButtonImage(Drawable rightButtonImage) {
        rightButtonImage.setBounds(0, 0, rightButtonImage.getMinimumWidth(), rightButtonImage.getMinimumHeight());
        mRightTextView.setCompoundDrawables(null, null, rightButtonImage, null);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Titlebar);
        mLeftButtonText = typedArray.getString(R.styleable.Titlebar_leftButtonText);
        mLeftButtonTextColor = typedArray.getColor(R.styleable.Titlebar_leftButtonTextColor, Color.GRAY);
        mLeftButtonSize = typedArray.getDimension(R.styleable.Titlebar_leftButtonTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mLeftButtonImage = typedArray.getDrawable(R.styleable.Titlebar_leftButtonImage);
        mLeftButtonImageWidth = typedArray.getDimension(R.styleable.Titlebar_leftButtonImageWidth, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, LayoutParams.WRAP_CONTENT, getResources().getDisplayMetrics()));
        mLeftButtonImageHeight = typedArray.getDimension(R.styleable.Titlebar_leftButtonImageHeight, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, LayoutParams.WRAP_CONTENT, getResources().getDisplayMetrics()));

        mTitleButtonText = typedArray.getString(R.styleable.Titlebar_titleText);
        mTitleButtonTextColor = typedArray.getColor(R.styleable.Titlebar_titleColor, Color.GRAY);
        mTitleButtonSize = typedArray.getDimension(R.styleable.Titlebar_titleSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));

        mRightButtonText = typedArray.getString(R.styleable.Titlebar_rightButtonText);
        mRightButtonTextColor = typedArray.getColor(R.styleable.Titlebar_rightButtonTextColor, Color.GRAY);
        mRightButtonSize = typedArray.getDimension(R.styleable.Titlebar_rightButtonTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mRightButtonImage = typedArray.getDrawable(R.styleable.Titlebar_rightButtonImage);
        mRightButtonImageWidth = typedArray.getDimension(R.styleable.Titlebar_rightButtonImageWidth, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, LayoutParams.WRAP_CONTENT, getResources().getDisplayMetrics()));
        mRightButtonImageHeight = typedArray.getDimension(R.styleable.Titlebar_rightButtonImageHeight, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, LayoutParams.WRAP_CONTENT, getResources().getDisplayMetrics()));

        typedArray.recycle();
    }


    private void initView(Context context) {
        if (mLeftButtonImage == null & mLeftButtonText != null) {
            // 当用户没有设置左侧按钮图片并设置了左侧的按钮本文本属性时--添加左侧文按钮
            mLeftTextView = new TextView(context);
            mLeftTextView.setText(mLeftButtonText);
            mLeftTextView.setTextColor(mLeftButtonTextColor);
            mLeftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftButtonSize);
            LayoutParams leftParams = new LayoutParams((int) mLeftButtonImageWidth, (int) mLeftButtonImageHeight);
            leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            leftParams.addRule(RelativeLayout.CENTER_VERTICAL);
            addView(mLeftTextView, leftParams);
        } else if (mLeftButtonImage != null) {
            // 添加左侧图片按钮
            LayoutParams leftParams = new LayoutParams((int) mLeftButtonImageWidth, (int) mLeftButtonImageHeight);
            leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            leftParams.addRule(RelativeLayout.CENTER_VERTICAL);
            mLeftButton = new ImageView(context);
            mLeftButton.setImageDrawable(mLeftButtonImage);
            addView(mLeftButton, leftParams);
        }

        if (mTitleButtonText != null) {
            // 添加中间标题
            titleTextView = new TextView(context);
            titleTextView.setText(mTitleButtonText);
            titleTextView.setTextColor(mTitleButtonTextColor);
            titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleButtonSize);
            LayoutParams titleTextViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            titleTextViewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            addView(titleTextView, titleTextViewParams);
        }

        if (mRightButtonText != null) {
            // 当用户没有设置右侧按钮图片并设置了左侧的按钮文本属性时--添加右侧文本按钮
            mRightTextView = new TextView(context);
            mRightTextView.setText(mRightButtonText);
            mRightTextView.setTextColor(mRightButtonTextColor);
            mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightButtonSize);
            mRightTextView.setGravity(Gravity.CENTER_VERTICAL);
            LayoutParams rightParams = new LayoutParams((int) mRightButtonImageWidth, (int) mRightButtonImageHeight);
            rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rightParams.addRule(RelativeLayout.CENTER_VERTICAL);
            addView(mRightTextView, rightParams);
        } else if (mRightButtonImage != null) {
            // 添加右侧图片按钮
            LayoutParams rightParams = new LayoutParams((int) mRightButtonImageWidth, (int) mRightButtonImageHeight);
            rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rightParams.addRule(RelativeLayout.CENTER_VERTICAL);
            mRightButton = new ImageView(context);
            mRightButton.setImageDrawable(mRightButtonImage);
            addView(mRightButton, rightParams);
        }
    }

    /**
     * 在button点击事件接口
     */
    public interface OnButtonClickListener {
        void onLeftClick();
    }

    /**
     * 在button点击事件接口
     */
    public interface OnRightButtonClickListener {
        void onRightClick(View v);
    }

    /**
     * 设置点击事件
     *
     * @param onButtonClickListener
     */
    public void setOnButtonClickListener(final OnButtonClickListener onButtonClickListener) {
        if (onButtonClickListener != null) {
            if (mLeftTextView != null) {
                mLeftTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClickListener.onLeftClick();
                    }
                });
            }
            if (mLeftButton != null) {
                mLeftButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClickListener.onLeftClick();
                    }
                });
            }
        }
    }

    /**
     * 设置点击事件
     *
     * @param onButtonClickListener
     */
    public void setOnRightButtonClickListener(final OnRightButtonClickListener onButtonClickListener) {
        if (onButtonClickListener != null) {
            if (mRightTextView != null) {
                mRightTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClickListener.onRightClick(v);
                    }
                });
            }
            if (mRightButton != null) {
                mRightButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClickListener.onRightClick(v);
                    }
                });
            }
        }
    }

}
