package com.vs.vipsai.widget.xchart;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: cynid
 * Created on 3/31/18 3:02 PM
 * Description:
 */

public class CircleChart extends View {

    private int mWidth;
    private int mHeight;
    private String[] pieName;
    private RectF oval;
    private String[] rangeString;
    private List<Integer> listRange = new ArrayList<Integer>();
    private List<Integer> listNunber = new ArrayList<Integer>();
    private int[] paintColors;
    private List<Float> listDegree = new ArrayList<Float>();
    private Paint mPaint;

    public CircleChart(Context context) {
        super(context);
        init();
    }

    public CircleChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }



    /**
     * @param listNunber  数的集合
     * @param paintColors 画笔颜色集合
     * @param pieName     饼状图的名称
     */
    public void setData(List<Integer> listNunber, int[] paintColors, String[] pieName, String[] rangeString) {
        this.rangeString = rangeString;
        this.pieName = pieName;
        this.listNunber.clear();
        this.listNunber.addAll(listNunber);
        this.paintColors = paintColors;

        this.listDegree.clear();
        initDegree();//计算数量占有的度数(比例)
        postInvalidate();
    }

    private void initDegree() {
        //遍历集合的数据  ，并判断范围
        int num_zl = listNunber.get(0);
        int num_gz = listNunber.get(1);

        //比例的个数添加到集合中
        listRange.add(num_zl);
        listRange.add(num_gz);

        //计算比例并添加到集合中
        listDegree.add(360f * num_zl * 0.01f);
        listDegree.add(360f * num_gz * 0.01f);

    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(2f);
        mPaint.setColor(Color.WHITE);
        setBackgroundColor(Color.BLACK);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景颜色和背景线
        drawBackGroudLines(canvas);

        //画饼状图和文字
        drawArcView(canvas);


    }

    private void drawArcView(Canvas canvas) {
        float starDegree = -10;   //开始的角度是可以任意的
        mPaint.setTextSize(30);
        //画饼状图
        for (int i = 0; i < listDegree.size(); i++) {
            mPaint.setColor(paintColors[i]); //设置线条的颜色，Int类型
            Log.e("TAG", "listDegree:" + listDegree.get(i));
            canvas.drawArc(oval, starDegree, listDegree.get(i), true, mPaint);
            starDegree += listDegree.get(i);

            //画文字
//            canvas.drawText(pieName[i] + "(" + rangeString[i] + ")：" + listRange.get(i) + "次", 10, i * 50, mPaint);

        }
    }


    private void drawBackGroudLines(Canvas canvas) {
        //背景颜色,灰色
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
        canvas.drawLine(0, 0, mWidth, 0, mPaint);
        canvas.drawLine(0, mHeight, mWidth, mHeight, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        oval = new RectF(0, 0, mWidth, mHeight);

    }

}
