package com.alfred0ga.texting.loginModule.model.dataAccess;

import com.google.firebase.auth.FirebaseUser;

public interface StatusAuthCallback {
    void onGetUser(FirebaseUser user);
    void onLaunchUILogin();
}
