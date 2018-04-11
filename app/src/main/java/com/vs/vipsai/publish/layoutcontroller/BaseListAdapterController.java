package com.vs.vipsai.publish.layoutcontroller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  布局控制器基类,提供BaseAdapter操作
 */
public abstract class BaseListAdapterController<T> extends ArrayDataController<T> {

    protected AbsListView mAbsListView;

    @Override
    protected void init(Context context) {
        super.init(context);
        mAbsListView = getAbsListView();
        if(mAbsListView != null) {
            mAbsListView.setAdapter(mAdapter);
        }
    }

    public void setOnItemClick(AdapterView.OnItemClickListener listener) {
        AbsListView listView = getAbsListView();
        if(listView != null) {
            listView.setOnItemClickListener(listener);
        }
    }

    @Override
    public <T1 extends ArrayDataController> T1 wrap(ViewGroup layout) {
        if(!AbsListView.class.isAssignableFrom(layout.getClass())) {
            throw new ClassCastException("wapped view must be subclass of AbsListView.");
        }
        return super.wrap(layout);
    }

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
                return onGetView(data, position, convertView, parent);
            }
            return null;
        }
    };

    /**布局里的列表*/
    protected AbsListView getAbsListView() {return null;}

    @Override
    public void setData(List<T> datas) {
        super.setData(datas);
        mAdapter.notifyDataSetChanged();
    }

    public void notifyDataChanged(){
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void appendData(List<T> data, boolean top) {
        super.appendData(data, top);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void appendData(T data, boolean top) {
        super.appendData(data, top);
        mAdapter.notifyDataSetChanged();
    }

    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    protected View onGetView(T data, int position, View convertView, ViewGroup parent){
        return null;
    }
}
