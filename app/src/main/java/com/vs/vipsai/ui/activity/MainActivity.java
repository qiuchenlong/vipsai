package com.vs.vipsai.ui.activity;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.vs.mvp.router.Router;
import com.vs.vipsai.OnTabReselectListener;
import com.vs.vipsai.R;
import com.vs.vipsai.ui.fragment.DefaultFragment;
import com.vs.vipsai.ui.nav.NavFragment;
import com.vs.vipsai.ui.nav.NavigationButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: cynid
 * Created on 3/12/18 1:49 PM
 * Description:
 *
 * 主界面
 * @author cyndi
 */

public class MainActivity extends BaseActivity implements NavFragment.OnNavigationReselectListener {

//    @BindView(R.id.tabHost)
//    FragmentTabHost fragmentTabHost;

    @BindView(R.id.activity_main_ui)
    LinearLayout mMainUi;

//    @BindView(R.id.fag_nav)
//    NavFragment mNavBar;

    private NavFragment mNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);

        ButterKnife.bind(this);

        FragmentManager manager = getSupportFragmentManager();
        mNavBar = ((NavFragment) manager.findFragmentById(R.id.fag_nav));
        mNavBar.setup(this, manager, R.id.main_container, this);


//        // init TabHost
//        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.container);
//
//        // delete divider
//        if (Build.VERSION.SDK_INT > 10) {
//            fragmentTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
//        }
//
//        // 获取枚举声明的对象集合
//        MainTab[] values = MainTab.values();
//        for (int i = 0; i < values.length; i++) {
//            MainTab mainTab = values[i];
//
//            Button button = new Button(this);
//            button.setText(mainTab.title);
//
//            if(i == 2){
//                // 当设置第三个选项卡时, 隐藏, 但占着位置
//                button.setVisibility(View.INVISIBLE);
//            }
//
//            // 2. 创建选项卡, 配置选项卡的内容
//            TabHost.TabSpec tab = fragmentTabHost
//                    .newTabSpec(mainTab.title) // 创建
//                    .setIndicator(button);     // 配置
//
//            Bundle args = new Bundle();
//            args.putString("key",mainTab.arg);
//            // 3. 将选项卡添加到TabHost
//            fragmentTabHost.addTab(tab, mainTab.clazz, args);
//        }
//
//        //        TabHost.TabSpec tab = fth.newTabSpec("综合").setIndicator("综合");
////        TabHost.TabSpec tab1 = fth.newTabSpec("动弹").setIndicator("动弹");
////
////        // 3. 将选项卡添加到TabHost
////        Bundle args = new Bundle();
////        args.putString("key","综合的参数");
////        fth.addTab(tab, DefaultFragment.class, args);
////        Bundle args1 = new Bundle();
////        args1.putString("key","动弹的参数");
////        fth.addTab(tab1, DefaultFragment.class, args1);
//
//
//        //        // 内部原理
//        try {
//            DefaultFragment f = DefaultFragment.class.newInstance();
////            f.setArguments(args);
//            FragmentTransaction ft = getSupportFragmentManager()
//                    .beginTransaction();
//
////            List<Fragment> fragments = getSupportFragmentManager().getFragments();
////            fragments.contains()
//
////            ft.add()
////            ft.show()
////            ft.hide()
////            ft.remove()
//
//            ft.replace(R.id.container, f)
//                    .commit();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void initData(Bundle savedInstanceState) {

        checkUpdate();
    }


    private void checkUpdate() {
//        int a = "1234";
//        String a = "";
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public Object newP() {
        return null;
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(MainActivity.class)
                .launch();
    }


    public enum MainTab {
        // 枚举对象声明区域
        News("综合", "综合的参数", DefaultFragment.class),
        Tweet("动弹", "动弹的参数", DefaultFragment.class),
        Add("更多", "更多的参数", DefaultFragment.class),
        Explorer("发现", "发现的参数", DefaultFragment.class),
        Me("我", "我的参数", DefaultFragment.class);

        /**
         * 每个对象包含的 属性/构造/函数 区域
         */
        String title;
        String arg;
        Class<?> clazz;

        MainTab(String title, String arg, Class<?> clazz) {
            this.title = title;
            this.arg = arg;
            this.clazz = clazz;
        }
    }



    @Override
    public void onReselect(NavigationButton navigationButton) {
        Fragment fragment = navigationButton.getFragment();
        if (fragment != null
                && fragment instanceof OnTabReselectListener) {
            OnTabReselectListener listener = (OnTabReselectListener) fragment;
            listener.onTabReselect();
        }
    }

}
