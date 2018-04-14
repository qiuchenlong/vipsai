package com.vs.vipsai.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.Picture;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.ScrollView;

import com.vs.vipsai.R;
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.util.TLog;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;

/**
 * Author: cynid
 * Created on 4/13/18 9:23 AM
 * Description:
 */

public class MatchView extends ScrollView implements NestedScrollingChild {

    private Paint mPaint;

    private Paint mTextPaint;

    private Paint mEffectPaint;

    private Paint mLinePaint;


    private Path path;

    private int mViewWidth;
    private int mViewHeight;

    private int mPositionX;
    private int mPositionY;


    private int mImageWidth;
    private int mImageHeight;



    private int currentCount = 8;

    private List<Match> mSourceList;


    private NestedScrollingChildHelper mChildHepler;


    public MatchView(Context context) {
        this(context, null);
    }

    public MatchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);



        // 创建画笔
        mTextPaint = new Paint();
        // 设置颜色
        mTextPaint.setColor(Color.BLACK);
        // 设置样式
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(50);
        mTextPaint.setTextAlign(Paint.Align.CENTER);



        mEffectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEffectPaint.setAntiAlias(true);
        mEffectPaint.setStyle(Paint.Style.STROKE);
        mEffectPaint.setColor(Color.BLUE);
        mEffectPaint.setStrokeWidth(3);
        DashPathEffect effects1 = new DashPathEffect(new float[] { 10, 10}, 1);
        mEffectPaint.setPathEffect(effects1);



        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.GRAY);
        mLinePaint.setStrokeWidth(3);


        path = new Path();


        mPositionX = 200;
        mPositionY = 500;

        mImageWidth = 160;
        mImageHeight = 120;

        if (mSourceList == null || mSourceList.size() == 0) {
            initSource();
        }



        //设置Touch监听
//        this.setOnTouchListener(this);


        mChildHepler = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);

    }


    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mChildHepler.setNestedScrollingEnabled(enabled);
    }


    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow) {
        Log.d("TAG", "dispatchNestedScroll...");
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean startNestedScroll(int axes) {
//        return super.startNestedScroll(axes);
        if (isNestedScrollingEnabled()) {
            ViewParent p = getParent();
            View child = this;
            while (p != null) {
                try {
                    // 关键代码
                    if (p.onStartNestedScroll(child, this, axes)) {
//                        mNestedScrollingParent = p;
                        p.onNestedScrollAccepted(child, this, axes);
                        return true;
                    }
                } catch (AbstractMethodError e) {
                    // ...
                }
                if (p instanceof View) {
                    child = (View) p;
                }
                p = p.getParent();
            }
        }
        return false;
    }





    public void setSourcre(List<Match> sourceList) {
        mSourceList = sourceList;
    }

    private void initSource() {
        currentCount = 8;
        mSourceList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Match match = new Match();
            match.setmRes(R.drawable.tmp_head_im);
            mSourceList.add(match);
        }
    }

    private void initSource(int count) {
        currentCount = count;
        mSourceList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Match match = new Match();
            match.setmRes(R.drawable.tmp_head_im);
            mSourceList.add(match);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mViewWidth = getWidth();
        mViewHeight = getHeight();

        TLog.d("mViewWidth = ", "" + mViewWidth);


        mPaint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, mViewWidth, mViewHeight, mPaint);



        // translate view top center
        canvas.translate(mViewWidth / 2, 0);

        String title = "MATCH CHART";
//        canvas.drawText(title, 0, 100, mTextPaint);

        String subtitle = "( 4 / 16)";
//        canvas.drawText(title, 0, 200, mTextPaint);





        // translate view center
        canvas.translate(0, mViewHeight / 2);





        drawPeople(canvas, mSourceList);




