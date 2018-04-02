package com.vs.vipsai.search;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Filter;

import com.vs.vipsai.publish.layoutcontroller.UserProfileListController;
import com.vs.vipsai.publish.modules.LetterGroupHelper;
import com.vs.vipsai.publish.viewmodels.VMUser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  用户搜索帮助类
 *  T 要比较的类
 *  Result 结果类
 */
public class UserProfileSearchFilter extends Filter{

    protected List<VMUser> mOrigin = new ArrayList<>();
    protected List<VMUser> mResult = new ArrayList<>();
    protected SearchListener<VMUser,UserProfileListController.UserProfile> mResultHandler;
    protected Context mAppContext;
    protected Handler mHandler = new Handler(Looper.getMainLooper());

    private boolean mStandalone;

    public UserProfileSearchFilter(Context context, SearchListener<VMUser,UserProfileListController.UserProfile> listener) {
        mResultHandler = listener;
        mAppContext = context.getApplicationContext();
    }

    /**
     * 单独展示模式，有搜索是显示列表，否则隐藏；初始化时隐藏
     * @param value
     */
    public void standalone(boolean value) {
        mStandalone = value;
        if(value) {
            mResultHandler.setSearchResultVisibility(View.GONE);
        }
    }

    /**
     * 充值数据源
     * @param searchFrom
     */
    public void reset(List<VMUser> searchFrom) {
        mOrigin.clear();
        if(searchFrom != null) {
            mOrigin.addAll(searchFrom);
        }
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults result = new FilterResults();
        if(mOrigin.size() > 0) {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResultHandler.onPreSearch();
                }
            });

            mResult.clear();
            if (charSequence.toString().trim().length() > 0) {
                Pattern p = Pattern.compile(".*" + charSequence.toString().trim() + ".*");

                for (VMUser user : mOrigin) {
                    Matcher m = p.matcher(mResultHandler.matchString(user));
                    if(m.find()) {
                        mResult.add(user);
                    }
                }

            }else {
                mResult.addAll(mOrigin);
            }

            result.values = onSearchResult(mResult);
        }
        return result;
    }

    protected List<UserProfileListController.UserProfile> onSearchResult(List<VMUser> result) {
        return LetterGroupHelper.buildLetterGroupForUser(result);
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        List<UserProfileListController.UserProfile> result = (List<UserProfileListController.UserProfile>)filterResults.values;
        mResultHandler.onSearchComplete(result);
        mResultHandler.setSearchResultVisibility(mStandalone && charSequence.length() <= 0 ? View.GONE : View.VISIBLE);
    }
}
