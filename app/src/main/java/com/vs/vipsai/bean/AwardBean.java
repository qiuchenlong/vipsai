package com.vs.vipsai.bean;

import java.io.Serializable;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  奖品数据
 */
public class AwardBean implements Serializable{

    /**现金**/
    public static final int TYPE_CASH = 1;
    /**礼品*/
    public static final int TYPE_PRESENT = 2;

    protected long id;
    public String title;
    protected int awardType;
    public String description;
    protected String[] icons;

    public int getAwardType() {
        return awardType;
    }

    public void setAwardType(int awardType) {
        this.awardType = awardType;
    }

    public String[] getIcons() {
        return icons;
    }

    public void setIcons(String[] icons) {
        this.icons = icons;
    }

    public long getId() {return id;}

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
                "   {\"id\":1,\"title\":\"微币：20/10/5\",\"awardType\":1,\"description\":\"在比赛中获胜即可获得微币奖励。第一名：20微币；第二名：10微币；第三名：5微币。微币可用于超级投票。\",\"icons\":[\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\",\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\", \"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\", \"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"]},\n" +
                "   {\"id\":2,\"title\":\"超级奖金池\",\"awardType\":1,\"description\":\"所有奖金在同一个奖金池里\",\"icons\":[\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\",\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\", \"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"]},\n" +
                "   {\"id\":3,\"title\":\"文青必备背包\",\"awardType\":2,\"description\":\"2018最新款，限量定制背包\",\"icons\":[\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\",\"http://img0.imgtn.bdimg.com/it/u=52803214,3583596752&fm=27&gp=0.jpg\"]}\n" +
                "]},\n" +
                "\"time\":\"2018-03-14 14:26:37\"}";
    }
}
