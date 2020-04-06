package com.alfred0ga.texting.addModule.model;

import com.alfred0ga.texting.addModule.events.AddEvent;
import com.alfred0ga.texting.addModule.model.dataAccess.RealtimeDatabase;
import com.alfred0ga.texting.common.model.BasicEventsCallback;
import com.alfred0ga.texting.common.model.EventsCallback;
import com.alfred0ga.texting.common.model.dataAccess.FirebaseAuthenticationAPI;

import org.greenrobot.eventbus.EventBus;

public class AddInteractorClass implements AddInteractor {
    private RealtimeDatabase mDatabase;
    private FirebaseAuthenticationAPI mAuthenticationAPI;

    public AddInteractorClass() {
        this.mDatabase = new RealtimeDatabase();
        this.mAuthenticationAPI = FirebaseAuthenticationAPI.getInstance();
    }

    @Override
    public void addFriend(String email) {
        mDatabase.checkUserExist(email, new EventsCallback() {
            @Override
            public void onSuccess() {
                mDatabase.checkRequestNotExist(email, mAuthenticationAPI.getCurrentUser().getUid(),
                        new EventsCallback() {
                            @Override
                            public void onSuccess() {
                                mDatabase.addFriend(email, mAuthenticationAPI.getAuthUser(), new BasicEventsCallback() {
                                    @Override
                                    public void onSuccess() {
                                        post(AddEvent.SEND_REQUEST_SUCCESS);
                                    }

                                    @Override
                                    public void onError() {
                                        post(AddEvent.ERROR_SERVER);
                                    }
                                });
                            }
                            @Override
                            public void onError(int typeEvent, int resMsg) {
                                post(typeEvent, resMsg);
                            }
                        });
            }
            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg);
            }
        });
    }

    private void post(int typeEvent){
        post(typeEvent, 0);
    }

    private void post(int typeEvent, int resMsg) {
        AddEvent event = new AddEvent();
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }
}
