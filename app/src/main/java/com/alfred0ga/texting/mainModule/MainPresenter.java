package com.alfred0ga.texting.mainModule;

import com.alfred0ga.texting.common.pojo.User;
import com.alfred0ga.texting.mainModule.events.MainEvent;

public interface MainPresenter {

    void onCreate();
    void onDestroy();
    void inPause();
    void onResume();

    void signOff();
    User getCurrentUser();
    void removeFriend(String friendUid);

    void acceptRequest(User user);
    void denyRequest(User user);

    void onEventListener(MainEvent evet);

}
