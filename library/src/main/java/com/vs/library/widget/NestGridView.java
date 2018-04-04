package com.vs.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 可嵌套GridView
 * @author chends
 *
 */
public class NestGridView extends GridView{

	public NestGridView(Context context) {
		super(context);
	}
	
	public NestGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override 
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {      
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST); 
        super.onMeasure(widthMeasureSpec, expandSpec); 
    } 
}
