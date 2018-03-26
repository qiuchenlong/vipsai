package com.vs.vipsai.widget.wheel;

/**
 * Author: cynid
 * Created on 3/26/18 11:34 AM
 * Description:
 */

public class OnItemSelectedRunnable implements Runnable {

    final WheelView loopView;

    OnItemSelectedRunnable(WheelView loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        loopView.onItemSelectedListener.onItemSelected(loopView.getCurrentItem());
    }

}
