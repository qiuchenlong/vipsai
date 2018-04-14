package com.vs.library;

import android.view.View;

/**
 * Created by chends on 2017/9/19.
 */

public interface OnViewClickListener<T> {
    public void onViewClick(View view, T entity);
}
