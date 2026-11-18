package org.michat.messenger.mcnet;

public class SendSmsCode extends NetworkObject {
    private final int smsCode;

    public SendSmsCode(int smsCode) {
        super(1, NetworkObjectNames.SEND_SMS_CODE_NAME);

        this.smsCode = smsCode;
    }

    public int getSmsCode() {
        return smsCode;
    }
}
