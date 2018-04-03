package com.vs.vipsai.publish.fragments;

import com.vs.vipsai.R;
import com.vs.vipsai.base.fragments.BaseFragment;
import com.vs.vipsai.publish.layoutcontroller.InputBarController;
import com.vs.vipsai.search.SearchListener;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Filter;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  带搜索栏fragment
 */
public abstract class SearchFragment<T,Result> extends BaseFragment implements SearchListener<T,Result>{

    private Filter mSearchFilter = null;
//    private boolean mSearchByBtn;
    private InputBarController mInputBarController;

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mInputBarController = InputBarController.wrapper((ViewGroup)root.findViewById(R.id.search_bar_input));
        mInputBarController.setHint(R.string.search);
        mInputBarController.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mInputBarController.addTextWatcher(mTextWatcher);
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {
//            if(mSearchFilter != null && !mSearchByBtn) {
            if(mSearchFilter != null) {
                mSearchFilter.filter(editable.toString().trim());
            }
        }
    };

    /**
     * 设置搜索过滤器
     * @param filter
     */
    public void setSearchFilter(Filter filter) {
        mSearchFilter = filter;
    }

    @Override
    protected void onBindViewBefore(View root) {
        super.onBindViewBefore(root);
        onCreateContent((ViewGroup)root.findViewById(R.id.search_container));
    }

    protected abstract void onCreateContent(ViewGroup parent);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void setSearchResultVisibility(int visibility) {}
}
