package com.vs.vipsai.publish.layoutcontroller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.vs.vipsai.R;
import com.vs.vipsai.widget.LetterIndexView;

import java.util.ArrayList;
import java.util.List;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  绑定布局R.layout.expandable_list_view
 *  ExpandableListView控制器
 */
public class ExpandableListViewController<T extends ExpandableListViewController.ItemData> extends ArrayDataController<T> {

    private ExpandableListView mExpandableListView;
    protected LetterIndexView mLetterIndex;

    /**点击group是否可以收缩或展开*/
    private boolean mExpandable = true;

    @Override
    protected int getLayoutId() {
        return R.layout.expandable_list_view;
    }

    protected void init(Context context) {
        mExpandableListView = findViewById(R.id.expandable_list_view);
        mExpandableListView.setAdapter(mAdapter);
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return mExpandable;
            }
        });
        mLetterIndex = findViewById(R.id.letter_index);
        mLetterIndex.setIndexChangeListener(mOnIndexChangeListener);
        mLetterIndex.setFontSize(14);
    }

    private LetterIndexView.OnIndexChangeListener mOnIndexChangeListener = new LetterIndexView.OnIndexChangeListener() {
        @Override
        public void onIndexChange(int index) {
            mExpandableListView.setSelectedGroup(index);
//            Toast.makeText(mRoot.getContext(), "index:" + index, Toast.LENGTH_SHORT).show();
        }
    };

    public int getTotalChildCount() {
        int result = 0;
        for(int i = 0; i < mAdapter.getGroupCount(); i++) {
            ItemData group = (ItemData)mAdapter.getGroup(i);
            result += group.getChildCount();
        }

        return result;
    }

    /**
     * 可以运行在非主线程
     * @param datas
     * @param expandAll
     */
    public void setData(List<T> datas, boolean expandAll) {
        if(datas != null) {
            if(expandAll) {
                for(int i = 0; i < datas.size(); i++) {
                    appendData(datas.get(i), false);
                    mExpandableListView.expandGroup(i);
                }
            }else {
                setData(datas);
            }
        }

        mAdapter.notifyDataSetChanged();

    }



    /**
     * 点击group是否可以收缩或展开
     * @param value
     */
    public void setExpandable(boolean value) {
        mExpandable = value;
    }

    private BaseExpandableListAdapter mAdapter = new BaseExpandableListAdapter() {
        @Override
        public int getGroupCount() {
            return getDataSize();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            ItemData group = (ItemData)getGroup(groupPosition);
            return group.getChildCount();
        }

        @Override
        public ItemData getGroup(int groupPosition) {
            return getData(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            ItemData group = getGroup(groupPosition);
            return group == null ? null : group.getChild(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ItemData group = getGroup(groupPosition);
            if(group != null) {
                return onGetGroupView((T)getGroup(groupPosition), isExpanded, convertView, parent);
            }

            return null;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            Object child = getChild(groupPosition, childPosition);
            if(child != null) {
                return onGetChildView(child, childPosition, isLastChild, convertView, parent);
            }
            return null;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    };

    protected View onGetGroupView(T group, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    protected View onGetChildView(Object child, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    public static class ItemData<childT> {
        private List<childT> mChilds = new ArrayList<>();

        public void appendChild(childT child) {
            mChilds.add(child);
        }

        public int getChildCount(){return mChilds.size();}
        public childT getChild(int index) {return index >= 0 && index < mChilds.size() ? mChilds.get(index) : null;}
    }
}
