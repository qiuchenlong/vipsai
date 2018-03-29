package com.vs.vipsai.main.my;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vs.vipsai.R;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.bean.SubBean;
import com.vs.vipsai.util.ImageUtils;

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

        if (position == 0) {
            vh.tv_title.setText("3月28日，@张继科@景甜 正式公布恋情，张继科的这些小心思你看懂了吗？2010年3月28日他首次获得个人亚洲杯男单冠军，时隔8年在这个特殊日子高调认爱，时间刚好是景甜生日（7:21=7月21日），而328更是“相爱吧”的谐音！从微博暧昧互动到车内拥吻被拍，再到公布恋情大方撒糖，真是“科甜，可甜”！ \u200B");
//            vh.iv_me.setImageBitmap(ImageUtils.readBitmap(mContext, R.mipmap.image_no_1));
            vh.iv_me.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(mContext.getResources(), R.mipmap.image_no_1, 100, 100));
        } else if (position == 1) {
            vh.tv_title.setText(Html.fromHtml("【<font color='#48AAFB'>#张继科景甜公布恋情#</font> 所以景甜会来厦门探班张继科集训吗[嘻嘻]】刚刚， 张继科微博晒出与景甜合影，正式公开恋情！恭喜恭喜！"));
//            vh.iv_me.setImageBitmap(ImageUtils.readBitmap(mContext, R.mipmap.image_no_2));
            vh.iv_me.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(mContext.getResources(), R.mipmap.image_no_2, 100, 100));
        } else {
            vh.tv_title.setText("今天，回家是唯一的主题。[心]    #如你所愿#");
//            vh.iv_me.setImageBitmap(ImageUtils.readBitmap(mContext, R.mipmap.image_no_3));
            vh.iv_me.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(mContext.getResources(), R.mipmap.image_no_3, 100, 100));
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
