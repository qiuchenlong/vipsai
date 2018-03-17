package com.vs.vipsai.tweet.contract;

import android.content.Context;
import android.os.Bundle;

/**
 * Author: cynid
 * Created on 3/16/18 3:53 PM
 * Description:
 */

public interface TweetPublishContract {

    interface Operator {
        void setDataView(View view, String defaultContent, String[] defaultImages, String localImg); // About.Share about,

        void publish();

        void onBack();

        void loadData();

        void onSaveInstanceState(Bundle outState);

        void onRestoreInstanceState(Bundle savedInstanceState);
    }

    interface View {
        Context getContext();

        String getContent();

        void setContent(String content, boolean needSelectionEnd);

//        void setAbout(About.Share about, boolean needCommit);

        boolean needCommit();

        String[] getImages();

        void setImages(String[] paths);

        void finish();

        Operator getOperator();

        boolean onBackPressed();
    }

}
