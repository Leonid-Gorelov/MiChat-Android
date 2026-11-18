package org.michat.messenger.mcnet;

public class User extends NetworkObject {
    private final long userId;
    private final String sessionToken;

    public User(long userId, String sessionToken) {
        super(1, NetworkObjectNames.USER_NAME);

        this.userId = userId;
        this.sessionToken = sessionToken;
    }

    public long getUserId() {
        return userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
