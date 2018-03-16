package com.vs.vipsai;

import com.vs.vipsai.api.ApiHttpClient;

/**
 * Author: cynid
 * Created on 3/13/18 3:22 PM
 * Description:
 */

public class VSApplication extends AppContext {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {

        // 初始化网络请求
        ApiHttpClient.init(this);
    }
}
