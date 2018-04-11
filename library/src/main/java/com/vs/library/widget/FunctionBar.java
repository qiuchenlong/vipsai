package com.vs.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
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
import android.widget.Toast;

import com.vs.library.R;
import com.vs.library.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能条，可以用于输入条，功能条
 */
@InverseBindingMethods({@InverseBindingMethod(type=FunctionBar.class, attribute = "text")})
public class FunctionBar extends LinearLayout implements TextWatcher{

    public enum Type {
        TEXT,NAME_CHINA,PASSWORD,EMAIL,PHONE,NUMBER,FLOAT
    }

    private TextView mTitle;
    private EditText mEditView;
    private View mClearBtn;
    private boolean mShowDelBtn = true;
    private OnTextChangeListener mBindingListener;

    private int mType = Type.TEXT.ordinal();
    private boolean mPwdVisiable;
    private int mMinLength;
    private boolean mRequest;

    private List<InputFilter> mInputFilter;

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
        mTitle = new TextView(context);
        mTitle.setTextColor(a.getColor(R.styleable.FunctionBar_functionTitleColor, Color.BLACK));
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimension(R.styleable.FunctionBar_functionTitleSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13,
                getResources().getDisplayMetrics())));
        if(a.hasValue(R.styleable.FunctionBar_functionTitle)){
            mTitle.setText(a.getText(R.styleable.FunctionBar_functionTitle));
        }else {
            mTitle.setVisibility(View.GONE);
        }
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        lp.rightMargin = padding_8;
        addView(mTitle, lp);

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

        setInputType(a.getInteger(R.styleable.FunctionBar_functionInputType, Type.TEXT.ordinal()));
        setNumericPrecision(a.getInt(R.styleable.FunctionBar_numericPrecision, 2));
        setEditable(a.getBoolean(R.styleable.FunctionBar_functionEditable, true));

        a.recycle();

        addTextWatcher(this);

    }

    /**
     * 限制最大长度
     * @param length
     */
    public void setMaxLength(int length) {
        if(mInputFilter == null) {
            mInputFilter = new ArrayList<>();
        }
        mInputFilter.add(new InputFilter.LengthFilter(length));
        mEditView.setFilters(mInputFilter.toArray(new InputFilter[0]));
    }

    /**
     * 限制最小长度
     * @param length
     */
    public void setMinLength(int length) {
        mMinLength = length;
    }

    /**限制小数位数
     * @param length
     * @return
     */
    public void setNumericPrecision(int length) {
        if(mInputFilter == null) {
            mInputFilter = new ArrayList<>();
        }else for (InputFilter filter : mInputFilter) {
            if(filter instanceof PrecisionLimit) {
                return;
            }
        }
        mInputFilter.add(new PrecisionLimit(length));
        mEditView.setFilters(mInputFilter.toArray(new InputFilter[0]));
    }

    private class PrecisionLimit implements InputFilter {

        int mLength;

        public PrecisionLimit(int length) {
            mLength = length;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (TextUtils.isEmpty(source)) {
                return null;
            }
            String dValue = dest.toString();
            String[] splitArray = dValue.split("\\.");
            if (splitArray.length > 1) {
                String dotValue = splitArray[1];
                int diff = dotValue.length() + 1 - mLength;
                if (diff > 0) {
                    return source.subSequence(start, end - diff);
                }
            }
            return null;
        }
    }

    /**
     * 必填字段
     */
    public void requestField() {
        mRequest = true;
    }

    /**
     * 检查输入的有效性
     * @param toastString
     * @return
     */
    public boolean checkValidateAndToast(int toastString) {
        final Editable text = mEditView.getText();
        if(mRequest && TextUtils.isEmpty(text.toString())) {
            if(toastString != 0) {
                Toast.makeText(getContext(), toastString, Toast.LENGTH_SHORT).show();
            }
            mEditView.requestFocus();
            return false;
        }

        if(mMinLength > 0 && text.length() < mMinLength) {
            if(toastString != 0) {
                Toast.makeText(getContext(), mTitle.getText() + getContext().getString(R.string.min_length_hint, String.valueOf(mMinLength)), Toast.LENGTH_SHORT).show();
            }
            mEditView.requestFocus();
            return false;
        }

        if((Type.EMAIL.ordinal() == mType && text.length() > 0 && !StringUtil.isEmail(text.toString().trim())) ||
                (Type.PASSWORD.ordinal() == mType && !StringUtil.isNumText(text.toString().trim())) ||
                (Type.NAME_CHINA.ordinal() == mType && !StringUtil.onlyChinese(text.toString().trim())) ||
                (Type.PHONE.ordinal() == mType && text.length() > 0 && !StringUtil.isPhoneNum(text.toString().trim())) ||
                (Type.TEXT.ordinal() == mType && !StringUtil.isNumTextSimplified(text.toString().trim(),0,0))) {
            if(toastString != 0) {
                Toast.makeText(getContext(), getContext().getString(R.string.format_error, mTitle.getText()), Toast.LENGTH_SHORT).show();
            }
            mEditView.requestFocus();
            return false;
        }

        return true;
    }

    public void setEditable(boolean enable) {
        mEditView.setEnabled(enable);
        mShowDelBtn = enable;
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

    public void setInputType(int type) {
        mType = type;
        if(Type.PASSWORD.ordinal() == mType) {
            setPwdVisiable(false);
        }else if(Type.EMAIL.ordinal() == mType) {
            mEditView.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        }else if(Type.PHONE.ordinal() == mType){
            mEditView.setInputType(InputType.TYPE_CLASS_PHONE);
        }else if(Type.NUMBER.ordinal() == mType) {
            mEditView.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else if(Type.FLOAT.ordinal() == mType) {
            mEditView.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_VARIATION_NORMAL);
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

    public void setOnTextChangedListener(OnTextChangeListener listener) {
        mBindingListener = listener;
    }

    @BindingAdapter("textAttrChanged")
    public static void setTextListener(FunctionBar bar, final InverseBindingListener textChanged) {
        if(textChanged == null) {
            bar.setOnTextChangedListener(null);
        }else {
            bar.setOnTextChangedListener(new OnTextChangeListener() {
                @Override
                public void onTextChanged(FunctionBar bar, String text) {
                    textChanged.onChange();
                }
            });
        }
    }

    private void setPwdVisiable(boolean visiable) {
//        if(mRoot != null) {
//            EditText editText = (EditText)mRoot.findViewById(R.id.editor);
//            TextView textView = (TextView)mRoot.findViewById(R.id.clear_btn);
//            if(!visiable) {
//                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                textView.setText(R.string.iconfont_eye_visiable);
//            }else {
//                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                textView.setText(R.string.iconfont_eye_invisiable);
//            }
//            mPwdVisiable = visiable;
//        }

    }

    public interface OnTextChangeListener{
        void onTextChanged(FunctionBar bar, String text);
    }

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
            if(Type.PASSWORD.ordinal() == mType) {
                setPwdVisiable(!mPwdVisiable);
            }else {
                mEditView.getText().clear();
            }

        }
    };

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        mClearBtn.setVisibility(s.length() > 0 && mShowDelBtn ? View.VISIBLE : View.GONE);
        if(mBindingListener != null) {
            mBindingListener.onTextChanged(FunctionBar.this, s.toString());
        }
    }
}
