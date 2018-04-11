package com.vs.library.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vs.library.R;

/**
 * 功能条，可以用于输入条，功能条
 */
public class FunctionBar extends LinearLayout {

    public FunctionBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        createView(context,attrs);
    }

    private void createView(Context context, AttributeSet attrs) {

        setGravity(Gravity.CENTER_VERTICAL);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FunctionBar, 0, 0);
        int padding_8 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics());
        //title
        TextView textView = new TextView(context);
        textView.setTextColor(a.getColor(R.styleable.FunctionBar_functionTitleColor, Color.BLACK));
        textView.setTextSize(a.getDimension(R.styleable.FunctionBar_functionTitleSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14,
                getResources().getDisplayMetrics())));
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        lp.rightMargin = padding_8;
        addView(textView, lp);

        //content
        Context wrapContext = context;
        if(a.hasValue(R.styleable.FunctionBar_functionEditStyle)) {
            wrapContext = new ContextWrapper(context);
            wrapContext.setTheme(a.getResourceId(R.styleable.FunctionBar_functionEditStyle, 0));
        }
        EditText editText = new EditText(wrapContext);
        lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        addView(editText, lp);

        //clearButton
        ImageView imageView = new ImageView(context);
        if(a.hasValue(R.styleable.FunctionBar_clearImageRes)) {
            imageView.setImageResource(a.getResourceId(R.styleable.FunctionBar_clearImageRes, 0));
        }
        if(a.hasValue(R.styleable.FunctionBar_clearImageSize)) {
            float defaultSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics());
            int size = a.getDimensionPixelSize(R.styleable.FunctionBar_clearImageSize, (int)defaultSize);
            lp = new LayoutParams(size, size, 0);
        }else {
            lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        }
        lp.rightMargin = padding_8;
        imageView.setVisibility(View.GONE);
        addView(imageView, lp);

        //rightArrow
        imageView = new ImageView(context);
        if(a.hasValue(R.styleable.FunctionBar_arrowImageRes)) {
            imageView.setImageResource(a.getResourceId(R.styleable.FunctionBar_arrowImageRes, 0));
        }
        if(a.hasValue(R.styleable.FunctionBar_arrowImageSize)) {
            float defaultSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics());
            int size = a.getDimensionPixelSize(R.styleable.FunctionBar_arrowImageSize, (int)defaultSize);
            lp = new LayoutParams(size, size, 0);
        }else {
            lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        }
        lp.rightMargin = padding_8;
        imageView.setVisibility(View.GONE);
        addView(imageView, lp);

        a.recycle();
    }
}
