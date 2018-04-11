package com.vs.vipsai.publish.layoutcontroller;

import android.support.v4.app.AppOpsManagerCompat;
import android.support.v7.content.res.AppCompatResources;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vs.vipsai.AppContext;
import com.vs.vipsai.R;
import com.vs.vipsai.publish.viewmodels.VMInputBar;
import com.vs.vipsai.util.StringUtils;
import com.vs.vipsai.util.TDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  绑定布局R.layout.input_bar
 *  输入框控制器
 */
public class InputBarController extends VMInputBar implements TextWatcher{

    public enum Type {
        TEXT,NAME_CHINA,PASSWORD,EMAIL,PHONE,NUMBER,FLOAT
    }

    private ViewGroup mRoot;
    private Type mType = Type.TEXT;
    private int mMinLength;
    private boolean mRequest;
    private boolean mPwdVisiable;

    private boolean mShowDelBtn = true;

    private List<InputFilter> mInputFilter;

    public InputBarController wrapper(ViewGroup inputLay) {
        mRoot = inputLay;
        init(mRoot);
        return this;
    }

    public InputBarController() {}

    public View attachTo(ViewGroup parent, boolean attach) {

        if(mRoot != null && mRoot.getParent() != null) {
            ((ViewGroup)mRoot.getParent()).removeView(mRoot);
        }

        mRoot = (ViewGroup)LayoutInflater.from(parent.getContext()).inflate(R.layout.input_bar, parent, false);
        init(mRoot);
        if(attach) {
            parent.addView(mRoot);
        }

        return mRoot;
    }

    private void setRightArrow(int resId) {
        if(mRoot != null) {
            ImageView arrow = mRoot.findViewById(R.id.inputbar_rightarrow);
            arrow.setImageResource(resId);
        }
    }

    public InputBarController setImeOptions(int imeOptions) {
        if(mRoot != null) {
            EditText editText = (EditText)mRoot.findViewById(R.id.editor);
            editText.setImeOptions(imeOptions);
        }
        return this;
    }

    public InputBarController setTitle(int titleRes) {
        if(mRoot != null) {
            TextView textView = (TextView)mRoot.findViewById(R.id.textView);
            textView.setText(titleRes);
        }
        return this;
    }

    public InputBarController setBarHeight(int height) {
        if(mRoot != null) {
//            EditText editText = (EditText)mRoot.findViewById(R.id.editor);
            mRoot.setMinimumHeight(height);
//            ViewGroup.LayoutParams lp = editText.getLayoutParams();
//            lp.height = height;
//            editText.setLayoutParams(lp);
        }
        return this;
    }

    public InputBarController setGravity(int gravity) {
        if(mRoot != null) {
            EditText editText = mRoot.findViewById(R.id.editor);
            editText.setGravity(gravity);
        }
        return this;
    }

    public InputBarController setBarPadding(int left, int top, int right, int bottom) {
        if(mRoot != null) {
            mRoot.setPadding(left,top,right,bottom);
        }

        return this;
    }

    public InputBarController enableMultiLine(boolean value) {
        if(mRoot != null) {
            EditText editText = (EditText)mRoot.findViewById(R.id.editor);
            editText.setSingleLine(!value);
        }
        return this;
    }

    public InputBarController setText(CharSequence text) {
        if(mRoot != null) {
            EditText editText = (EditText)mRoot.findViewById(R.id.editor);
            editText.setText(text);
        }
        return this;
    }

    public InputBarController setBarBackground(int bgRes) {
        if(mRoot != null) {
            int[] padding = new int[]{mRoot.getPaddingLeft(), mRoot.getPaddingTop(), mRoot.getPaddingRight(), mRoot.getPaddingBottom()};
            mRoot.setBackgroundResource(bgRes);
            mRoot.setPadding(padding[0],padding[1],padding[2],padding[3]);
        }
        return this;
    }

