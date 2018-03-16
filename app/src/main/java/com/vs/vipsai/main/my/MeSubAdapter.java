package com.vs.vipsai.main.my;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vs.vipsai.R;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.bean.SubBean;

/**
 * Author: cynid
 * Created on 3/15/18 11:43 AM
 * Description:
 */

public class MeSubAdapter extends BaseRecyclerAdapter<SubBean> implements BaseRecyclerAdapter.OnLoadingHeaderCallBack {


    public MeSubAdapter(Context context, int mode) {
        super(context, mode);
        setOnLoadingHeaderCallBack(this);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new MeViewHolder(mInflater.inflate(R.layout.item_list_sub_me_of_newest, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, SubBean item, int position) {
        MeViewHolder vh = (MeViewHolder) holder;

        vh.tv_time.setText("2018.02.01");
        vh.tv_title.setText("go go go ...");
        vh.iv_me.setImageResource(R.mipmap.tmp_bg);


//        BlogViewHolder vh = (BlogViewHolder) holder;
//        vh.tv_time.setText("2012-12-01 22:10");

    }


    /**
     * header view
     * */
    @Override
    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {
        return new HeaderViewHolder(mHeaderView);
    }

    @Override
    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {

    }





    private static class MeViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time, tv_title;
        ImageView iv_me;

        MeViewHolder(View itemView) {
            super(itemView);
            tv_time = (TextView) itemView.findViewById(R.id.item_list_sub_me_time);
            tv_title = (TextView) itemView.findViewById(R.id.item_list_sub_me_title);
            iv_me = (ImageView) itemView.findViewById(R.id.item_list_sub_me_imageview);
        }
    }


}
