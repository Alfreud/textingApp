package com.alfred0ga.texting.chatModule.model;

import com.alfred0ga.texting.common.pojo.Message;

public interface MessagesEventListener {
    void onMessageReceived(Message message);
    void onError(int resMsg);
}
