package org.michat.messenger.mcnet;

public class FindUser extends NetworkObject {
    private final String searchInput;

    public FindUser(String searchInput) {
        super(1, NetworkObjectNames.FIND_USER_NAME);

        this.searchInput = searchInput;
    }

    public String getSearchInput() {
        return searchInput;
    }
}
