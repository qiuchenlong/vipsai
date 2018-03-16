package com.vs.vipsai.common.drawable;

/**
 * Author: cynid
 * Created on 3/13/18 2:04 PM
 * Description:
 *
 * Interface that drawables supporting animations should implement.
 * Form:https://github.com/qiujuer/Genius-Android
 */

public interface Animatable extends android.graphics.drawable.Animatable {

    /**
     * This is drawable animation frame duration
     */
    int FRAME_DURATION = 16;

    /**
     * This is drawable animation duration
     */
    int ANIMATION_DURATION = 250;

}
