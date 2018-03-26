package com.vs.vipsai.emoji;

import android.content.Context;
import android.widget.EditText;

import com.vs.vipsai.face.FacePanelView;
import com.vs.vipsai.util.TDevice;
import com.vs.vipsai.widget.face.FaceRecyclerView;

import static com.vs.vipsai.BaseApplication.getContext;

/**
 * Author: cynid
 * Created on 3/26/18 5:55 PM
 * Description:
 */

public class EmojiView extends FacePanelView {
    private EditText mEditText;

    public EmojiView(Context context, EditText editText) {
        super(context);
        this.mEditText = editText;
        setListener(new FacePanelView.FacePanelListener() {
            @Override
            public void onDeleteClick() {
                InputHelper.backspace(mEditText);
            }

            @Override
            public void hideSoftKeyboard() {
                TDevice.hideSoftKeyboard(mEditText);
            }

            @Override
            public void onFaceClick(Emojicon v) {
                InputHelper.input2OSC(mEditText, v);
            }
        });
    }

    @Override
    protected FaceRecyclerView createRecyclerView() {
        return new EmojiRecyclerView(getContext(), this);
    }
}
