package com.vs.vipsai.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.vs.vipsai.R;

/**
 * Author: cynid
 * Created on 3/26/18 4:00 PM
 * Description:
 */

public class ObservableScrollView extends ScrollView {

    private RelativeLayout mImageView;
    private int mImageViewHeight = 0;

    public interface ScrollViewListener {

        void onScrollChanged(ObservableScrollView scrollView, int x, int y,
                             int oldx, int oldy);

    }

    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mImageViewHeight = context.getResources().getDimensionPixelSize(R.dimen.size_default_height); // 250dp
    }


    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }


    public void setZoomImageView(RelativeLayout iv){
        mImageView = iv;
    }


    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if(ev.getY()<500){
//            return true;
//        }
//
////        TLog.log("ev.getY()="+ev.getY());
////        switch (ev.getAction()) {
////            case MotionEvent.ACTION_DOWN:
////                TLog.log("ACTION_DOWN");
////                xDistance = yDistance = 0f;
////                xLast = ev.getX();
////                yLast = ev.getY();
////                break;
////            case MotionEvent.ACTION_MOVE:
////                TLog.log("ACTION_MOVE");
////                final float curX = ev.getX();
////                final float curY = ev.getY();
////
////                xDistance += Math.abs(curX - xLast);
////                yDistance += Math.abs(curY - yLast);
////                xLast = curX;
////                yLast = curY;
////
////
////                TLog.log("yDistance="+yDistance);
////
////                if(yDistance > 0){
////                    return true;
////                }
//////                if (xDistance > yDistance) {
//////                    return false;
//////                }
////        }
//        return super.onInterceptTouchEvent(ev);
//    }



    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        // TODO Auto-generated method stub

//if(deltaY < -100){
//    mImageView.getLayoutParams().height = mImageViewHeight;
//    mImageView.requestLayout();
//
//    return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
//            scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
//}




        // 手指拉动 并且 是下拉 并且 是从顶部开始
        if(isTouchEvent && deltaY < 0 && scrollY == 0){
            mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY;
            mImageView.requestLayout();
        }else{
            if(mImageView.getHeight() > mImageViewHeight){
                mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY;
                mImageView.requestLayout();
            }
        }



        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }


    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {

        View header = (View) mImageView.getParent();
        if(header.getTop()<0&&mImageView.getHeight()>mImageViewHeight){
            mImageView.getLayoutParams().height = mImageView.getHeight() + header.getTop();
            header.layout(header.getLeft(), 0, header.getRight(), header.getHeight());
            mImageView.requestLayout();
        }

        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }

    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub

        int action = ev.getAction();
        if(action == MotionEvent.ACTION_UP){
            ResetAnimation animation = new ResetAnimation(mImageView, mImageViewHeight);
            animation.setDuration(300);
            mImageView.startAnimation(animation);
        }


        return super.onTouchEvent(ev);
    }

    // 自定义动画
    public class ResetAnimation extends Animation {


        private RelativeLayout mIv;
        private int targetHeight;
        private int originalHeight;
        private int extraHeight;
        public ResetAnimation(RelativeLayout iv, int targetHeight){
            this.mIv = iv;
            this.targetHeight = targetHeight;
            this.originalHeight = mIv.getHeight();
            this.extraHeight = originalHeight - targetHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {
            // TODO Auto-generated method stub

            mImageView.getLayoutParams().height = (int) (originalHeight - extraHeight*interpolatedTime);
            mImageView.requestLayout();

            super.applyTransformation(interpolatedTime, t);
        }

    }

}
