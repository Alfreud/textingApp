package com.alfred0ga.texting.loginModule.view;

import android.content.Intent;

public interface LoginView {

    void showProgress();
    void hideProgress();

    void openMainActivity();
    void openUILogin();

    void showLoginSuccessdully(Intent data);
    void showMessageStarting();
    void showError(int resMsg);

}
