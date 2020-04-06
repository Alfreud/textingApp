package com.alfred0ga.texting.addModule.model.dataAccess;

import androidx.annotation.NonNull;

import com.alfred0ga.texting.R;
import com.alfred0ga.texting.addModule.events.AddEvent;
import com.alfred0ga.texting.common.model.BasicEventsCallback;
import com.alfred0ga.texting.common.model.EventsCallback;
import com.alfred0ga.texting.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.alfred0ga.texting.common.pojo.User;
import com.alfred0ga.texting.common.utils.UtilsCommon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase() {
        this.mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void checkUserExist(String email, EventsCallback callback){
        DatabaseReference usersReference = mDatabaseAPI.getRootReference()
                .child(FirebaseRealtimeDatabaseAPI.PATH_USERS);

        Query userByEmailQuery = usersReference.orderByChild(User.EMAIL).equalTo(email).limitToFirst(1);
        userByEmailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    callback.onSuccess();
                }else{
                    callback.onError(AddEvent.ERROR_EXIST, R.string.addFriend_error_user_exist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(AddEvent.ERROR_SERVER, R.string.addFriend_error_message);
            }
        });
    }

    public void checkRequestNotExist(String email, String myUid, EventsCallback callback){
        String emailEncoded = UtilsCommon.getEmailEncoded(email);
        DatabaseReference myRequestReference = mDatabaseAPI.getRequestReference(emailEncoded).child(myUid);

        myRequestReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    callback.onError(AddEvent.ERROR_EXIST, R.string.addFriend_message_request_exist);
                }else{
                    callback.onSuccess();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(AddEvent.ERROR_SERVER, R.string.addFriend_error_message);
            }
        });
    }

    public void addFriend(String email, User myUser, BasicEventsCallback callback){
        Map<String, Object> myUserMap = new HashMap<>();
        myUserMap.put(User.USERNAME, myUser.getUsername());
        myUserMap.put(User.EMAIL, myUser.getEmail());
        myUserMap.put(User.PHOTO_URL, myUser.getPhotoUrl());

        final String emailEncoded = UtilsCommon.getEmailEncoded(email);
        DatabaseReference userReference = mDatabaseAPI.getRequestReference(emailEncoded);
        userReference.child(myUser.getUid()).updateChildren(myUserMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError();
                    }
                });
    }
}
