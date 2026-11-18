package org.michat.messenger;

import android.util.Log;

import org.michat.messenger.mcnet.IUpdateListener;
import org.michat.messenger.mcnet.NetworkManager;
import org.michat.messenger.mcnet.NetworkObject;
import org.michat.messenger.mcnet.NetworkObjectNames;
import org.michat.messenger.mcnet.SetChatList;

public class ChatListViewController implements IUpdateListener {
    private NetworkManager networkManager;

    public void init(NetworkManager networkManager) {
        this.networkManager = networkManager;

        networkManager.addUpdateListener(NetworkObjectNames.SET_CHAT_LIST, this);
    }

    @Override
    public void onMessage(NetworkObject networkObject) {
        SetChatList setChatList = (SetChatList) networkObject;

        for (String displayName : setChatList.getDisplayNames()) {
            Log.d("app", displayName);
        }

        //TODO: draw list of chats
    }
}
