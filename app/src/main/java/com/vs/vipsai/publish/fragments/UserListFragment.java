package com.vs.vipsai.publish.fragments;

import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.bean.PageBean;
import com.vs.vipsai.bean.ResultBean;
import com.vs.vipsai.publish.layoutcontroller.ExpandableListViewController;
import com.vs.vipsai.publish.layoutcontroller.UserProfileListController;
import com.vs.vipsai.publish.modules.LetterGroupHelper;
import com.vs.vipsai.publish.viewmodels.VMUser;
import com.vs.vipsai.search.UserProfileSearchFilter;

import java.util.List;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  创建样板比赛 - 选择参赛者
 */
public class UserListFragment extends SearchFragment<VMUser, UserProfileListController.UserProfile> {

    private ExpandableListViewController mViewController;
    private UserProfileSearchFilter mSearchFilter;

    @Override
    protected void onCreateContent(ViewGroup parent) {
        UserProfileListController viewController = new UserProfileListController()
                                .attachTo(parent, true);
        viewController.setPicking(true);
        mViewController = viewController;
    }

    @Override
    public void initData() {
        super.initData();
        mSearchFilter = new UserProfileSearchFilter(getContext(), this);
        setSearchFilter(mSearchFilter);

        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
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
