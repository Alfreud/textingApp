package com.alfred0ga.texting.mainModule.model.dataAcces;

import com.alfred0ga.texting.common.pojo.User;

public interface UserEventListener {

    void onUserAdded(User user);
    void onUserUpdated(User user);
    void onUserRemoved(User user);

    void onError(int resMsg);

}
