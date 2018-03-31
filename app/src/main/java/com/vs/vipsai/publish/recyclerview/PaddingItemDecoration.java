package com.vs.vipsai.publish.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class PaddingItemDecoration extends RecyclerView.ItemDecoration {
    private int[] mSpace;

    public PaddingItemDecoration(int left, int top, int right, int bottom) {
        mSpace = new int[]{left,top,right,bottom};
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace[0];

        if(parent.getChildAdapterPosition(view) == 0) {
            outRect.top = mSpace[1];
        }else {
            outRect.top = mSpace[1] / 2;
        }
        outRect.right = mSpace[2];
        if(parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = mSpace[3];
        }else {
            outRect.bottom = mSpace[3] / 2;
        }

    }
}
