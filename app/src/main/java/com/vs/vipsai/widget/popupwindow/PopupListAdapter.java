package com.vs.vipsai.widget.popupwindow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vs.vipsai.R;

import java.util.List;
import java.util.Map;

/**
 * Author: cynid
 * Created on 3/21/18 3:52 PM
 * Description:
 */

public class PopupListAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, Object>> list;

    public PopupListAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.popup_list_item, null);
        }
        ViewHolder holder = ViewHolder.getHolder(convertView);
        holder.im_popup_item.setImageResource((Integer) list.get(position).get("image"));
        holder.tv_popup_item.setText(list.get(position).get("title").toString());
        return convertView;
    }

    public static class ViewHolder{
        ImageView im_popup_item;
        TextView tv_popup_item;
        private ViewHolder(View convertView){
            im_popup_item = (ImageView) convertView.findViewById(R.id.iv_popup_item);
            tv_popup_item = (TextView) convertView.findViewById(R.id.tv_popup_item);
        }

        public static ViewHolder getHolder(View convertView){
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if(holder == null){
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }

}
