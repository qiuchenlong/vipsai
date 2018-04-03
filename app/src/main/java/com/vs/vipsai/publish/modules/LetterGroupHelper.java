package com.vs.vipsai.publish.modules;

import com.vs.vipsai.bean.User;
import com.vs.vipsai.publish.layoutcontroller.ExpandableListViewController;
import com.vs.vipsai.publish.layoutcontroller.UserProfileListController;
import com.vs.vipsai.publish.viewmodels.VMUser;
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
 *  对表按拼音分组帮助类
 */
public class LetterGroupHelper {

    public static List<UserProfileListController.UserProfile> buildLetterGroupForUser(List<VMUser> from) {
        List<UserProfileListController.UserProfile> result = new ArrayList<UserProfileListController.UserProfile>();
        HashMap<String, UserProfileListController.UserProfile> tmp = new HashMap<>();
        for (VMUser user : from) {

            String letter = PinYin.getPinYin(user.getName()).substring(0, 1).toUpperCase();
            UserProfileListController.UserProfile index = tmp.get(letter);
            if(index == null) {
                index = new UserProfileListController.UserProfile();
                index.setPinyin(letter);
                tmp.put(letter, index);
            }
            index.appendChild(user);
        }

        result.addAll(tmp.values());

        Collections.sort(result, new Comparator<UserProfileListController.UserProfile>() {
            @Override
            public int compare(UserProfileListController.UserProfile beanUserInfo, UserProfileListController.UserProfile t1) {
                if(beanUserInfo != null && beanUserInfo.getPinyin() != null && t1 != null && t1.getPinyin() != null) {
                    return beanUserInfo.getPinyin().compareToIgnoreCase(t1.getPinyin());
                }
                return 0;
            }
        });

        return result;
    }
}
