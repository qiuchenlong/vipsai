package com.vs.vipsai.widget.popupwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Author: cynid
 * Created on 3/21/18 2:39 PM
 * Description:
 */

public class PopupListView extends ListView {

    private Context context;

    public PopupListView(Context context) {
        this(context, null);
    }

    public PopupListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopupListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = measureWidthByChilds() + getPaddingLeft() + getPaddingRight();
        super.onMeasure(MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.UNSPECIFIED),
                heightMeasureSpec);//注意，这个地方一定是MeasureSpec.UNSPECIFIED
    }

    public int measureWidthByChilds() {
        int maxWidth = 0;
        View view = null;
        for (int i = 0; i < getAdapter().getCount(); i++) {
            view = getAdapter().getView(i, view, this);
            view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            if (view.getMeasuredWidth() > maxWidth) {
                maxWidth = view.getMeasuredWidth();
            }
            view = null;
        }
        return maxWidth;
    }

}
