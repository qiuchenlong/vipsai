package com.vs.vipsai.publish.layoutcontroller;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vs.vipsai.R;
import com.vs.vipsai.util.StringUtils;
import com.vs.vipsai.util.TDevice;

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
public class ExpandableListViewController<T extends ExpandableListViewController.ItemData> extends BaseExpandableListAdapter {

    private ViewGroup mRoot;
    private ExpandableListView mExpandableListView;

    /**点击group是否可以收缩或展开*/
    private boolean mExpandable = true;

    private List<T> mGroups = new ArrayList<>();

    public static ExpandableListViewController wrapper(ViewGroup layout) {
        return new ExpandableListViewController(layout);
    }

    public ExpandableListViewController() {}

    private ExpandableListViewController(ViewGroup layout) {
        mRoot = layout;
        init();
    }

    public View attachTo(ViewGroup parent, boolean attach) {

        if(mRoot != null && mRoot.getParent() != null) {
            ((ViewGroup)mRoot.getParent()).removeView(mRoot);
        }

        mRoot = (ViewGroup)LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_list_view, parent, false);
        init();

        if(attach) {
            parent.addView(mRoot);
        }

        return mRoot;
    }

    private void init() {
        mExpandableListView = (ExpandableListView)mRoot.findViewById(R.id.expandable_list_view);
        mExpandableListView.setAdapter(this);
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return mExpandable;
            }
        });
    }

    public void setData(List<T> datas, boolean expandAll) {
        mGroups.clear();
        if(datas != null) {
            if(expandAll) {
                for(int i = 0; i < datas.size(); i++) {
                    mGroups.add(datas.get(i));
                    mExpandableListView.expandGroup(i);
                }
            }else {
                mGroups.addAll(datas);
            }
        }

        notifyDataSetChanged();
    }

    /**
     * 点击group是否可以收缩或展开
     * @param value
     */
    public void setExpandable(boolean value) {
        mExpandable = value;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ItemData group = (ItemData)getGroup(groupPosition);
        return group.getChildCount();
    }

    @Override
    public ItemData getGroup(int groupPosition) {
        return groupPosition >= 0 && groupPosition < mGroups.size() ?
                mGroups.get(groupPosition) : null;
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
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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
