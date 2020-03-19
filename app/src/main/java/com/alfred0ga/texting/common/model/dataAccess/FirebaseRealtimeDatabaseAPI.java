package com.alfred0ga.texting.common.model.dataAccess;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FirebaseRealtimeDatabaseAPI {

    public static final String SEPARATOR = "___&___";
    public static final String PATH_USERS = "users";
    public static final String PATH_CONTACTS = "contacts";
    public static final String PATH_REQUESTS = "requests";

    private DatabaseReference mDatabaseReference;



    private static class SingletonHolder{
        private static final FirebaseRealtimeDatabaseAPI INSTANCE = new FirebaseRealtimeDatabaseAPI();

    }
    public static FirebaseRealtimeDatabaseAPI getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private FirebaseRealtimeDatabaseAPI(){
        this.mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    /*
    *  References
    * */

    public DatabaseReference getRootReference(){
        return mDatabaseReference.getRoot();
    }

    public DatabaseReference getUserReferenceByUid(String uid){
        //return getRootReference().child(PATH_USERS).child(uid);
        return getRootReference().child(PATH_USERS);
    }

    public DatabaseReference getContactsReference(String uid) {
        return getUserReferenceByUid(uid).child(PATH_CONTACTS);
    }

    public DatabaseReference getRequestReference(String email) {
        return getRootReference().child(PATH_REQUESTS);
    }

}
