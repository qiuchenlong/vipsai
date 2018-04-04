package com.vs.vipsai.main.my;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vs.vipsai.R;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.bean.SubBean;
import com.vs.vipsai.util.ImageUtils;
import com.vs.vipsai.util.TDevice;

/**
 * Author: cynid
 * Created on 3/15/18 11:43 AM
 * Description:
 */

public class AllSubAdapter extends BaseRecyclerAdapter<SubBean> implements BaseRecyclerAdapter.OnLoadingHeaderCallBack {


    public AllSubAdapter(Context context, int mode) {
        super(context, mode);
        setOnLoadingHeaderCallBack(this);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new MeViewHolder(mInflater.inflate(R.layout.item_list_sub_me_of_all, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, SubBean item, int position) {

        ViewGroup.LayoutParams params =  holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数
        params.height = (int) TDevice.dp2px(250);//把随机的高度赋予item布局
        holder.itemView.setLayoutParams(params);//把params设置给item布局


        MeViewHolder vh = (MeViewHolder) holder;

//        vh.tv_time.setText("2018.02.01");
//        vh.tv_title.setText("go go go ...");

        if (position == 0) {
            vh.iv_winner.setImageResource(R.mipmap.image_no_11);
//            vh.iv_winner.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(mContext.getResources(), R.mipmap.image_no_10, 100, 100));
        }else if (position == 1) {
            vh.iv_winner.setImageResource(R.mipmap.image_no_4);
//            vh.iv_winner.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(mContext.getResources(), R.mipmap.image_no_7, 100, 100));
        } else {
            vh.iv_winner.setImageResource(R.mipmap.image_no_6);
//            vh.iv_winner.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(mContext.getResources(), R.mipmap.image_no_6, 100, 100));
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
