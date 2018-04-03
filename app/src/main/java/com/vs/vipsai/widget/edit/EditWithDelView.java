package com.vs.vipsai.widget.edit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.vs.vipsai.R;

/**
 * Author: cynid
 * Created on 4/3/18 4:13 PM
 * Description:
 *
 * 可删除的EditText
 */

@SuppressLint("AppCompatCustomView")
public class EditWithDelView extends EditText {

    private final static String TAG = "EditWithDelView";

    private Drawable imgDisable;

    private Drawable imgAble;

    private Context mContext;



    public EditWithDelView(Context context) {

        super(context);

        mContext = context;

        init();

    }



    public EditWithDelView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        mContext = context;

        init();

    }



    public EditWithDelView(Context context, AttributeSet attrs) {

        super(context, attrs);

        mContext = context;

        init();

    }



    private void init() {

//        imgAble = ResUtils.getDrawable(R.drawable.delete);
        imgAble = getResources().getDrawable(R.mipmap.keyboard_delete_img);

        addTextChangedListener(new TextWatcher() {

            @Override

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override

            public void afterTextChanged(Editable s) {

                setDrawable();

            }

        });



        this.setOnFocusChangeListener(new OnFocusChangeListener() {



            @Override

            public void onFocusChange(View v, boolean hasFocus) {

                // TODO Auto-generated method stub

                if(hasFocus&&length()>=1){

                    setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null, imgAble, null);

                }else{

                    setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null, null, null);

                }

            }

        });

    }



    //根据字符长度加载提示Drawable

    private void setDrawable() {

        if(length() < 1){

            setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null, null, null);

        }else if(isFocused()){

            setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null, imgAble, null);

        }

    }



    //响应触摸点击事件

    @Override

    public boolean onTouchEvent(MotionEvent event) {

        if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {

            int eventX = (int) event.getRawX();

            int eventY = (int) event.getRawY();

            Rect rect = new Rect();

            getGlobalVisibleRect(rect);

            rect.left = rect.right - 50;

            rect.top = rect.bottom - 50;

            if(rect.contains(eventX, eventY)){

                setText("");

                setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null, null, null);

            }

        }

        return super.onTouchEvent(event);

    }



    @Override

    protected void finalize() throws Throwable {

        super.finalize();

    }

}
