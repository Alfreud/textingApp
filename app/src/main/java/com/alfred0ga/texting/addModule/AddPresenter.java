package com.alfred0ga.texting.addModule;

import com.alfred0ga.texting.addModule.events.AddEvent;

public interface AddPresenter {

    void onShow();
    void onDestroy();

    void AddFriend(String email);
    void onEventListener(AddEvent event);

}
