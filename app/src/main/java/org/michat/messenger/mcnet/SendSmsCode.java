package org.michat.messenger.mcnet;

public class SendSmsCode extends NetworkObject {
    private final int smsCode;

    public SendSmsCode(int smsCode, int version) {
        super(version, "SendSmsCode");

        this.smsCode = smsCode;
    }

    public int getSmsCode() {
        return smsCode;
    }
}
