package com.alfred0ga.texting.profileModule.model;

import android.app.Activity;
import android.net.Uri;

public interface ProfileInteractor {

    void updateUsername(String username);
    void updateImage(Uri uri, String oldPhotoUrl);
}