    public InputBarController addTextWatcher(TextWatcher watcher) {
        if(mRoot != null) {
            EditText edit = (EditText)mRoot.findViewById(R.id.editor);
            edit.addTextChangedListener(watcher);
        }
        return this;
    }

    public InputBarController setMaxLength(int length) {
        if(mRoot != null && length > 0) {
            EditText edit = (EditText)mRoot.findViewById(R.id.editor);
            if(mInputFilter == null) {
                mInputFilter = new ArrayList<>();
            }
            mInputFilter.add(new InputFilter.LengthFilter(length));
            edit.setFilters(mInputFilter.toArray(new InputFilter[0]));
        }
        return this;
    }

    /**限制小数位数
     * @param length
     * @return
     */
    public InputBarController setNumericPrecision(int length) {
        if(mRoot != null && length > 0 && Type.FLOAT == mType) {

            EditText edit = (EditText)mRoot.findViewById(R.id.editor);
            if(mInputFilter == null) {
                mInputFilter = new ArrayList<>();
            }else for (InputFilter filter : mInputFilter) {
                if(filter instanceof PrecisionLimit) {
                    return this;
                }
            }
            mInputFilter.add(new PrecisionLimit(length));
            edit.setFilters(mInputFilter.toArray(new InputFilter[0]));
        }
        return this;
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

    public InputBarController setMinLength(int length) {
        mMinLength = length;
        return this;
    }

    public InputBarController setHint(int hintRes) {
        if(mRoot != null) {
            EditText editText = (EditText)mRoot.findViewById(R.id.editor);
            editText.setHint(hintRes);
        }
        return this;
    }

    public InputBarController setHint(CharSequence hint) {
        if(mRoot != null) {
            EditText editText = (EditText)mRoot.findViewById(R.id.editor);
            editText.setHint(hint);
        }
        return this;
    }

    public InputBarController setTextSize(int sizeRes) {
        if(mRoot != null) {
            EditText editText = (EditText)mRoot.findViewById(R.id.editor);
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRoot.getContext().getResources().getDimensionPixelSize(sizeRes));
        }
        return this;
    }

