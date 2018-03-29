package com.vs.vipsai.main.past;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vs.vipsai.R;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.bean.SubBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: cynid
 * Created on 3/15/18 11:43 AM
 * Description:
 *
 * 冠军作品栏目
 */

public class ChampionWorkSubAdapter extends BaseRecyclerAdapter<SubBean> implements BaseRecyclerAdapter.OnLoadingHeaderCallBack {

    private List heights;

    public ChampionWorkSubAdapter(Context context, int mode) {
        super(context, mode);
        setOnLoadingHeaderCallBack(this);


    }

    private void getRandomHeight(int count){//得到随机item的高度
        heights = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            heights.add((int)(400+Math.random()*500));
        }
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new MeViewHolder(mInflater.inflate(R.layout.item_list_sub_me_of_winner, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, SubBean item, int position) {

        getRandomHeight(this.getCount());


        ViewGroup.LayoutParams params =  holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数
        params.height = (int) heights.get(position);//把随机的高度赋予item布局
        holder.itemView.setLayoutParams(params);//把params设置给item布局


        MeViewHolder vh = (MeViewHolder) holder;

//        vh.tv_time.setText("2018.02.01");
//        vh.tv_title.setText("go go go ...");

        if (position == 0) {
            vh.iv_winner.setImageResource(R.mipmap.image_no_100);
        } else if (position == 0) {
            vh.iv_winner.setImageResource(R.mipmap.image_no_101);
        } else {
            vh.iv_winner.setImageResource(R.mipmap.image_no_102);
        }


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
        ImageView iv_winner;

        MeViewHolder(View itemView) {
            super(itemView);
            iv_winner = (ImageView) itemView.findViewById(R.id.item_list_sub_me_of_winner_imageview);
        }
    }


}
