package com.vs.vipsai.main.recommend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vs.vipsai.AppContext;
import com.vs.vipsai.R;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.bean.SubBean;
import com.vs.vipsai.bean.popularData;
import com.vs.vipsai.ui.PopupWindowDialog;
import com.vs.vipsai.ui.dialog.ShareDialogBuilder;
import com.vs.vipsai.ui.videoplayer.PlayActivity;
import com.vs.vipsai.util.TextUtil;
import com.vs.vipsai.widget.PortraitView;
import com.vs.vipsai.widget.countdown.CountDownTimerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: cynid
 * Created on 3/13/18 3:30 PM
 * Description:
 *
 * 关注栏目
 */

public class AttentionSubAdapter extends BaseRecyclerAdapter<SubBean> implements BaseRecyclerAdapter.OnLoadingHeaderCallBack {

//    private OSCApplication.ReadState mReadState;

    public AttentionSubAdapter(Context context, int mode, Activity activity) {
        super(context, mode);
//        mReadState = OSCApplication.getReadState("sub_list");
        setOnLoadingHeaderCallBack(this);

        mActivity = activity;
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
        return new BlogViewHolder(mInflater.inflate(R.layout.item_list_sub_attention, parent, false));
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

//        boolean isToday = StringUtils.isSameDay(mSystemTime, item.getPubDate());
//        if (isToday) {
//            spannable.append("[icon] ");
//            Drawable originate = resources.getDrawable(R.mipmap.ic_label_today);
//            if (originate != null) {
//                originate.setBounds(0, 0, originate.getIntrinsicWidth(), originate.getIntrinsicHeight());
//            }
//            ImageSpan imageSpan = new ImageSpan(originate, ImageSpan.ALIGN_BOTTOM);
//            spannable.setSpan(imageSpan, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        }
//
//        if (item.isOriginal()) {
//            spannable.append("[icon] ");
//            Drawable originate = resources.getDrawable(R.mipmap.ic_label_originate);
//            if (originate != null) {
//                originate.setBounds(0, 0, originate.getIntrinsicWidth(), originate.getIntrinsicHeight());
//            }
//            ImageSpan imageSpan = new ImageSpan(originate, ImageSpan.ALIGN_BOTTOM);
//            spannable.setSpan(imageSpan, isToday ? 7 : 0, isToday ? 13 : 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        } else {
//            spannable.append("[icon] ");
//            Drawable originate = resources.getDrawable(R.mipmap.ic_label_reprint);
//            if (originate != null) {
//                originate.setBounds(0, 0, originate.getIntrinsicWidth(), originate.getIntrinsicHeight());
//            }
//            ImageSpan imageSpan = new ImageSpan(originate, ImageSpan.ALIGN_BOTTOM);
//            spannable.setSpan(imageSpan, isToday ? 7 : 0, isToday ? 13 : 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        }
//
//        if (item.isRecommend()) {
//            spannable.append("[icon] ");
//            Drawable recommend = resources.getDrawable(R.mipmap.ic_label_recommend);
//            if (recommend != null) {
//                recommend.setBounds(0, 0, recommend.getIntrinsicWidth(), recommend.getIntrinsicHeight());
//            }
//            ImageSpan imageSpan = new ImageSpan(recommend, ImageSpan.ALIGN_BOTTOM);
//            spannable.setSpan(imageSpan, isToday ? 14 : 7, isToday ? 20 : 13, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        }

//        title.setText(spannable.append(item.getTitle()));

        title.setText(TextUtil.tf_keyword("#Android# @IOS #Nodejs 腾讯QQ，8亿人在用的即时通讯软件，你不仅可以在各类通讯终端上通过QQ聊天交友，还能进行免费的视频、语音通话，或者随时随地收发重要文件。欢迎访问QQ官方网站，下载体验最新版QQ，了解QQ最新功能。 "));


//        String body = item.getBody();
//        if (!TextUtils.isEmpty(body)) {
//            body = body.trim();
//            if (!TextUtils.isEmpty(body)) {
//                content.setText(body);
//                content.setVisibility(View.VISIBLE);
//            } else {
//                content.setVisibility(View.GONE);
//            }
//        } else {
//            content.setVisibility(View.INVISIBLE);
//        }

//        if (mReadState.already(item.getKey())) {
//            title.setTextColor(TDevice.getColor(resources, R.color.text_desc_color));
//            content.setTextColor(TDevice.getColor(resources, R.color.text_secondary_color));
//        } else {
//            title.setTextColor(TDevice.getColor(resources, R.color.text_title_color));
//            content.setTextColor(TDevice.getColor(resources, R.color.text_desc_color));
//        }

//        Author author = item.getAuthor();
//        String authorName;
//        if (author != null && !TextUtils.isEmpty(authorName = author.getName())) {
//            vh.tv_time.setText(String.format("@%s %s",
//                    (authorName.length() > 9 ? authorName.substring(0, 9) : authorName),
//                    StringUtils.formatSomeAgo(item.getPubDate())));
//        } else {
//            vh.tv_time.setText(StringUtils.formatSomeAgo(item.getPubDate()));

//            vh.tv_title.setText(item.getSlug());

            vh.tv_time.setText("2012-12-01 22:10");

            String looptimeStr = "01D 22H 02M 29S";
            Spannable looptimeSpa = new SpannableStringBuilder(looptimeStr);
        looptimeSpa.setSpan(new AbsoluteSizeSpan(20), looptimeStr.indexOf("D"), looptimeStr.indexOf("D") + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        looptimeSpa.setSpan(new AbsoluteSizeSpan(20), looptimeStr.indexOf("H"), looptimeStr.indexOf("H") + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        looptimeSpa.setSpan(new AbsoluteSizeSpan(20), looptimeStr.indexOf("M"), looptimeStr.indexOf("M") + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        looptimeSpa.setSpan(new AbsoluteSizeSpan(20), looptimeStr.indexOf("S"), looptimeStr.indexOf("S") + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//            vh.loopTime.setText(looptimeSpa);

            // 设置倒计时的时、分、秒
            vh.loopTime.setDateTime(1, 0, 0, 20);
            // 开始倒计时
            vh.loopTime.start();



//        }


//        see.setText(String.valueOf(item.getStatistics().getView()));
//        answer.setText(String.valueOf(item.getStatistics().getComment()));

        vh.btn_pull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popdialog = new PopupWindowDialog(mActivity, itemsOnClick);
                //显示窗口
                popdialog.showAtLocation(mActivity.findViewById(R.id.view_pager), Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
            }
        });




//        vh.username.setText(item.getOwner().getUsername());


//        Author author = new Author();
//        author.setPortrait(item.getOwner().getProfile().getAvatar_url());
//        vh.portraitView.setup(author);


        vh.portraitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        vh.tv_title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        vh.tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AppContext.getContext(), SearchActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                AppContext.getInstance().startActivity(intent);

//                SearchActivity.show(AppContext.getContext());
            }
        });


//        vh.tv_description.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BlogDetailActivity.show(AppContext.getContext(), 1);
//            }
//        });

//        Log.d("attentionsubadapter", "aaa:" + item.getImage_urls());
//        Glide.with(mContext).load(item.getImage_urls())
//                .error(R.mipmap.widget_default_face)
//                .placeholder(R.mipmap.widget_default_face)
//                .into(vh.imageView);


