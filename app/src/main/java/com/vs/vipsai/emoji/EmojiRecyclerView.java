package com.vs.vipsai.emoji;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.ViewParent;

import com.vs.vipsai.widget.face.FaceRecyclerView;

/**
 * Author: cynid
 * Created on 3/26/18 5:57 PM
 * Description:
 */

public class EmojiRecyclerView extends FaceRecyclerView {

    EmojiRecyclerView(Context context, FaceRecyclerView.OnFaceClickListener listener) {
        super(context, listener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ViewParent parent = this;
        while (!((parent = parent.getParent()) instanceof ViewPager)) ;
        parent.requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
