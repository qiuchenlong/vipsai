package com.vipsai.imui.chatinput.camera;


import com.vipsai.imui.chatinput.listener.CameraEventListener;
import com.vipsai.imui.chatinput.listener.OnCameraCallbackListener;


public interface CameraSupport {
    CameraSupport open(int cameraId, int width, int height, boolean isFacingBack);
    void release();
    void takePicture();
    void setCameraCallbackListener(OnCameraCallbackListener listener);
    void setCameraEventListener(CameraEventListener listener);
    void startRecordingVideo();
    void cancelRecordingVideo();
    String finishRecordingVideo();
}
