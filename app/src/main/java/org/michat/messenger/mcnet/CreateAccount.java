package org.michat.messenger.mcnet;

public class CreateAccount extends NetworkObject {
    private final String name;
    private final String username;
    private final String sessionToken;

    public CreateAccount(String name, String username, String sessionToken) {
        super(1, NetworkObjectNames.CREATE_ACCOUNT_NAME);

        this.name = name;
        this.username = username;
        this.sessionToken = sessionToken;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
