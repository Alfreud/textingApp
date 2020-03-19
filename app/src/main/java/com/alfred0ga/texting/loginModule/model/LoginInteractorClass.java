package com.alfred0ga.texting.loginModule.model;

import com.alfred0ga.texting.common.model.EventErrorTypeListener;
import com.alfred0ga.texting.common.pojo.User;
import com.alfred0ga.texting.loginModule.events.LoginEvent;
import com.alfred0ga.texting.loginModule.model.dataAccess.Authentication;
import com.alfred0ga.texting.loginModule.model.dataAccess.RealtimeDatabase;
import com.alfred0ga.texting.loginModule.model.dataAccess.StatusAuthCallback;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;

public class LoginInteractorClass implements LoginInteractor {

    private Authentication mAuthentication;
    private RealtimeDatabase mDatabase;

    public LoginInteractorClass() {
        mAuthentication = new Authentication();
        mDatabase = new RealtimeDatabase();
    }

    @Override
    public void onResume() {
        mAuthentication.onResume();
    }

    @Override
    public void onPause() {
        mAuthentication.onPause();
    }

    @Override
    public void getStatusAuth() {
        mAuthentication.getStatusAutn(new StatusAuthCallback() {
            @Override
            public void onGetUser(FirebaseUser user) {
                post(LoginEvent.STATUS_AUTH_SUCCESS, user);
            }

            @Override
            public void onLaunchUILogin() {
                post(LoginEvent.STATUS_AUTH_ERROR);
                mDatabase.checkUserExist(mAuthentication.gatCurrenUser().getUid(), new EventErrorTypeListener() {
                    @Override
                    public void onError(int typeEvent, int resMsg) {
                        if(typeEvent == LoginEvent.USER_NOT_EXIST){
                            registerUser();
                        }else {
                            post(typeEvent);
                        }
                    }
                });
            }
        });
    }

    private void registerUser() {
        User currentUser = mAuthentication.gatCurrenUser();
        mDatabase.registerUser(currentUser);
    }

    private void post(int typeEvent) {
        post(typeEvent, null);
    }

    private void post(int  typeEvent, FirebaseUser user) {
        LoginEvent event = new LoginEvent();
        event.setTypeEvent(typeEvent);
        event.setUser(user);
        EventBus.getDefault().post(event);
    }
}
