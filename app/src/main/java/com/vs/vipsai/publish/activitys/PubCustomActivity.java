package com.vs.vipsai.publish.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.vs.library.widget.HeaderGridView;
import com.vs.vipsai.AppConfig;
import com.vs.vipsai.BR;
import com.vs.vipsai.R;
import com.vs.vipsai.bean.AwardBean;
import com.vs.vipsai.bean.SubjectBean;
import com.vs.vipsai.bean.TournamentBean;
import com.vs.vipsai.media.SelectImageActivity;
import com.vs.vipsai.media.config.SelectOptions;
import com.vs.vipsai.publish.RequestCode;
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
 *  创建私人比赛
 */
public class PubCustomActivity extends ToolbarActivity{

    private static final String EXTRA_PRESET_TOURNAMENT = "EXTRA_PRESET_TOURNAMENT";

    private RecommendedSubjectList mRecommendSubjects;

    public ObservableField<String> awards = new ObservableField<>("");

    private File mLocalDir;

    /**
     * 打开赛事编辑器
     * @param context
     * @param preset 需要编辑的赛事， 如果为null 则创建新赛事
     */
    public static void open(Context context, TournamentBean preset) {
        Intent intent = new Intent(context, PubCustomActivity.class);
        if(preset != null) {
            intent.putExtra(EXTRA_PRESET_TOURNAMENT, preset);
        }
        context.startActivity(intent);
    }

    @Override
    protected void bindContent(ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.activity_pub_custom, parent, true);
        binding.setVariable(BR.PubCustomActivity, this);

        GridView gridView = binding.getRoot().findViewById(R.id.grid_view);
        mRecommendSubjects = new RecommendedSubjectList().wrap(gridView);


        ViewDataBinding headBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_pub_custom_header,
                                    null, false);
        headBinding.setVariable(BR.TournamentCollector, TournamentCollector.get());
        headBinding.setVariable(BR.PubCustomActivity, this);
        mRecommendSubjects.addHeaderView(headBinding.getRoot(), null, false);

        gridView.setAdapter(mRecommendSubjects.getAdapter());

        setMenuButton(R.string.public_txt);

        mLocalDir = new File(getFilesDir() + AppConfig.PUBLISH_LOCAL_COVERS_DIR);
        if(!mLocalDir.exists()) {
            mLocalDir.mkdir();
        }
    }

    @Override
    protected void initData() {
        super.initData();

        //测试
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

        mRecommendSubjects.setData(datas);
    }

    @Override
    protected boolean initBundle(Bundle bundle) {
        TournamentBean tournament = null;
        if(bundle != null && bundle.containsKey(EXTRA_PRESET_TOURNAMENT)) {
            tournament = bundle.getParcelable(EXTRA_PRESET_TOURNAMENT);
        }

        if(tournament == null) {
            tournament = new TournamentBean();
        }

        //比赛信息收集器
        TournamentCollector.build(tournament);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RequestCode.REQUEST_PICK_TOURNAMENT_TYPE == requestCode && RESULT_OK == resultCode && data != null) {
            SubjectBean subject = (SubjectBean)data.getSerializableExtra(TournamentTypeListActivity.EXTRA_SUBJECT);
            TournamentCollector c = TournamentCollector.get();
            if(subject != null && c != null) {
                c.type.set(subject.title);
            }
        }else if(RequestCode.REQUEST_EDIT_AWARD == requestCode && RESULT_OK == resultCode) {
            List<AwardBean> result = TournamentCollector.get().getAwards();
            StringBuilder sb = new StringBuilder();
            if(result != null) {
                final int count = result.size();
                for (int i = count - 1; i >= 0; i--) {
                    if(sb.length() > 0) {
                        sb.append(" ; ");
                    }
                    sb.append(result.get(i).rankings).append("：")
                            .append(result.get(i).title);
                }
            }
            awards.set(sb.toString());
        }else if(RequestCode.REQUEST_SET_TIME == requestCode && RESULT_OK == resultCode && data != null) {
            TournamentBean tournament = data.getParcelableExtra(PubCustomSetTimeActivity.EXTRA_RESULT);
            if(tournament != null) {
                if(tournament.startImmediate) {
                    TournamentCollector.get().time.set(getString(R.string.start_immediately_default));
                }else {
                    TournamentCollector.get().time.set(tournament.startTime);
                }
                TournamentCollector.get().getTournament().setTimeTheme(tournament);
            }
        }
    }

    /**设置时间*/
    public void setTime(View view){
        TournamentBean tmp = new TournamentBean();
        tmp.setTimeTheme(TournamentCollector.get().getTournament());
        PubCustomSetTimeActivity.openForResult(this, RequestCode.REQUEST_SET_TIME, tmp);
    }

    public void pickCover(View view) {
        SelectImageActivity.show(view.getContext(), new SelectOptions.Builder()
                .setHasCam(true)
                .setCrop(700, 400)
                .setCallback(new SelectOptions.Callback() {
                    @Override
                    public void doSelected(String[] images) {
                        TournamentCollector c = TournamentCollector.get();
                        if(c != null && images != null && images.length > 0) {
                            File tmp = new File(mLocalDir, String.valueOf(System.currentTimeMillis()) + ".jpg");
                            if(new File(images[0]).renameTo(tmp)) {
                                c.localCover.set(tmp.getAbsolutePath());
                            }
                        }else {
                            c.localCover.set("");
                        }

                        mRecommendSubjects.notifyDataChanged();

                    }
                }).build());
    }

    public void removeCover(View view) {
        TournamentCollector c = TournamentCollector.get();
        if(c != null) {
            c.localCover.set("");
        }
    }

    public void pickType(View view) {
        TournamentTypeListActivity.openForResult(this, RequestCode.REQUEST_PICK_TOURNAMENT_TYPE);
    }

    public void setAward(View view) {
        EditAwardActivity.openForResult(this, RequestCode.REQUEST_EDIT_AWARD);
    }

    private class RecommendedSubjectList extends BaseListAdapterController<SubjectBean> {

        public void addHeaderView(View view, Object data, boolean selectable){
            ((HeaderGridView)mRoot).addHeaderView(view, data, selectable);
        }

        @Override
        protected View onGetView(SubjectBean data, int position, View convertView, ViewGroup parent) {
            return super.onGetView(data, position, convertView, parent);
        }

        @Override
        protected int getLayoutId() {
            return 0;
        }
    }

    @Override
    protected void onDestroy() {
        TournamentCollector.release();
        super.onDestroy();
    }

}
