package com.vs.vipsai.publish.viewmodels;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.vs.vipsai.BR;
import com.vs.vipsai.R;
import com.vs.vipsai.bean.PageBean;
import com.vs.vipsai.publish.layoutcontroller.ArrayDataController;
import com.vs.vipsai.publish.layoutcontroller.BaseListAdapterController;
import com.vs.vipsai.widget.FitHeightImageView;
import com.vs.vipsai.widget.GlidImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 * 创建样板比赛-选择封面界面模型，绑定R.layout.fragment_publish_pick_cover
 */
public class VMPickCover{

    public int placeHolder = R.mipmap.ic_default_image;

    public ObservableField<String> cover = new ObservableField<>("");

    private CoverListController mCoverList;

    private VMImageItem mSelected;

    private void wrapGridView(GridView view) {
        mCoverList = new CoverListController().wrap(view);

        //测试
        String[] test = new String[]{
            "http://img3.imgtn.bdimg.com/it/u=1998509219,3699571886&fm=27&gp=0.jpg",
                "http://img5.imgtn.bdimg.com/it/u=3053360598,3934153851&fm=200&gp=0.jpg",
                "http://img0.imgtn.bdimg.com/it/u=818473686,553939944&fm=27&gp=0.jpg",
                "http://img4.imgtn.bdimg.com/it/u=1046180996,258140096&fm=27&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=2715483356,1903544801&fm=27&gp=0.jpg",
                "http://img0.imgtn.bdimg.com/it/u=268449436,487014380&fm=27&gp=0.jpg",
                "http://img0.imgtn.bdimg.com/it/u=4152615134,253319342&fm=27&gp=0.jpg",
                "http://img5.imgtn.bdimg.com/it/u=2794000032,2725020285&fm=27&gp=0.jpg"
        };
        List<VMImageItem> data = new ArrayList<>();
        for (String url : test) {
            VMImageItem item = new VMImageItem();
            item.url = url;
            data.add(item);
        }

        mCoverList.setData(data);
        view.setAdapter(mCoverList.getAdapter());
        view.setOnItemClickListener(mOnItemClickListener);

    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            VMImageItem item = mCoverList.getData(position);
            item.selected.set(true);
            if(mSelected != null && mSelected != item) {
                mSelected.selected.set(false);
            }
            mSelected = item;
        }
    };

    private class CoverListController extends BaseListAdapterController<VMImageItem> {
        @Override
        protected View onGetView(VMImageItem data, View convertView, ViewGroup parent) {
            if(convertView == null) {
                ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_image,
                                                parent, false);
                convertView = binding.getRoot();

                convertView.setTag(binding);

            }

            ViewDataBinding binding = (ViewDataBinding)convertView.getTag();
            binding.setVariable(BR.VMImageItem, data);
            binding.executePendingBindings();
            return convertView;
        }

        @Override
        protected int getLayoutId() {
            return 0;
        }
    }

    @BindingAdapter("bindModel")
    public static void bindGridView(GridView view, VMPickCover model) {
        model.wrapGridView(view);
    }

}
