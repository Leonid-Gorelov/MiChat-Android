package org.michat.messenger.mcnet;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.michat.messenger.GsonParser;
import org.michat.messenger.StorageManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.*;
import okio.ByteString;

public class NetworkManager {
    private String webSockerUrl = "wss://echo.websocket.org";
    private String restApiUrl = "https://url.ru";
    private OkHttpClient okHttpClient = new OkHttpClient();
    private WebSocket webSocket;
    private Map<String, IUpdateListener> updateListeners = new ConcurrentHashMap<>();
    private AuthController authController;
    private StorageManager storageManager;

    public void init(AuthController authController, StorageManager storageManager) {
        this.authController = authController;
        this.storageManager = storageManager;
    }

    private void startWs() {
        Request webSocketRequest = new Request.Builder().url(webSockerUrl).build();

        webSocket = okHttpClient.newWebSocket(webSocketRequest, new WebSocketListener() {
            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                Log.d("app", "Open");
                super.onOpen(webSocket, response);
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                Log.d("app", "Message text");
                super.onMessage(webSocket, text);

                Update update = (Update) GsonParser.fromJson(text, Update.class);

                update.getNetworkObjects().forEach((networkObject -> {
                    IUpdateListener listener = updateListeners.get(networkObject.getType());

                    listener.onMessage(networkObject);
                }));
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
                Log.d("app", "Message byte");
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                Log.e("app", "Connection error" + t.getMessage(), t);
                super.onFailure(webSocket, t, response);
            }
        });
    }

    public void addUpdateListener(String type, IUpdateListener listener) {
        updateListeners.put(type, listener);
    }

    public void sendPhoneNumber(SendPhoneNumber sendPhoneNumber) {
        new Thread(() -> {
            postRestApi("/sendPhone", sendPhoneNumber);
        }).start();
    }

    public void sendSmsCode(SendSmsCode sendSmsCode) {
        new Thread(() -> {
            GetLogInResult logInResult = (GetLogInResult) postRestApi("/sendSms", sendSmsCode);

            if (logInResult.getId() != -1) {
                checkLogInResult(logInResult);
            }
        }).start();
    }

    public void sendUser(User user) {
        if (storageManager.getString(storageManager.SESSION_TOKEN) != null) {
            new Thread(() -> {
                GetLogInResult logInResult = (GetLogInResult) postRestApi("/sendUser", user);
                boolean needToPhoneLogIn = !checkLogInResult(logInResult);

                authController.setIsLogin(!needToPhoneLogIn);
            }).start();
        }
    }

    public void createAccount(CreateAccount createAccount) {
        GetLogInResult logInResult = (GetLogInResult) postRestApi("/createAccount", createAccount);

        checkLogInResult(logInResult);
    }

    private boolean checkLogInResult(GetLogInResult logInResult) {
        if (logInResult.getResultCode() == 200) {
            authController.setIsLogin(true);
            startWs();
            return true;
        } else {
            return false;
        }
    }

    public NetworkObject postRestApi(String endpoint, NetworkObject networkObject) {
        String json = GsonParser.toJson(networkObject);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Request request = new Request.Builder().url(restApiUrl + endpoint).post(requestBody).build();

        Response response = null;

        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (response.isSuccessful()) {
            try {
                return GsonParser.fromJson(response.body().string());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public void sendWs(NetworkObject networkObject) {
        webSocket.send(GsonParser.toJson(networkObject));
    }
}
