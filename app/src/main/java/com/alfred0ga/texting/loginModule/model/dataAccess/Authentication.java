package com.alfred0ga.texting.loginModule.model.dataAccess;

import androidx.annotation.NonNull;

import com.alfred0ga.texting.common.model.dataAccess.FirebaseAuthenticationAPI;
import com.alfred0ga.texting.common.pojo.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication {

    private FirebaseAuthenticationAPI mAuthenticationAPI;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public Authentication(){
        mAuthenticationAPI = FirebaseAuthenticationAPI.getInstance();
    }

    public void onResume(){
        mAuthenticationAPI.getmFirebaseAuth().addAuthStateListener(mAuthStateListener);
    }

    public void onPause(){
        if (mAuthStateListener != null){
            mAuthenticationAPI.getmFirebaseAuth().removeAuthStateListener(mAuthStateListener);
        }
    }

    public void getStatusAutn(StatusAuthCallback callback){
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    callback.onGetUser(user);
                }else {
                    callback.onLaunchUILogin();
                }
            }
        };
    }

    public User gatCurrenUser(){
        return mAuthenticationAPI.getAuthUser();
    }
}
