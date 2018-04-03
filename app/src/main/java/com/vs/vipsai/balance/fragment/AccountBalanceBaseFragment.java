package com.vs.vipsai.balance.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.vs.vipsai.R;
import com.vs.vipsai.base.fragments.BaseFragment;
import com.vs.vipsai.util.SimplexToast;

import java.lang.reflect.Method;

/**
 * Author: cynid
 * Created on 4/2/18 2:33 PM
 * Description:
 */

public class AccountBalanceBaseFragment extends BaseFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }


    @Override
    public void initView(View view) {
        super.initView(view);


    }

//    /**
//     * 创建菜单
//     *
//     * @param menu
//     * @param inflater
//     */
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        if (menu != null) {
//            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
//                try {
//                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
//                    method.setAccessible(true);
//                    method.invoke(menu, true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        inflater.inflate(R.menu.save_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        MenuItem itemSave = menu.findItem(R.id.public_menu_shared);
//        itemSave.setTitle(Html.fromHtml("<font size='2px' color='#48AAFB'>" + getMenuTitle() + "</font>"));
////        if (TextUtils.isEmpty(etMobile.getText())) {
////            itemSubmit.setEnabled(false);
////            itemSubmit.setTitle(Html.fromHtml("<font color='#666666'>完成</font>"));
////        } else {
////            itemSubmit.setEnabled(true);
////            itemSubmit.setTitle(Html.fromHtml("<font color='#5CC31F'>完成</font>"));
////        }
//
//        super.onPrepareOptionsMenu(menu);
//    }
//
//    /**
//     * 菜单按钮点击事件
//     *
//     * @param item
//     * @return
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.public_menu_shared:
//                SimplexToast.show(getContext(), "public_menu_shared");
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//
//    private String titleName = "";
//
//    protected void setMenuTitle(String title) {
//        titleName = title;
//    }
//
//    protected String getMenuTitle() {
//        return titleName;
//    }

}
