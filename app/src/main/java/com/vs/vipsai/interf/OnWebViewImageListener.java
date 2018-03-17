package com.vs.vipsai.interf;

/**
 * Author: cynid
 * Created on 3/17/18 11:11 AM
 * Description:
 *
 * 监听webview上的图片
 */

public interface OnWebViewImageListener {

    /**
     * 点击webview上的图片，传入该缩略图的大图Url
     *
     * @param bigImageUrl
     */
    void showImagePreview(String bigImageUrl);

}
