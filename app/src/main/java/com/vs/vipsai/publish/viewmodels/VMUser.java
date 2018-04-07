package com.vs.vipsai.publish.viewmodels;

import android.databinding.ObservableField;
import android.view.View;

import com.vs.vipsai.R;
import com.vs.vipsai.bean.User;
import com.vs.vipsai.publish.layoutcontroller.ExpandableListViewController;
import com.vs.vipsai.publish.layoutcontroller.UserProfileListController;
import com.vs.vipsai.util.PinYin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  用户的profile model
 */
public class VMUser extends User {

    /**是否显示选择框*/
    public ObservableField<Boolean> picking = new ObservableField<>();

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkbox:
                v.setSelected(!v.isSelected());
                break;
        }
    }

}
