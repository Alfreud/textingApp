package com.alfred0ga.texting.chatModule.model;

import android.app.Activity;
import android.net.Uri;

import com.alfred0ga.texting.chatModule.events.ChatEvent;
import com.alfred0ga.texting.chatModule.model.dataAccess.RealtimeDatabase;
import com.alfred0ga.texting.chatModule.model.dataAccess.Storage;
import com.alfred0ga.texting.common.Constants;
import com.alfred0ga.texting.common.model.StorageUploadImageCallback;
import com.alfred0ga.texting.common.model.dataAccess.FirebaseAuthenticationAPI;
import com.alfred0ga.texting.common.pojo.Message;
import com.alfred0ga.texting.common.pojo.User;

import org.greenrobot.eventbus.EventBus;

public class ChatInteractorClass implements ChatInteractor {
    private RealtimeDatabase mDatabase;
    private FirebaseAuthenticationAPI mAuthenticationAPI;
    private Storage mStorage;

    private User mMyUser;
    private String mFriendUid;
    private String mFriendEmail;

    private long mLastConnectioFriend;
    private String mUidConnectedFriend = "";

    public ChatInteractorClass() {
        this.mDatabase = new RealtimeDatabase();
        this.mAuthenticationAPI = FirebaseAuthenticationAPI.getInstance();
        this.mStorage = new Storage();
    }

    private User getCurrentUser(){
        if(mMyUser == null){
            mMyUser = mAuthenticationAPI.getAuthUser();
        }
        return mMyUser;
    }

    @Override
    public void subscribeToFriend(String friendUid, String friendEmail) {
        this.mFriendEmail = friendEmail;
        this.mFriendUid = friendUid;

        mDatabase.subscribeToFriend(friendUid, new LastConnectionEventListener() {
            @Override
            public void onSuccess(boolean online, long lastConnection, String uidConnectedFriend) {
                postStatusFriend(online, lastConnection);
                mUidConnectedFriend = uidConnectedFriend;
                mLastConnectioFriend = lastConnection;
            }
        });

        mDatabase.setMessagesRead(getCurrentUser().getUid(), friendUid);
    }

    @Override
    public void unsubscribeToFriend(String friendUid) {
        mDatabase.unsubscribeToFriend(friendUid);
    }

    @Override
    public void subscribeToMessages() {
        mDatabase.subscribeToMessages(getCurrentUser().getEmail(), mFriendEmail,
                new MessagesEventListener() {
                    @Override
                    public void onMessageReceived(Message message) {
                        String msgSender = message.getSender();
                        message.setSentByMe(msgSender.equals(getCurrentUser().getEmail()));
                        postMessage(message);
                    }

                    @Override
                    public void onError(int resMsg) {
                        post(ChatEvent.ERROR_SERVER, resMsg);
                    }
                });
        mDatabase.getmDatabaseAPI().updateMyLastConnection(Constants.ONLINE, mFriendUid, getCurrentUser().getUid());
    }

    @Override
    public void unsubscribeToMessages() {
        mDatabase.unsubscribeToMessages(getCurrentUser().getEmail(), mFriendEmail);
        mDatabase.getmDatabaseAPI().updateMyLastConnection(Constants.OFFLINE, getCurrentUser().getUid());
    }

    @Override
    public void sendMessage(String msg) {
        sendMessage(msg, null);
    }

    @Override
    public void sendImage(Activity activity, Uri ImageUri) {
        mStorage.uploadImageChat(activity, ImageUri, getCurrentUser().getEmail(),
                new StorageUploadImageCallback() {
                    @Override
                    public void onSuccess(Uri newUri) {
                        sendMessage(null, newUri.toString());
                        postUploadSucces();
                    }

                    @Override
                    public void onError(int resMsg) {
                        post(ChatEvent.IMAGE_UPLOAD_FAIL, resMsg);
                    }
                });
    }

    private void sendMessage(final String msg, String photoUrl){
        mDatabase.sendMessage(msg, photoUrl, mFriendEmail, getCurrentUser(),
                new SendMessageListener() {
                    @Override
                    public void onSeccess() {
                        if(mUidConnectedFriend.equals(getCurrentUser().getUid())){
                            mDatabase.sumUnreadMessages(getCurrentUser().getUid(), mFriendUid);

                            // TODO notify
                        }
                    }
                });
    }

    private void postUploadSucces(){
        post(ChatEvent.IMAGE_UPLOAD_SUCCESS, 0, null, false, 0);
    }

    private void postMessage(Message message){
        post(ChatEvent.MESSAGE_ADDED, 0, message, false, 0);
    }

    private  void post(int typeEvent, int resMsg){
        post(typeEvent,resMsg, null, false, 0);
    }

    private void postStatusFriend(boolean online, long lastConnection) {
        post(ChatEvent.GET_STATUS_FRIEND, 0, null, online, lastConnection);
    }

    private void post(int typeEvent, int resMsg, Message message, boolean online, long lastConnection){
        ChatEvent event = new ChatEvent();
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        event.setMessage(message);
        event.setConnected(online);
        event.setLastConnection(lastConnection);
        EventBus.getDefault().post(event);
    }
}
