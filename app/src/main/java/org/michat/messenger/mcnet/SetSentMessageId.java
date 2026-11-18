package org.michat.messenger.mcnet;

public class SetSentMessageId extends NetworkObject {
    private final int tempMessageId;
    private final long messageId;

    public SetSentMessageId(int tempMessageId, long messageId) {
        super(1, NetworkObjectNames.SET_SENT_MESSAGE_ID_NAME);

        this.tempMessageId = tempMessageId;
        this.messageId = messageId;
    }

    public int getTempMessageId() {
        return tempMessageId;
    }

    public long getMessageId() {
        return messageId;
    }
}
