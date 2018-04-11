package com.vs.vipsai.util;

import android.text.Html;
import android.text.Spanned;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: cynid
 * Created on 3/30/18 4:41 PM
 * Description:
 *
 * 文本解析类
 */

public class TextUtil {

    private static final String KeyWordPattern = "(^|)@(.*?)($|\\s)|#(\\w*?)#|(^|)#(.*?)($|\\s)";


    /**
     * 正则匹配@#关键字，并添加font标签
     *
     * @param source
     * @return
     */
    public static Spanned tf_keyword(String source) {
        Pattern p = Pattern.compile(KeyWordPattern, Pattern.CASE_INSENSITIVE); // 正则匹配大小写
        Matcher m = p.matcher(source);

        List<String> resultArray = new ArrayList<>();

        while (m.find()) {
            resultArray.add(m.group().trim()); // 去除空格
        }

        // 添加font标记
        for (String r: resultArray) {
            source = source.replace(r, "<font color='#48AAFB'>" + r + "</font>");
        }

        // 去重font标记
        source = source.replaceAll("(<font color='#48AAFB'>){2,}", "<font color='#48AAFB'>");
        source = source.replaceAll("(</font>){2,}", "</font>");

        return Html.fromHtml(source);
    }


}

