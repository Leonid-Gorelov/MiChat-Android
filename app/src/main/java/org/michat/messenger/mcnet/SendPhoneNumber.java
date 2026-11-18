package org.michat.messenger.mcnet;

public class SendPhoneNumber extends NetworkObject {
    private final String phoneNumber;

    public SendPhoneNumber(String phoneNumber) {
        super(1, NetworkObjectNames.SEND_PHONE_NUMBER_NAME);

        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
