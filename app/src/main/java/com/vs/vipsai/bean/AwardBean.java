package com.vs.vipsai.bean;

import android.text.TextUtils;
import android.view.View;

import java.io.Serializable;

import com.vs.vipsai.publish.viewmodels.VMAwardItem;
import com.vs.vipsai.publish.viewmodels.VMImageItem;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  奖品数据
 */
public class AwardBean extends VMAwardItem implements Serializable{

    /**现金**/
    public static final int TYPE_CASH = 1;
    public static final String TYPE_CASH_STR = "现金";
    /**礼品*/
    public static final int TYPE_PRESENT = 2;
    public static final String TYPE_PRESENT_STR = "礼品";

    protected long id;
    public String title = "";
    public String awardType = "";
    public String description = "";
    protected String[] icons;

    public String rankings = "";
    public String reward;
    private String[] mLocalPics;

    @Override
    protected void onBuildImage(int index, VMImageItem imageItem) {
        super.onBuildImage(index, imageItem);
        if(mLocalPics != null && index < mLocalPics.length) {
            imageItem.localPath.set(mLocalPics[index]);
        }
        if(icons != null && index < icons.length) {
            imageItem.url = icons[index];
        }
    }

    public String[] getIcons() {
        return icons;
    }

    public void setIcons(String[] icons) {
        this.icons = icons;
    }

    public long getId() {return id;}

    public float getRewardValue() {
        if(!TextUtils.isEmpty(reward)) {
            try {
                return Float.valueOf(reward);
            }catch (NumberFormatException e){}
        }

        return 0;
    }

    @Override
    protected void onDelClick(View v, int index) {
        if(icons != null && index >= 0 && index < icons.length) {
            String[] tmp = null;
            if(icons.length > 1) {
                //移除删除项
                tmp = new String[icons.length - 1];
                int j = 0;
                for (int i = 0; i < icons.length; i++) {
                    if (i != index) {
                        tmp[j++] = icons[i];
                    }
                }

            }
            icons = tmp;
        }

        if(mLocalPics != null && index >= 0 && index < mLocalPics.length) {
            String[] tmp = null;
            if(mLocalPics.length > 1) {
                tmp = new String[mLocalPics.length - 1];
                int j = 0;
                for (int i = 0; i < mLocalPics.length; i++) {
                    if (i != index) {
                        tmp[j++] = mLocalPics[i];
                    }
                }
            }
            mLocalPics = tmp;
        }
    }

    public void addLocalPath(int index, String path) {
        int inputPosition = 0;
        if(mLocalPics != null) {
            if(index >= mLocalPics.length) {
                String[] tmp = new String[mLocalPics.length + 1];
                System.arraycopy(mLocalPics, 0, tmp, 0, mLocalPics.length);
                tmp[mLocalPics.length] = path;
                inputPosition = mLocalPics.length;
                mLocalPics = tmp;
            }else {
                inputPosition = index;
            }

        }else {
            mLocalPics = new String[]{path};
        }

        setLocalImage(inputPosition, path);
    }

    /**
     * 数据是否有效，齐全
     * @return true 有效 or false 无效
     */
    public boolean isDataValid() {

        if(TextUtils.isEmpty(rankings)) {
            return false;
        }

        if(TextUtils.isEmpty(awardType)) {
            return false;
        }

        if(TextUtils.isEmpty(title)) {
            return false;
        }

        if(TextUtils.isEmpty(description)) {
            return false;
        }

        return true;
    }

    /**
     * 测试数据
     * @return
     */
    public static String getTestData() {
        return "{\"code\":1,\n" +
                " \"message\":\"success\",\n" +
                " \"notice\":{\n" +
                "    \"newsCount\":35    \n" +
                "},\n" +
                "\"result\":{\"items\":[\n" +
                "   {\"id\":1,\"title\":\"微币：20/10/5\",\"awardType\":\"现金\",\"description\":\"在比赛中获胜即可获得微币奖励。第一名：20微币；第二名：10微币；第三名：5微币。微币可用于超级投票。\",\"icons\":[\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\",\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\", \"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\", \"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"]},\n" +
                "   {\"id\":2,\"title\":\"超级奖金池\",\"awardType\":\"现金\",\"description\":\"所有奖金在同一个奖金池里\",\"icons\":[\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\",\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\", \"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"]},\n" +
                "   {\"id\":3,\"title\":\"文青必备背包\",\"awardType\":\"礼品\",\"description\":\"2018最新款，限量定制背包\",\"icons\":[\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\",\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"]}\n" +
                "]},\n" +
                "\"time\":\"2018-03-14 14:26:37\"}";
    }

    @Override
    public int getIconSize() {
        return icons == null ? 0 : icons.length;
    }

    @Override
    public int getLocalPicSize() {
        return mLocalPics == null ? 0 : mLocalPics.length;
    }

    @Override
    public String getLocalPath(int index) {
        if(mLocalPics != null && index >= 0 && index < mLocalPics.length) {
            return mLocalPics[index];
        }
        return "";
    }

    @Override
    public String getIconUrl(int index) {
        if(icons != null && index >= 0 && index < icons.length) {
            return icons[index];
        }
        return "";
    }

}
