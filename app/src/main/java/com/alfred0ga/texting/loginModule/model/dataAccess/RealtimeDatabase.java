package com.alfred0ga.texting.loginModule.model.dataAccess;

import androidx.annotation.NonNull;

import com.alfred0ga.texting.R;
import com.alfred0ga.texting.common.model.EventErrorTypeListener;
import com.alfred0ga.texting.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.alfred0ga.texting.common.pojo.User;
import com.alfred0ga.texting.loginModule.events.LoginEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void registerUser(User user){
        Map<String, Object> values = new HashMap<>();
        values.put(User.USERNAME, user.getUsername());
        values.put(User.EMAIL, user.getEmail());
        values.put(User.PHOTO_URL, user.getPhotoUrl());

        mDatabaseAPI.getUserReferenceByUid(user.getUid()).updateChildren(values);
    }

    public void checkUserExist(String uid, EventErrorTypeListener listener){
        mDatabaseAPI.getUserReferenceByUid(uid).child(User.EMAIL)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){
                            listener.onError(LoginEvent.USER_NOT_EXIST, R.string.login_error_user_exist);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        listener.onError(LoginEvent.ERROR_SERVER, R.string.login_message_error);
                    }
                });
    }

}
