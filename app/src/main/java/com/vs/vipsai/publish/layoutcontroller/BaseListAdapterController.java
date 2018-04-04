package com.vs.vipsai.publish.layoutcontroller;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  布局控制器基类,提供BaseAdapter操作
 */
public abstract class BaseListAdapterController<T> extends ArrayDataController<T> {

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return getDataSize();
        }

        @Override
        public T getItem(int position) {
            return getData(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            T data = getItem(position);
            if(data != null) {
                return onGetView(data, convertView, parent);
            }
            return null;
        }
    };

    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    protected View onGetView(T data, View convertView, ViewGroup parent){
        return null;
    }
}
