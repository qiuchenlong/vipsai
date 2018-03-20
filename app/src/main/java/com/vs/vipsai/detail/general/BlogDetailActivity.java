package com.vs.vipsai.detail.general;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.vs.vipsai.R;
import com.vs.vipsai.bean.News;
import com.vs.vipsai.bean.SubBean;
import com.vs.vipsai.db.DBManager;
import com.vs.vipsai.detail.DetailActivity;
import com.vs.vipsai.detail.DetailFragment;

/**
 * Author: cynid
 * Created on 3/20/18 9:27 AM
 * Description:
 */

public class BlogDetailActivity extends DetailActivity {

    public static void show(Context context, SubBean bean) {
        Intent intent = new Intent(context, BlogDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_bean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void show(Context context, long id) {
        Intent intent = new Intent(context, BlogDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        SubBean bean = new SubBean();
        bean.setType(News.TYPE_ATTENTION);
        bean.setId(id);
        bundle.putSerializable("sub_bean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void show(Context context, long id, boolean isFav) {
        Intent intent = new Intent(context, BlogDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        SubBean bean = new SubBean();
        bean.setType(News.TYPE_ATTENTION);
        bean.setId(id);
        bean.setFavorite(isFav);
        bundle.putSerializable("sub_bean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected DetailFragment getDetailFragment() {
        return BlogDetailFragment.newInstance();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_question_detail, menu);
//        MenuItem item = menu.findItem(R.id.menu_comment);
//        if (item != null) {
//            View action = item.getActionView();
//            if (action != null) {
//                View tv = action.findViewById(R.id.tv_comment_count);
//                if (tv != null) {
//                    mCommentCountView = (TextView) tv;
//                    if (mBean.getStatistics() != null)
//                        mCommentCountView.setText(mBean.getStatistics().getComment() + "");
//                }
//            }
//        }
//        MenuItem menuItem = menu.findItem(R.id.menu_report);
//        DrawableCompat.setTint(menuItem.getIcon(),Color.WHITE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_comment:
//                break;
//            case R.id.menu_report:
//                if (!AccountHelper.isLogin()) {
//                    LoginActivity.show(this);
//                    return false;
//                }
//                toReport(mBean.getId(), mBean.getHref());
//                break;
//        }
        return false;
    }

    protected void toReport(long id, String href) {
//        ReportDialog.create(this, id, href, Report.TYPE_BLOG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK && mBehavior != null) {
//            mBehavior.setIsComment(1);
//            DBManager.getInstance()
//                    .update(mBehavior,"operate_time=?", String.valueOf(mBehavior.getOperateTime()));
//        }
    }

}
