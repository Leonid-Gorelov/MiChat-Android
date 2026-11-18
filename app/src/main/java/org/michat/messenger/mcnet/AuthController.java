package org.michat.messenger.mcnet;

import org.michat.messenger.StorageManager;

public class AuthController {

    private boolean isLogin;
    NetworkManager networkManager;
    StorageManager storageManager;

    public void init(NetworkManager networkManager, StorageManager storageManager) {
        this.networkManager = networkManager;
        this.storageManager = storageManager;

        isLogin = storageManager.getString(storageManager.SESSION_TOKEN) != null;

        if (isLogin) {
            networkManager.sendUser(new User(storageManager.getLong(storageManager.USER_ID), storageManager.getString(storageManager.SESSION_TOKEN)));
        }
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean getIsLogin() {
        return isLogin;
    }

    public void createAccount(String name, String userName) {
        new CreateAccount(name, userName, storageManager.getString(storageManager.SESSION_TOKEN));
    }

    public void setPhoneNumber(String phoneNumber) {
        networkManager.sendPhoneNumber(new SendPhoneNumber(phoneNumber));
    }

    public void setSmsCode(int code) {
        networkManager.sendSmsCode(new SendSmsCode(code));
    }
}
