package com.vs.vipsai.publish.layoutcontroller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vs.vipsai.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  布局控制器基类,提供列表数据操作
 */
public abstract class ArrayDataController<T> {

    protected ViewGroup mRoot;

    private List<T> mData = new ArrayList<>();

    public ArrayDataController() {}

    public <T extends ArrayDataController> T wrap(ViewGroup layout) {
        mRoot = layout;
        init(layout.getContext());
        return (T)this;
    }

    public <T extends ArrayDataController> T attachTo(ViewGroup parent, boolean attach) {

        if(mRoot != null && mRoot.getParent() != null) {
            ((ViewGroup)mRoot.getParent()).removeView(mRoot);
        }

        mRoot = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        init(mRoot.getContext());

        if(attach) {
            parent.addView(mRoot);
        }

        return (T)this;
    }

    protected void init(Context context) {}

    /**要绑定的布局*/
    protected abstract int getLayoutId();

    public void setData(List<T> datas) {
        mData.clear();
        if(datas != null) {
            mData.addAll(datas);
        }
    }

    public int getDataSize() {return mData.size();}

    public T getData(int index) {
        return index >= 0 && index < mData.size() ? mData.get(index) : null;
    }

    public void appendData(List<T> data, boolean top) {
        if(data != null) {
            if(top) {
                mData.addAll(0, data);
            }else {
                mData.addAll(data);
            }
        }
    }

    public List<T> getData() {
        return mData;
    }

    public void appendData(T data, boolean top) {
        if(data != null) {
            if(top) {
                mData.add(0, data);
            }else {
                mData.add(data);
            }
        }
    }

    public <T> T findViewById(int id) {
        return (T)mRoot.findViewById(id);
    }
}
