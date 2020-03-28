package com.alfred0ga.texting.chatModule.view;

import android.content.Intent;

import com.alfred0ga.texting.common.pojo.Message;

public interface ChatView {
    void showProgress();
    void hideProgress();

    void onStatusUser(boolean connected, long lastConnection);

    void onError(int resMsg);

    void onMessageReceived(Message msg);

    void openDialogPreview(Intent data);
}
