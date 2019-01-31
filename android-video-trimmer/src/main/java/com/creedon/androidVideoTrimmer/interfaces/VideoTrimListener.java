package com.creedon.androidVideoTrimmer.interfaces;


public interface VideoTrimListener {
    void onStartTrim();
    void onFinishTrim(String url);
    void onCancel();
}
