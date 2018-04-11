package com.vs.vipsai.messages.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vs.vipsai.R;

import com.vipsai.imui.chatinput.ChatInputView;
import com.vipsai.imui.chatinput.listener.OnCameraCallbackListener;
import com.vipsai.imui.chatinput.listener.OnClickEditTextListener;
import com.vipsai.imui.chatinput.listener.OnMenuClickListener;
import com.vipsai.imui.chatinput.listener.RecordVoiceListener;
import com.vipsai.imui.chatinput.record.RecordVoiceButton;
import com.vipsai.imui.messages.MessageList;
import com.vipsai.imui.messages.MsgListAdapter;
import com.vipsai.imui.messages.ptr.PtrDefaultHeader;
import com.vipsai.imui.messages.ptr.PullToRefreshLayout;
import com.vipsai.imui.utils.DisplayUtil;

/**
 * Author: cynid
 * Created on 4/10/18 4:40 PM
 * Description:
 */

public class ChatView extends RelativeLayout {

    private TextView mTitle;
    private LinearLayout mTitleContainer;
    private MessageList mMsgList;
    private ChatInputView mChatInput;
    private RecordVoiceButton mRecordVoiceBtn;
    private PullToRefreshLayout mPtrLayout;
    private ImageButton mSelectAlbumIb;

    public ChatView(Context context) {
        super(context);
    }

    public ChatView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initModule() {
//        mTitleContainer = (LinearLayout) findViewById(R.id.title_container);
//        mTitle = (TextView) findViewById(R.id.title_tv);
        mMsgList = (MessageList) findViewById(R.id.msg_list);
        mChatInput = (ChatInputView) findViewById(R.id.chat_input);
        mPtrLayout = (PullToRefreshLayout) findViewById(R.id.pull_to_refresh_layout);

        /**
         * Should set menu container height once the ChatInputView has been initialized.
         * For perfect display, the height should be equals with soft input height.
         */
        mChatInput.setMenuContainerHeight(819);
        mRecordVoiceBtn = mChatInput.getRecordVoiceButton();
        mSelectAlbumIb = mChatInput.getSelectAlbumBtn();
        PtrDefaultHeader header = new PtrDefaultHeader(getContext());
//        int[] colors = getResources().getIntArray(R.array.google_colors);
//        header.setColorSchemeColors(colors);
        header.setLayoutParams(new LayoutParams(-1, -2));
        header.setPadding(0, DisplayUtil.dp2px(getContext(),15), 0,
                DisplayUtil.dp2px(getContext(),10));
        header.setPtrFrameLayout(mPtrLayout);
//        mMsgList.setDateBgColor(Color.parseColor("#FF4081"));
//        mMsgList.setDatePadding(5, 10, 10, 5);
//        mMsgList.setEventTextPadding(5);
//        mMsgList.setEventBgColor(Color.parseColor("#34A350"));
//        mMsgList.setDateBgCornerRadius(15);
        mMsgList.setHasFixedSize(true);
        mPtrLayout.setLoadingMinTime(1000);
        mPtrLayout.setDurationToCloseHeader(1500);
        mPtrLayout.setHeaderView(header);
        mPtrLayout.addPtrUIHandler(header);
        // ä¸‹æ‹‰åˆ·æ–°æ—¶ï¼Œå†…å®¹å›ºå®šï¼Œåªæœ‰ Header å˜åŒ–
        mPtrLayout.setPinContent(true);
        // set show display name or not
//        mMsgList.setShowReceiverDisplayName(true);
//        mMsgList.setShowSenderDisplayName(false);
    }

    public PullToRefreshLayout getPtrLayout() {
        return mPtrLayout;
    }

    public void setTitle(String title) {
//        mTitle.setText(title);
    }

    public void setMenuClickListener(OnMenuClickListener listener) {
        mChatInput.setMenuClickListener(listener);
    }

    public void setAdapter(MsgListAdapter adapter) {
        mMsgList.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mMsgList.setLayoutManager(layoutManager);
    }

    public void setRecordVoiceFile(String path, String fileName) {
        mRecordVoiceBtn.setVoiceFilePath(path, fileName);
    }

    public void setCameraCaptureFile(String path, String fileName) {
        mChatInput.setCameraCaptureFile(path, fileName);
    }

    public void setRecordVoiceListener(RecordVoiceListener listener) {
        mChatInput.setRecordVoiceListener(listener);
    }

    public void setOnCameraCallbackListener(OnCameraCallbackListener listener) {
        mChatInput.setOnCameraCallbackListener(listener);
    }

    @Override
    public void setOnTouchListener(OnTouchListener listener) {
        mMsgList.setOnTouchListener(listener);
    }

    public void setOnTouchEditTextListener(OnClickEditTextListener listener) {
        mChatInput.setOnClickEditTextListener(listener);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    public ChatInputView getChatInputView() {
        return mChatInput;
    }

    public MessageList getMessageListView() {
        return mMsgList;
    }

    public ImageButton getSelectAlbumBtn() {
        return this.mSelectAlbumIb;
    }
}
