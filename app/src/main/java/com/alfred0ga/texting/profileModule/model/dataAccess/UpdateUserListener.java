package com.alfred0ga.texting.profileModule.model.dataAccess;

public interface UpdateUserListener {

    void onSuccess();
    void onNotifyContacts();
    void onError(int resMsg);

}
