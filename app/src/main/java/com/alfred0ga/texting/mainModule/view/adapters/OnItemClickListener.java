package com.alfred0ga.texting.mainModule.view.adapters;

import com.alfred0ga.texting.common.pojo.User;

public interface OnItemClickListener {

    void onItemClick(User user);
    void onItemLongClick(User user);

    void onAcceptRquest(User user);
    void onDenyRequest(User user);

}
