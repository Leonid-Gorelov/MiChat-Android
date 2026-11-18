package org.michat.messenger.mcnet;

import android.util.Log;

import org.michat.messenger.StorageManager;

public class MessagesController implements IUpdateListener {
    private NetworkManager networkManager;
    private StorageManager storageManager;

    public void init(NetworkManager networkManager, StorageManager storageManager) {
        this.networkManager = networkManager;
        this.storageManager = storageManager;

        networkManager.addUpdateListener(NetworkObjectNames.CHAT_MESSAGE_NAME, this);
    }

    @Override
    public void onMessage(NetworkObject networkObject) {
        ChatMessage chatMessage = (ChatMessage) networkObject;

        Log.d("app", chatMessage.getText());
    }

    public void sendMessage(String text, long target, long targetId){
        networkManager.sendWs(new ChatMessage(text, storageManager.getLong(storageManager.USER_ID), targetId, -1, null, System.currentTimeMillis()));
    }
}
