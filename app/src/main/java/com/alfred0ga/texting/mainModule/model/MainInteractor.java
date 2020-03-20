package com.alfred0ga.texting.mainModule.model;

import com.alfred0ga.texting.common.pojo.User;

public interface MainInteractor {

    void suscribeToUserList();
    void unsuscribeToUserList();

    void signOff();

    User getCurrentUser();
    void removeFriend(String friendUid);

    void acceptRequest(User user);
    void denyRequest(User user);

}
