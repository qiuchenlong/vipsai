package com.vs.vipsai.city.card;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Author: cynid
 * Created on 3/19/18 11:30 AM
 * Description:
 */

public class CityCardGridView extends GridView {

    public CityCardGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    public CityCardGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }


    public CityCardGridView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
