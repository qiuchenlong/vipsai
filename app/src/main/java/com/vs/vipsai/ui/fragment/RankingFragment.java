package com.vs.vipsai.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.vs.vipsai.R;
import com.vs.vipsai.base.fragments.BaseFragment;
import com.vs.vipsai.ui.SimpleBackActivity;
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.util.UIHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: cynid
 * Created on 4/2/18 9:24 AM
 * Description:
 *
 * 排行榜
 */

public class RankingFragment extends BaseFragment {

    @BindView(R.id.fragment_ranking_recyclerview)
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置标题居中
        SimpleBackActivity activity = (SimpleBackActivity) getActivity();
        activity.setTitleCenter(this);
        activity.setTitleColorByColorId(Color.argb(255, 51, 51, 51));

        activity.findViewById(R.id.toolbar).setBackgroundColor(Color.TRANSPARENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.view_root);
//        linearLayout.setBackgroundResource(R.mipmap.image_no_2);

        activity.findViewById(R.id.container).setBackgroundColor(Color.TRANSPARENT);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            activity.getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }


        setHasOptionsMenu(true); //添加菜单不调用该方法是没有用的
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void initView(View view) {
        super.initView(view);

        List<Map<String, String>> list = new ArrayList<>();
        for (int i=0 ; i<7; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", "aaaa" + i);
            list.add(map);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RankingAdapter(getContext(), list));
    }

    /**
     * 创建菜单
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        inflater.inflate(R.menu.save_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem itemSave = menu.findItem(R.id.public_menu_shared);
        itemSave.setTitle(Html.fromHtml("<font color='#999999'>规则</font>"));
        super.onPrepareOptionsMenu(menu);
    }

    /**
     * 菜单按钮点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.public_menu_shared:
                UIHelper.showRankingRuleFingerprint(getContext());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    class RankingAdapter extends RecyclerView.Adapter {

        private Context mContext;
        private List<Map<String, String>> mItem;

        public RankingAdapter(Context context, List<Map<String, String>> list) {
            this.mContext = context;
            this.mItem = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_ranking, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mItem.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
