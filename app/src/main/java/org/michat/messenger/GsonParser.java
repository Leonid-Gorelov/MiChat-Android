package org.michat.messenger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.michat.messenger.mcnet.ChatMessage;
import org.michat.messenger.mcnet.CreateAccount;
import org.michat.messenger.mcnet.FindUser;
import org.michat.messenger.mcnet.GetChat;
import org.michat.messenger.mcnet.GetLogInResult;
import org.michat.messenger.mcnet.MarkAsRead;
import org.michat.messenger.mcnet.NetworkObject;
import org.michat.messenger.mcnet.SendPhoneNumber;
import org.michat.messenger.mcnet.SendSmsCode;
import org.michat.messenger.mcnet.SetChatList;
import org.michat.messenger.mcnet.SetSearchResult;
import org.michat.messenger.mcnet.SetSentMessageId;

import java.util.Map;

public class GsonParser {
    private static Gson gson = new GsonBuilder().serializeNulls().create();

    private static Map<String, Class<? extends NetworkObject>> map = Map.<String, Class<? extends NetworkObject>>ofEntries(
            Map.entry("ChatMessage", ChatMessage.class),
            Map.entry("CreateAccount", CreateAccount.class),
            Map.entry("FindUser", FindUser.class),
            Map.entry("GetChat", GetChat.class),
            Map.entry("GetLogInResult", GetLogInResult.class),
            Map.entry("MarkAsRead", MarkAsRead.class),
            Map.entry("SendPhoneNumber", SendPhoneNumber.class),
            Map.entry("SendSmsCode", SendSmsCode.class),
            Map.entry("SetChatList", SetChatList.class),
            Map.entry("SetSearchResult", SetSearchResult.class),
            Map.entry("SetSentMessageId", SetSentMessageId.class)
    );

    public static String toJson(NetworkObject networkObject) {
        return gson.toJson(networkObject);
    }

    public static NetworkObject fromJson(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        return gson.fromJson(json, map.get(type));
    }
}
