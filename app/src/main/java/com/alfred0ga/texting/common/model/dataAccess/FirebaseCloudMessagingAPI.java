package com.alfred0ga.texting.common.model.dataAccess;

import androidx.annotation.NonNull;

import com.alfred0ga.texting.common.utils.UtilsCommon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class FirebaseCloudMessagingAPI {
    private FirebaseMessaging mfirebaseMessaging;

    private static class SingletonHolder{
        private static final FirebaseCloudMessagingAPI INSTANCE = new FirebaseCloudMessagingAPI();
    }

    public static FirebaseCloudMessagingAPI getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public FirebaseCloudMessagingAPI() {
        this.mfirebaseMessaging = FirebaseMessaging.getInstance();
    }

    // Metodos
    public void subscribeToMyTopic(String myEmail){
        mfirebaseMessaging.subscribeToTopic(UtilsCommon.getEmailToTopic(myEmail))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            // Reintentar y notificar
                        }
                    }
                });
    }

    public void unsubscribeToMyTopic(String myEmail){
        mfirebaseMessaging.unsubscribeFromTopic(UtilsCommon.getEmailToTopic(myEmail))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            // Reintentar
                        }
                    }
                });
    }
}
