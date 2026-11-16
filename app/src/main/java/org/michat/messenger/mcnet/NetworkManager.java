package org.michat.messenger.mcnet;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.michat.messenger.GsonParser;
import org.michat.messenger.StorageManager;

import java.io.IOException;

import okhttp3.*;
import okio.ByteString;

public class NetworkManager {
    private static String webSockerUrl = "wss://echo.websocket.org";
    private static String restApiUrl = "https://url.ru";
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static WebSocket webSocket;

    public static void init() {
        if (StorageManager.getString("sessionToken") != null) {
            new Thread(() -> {
                GetLogInResult logInResult = (GetLogInResult) postRestApi("/sendUser", new User(1, "", 1));
                boolean needToPhoneLogIn = !checkLogInResult(logInResult);

                if (needToPhoneLogIn) {
                    sendPhoneNumber();
                    sendSmsCode();
                }
            }).start();
        }
    }

    private static void startWs() {
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

    public static void sendPhoneNumber() {
        new Thread(() -> {
            postRestApi("/sendPhone", new SendPhoneNumber("+7", 1));
        }).start();
    }

    public static void sendSmsCode() {
        new Thread(() -> {
            GetLogInResult logInResult = (GetLogInResult) postRestApi("/sendSms", new SendSmsCode(1, 1));

            checkLogInResult(logInResult);
        }).start();
    }

    private static boolean checkLogInResult(GetLogInResult logInResult) {
        if (logInResult.getResultCode() == 200) {
            startWs();
            return true;
        } else {
            return false;
        }
    }

    public static NetworkObject postRestApi(String endpoint, NetworkObject networkObject) {
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

    public static void sendWs(NetworkObject networkObject) {
        webSocket.send(GsonParser.toJson(networkObject));
    }
}
