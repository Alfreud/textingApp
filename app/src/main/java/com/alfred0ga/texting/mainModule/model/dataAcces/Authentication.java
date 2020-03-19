package com.alfred0ga.texting.mainModule.model.dataAcces;

import com.alfred0ga.texting.common.model.dataAccess.FirebaseAuthenticationAPI;

public class Authentication {

    private FirebaseAuthenticationAPI mAuthenticationAPI;

    public Authentication() {
        mAuthenticationAPI = FirebaseAuthenticationAPI.getInstance();
    }

    public FirebaseAuthenticationAPI getmAuthenticationAPI() {
        return mAuthenticationAPI;
    }

    public void signOff(){
        mAuthenticationAPI.getmFirebaseAuth().signOut();
    }
}
