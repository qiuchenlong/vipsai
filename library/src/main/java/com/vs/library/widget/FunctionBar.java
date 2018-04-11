package com.vs.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.InverseBindingAdapter;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
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
public class FunctionBar extends LinearLayout implements TextWatcher{

    private EditText mEditView;
    private View mClearBtn;
    private boolean mShowDelBtn = true;

    public FunctionBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        createView(context,attrs);
    }

    private void createView(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);
        super.setGravity(Gravity.CENTER_VERTICAL);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FunctionBar, 0, 0);
        int padding_8 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics());
        //title
        TextView textView = new TextView(context);
        textView.setTextColor(a.getColor(R.styleable.FunctionBar_functionTitleColor, Color.BLACK));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimension(R.styleable.FunctionBar_functionTitleSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13,
                getResources().getDisplayMetrics())));
        if(a.hasValue(R.styleable.FunctionBar_functionTitle)){
            textView.setText(a.getText(R.styleable.FunctionBar_functionTitle));
        }else {
            textView.setVisibility(View.GONE);
        }
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        lp.rightMargin = padding_8;
        addView(textView, lp);

        //content
        if(a.hasValue(R.styleable.FunctionBar_functionEditStyle)) {
//            mEditView = new EditText(context, null, R.attr.functionEditStyle);
            Context c = new ContextThemeWrapper(context, a.getResourceId(R.styleable.FunctionBar_functionEditStyle, 0));
            mEditView = new EditText(c);
        }else {
            mEditView = new EditText(context);
        }
        lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        addView(mEditView, lp);

        setContentBackground(android.R.color.transparent);
        setEditable(a.getBoolean(R.styleable.FunctionBar_functionEditable, true));
        if(a.hasValue(R.styleable.FunctionBar_android_hint)) {
            setHint(a.getString(R.styleable.FunctionBar_android_hint));
        }
        if( a.hasValue(R.styleable.FunctionBar_android_gravity)) {
            setGravity(a.getInt(R.styleable.FunctionBar_android_gravity, Gravity.LEFT));
        }

        float defaultSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics());
        //clearButton
        ImageView imageView = new ImageView(context);
        if(a.hasValue(R.styleable.FunctionBar_clearImageRes)) {
            imageView.setImageDrawable(a.getDrawable(R.styleable.FunctionBar_clearImageRes));
        }
        if(a.hasValue(R.styleable.FunctionBar_clearImageSize)) {
            int size = a.getDimensionPixelSize(R.styleable.FunctionBar_clearImageSize, (int)defaultSize);
            lp = new LayoutParams(size, size, 0);
        }else {
            lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        }
        lp.leftMargin = padding_8;
        imageView.setVisibility(View.GONE);
        addView(imageView, lp);
        imageView.setOnClickListener(mClearClick);
        setShowClearBtn(a.getBoolean(R.styleable.FunctionBar_showClearBtn, true));
        mClearBtn = imageView;

        //rightArrow
        imageView = new ImageView(context);
        if(a.hasValue(R.styleable.FunctionBar_arrowImageRes)) {
            imageView.setImageResource(a.getResourceId(R.styleable.FunctionBar_arrowImageRes, 0));
        }
        imageView.setVisibility(a.getBoolean(R.styleable.FunctionBar_showArrow, false) ? View.VISIBLE : View.GONE);

        if(a.hasValue(R.styleable.FunctionBar_arrowImageSize)) {
            int size = a.getDimensionPixelSize(R.styleable.FunctionBar_arrowImageSize, (int)defaultSize);
            lp = new LayoutParams(size, size, 0);
        }else {
            lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        }
        lp.leftMargin = padding_8;
        addView(imageView, lp);

        a.recycle();

        addTextWatcher(this);
    }

    public void setEditable(boolean enable) {
        mEditView.setEnabled(enable);
//        mShowDelBtn = editable;
        if(enable) {
            mEditView.setClickable(true);
            mEditView.setLongClickable(true);
            mEditView.setFocusable(true);
            mEditView.setFocusableInTouchMode(true);
        }else {
            mEditView.setClickable(false);
            mEditView.setLongClickable(false);
            mEditView.setFocusable(false);
            mEditView.setFocusableInTouchMode(false);
        }
    }

    public void setShowClearBtn(boolean show) {
        mShowDelBtn = show;
    }

    @Override
    public void setGravity(int gravity) {
        if(mEditView != null) {
            mEditView.setGravity(gravity);
        }
    }

    public void setText(String text) {
        mEditView.setText(text);
    }

    public String getText() {
        if(mEditView != null) {
            return mEditView.getText().toString();
        }
        return "";
    }

//    @InverseBindingAdapter(attribute = "app:text")
//    public static String getText(FunctionBar functionBar) {
//        return functionBar.getText();
//    }

    public void setContentBackground(int resId) {
        mEditView.setBackgroundResource(resId);
    }

    public void setHint(String hint) {
        mEditView.setHint(hint);
    }

    public void addTextWatcher(TextWatcher watcher) {
        mEditView.addTextChangedListener(watcher);
    }

    private View.OnClickListener mClearClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if(Type.PASSWORD == mType) {
//                setPwdVisiable(!mPwdVisiable);
//            }else {
//                editText.getText().clear();
//            }
            mEditView.getText().clear();
        }
    };

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        mClearBtn.setVisibility(s.length() > 0 && mShowDelBtn ? View.VISIBLE : View.GONE);
    }
}
