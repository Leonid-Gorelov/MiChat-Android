package org.michat.messenger.mcnet;

public class GetLogInResult extends NetworkObject {
    private long id;
    private final String sessionToken;
    private final int resultCode;

    public GetLogInResult(long id, String sessionToken, int resultCode, int version) {
        super(version, "GetLogInResult");

        this.id = id;
        this.sessionToken = sessionToken;
        this.resultCode = resultCode;
    }

    public long getId() {
        return id;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public int getResultCode() {
        return resultCode;
    }
}