        if (position % 2 == 0) {
            vh.layoutSingle.setVisibility(View.VISIBLE);
            vh.layoutBettle.setVisibility(View.GONE);
        } else {
            vh.layoutSingle.setVisibility(View.GONE);
            vh.layoutBettle.setVisibility(View.VISIBLE);
        }


        if (position == 0) {
            vh.imageView.setImageResource(R.mipmap.image_no_6);
        } else if (position == 0) {
            vh.imageView.setImageResource(R.mipmap.image_no_3);
        } else {
            vh.imageView.setImageResource(R.mipmap.image_no_11);
        }

        vh.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                NotifyActicity.show(AppContext.getContext());
                Intent intent = new Intent(AppContext.getContext(), PlayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AppContext.getInstance().startActivity(intent);
            }
        });


        vh.shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SimplexToast.show(AppContext.getContext(), "position:" + position);
                showSharedDialog();
            }
        });

    }

    private static class BlogViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_description, tv_time, tv_comment_count, tv_view;
        LinearLayout ll_title;
        RelativeLayout btn_pull;
        PortraitView portraitView;
        ImageView imageView;
        LinearLayout shareLayout;

        TextView username;

        CountDownTimerView loopTime;


//        @BindView(R.id.item_list_sub_attention_layout_single)
        FrameLayout layoutSingle;

