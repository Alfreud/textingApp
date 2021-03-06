package com.alfred0ga.texting.mainModule.model;

import android.provider.SyncStateContract;

import com.alfred0ga.texting.common.Constants;
import com.alfred0ga.texting.common.model.BasicEventsCallback;
import com.alfred0ga.texting.common.model.dataAccess.FirebaseCloudMessagingAPI;
import com.alfred0ga.texting.common.pojo.User;
import com.alfred0ga.texting.mainModule.events.MainEvent;
import com.alfred0ga.texting.mainModule.model.dataAcces.Authentication;
import com.alfred0ga.texting.mainModule.model.dataAcces.RealtimeDatabase;
import com.alfred0ga.texting.mainModule.model.dataAcces.UserEventListener;

import org.greenrobot.eventbus.EventBus;

public class MainInteractorClass implements MainInteractor {
    private RealtimeDatabase mDatabase;
    private Authentication mAuthentication;
    // Notify
    private FirebaseCloudMessagingAPI mCloudMessagingAPI;

    private User mMyUser = null;

    public MainInteractorClass(){
        mDatabase = new RealtimeDatabase();
        mAuthentication = new Authentication();
        // Notify
        mCloudMessagingAPI = FirebaseCloudMessagingAPI.getInstance();
    }

    @Override
    public void suscribeToUserList() {
        mDatabase.subscribeToUserList(getCurrentUser().getUid(), new UserEventListener() {
            @Override
            public void onUserAdded(User user) {
                post(MainEvent.USER_ADDED, user);
            }

            @Override
            public void onUserUpdated(User user) {
                post(MainEvent.USER_UPDATED, user);
            }

            @Override
            public void onUserRemoved(User user) {
                post(MainEvent.USER_REMOVED, user);
            }

            @Override
            public void onError(int resMsg) {
                postError(resMsg);
            }
        });

        mDatabase.subscribeToRequests(getCurrentUser().getEmail(), new UserEventListener() {
            @Override
            public void onUserAdded(User user) {
                post(MainEvent.REQUEST_ADDED, user);
            }

            @Override
            public void onUserUpdated(User user) {
                post(MainEvent.REQUEST_UPDATED, user);
            }

            @Override
            public void onUserRemoved(User user) {
                post(MainEvent.REQUEST_REMOVED, user);
            }

            @Override
            public void onError(int resMsg) {
                post(MainEvent.ERROR_SERVER);
            }
        });

        changeConnectionStatus(Constants.ONLINE);
    }

    private void changeConnectionStatus(boolean online) {
        mDatabase.getmDatabaseAPI().updateMyLastConnection(online, getCurrentUser().getUid());
    }

    @Override
    public void unsuscribeToUserList() {
        mDatabase.unsubscribeToUsers(getCurrentUser().getUid());
        mDatabase.unsubscribeToRequest(getCurrentUser().getEmail());

        changeConnectionStatus(Constants.OFFLINE);
    }

    @Override
    public void signOff() {
        mCloudMessagingAPI.unsubscribeToMyTopic(getCurrentUser().getEmail());

        mAuthentication.signOff();
    }

    @Override
    public User getCurrentUser() {
        return mMyUser == null? mAuthentication.getmAuthenticationAPI().getAuthUser() : mMyUser;
    }

    @Override
    public void removeFriend(String friendUid) {
        mDatabase.removeUser(friendUid, getCurrentUser().getUid(), new BasicEventsCallback() {
            @Override
            public void onSuccess() {
                post(MainEvent.USER_REMOVED);
            }

            @Override
            public void onError() {
                post(MainEvent.ERROR_SERVER);
            }
        });
    }

    @Override
    public void acceptRequest(final User user) {
        mDatabase.acceptRequest(user, getCurrentUser(), new BasicEventsCallback() {
            @Override
            public void onSuccess() {
                post(MainEvent.REQUEST_ACCEPTED, user);
            }

            @Override
            public void onError() {
                post(MainEvent.ERROR_SERVER);
            }
        });
    }

    @Override
    public void denyRequest(User user) {
        mDatabase.denyRequest(user, getCurrentUser().getEmail(), new BasicEventsCallback() {
            @Override
            public void onSuccess() {
                post(MainEvent.REQUEST_DENIED);
            }

            @Override
            public void onError() {
                post(MainEvent.ERROR_SERVER);
            }
        });
    }

    private void postError(int resMsg) {
        post(MainEvent.ERROR_SERVER, null, resMsg);
    }

    private void post(int typeEvent){
        post(typeEvent, null, 0);
    }

    private void post(int typeEvent, User user){
        post(typeEvent, user, 0);
    }

    private void post(int typeEvent, User user, int resMsg){
        MainEvent event = new MainEvent();
        event.setTypeEvent(typeEvent);
        event.setUser(user);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }
}
