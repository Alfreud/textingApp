package com.alfred0ga.texting.addModule.view;

public interface AddView {
    void enableUIElements();
    void disableUIElements();
    void showProgress();
    void hideProgress();

    void friendAdded();
    void friendNotAdded();
}
