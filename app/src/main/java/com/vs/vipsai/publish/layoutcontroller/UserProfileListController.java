package com.vs.vipsai.publish.layoutcontroller;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vs.vipsai.BR;
import com.vs.vipsai.R;
import com.vs.vipsai.bean.User;
import com.vs.vipsai.publish.viewmodels.VMUser;
import com.vs.vipsai.util.PinYin;
import com.vs.vipsai.util.TDevice;

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
 *  用户列表控制器
 */
public class UserProfileListController extends ExpandableListViewController<UserProfileListController.UserProfile>{

    private boolean mPicking;

    @Override
    protected void init(Context context) {
        super.init(context);
        mLetterIndex.setTextColor(TDevice.getColor(context.getResources(), R.color.gray));
    }

    @Override
    public void setData(List<UserProfile> datas, boolean expandAll) {
        mLetterIndex.setIndexLetter(null);
        if(datas != null) {
            String[] indexMark = new String[datas.size()];
            for(int i = 0; i < indexMark.length; i++) {
                indexMark[i] = datas.get(i).getPinyin();
            }
            mLetterIndex.setIndexLetter(indexMark);
        }
        super.setData(datas, expandAll);
    }

    public void setPicking(boolean picking) {
        mPicking = picking;
    }

    @Override
    protected View onGetGroupView(UserProfile group, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null) {
            TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.letter_item, parent, false);
            convertView = textView;
        }
        ((TextView)convertView).setText(group.getPinyin());
        return convertView;
    }

    @Override
    protected View onGetChildView(Object child, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        VMUser user = (VMUser)child;
        if(convertView == null) {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.list_item_user_profile, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }
        user.picking.set(mPicking);
        ViewDataBinding binding = (ViewDataBinding)convertView.getTag();
        binding.setVariable(BR.user, user);
        binding.executePendingBindings();

        return convertView;
    }

    public static class UserProfile extends ExpandableListViewController.ItemData<VMUser> {
        private String mPinyin;

        public void setPinyin(String pinyin) {
            mPinyin = pinyin;
        }

        public String getPinyin() {
            return mPinyin;
        }

    }

    public static String testData() {
        return "{\n" +
                "\"code\":1,\"message\":\"success\",\"result\":{\"items\":[\n" +
                "{\"id\":1,\"name\":\"安妮宝贝\",\"portrait\":\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"},\n" +
                "{\"id\":2,\"name\":\"宝而爷\",\"portrait\":\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"},\n" +
                "{\"id\":3,\"name\":\"鲍二家的\",\"portrait\":\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"},\n" +
                "{\"id\":4,\"name\":\"卜世仁\",\"portrait\":\"http://img0.imgtn.bdimg.com/it/u=893407299,3791776195&fm=200&gp=0.jpg\"},\n" +
                "{\"id\":5,\"name\":\"范冰禀\",\"portrait\":\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"},\n" +
                "{\"id\":6,\"name\":\"冯紫英\",\"portrait\":\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"},\n" +
                "{\"id\":7,\"name\":\"航天飞机\",\"portrait\":\"http://img0.imgtn.bdimg.com/it/u=893407299,3791776195&fm=200&gp=0.jpg\"},\n" +
                "{\"id\":8,\"name\":\"恍恍惚惚\",\"portrait\":\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"},\n" +
                "{\"id\":8,\"name\":\"沐亦阳\",\"portrait\":\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"},\n" +
                "{\"id\":9,\"name\":\"邓家佳\",\"portrait\":\"http://img0.imgtn.bdimg.com/it/u=893407299,3791776195&fm=200&gp=0.jpg\"},\n" +
                "{\"id\":10,\"name\":\"张爱华\",\"portrait\":\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"},\n" +
                "{\"id\":11,\"name\":\"李维斯\",\"portrait\":\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"},\n" +
                "{\"id\":12,\"name\":\"锦相侯\",\"portrait\":\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"}\n" +
                "]},\n" +
                " \"time\":\"2018-04-01 14:23:03\"\n" +
                "}";
    }
}
