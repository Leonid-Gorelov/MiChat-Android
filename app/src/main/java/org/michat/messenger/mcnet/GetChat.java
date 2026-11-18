package org.michat.messenger.mcnet;

public class GetChat extends NetworkObject {
    private final long peerId;

    public GetChat(long peerId) {
        super(1, NetworkObjectNames.GET_CHAT_NAME);

        this.peerId = peerId;
    }

    public long getPeerId() {
        return peerId;
    }
}
