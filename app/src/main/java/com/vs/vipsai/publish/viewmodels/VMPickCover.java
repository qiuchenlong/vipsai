package com.vs.vipsai.publish.viewmodels;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.text.Html;

import com.vs.vipsai.AppConfig;
import com.vs.vipsai.BR;
import com.vs.vipsai.R;
import com.vs.vipsai.media.SelectImageActivity;
import com.vs.vipsai.media.config.SelectOptions;
import com.vs.vipsai.publish.TournamentCollector;
import com.vs.vipsai.publish.layoutcontroller.BaseListAdapterController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 * 创建样板比赛-选择封面界面模型，绑定R.layout.fragment_publish_pick_cover
 */
public class VMPickCover{

    public ObservableField<String> remoteCover = new ObservableField<>("");
    public ObservableField<String> localCover = new ObservableField<>("");

    private CoverListController mCoverList;

    private VMImageItem mSelected;

    public ObservableField<CharSequence> rule = new ObservableField<>();

    private File mLocalCoverCacheDir;

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

        data.get(0).selected.set(true);
        showSelectedCover(data.get(0));

        mCoverList.setData(data);

        rule.set(Html.fromHtml(view.getContext().getString(R.string.game_rule)));

        mLocalCoverCacheDir = new File(view.getContext().getFilesDir() + AppConfig.PUBLISH_LOCAL_COVERS_DIR);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pick_local_pic:       //pick local pics
                SelectImageActivity.show(view.getContext(), new SelectOptions.Builder()
                        .setHasCam(true)
                        .setCrop(700, 350)
                        .setCallback(new SelectOptions.Callback() {
                            @Override
                            public void doSelected(String[] images) {
                                if(images != null && images.length > 0) {
                                    try {
                                        String[] files = mLocalCoverCacheDir.list();
                                        int tmpCount = files == null ? 0 : files.length;
                                        File tmpFile = new File(mLocalCoverCacheDir, "tmp" + tmpCount);

                                        File file = new File(images[0]);
                                        if (file.renameTo(tmpFile)) {
                                            mCoverList.appendLocalPic(tmpFile.getAbsolutePath());
                                        }

                                    }catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }).build());
                break;
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        VMImageItem item = mCoverList.getData(position);
        item.selected.set(true);
        showSelectedCover(item);
    }

    public void onRuleCheckChanged(CompoundButton buttonView, boolean isChecked) {
        TournamentCollector c = TournamentCollector.get();
        if(c != null) {
            c.setRuleChecked(isChecked);
        }
    }

    //显示选中封面
    private void showSelectedCover(VMImageItem selected) {

        if(mSelected != null && mSelected != selected) {
            mSelected.selected.set(false);
        }
        mSelected = selected;
        if(TextUtils.isEmpty(selected.url)) {
            localCover.set(selected.localPath.get());
            remoteCover.set("");
        }else {
            remoteCover.set(selected.url);
            localCover.set("");
        }

        TournamentCollector c = TournamentCollector.get();
        if(c != null) {
            c.setCoverData(selected);
        }
    }

    //可选封面列表
    private class CoverListController extends BaseListAdapterController<VMImageItem> {

        private void appendLocalPic(String path) {
            if(!TextUtils.isEmpty(path)) {
                VMImageItem item = new VMImageItem();
                item.localPath.set(path);
                appendData(item, true);

                item.selected.set(true);
                showSelectedCover(item);
//                List<VMImageItem> images = new ArrayList<>();
//                for(int i = 0; i < paths.length; i++) {
//                    VMImageItem item = new VMImageItem();
//                    if(i == 0) {
//                        item.selected.set(true);
//                        showSelectedCover(item);
//                    }
//
//                    item.localPath = paths[i];
//                    images.add(item);
//                }
//
//                appendData(images, true);
            }
        }

        @Override
        protected AbsListView getAbsListView() {
            return (GridView)mRoot;
        }

        @Override
        protected View onGetView(VMImageItem data, int position, View convertView, ViewGroup parent) {
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
