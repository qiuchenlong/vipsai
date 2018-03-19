package com.vs.vipsai.city;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.vs.vipsai.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author: cynid
 * Created on 3/19/18 11:07 AM
 * Description:
 */

public class SortGroupMemberAdapter extends BaseAdapter implements SectionIndexer {

    private List<GroupMemberBean> list = null;
    private Context mContext;


    private String texts[] = null;

    GridView gridview ;

    ArrayList<HashMap<String, Object>> lstImageItem ;


    public SortGroupMemberAdapter(Context mContext, List<GroupMemberBean> list) {
        this.mContext = mContext;
        this.list = list;


        texts = new String[]{ "全国", "北京市",
                "上海市", "广州市",
                "深圳市", "杭州市",
                "重庆市", "武汉市"
        };



    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<GroupMemberBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final GroupMemberBean mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_city_list_item, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.llayout = (LinearLayout)view.findViewById(R.id.hot_city_layout);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Log.e("TAG", "position="+position);


        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());


        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }







        gridview = (GridView) view.findViewById(R.id.activity_city_list_gridview);
        lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < texts.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("hotcity", texts[i]);
            lstImageItem.add(map);
        }

        SimpleAdapter saImageItems = new SimpleAdapter(mContext,
                lstImageItem,// 数据源
                R.layout.activity_city_gridview_item,// 显示布局
                new String[] { "hotcity" },
                new int[] { R.id.activity_city_gridview_item_btn });

        gridview.setAdapter(saImageItems);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Log.e("TAG", "onItemClick...");
                if(gridViewOnItemClickListener != null){
                    gridViewOnItemClickListener.OnItemClickListener(parent, view, position, id, lstImageItem);
                }
            }
        });





        int section2 = getSectionForPosition2(position);
        Log.e("TAG", "section2="+section2);
        Log.e("TAG", "getPositionForSection2="+getPositionForSection2(35));

        if(position == getPositionForSection2(section2)){
            viewHolder.llayout.setVisibility(View.VISIBLE);
        }else{
            viewHolder.llayout.setVisibility(View.GONE);
        }

        if (position < 1) {
            Log.e("TAG", "position="+position);
            viewHolder.tvTitle.setVisibility(View.GONE);
        }else{
            viewHolder.tvTitle.setVisibility(View.VISIBLE);
        }



        viewHolder.tvTitle.setText(this.list.get(position).getName());

        return view;

    }

    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        LinearLayout llayout;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }


    public int getSectionForPosition2(int position) {
        return list.get(position).getName().charAt(0);
    }


    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }


    public int getPositionForSection2(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getName();
            if(sortStr.contains("#")){
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }
        }

        return -1;
    }




    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "热";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }


    public interface GridViewOnItemClickListener{
        public void OnItemClickListener(AdapterView<?> parent, View view,
                                        int position, long id, ArrayList<HashMap<String, Object>> lists);
    }

    public static void setEnterFragmentListener(GridViewOnItemClickListener mGridViewOnItemClickListener){
        gridViewOnItemClickListener = mGridViewOnItemClickListener;
    }

    public static GridViewOnItemClickListener gridViewOnItemClickListener;

}