//        @BindView(R.id.item_list_sub_attention_layout_bettle)
        FrameLayout layoutBettle;


        BlogViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_comment_count = (TextView) itemView.findViewById(R.id.tv_info_comment);
            tv_view = (TextView) itemView.findViewById(R.id.tv_info_view);
            ll_title = (LinearLayout) itemView.findViewById(R.id.ll_title);
            btn_pull = (RelativeLayout) itemView.findViewById(R.id.btn_pull);
            portraitView = (PortraitView) itemView.findViewById(R.id.iv_portrait);
            imageView = (ImageView) itemView.findViewById(R.id.item_list_sub_me_imageview);
            shareLayout = (LinearLayout) itemView.findViewById(R.id.item_list_sub_attention_share_layout);

            username = (TextView) itemView.findViewById(R.id.item_list_sub_attention_name);

            loopTime = (CountDownTimerView) itemView.findViewById(R.id.item_list_sub_attention_loop_time);

            layoutSingle = (FrameLayout) itemView.findViewById(R.id.item_list_sub_attention_layout_single);
            layoutBettle = (FrameLayout) itemView.findViewById(R.id.item_list_sub_attention_layout_bettle);
        }
    }


    public static final String DEFAULT = "https://channelfix.com/";
    private String mCurrentUrl = DEFAULT;

    private ShareDialogBuilder mShareDialogBuilder;
    private AlertDialog alertDialog;
    private Activity mActivity;

    /**
     * 打开分享dialog
     */
    private void showSharedDialog() {
        if (mShareDialogBuilder == null) {
            mShareDialogBuilder = ShareDialogBuilder.with(mActivity)
                    .title("title")
                    .content("content")
                    .url(mCurrentUrl)
                    .build();
        }
        if (alertDialog == null)
            alertDialog = mShareDialogBuilder.create();
        alertDialog.show();
    }



    private PopupWindowDialog popdialog;

    // 为弹出窗口实现监听类
    private android.view.View.OnClickListener itemsOnClick = new android.view.View.OnClickListener() {

        @Override
        public void onClick(View v) {
            popdialog.dismiss();

            Intent intent;

            switch (v.getId()) {
                case R.id.btn_take_photo:

////					if(Build.VERSION.CODENAME.equals("MNC")){ // 如果是6.0系统
////						if(checkSelf)
////					}else{
////
////					}
//
//
////					Toast.makeText(getActivity(), "照相", Toast.LENGTH_SHORT).show();
////					takePicturesWithFront();
//
//                    // 判断是否挂载了SD卡
//                    String savePath = "";
//                    String storageState = Environment.getExternalStorageState();
//                    if (storageState.equals(Environment.MEDIA_MOUNTED)) {
//                        savePath = Environment.getExternalStorageDirectory()
//                                .getAbsolutePath() + "/Diyache/Camera/";
//                        File savedir = new File(savePath);
//                        if (!savedir.exists()) {
//                            savedir.mkdirs();
//                        }
//                    }
//
//                    // 没有挂载SD卡，无法保存文件
//                    if (StringUtils.isEmpty(savePath)) {
////		                AppContext.showToastShort("无法保存照片，请检查SD卡是否挂载");
//                        return;
//                    }
//
//                    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
//                            .format(new Kits.Date());
//                    String fileName = "dyc_" + timeStamp + ".jpg";// 照片命名
//                    File out = new File(savePath, fileName);
//                    Uri uri = Uri.fromFile(out);
//
//                    theLarge = savePath + fileName;// 该照片的绝对路径
//
//                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                    startActivityForResult(intent,
//                            ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);

                    break;
                case R.id.btn_pick_photo:
//					Toast.makeText(getActivity(), "相册", Toast.LENGTH_SHORT).show();
//					selectePhotos();


//                    if (Build.VERSION.SDK_INT < 19) {
//                        intent = new Intent();
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
//                        intent.setType("image/*");
//                        startActivityForResult(Intent.createChooser(intent, "选择图片"),
//                                ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
//                    } else {
//                        intent = new Intent(Intent.ACTION_PICK,
//                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        intent.setType("image/*");
//                        startActivityForResult(Intent.createChooser(intent, "选择图片"),
//                                ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
//                    }

                    break;
                default:
//					Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
                    break;
            }

        }

    };

}
