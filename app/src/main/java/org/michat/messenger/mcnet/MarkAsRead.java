package org.michat.messenger.mcnet;

public class MarkAsRead extends NetworkObject {
    private final long messageId;

    public MarkAsRead(long messageId) {
        super(1, NetworkObjectNames.MARK_AS_READ_NAME);

        this.messageId = messageId;
    }

    public long getMessageId() {
        return messageId;
    }
}
