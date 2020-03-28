package com.alfred0ga.texting.profileModule.view;

public interface ProfoleView {

    void enableUIElements();
    void desableUIElements();

    void showProgress();
    void hideProgress();
    void showProgressImage();
    void hideProgressImage();

    void showUserData(String username, String email, String photoUrl):
    void launchGallery();
    void openDialogPreview();

    void menuEditMode();
    void menuNormalMode();

    void saveUsernameSucces();
    void updateImageSuccess(String photoUrl);
    void setResutOK(String username, String photoUrl);

    void onErrorUpload(int resMsg);
    void onError(int resMsg);

}