package com.vs.vipsai.publish.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vs.vipsai.R;
import com.vs.vipsai.BR;
import com.vs.vipsai.bean.SubjectBean;
import com.vs.vipsai.publish.layoutcontroller.BaseListAdapterController;
import com.vs.vipsai.publish.viewmodels.VMImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  比赛类别列表
 */
public class TournamentTypeListActivity extends ToolbarActivity {

    public static final String EXTRA_SUBJECT = "EXTRA_SUBJECT";

    private TypeListController mList;

    public static void openForResult(Activity context, int requestCode) {
        Intent intent = new Intent(context, TournamentTypeListActivity.class);
        context.startActivityForResult(intent,requestCode);
    }

    @Override
    protected boolean initBundle(Bundle bundle) {
        setResult(RESULT_CANCELED);
        return super.initBundle(bundle);
    }

    @Override
    protected void initData() {
        String[] urls = new String[]{"http://img3.imgtn.bdimg.com/it/u=1998509219,3699571886&fm=27&gp=0.jpg",
                "http://img5.imgtn.bdimg.com/it/u=3053360598,3934153851&fm=200&gp=0.jpg",
                "http://img0.imgtn.bdimg.com/it/u=818473686,553939944&fm=27&gp=0.jpg",
                "http://img4.imgtn.bdimg.com/it/u=1046180996,258140096&fm=27&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=2715483356,1903544801&fm=27&gp=0.jpg"};
        String[] title = new String[]{"美食", "聚会", "爱情", "音乐", "旅行"};
        String[] desc = new String[]{"唯有爱与美食不可辜负", "孤单，被热闹的夜赶出来", "乌云弊月烟波长，谁肯解思量", "在音乐里彰显态度，肆意地放浪形骸",
                "生活不止眼前的苟且，还有诗和远方"};
        //测试
        List<SubjectBean> datas = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            SubjectBean item = new SubjectBean();
            item.url = urls[i];
            item.title = title[i];
            item.description = desc[i];
            datas.add(item);
        }

        mList.setData(datas);
    }

    @Override
    protected void bindContent(ViewGroup parent) {
        mList = new TypeListController().attachTo(parent, true);
        mList.setOnItemClick(mItemClick);
    }

    private AdapterView.OnItemClickListener mItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SubjectBean data = mList.getData(position);
            Intent intent = new Intent();
            intent.putExtra(EXTRA_SUBJECT, data);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    private class TypeListController extends BaseListAdapterController<SubjectBean> {

        @Override
        protected AbsListView getAbsListView() {
            return (AbsListView)mRoot;
        }

        @Override
        protected View onGetView(SubjectBean data, int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                        R.layout.list_item_subject, parent, false);
                convertView = binding.getRoot();
                convertView.setTag(binding);
            }
            ViewDataBinding binding = (ViewDataBinding)convertView.getTag();
            binding.setVariable(BR.SubjectBean, data);
            binding.executePendingBindings();

            return convertView;
        }

        @Override
        protected int getLayoutId() {
            return R.layout.list_view;
        }
    }
}
