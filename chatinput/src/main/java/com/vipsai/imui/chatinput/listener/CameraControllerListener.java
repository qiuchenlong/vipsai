package com.vipsai.imui.chatinput.listener;

public interface CameraControllerListener {
    void onFullScreenClick();
    void onRecoverScreenClick();
    void onCloseCameraClick();
    void onSwitchCameraModeClick(boolean isRecordVideoMode);
}
