package org.michat.messenger.mcnet;

public class SetChatList extends NetworkObject {
    private final long[] peerIds;
    private final String[] displayNames;

    public SetChatList(long[] peerIds, String[] displayNames) {
        super(1, NetworkObjectNames.SET_CHAT_LIST);

        this.peerIds = peerIds;
        this.displayNames = displayNames;
    }

    public long[] getPeerIds() {
        return peerIds;
    }

    public String[] getDisplayNames() {
        return displayNames;
    }
}
