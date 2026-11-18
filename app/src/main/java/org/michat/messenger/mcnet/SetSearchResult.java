package org.michat.messenger.mcnet;

public class SetSearchResult extends NetworkObject {
    private final long peerId;

    public SetSearchResult(long peerId) {
        super(1, NetworkObjectNames.SET_SEARCH_RESULT_NAME);

        this.peerId = peerId;
    }

    public long getPeerId() {
        return peerId;
    }
}
