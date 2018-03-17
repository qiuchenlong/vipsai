package com.vs.vipsai.main.recommend;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vs.vipsai.AppContext;
import com.vs.vipsai.R;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.bean.SubBean;
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.util.StringUtils;
import com.vs.vipsai.util.TDevice;

/**
 * Author: cynid
 * Created on 3/13/18 3:30 PM
 * Description:
 *
 * 关注栏目
 */

public class AttentionSubAdapter extends BaseRecyclerAdapter<SubBean> implements BaseRecyclerAdapter.OnLoadingHeaderCallBack {

//    private OSCApplication.ReadState mReadState;

    public AttentionSubAdapter(Context context, int mode) {
        super(context, mode);
//        mReadState = OSCApplication.getReadState("sub_list");
        setOnLoadingHeaderCallBack(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {
        return new HeaderViewHolder(mHeaderView);
    }

    @Override
    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BlogViewHolder(mInflater.inflate(R.layout.item_list_sub_blog, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, SubBean item, final int position) {
        BlogViewHolder vh = (BlogViewHolder) holder;

        TextView title = vh.tv_title;
        TextView content = vh.tv_description;
        TextView see = vh.tv_view;
        TextView answer = vh.tv_comment_count;

        String text = "";
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);

        Resources resources = mContext.getResources();

        boolean isToday = StringUtils.isSameDay(mSystemTime, item.getPubDate());
        if (isToday) {
            spannable.append("[icon] ");
            Drawable originate = resources.getDrawable(R.mipmap.ic_label_today);
            if (originate != null) {
                originate.setBounds(0, 0, originate.getIntrinsicWidth(), originate.getIntrinsicHeight());
            }
            ImageSpan imageSpan = new ImageSpan(originate, ImageSpan.ALIGN_BOTTOM);
            spannable.setSpan(imageSpan, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        if (item.isOriginal()) {
            spannable.append("[icon] ");
            Drawable originate = resources.getDrawable(R.mipmap.ic_label_originate);
            if (originate != null) {
                originate.setBounds(0, 0, originate.getIntrinsicWidth(), originate.getIntrinsicHeight());
            }
            ImageSpan imageSpan = new ImageSpan(originate, ImageSpan.ALIGN_BOTTOM);
            spannable.setSpan(imageSpan, isToday ? 7 : 0, isToday ? 13 : 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            spannable.append("[icon] ");
            Drawable originate = resources.getDrawable(R.mipmap.ic_label_reprint);
            if (originate != null) {
                originate.setBounds(0, 0, originate.getIntrinsicWidth(), originate.getIntrinsicHeight());
            }
            ImageSpan imageSpan = new ImageSpan(originate, ImageSpan.ALIGN_BOTTOM);
            spannable.setSpan(imageSpan, isToday ? 7 : 0, isToday ? 13 : 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        if (item.isRecommend()) {
            spannable.append("[icon] ");
            Drawable recommend = resources.getDrawable(R.mipmap.ic_label_recommend);
            if (recommend != null) {
                recommend.setBounds(0, 0, recommend.getIntrinsicWidth(), recommend.getIntrinsicHeight());
            }
            ImageSpan imageSpan = new ImageSpan(recommend, ImageSpan.ALIGN_BOTTOM);
            spannable.setSpan(imageSpan, isToday ? 14 : 7, isToday ? 20 : 13, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

//        title.setText(spannable.append(item.getTitle()));

        title.setText(item.getTitle());


        String body = item.getBody();
        if (!TextUtils.isEmpty(body)) {
            body = body.trim();
            if (!TextUtils.isEmpty(body)) {
                content.setText(body);
                content.setVisibility(View.VISIBLE);
            } else {
                content.setVisibility(View.GONE);
            }
        } else {
            content.setVisibility(View.INVISIBLE);
        }

//        if (mReadState.already(item.getKey())) {
//            title.setTextColor(TDevice.getColor(resources, R.color.text_desc_color));
//            content.setTextColor(TDevice.getColor(resources, R.color.text_secondary_color));
//        } else {
            title.setTextColor(TDevice.getColor(resources, R.color.text_title_color));
            content.setTextColor(TDevice.getColor(resources, R.color.text_desc_color));
//        }

//        Author author = item.getAuthor();
//        String authorName;
//        if (author != null && !TextUtils.isEmpty(authorName = author.getName())) {
//            vh.tv_time.setText(String.format("@%s %s",
//                    (authorName.length() > 9 ? authorName.substring(0, 9) : authorName),
//                    StringUtils.formatSomeAgo(item.getPubDate())));
//        } else {
            vh.tv_time.setText(StringUtils.formatSomeAgo(item.getPubDate()));

            vh.tv_time.setText("2012-12-01 22:10");

//        }


//        see.setText(String.valueOf(item.getStatistics().getView()));
//        answer.setText(String.valueOf(item.getStatistics().getComment()));

        vh.btn_pull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimplexToast.show(AppContext.getContext(), "aaaaaaaaaa" + position);
            }
        });


    }

    private static class BlogViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_description, tv_time, tv_comment_count, tv_view;
        LinearLayout ll_title;
        Button btn_pull;

        BlogViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_comment_count = (TextView) itemView.findViewById(R.id.tv_info_comment);
            tv_view = (TextView) itemView.findViewById(R.id.tv_info_view);
            ll_title = (LinearLayout) itemView.findViewById(R.id.ll_title);
            btn_pull = (Button) itemView.findViewById(R.id.btn_pull);
        }
    }

}
