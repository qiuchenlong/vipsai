package com.vs.vipsai.publish.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.R;
import com.vs.vipsai.bean.PageBean;
import com.vs.vipsai.bean.ResultBean;
import com.vs.vipsai.publish.TournamentCollector;
import com.vs.vipsai.publish.layoutcontroller.ExpandableListViewController;
import com.vs.vipsai.publish.layoutcontroller.UserProfileListController;
import com.vs.vipsai.publish.modules.LetterGroupHelper;
import com.vs.vipsai.publish.viewmodels.VMUser;
import com.vs.vipsai.search.UserProfileSearchFilter;
import com.vs.vipsai.util.TDevice;

import java.util.List;

import butterknife.BindView;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  创建样板比赛 - 选择参赛者
 */
public class UserListFragment extends SearchFragment<VMUser, UserProfileListController.UserProfile> implements
                            VMUser.OnCheckedListener{

    private ExpandableListViewController mViewController;
    private UserProfileSearchFilter mSearchFilter;

    @BindView(R.id.search_bar_btn)
    public TextView mCountTip;

    @Override
    protected void onCreateContent(ViewGroup parent) {
        UserProfileListController viewController = new UserProfileListController()
                                .attachTo(parent, true);
        viewController.setPickMode(true, this);
        mViewController = viewController;
    }

    @Override
    public void onCheckedChange(VMUser user, boolean selected) {
        TournamentCollector c = TournamentCollector.get();
        if(c != null) {
            c.changedPlayer(user, selected);
        }

        showSelectedCountTip(c.getUserCount(), mViewController.getTotalChildCount());
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        //增加选择数量提示
        mCountTip.setTextColor(TDevice.getColor(getResources(), android.R.color.holo_red_dark));
    }

    private void showSelectedCountTip(int selected, int total) {
        if(total <= 0) {
            mCountTip.setVisibility(View.GONE);
        }else {
            mCountTip.setVisibility(View.VISIBLE);
            mCountTip.setText(getString(R.string.num_percent, selected, total));
        }
    }

    @Override
    public void initData() {
        super.initData();
        mSearchFilter = new UserProfileSearchFilter(getContext(), this);
        setSearchFilter(mSearchFilter);

        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {

                //测试
                ResultBean<PageBean<VMUser>> resultBean = AppOperator.createGson().fromJson(UserProfileListController.testData(),
                        new TypeToken<ResultBean<PageBean<VMUser>>>() {}.getType());

                List<VMUser> users = resultBean.getResult().getItems();

                mSearchFilter.reset(users);

                final List<UserProfileListController.UserProfile> profiles =LetterGroupHelper.buildLetterGroupForUser(users);

                AppOperator.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mViewController.setData(profiles, true);
                    }
                });

            }
        });

    }

    //////////////  SearchListener  ////////////////////////////////
    @Override
    public void onPreSearch() {}

    @Override
    public void onSearchComplete(List<UserProfileListController.UserProfile> resultData) {
        mViewController.setData(resultData, true);
    }

    @Override
    public String matchString(VMUser item) {
        StringBuilder sb = new StringBuilder("");
        sb.append(item.getName());
        return sb.toString();
    }
}
