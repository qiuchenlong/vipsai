package com.vs.vipsai.publish.viewmodels;

import android.support.v4.app.FragmentManager;
import android.view.View;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vs.vipsai.AppContext;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.bean.SubTab;
import com.vs.vipsai.cache.CacheManager;

import java.lang.ref.SoftReference;
import java.util.List;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  创建模板比赛- 选择主题
 */
public class VMPublishPickSubject extends VMTabFragment {


    public VMPublishPickSubject(FragmentManager fm, View root) {
        super(fm, root);
    }

    @Override
    protected List<SubTab> getTabConfig() {
        return SubTab.fromAsset(AppContext.getContext(), "tabs_publish_subject.json");
    }

}
