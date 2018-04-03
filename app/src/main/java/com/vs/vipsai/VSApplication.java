package com.vs.vipsai;

import com.vs.vipsai.api.ApiHttpClient;
import com.vs.vipsai.util.PinYin;

/**
 * Author: cynid
 * Created on 3/13/18 3:22 PM
 * Description:
 *
 * 在 manifset 中实际注册类
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

        //初始化拼音组件,用于用户列表分组
        PinYin.init(this);
        PinYin.validate();
    }
}