//        setLayerType(LAYER_TYPE_SOFTWARE, null);

        drawLine(canvas, mSourceList);




    }



    private void drawLine(Canvas canvas, List<Match> list) {
        for (int i = 0; i < list.size(); i += list.size()/2 ) {
            path.reset();

            int x = 0;
            int x2 = 0;
            int x3 = 0;

            if (i < list.size() / 2) {
                x = 80;
                x2 = 160;
                x3 = mViewWidth / 3 - mImageWidth;
            } else {
                x = -80;
                x2 = -160;
                x3 = -(mViewWidth / 3 - mImageWidth);
            }

            path.moveTo(0, 20);
            path.lineTo(x, 20);
            path.lineTo(x, 20 - (mImageHeight * mSourceList.size() / 4) / 2 - 20);
            path.lineTo(x2, 20 - (mImageHeight * mSourceList.size() / 4) / 2 - 20);
            path.lineTo(x2, 20 - (mImageHeight * mSourceList.size() / 4));
            path.lineTo(x3, 20 - (mImageHeight * mSourceList.size() / 4));

            path.moveTo(x2, 20 - (mImageHeight * mSourceList.size() / 4) / 2 - 20);
            path.lineTo(x2, 20 - (mImageHeight * mSourceList.size() / 4) / 2 / 2);
            path.lineTo(x3, 20 - (mImageHeight * mSourceList.size() / 4) / 2 / 2);


            path.moveTo(0, 20);
            path.lineTo(x, 20);
            path.lineTo(x, 20 + (mImageHeight * mSourceList.size() / 4) / 2 - 20);
            path.lineTo(x2, 20 + (mImageHeight * mSourceList.size() / 4) / 2 - 20);
            path.lineTo(x2, 20 + (mImageHeight * mSourceList.size() / 4));
            path.lineTo(x3, 20 + (mImageHeight * mSourceList.size() / 4));

            path.moveTo(x2, 20 + (mImageHeight * mSourceList.size() / 4) / 2 - 20);
            path.lineTo(x2, 20 + (mImageHeight * mSourceList.size() / 4) / 2 / 2);
            path.lineTo(x3, 20 + (mImageHeight * mSourceList.size() / 4) / 2 / 2);

//        path.lineTo(400, 20);
            canvas.drawPath(path, mLinePaint);
        }
    }


    private void drawPeople(Canvas canvas, List<Match> list) {
        for (int i = 0; i < list.size(); i++) {

            Match match = list.get(i);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), match.getmRes());
            bitmap = Bitmap.createScaledBitmap(bitmap, mImageWidth, mImageHeight, true);

            float left = 0;
            float top = 0;
            if (i < list.size() / 2) {
                left = -mViewWidth / 3;
                top = -mViewHeight / 3 + mImageHeight * 1.25f * i;
            } else {
                left = mViewWidth / 3 - mImageWidth;
                top = -mViewHeight / 3 + mImageHeight * 1.25f * (i - list.size() / 2);
            }

            canvas.drawBitmap(bitmap, left, top, new Paint());
        }
    }




    class Match {
        private int mRes;

        public int getmRes() {
            return mRes;
        }

        public void setmRes(int mRes) {
            this.mRes = mRes;
        }
    }




//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        Log.d("TAG", "onTouch...");
//        return false;
//    }
//
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.d("TAG", "onTouchEvent...");
////        return super.onTouchEvent(event);
//
//        //获取当前输入点的X、Y坐标（视图坐标）
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                //处理按下事件
//                Log.d("TAG", "ACTION_DOWN...");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                //处理移动事件
//                Log.d("TAG", "ACTION_MOVE...");
//                break;
//            case MotionEvent.ACTION_UP:
//                //处理离开事件
//                Log.d("TAG", "ACTION_UP...");
//                break;
//        }
//        return true;
//    }
//
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        Log.d("TAG", "dispatchTouchEvent...");
////        getParent().requestDisallowInterceptTouchEvent(true);
//        return super.dispatchTouchEvent(event);
//
////        return false;
//    }
//
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.d("TAG", "onInterceptTouchEvent...");
////        return super.onInterceptTouchEvent(ev);
//        return true;
//    }







    private float lastX, lastY;// 上一次记录的点
    private float lastDistance;//上一次两点间的距离

    private boolean flag = true;

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                lastX = event.getX();
//                lastY = event.getY();
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (event.getPointerCount() == 2) {//两点触摸
//                    final float disX = Math.abs(event.getX(0) - event.getX(1));//第一个点的偏移量
//                    final float disY = Math.abs(event.getY(0) - event.getY(1));//第二个点的偏移量
//                    final float dis = (float) Math.sqrt(disX * disX + disY * disY);//记录两点间的距离
//                    if (lastDistance == 0) {
//                        lastDistance = dis;//记录第一次
//                    } else {
//                        float scale = dis / lastDistance;
//                        lastDistance = dis;//替换上一次
////                        scaleImage(scale);
//                        if (scale > 1.05f) {
//                            int smallScale = currentCount * 2;
//                            if (smallScale >=32 ) {
//                                smallScale = 32;
//                            }
//                            initSource(smallScale);
//                            invalidate();
//                        }
//
//                        if (scale < 0.95f){
//                            int smallScale = currentCount / 2;
//                            if (smallScale <=8 ) {
//                                smallScale = 8;
//                            }
//                            initSource(smallScale);
//                            invalidate();
//                        }
//                    }
//
//                    flag = true;
//
//                } else if (event.getPointerCount() == 1) {//单点触摸
//                    final float currentX = event.getX();
//                    final float currentY = event.getY();
//                    final float disX = currentX - lastX;
//                    final float disY = currentY - lastY;
//                    scrollBy(-(int) disX, -(int) disY);//进行拖动视图
//                    lastX = currentX;//替换上一次位置
//                    lastY = currentY;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                lastX = 0;//恢复初始化状态
//                lastY = 0;
//                lastDistance = 0;
//                break;
//            default:
//                break;
//        }
//
//        super.onTouchEvent(event);
//        return flag;
//    }



}
