package com.vs.vipsai.widget.wheel;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Author: cynid
 * Created on 3/26/18 11:37 AM
 * Description:
 */

public class LoopViewGestureListener extends GestureDetector.SimpleOnGestureListener {

    final WheelView loopView;

    LoopViewGestureListener(WheelView loopview) {
        loopView = loopview;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        loopView.scrollBy(velocityY);
        return true;
    }

}
