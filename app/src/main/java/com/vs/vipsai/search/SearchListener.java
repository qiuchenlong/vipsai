package com.vs.vipsai.search;

import java.util.List;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  数据搜索监听器
 */
public interface SearchListener<T,Result> {

    void onPreSearch();

    /**
     * 搜索结果
     * @param resultData
     */
    void onSearchComplete(List<Result> resultData);
    /**设置搜索结果是否可见*/
    void setSearchResultVisibility(int visibility);

    String matchString(T item);
}
