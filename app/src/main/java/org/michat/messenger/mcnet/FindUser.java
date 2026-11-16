package org.michat.messenger.mcnet;

public class FindUser extends NetworkObject {
    private final String searchInput;

    public FindUser(String searchInput, int version) {
        super(version, "FindUser");

        this.searchInput = searchInput;
    }

    public String getSearchInput() {
        return searchInput;
    }
}