    /**
     * 内容类型
     * @param type
     */
    public InputBarController setType(Type type) {
        mType = type;
        if(Type.PASSWORD == mType) {
            setPwdVisiable(false);
        }else if(Type.EMAIL == mType) {
            setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        }else if(Type.PHONE == mType){
            setInputType(InputType.TYPE_CLASS_PHONE);
        }else if(Type.NUMBER == mType) {
            setInputType(InputType.TYPE_CLASS_NUMBER);
        }else if(Type.FLOAT == mType) {
            setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_VARIATION_NORMAL);
        }
        return this;
    }

    private InputBarController setInputType(int type) {
        if(mRoot != null) {
            EditText editText = (EditText)mRoot.findViewById(R.id.editor);
            editText.setInputType(type);
        }

        return this;
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

    public InputBarController requestFocus() {
        if(mRoot != null) {
            mRoot.findViewById(R.id.editor).requestFocus();
        }
        return this;
    }

    /**必填*/
    public InputBarController request() {
        mRequest = true;
        return this;
    }

    public void setCloseKeyboardOnLostFocus(boolean value) {
        if(mRoot != null) {
            EditText edit = (EditText)mRoot.findViewById(R.id.editor);
            if (value) {
                edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                     @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                         if(!hasFocus && v instanceof EditText) {
                            TDevice.closeKeyboard((EditText)v);
                        }
                    }
                });
            }else {
                edit.setOnFocusChangeListener(null);
            }
        }
    }

    private void init(View root) {
//        EditText edit = (EditText)mRoot.findViewById(R.id.editor);
//        edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(!hasFocus && v instanceof EditText) {
//                    TDevice.closeKeyboard((EditText)v);
//                }
//            }
//        });

        addTextWatcher(this);
//        setGravity(Gravity.CENTER);
        root.findViewById(R.id.clear_btn).setOnClickListener(mClearClick);

        setRightArrow(R.mipmap.ic_arrow_right);

        ImageView clear = mRoot.findViewById(R.id.clear_btn);
        clear.setImageResource(R.mipmap.ic_unsubscribe);
    }

    private View.OnClickListener mClearClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText editText = (EditText)mRoot.findViewById(R.id.editor);
            if(Type.PASSWORD == mType) {
                setPwdVisiable(!mPwdVisiable);
            }else {
                editText.getText().clear();
            }
        }
    };

    public String getText() {
        if(mRoot != null) {
            EditText editText = (EditText)mRoot.findViewById(R.id.editor);
            return editText.getText().toString();
        }

        return null;
    }

    public InputBarController enableClearBtn(boolean value) {
        mShowDelBtn = value;
        return this;
    }

    public InputBarController setEditable(boolean editable) {
        if(mRoot != null) {
            EditText editText = (EditText)mRoot.findViewById(R.id.editor);
            editText.setEnabled(editable);
            mShowDelBtn = editable;
            if(editable) {
//                editText.setFocusable(true);
//                editText.setFocusableInTouchMode(true);
                editText.setClickable(true);
                editText.setLongClickable(true);
            }else {
//                editText.setFocusable(false);
//                editText.setFocusableInTouchMode(false);
                editText.setClickable(false);
                editText.setLongClickable(false);
            }
        }

        return this;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mRoot.findViewById(R.id.clear_btn).setVisibility(s.length() > 0 && mShowDelBtn ? View.VISIBLE : View.GONE);
//        if(Type.FLOAT == mType && s.length() > 0) { //限制两位小数
//            EditText editText = (EditText)mRoot.findViewById(R.id.editor);
//            int selectionStart = editText.getSelectionStart();
//            int selectionEnd = editText.getSelectionEnd();
//
//            Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,2}$");
//            Matcher matcher = pattern.matcher(s.toString());
//
//            if (!matcher.matches()){
//                //删除多余输入的字（不会显示出来）
//                s.delete(selectionStart - 1, selectionEnd);
//                editText.setText(s);
//            }
//
//        }
    }

    public boolean checkValidateAndToast(boolean toast) {
        if(mRoot != null) {
            EditText editText = (EditText)mRoot.findViewById(R.id.editor);
            final Editable text = editText.getText();
            TextView textView = (TextView)mRoot.findViewById(R.id.textView);
            if(mRequest && TextUtils.isEmpty(text.toString())) {
                if(toast) {
                    Toast.makeText(mRoot.getContext(), mRoot.getContext().getString(R.string.please_input, textView.getText()), Toast.LENGTH_SHORT).show();
                }
                editText.requestFocus();
                return false;
            }

            if(mMinLength > 0 && text.length() < mMinLength) {
                if(toast) {
                    Toast.makeText(mRoot.getContext(), textView.getText() + mRoot.getContext().getString(R.string.min_length_hint, String.valueOf(mMinLength)), Toast.LENGTH_SHORT).show();
                }
                editText.requestFocus();
                return false;
            }

            if((Type.EMAIL == mType && text.length() > 0 && !StringUtils.isEmail(text.toString().trim())) ||
                    (Type.PASSWORD == mType && !StringUtils.isNumText(text.toString().trim())) ||
                    (Type.NAME_CHINA == mType && !StringUtils.onlyChinese(text.toString().trim())) ||
                    (Type.PHONE == mType && text.length() > 0 && !StringUtils.isPhoneNum(text.toString().trim())) ||
                    (Type.TEXT == mType && !StringUtils.isNumTextSimplified(text.toString().trim(),0,0))) {
                if(toast) {
                    Toast.makeText(mRoot.getContext(), mRoot.getContext().getString(R.string.format_error, textView.getText()), Toast.LENGTH_SHORT).show();
                }
                editText.requestFocus();
                return false;
            }
        }

        return true;
    }
}
