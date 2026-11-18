package org.michat.messenger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.michat.messenger.mcnet.ChatMessage;
import org.michat.messenger.mcnet.CreateAccount;
import org.michat.messenger.mcnet.FindUser;
import org.michat.messenger.mcnet.GetChat;
import org.michat.messenger.mcnet.GetLogInResult;
import org.michat.messenger.mcnet.MarkAsRead;
import org.michat.messenger.mcnet.NetworkObject;
import org.michat.messenger.mcnet.NetworkObjectNames;
import org.michat.messenger.mcnet.SendPhoneNumber;
import org.michat.messenger.mcnet.SendSmsCode;
import org.michat.messenger.mcnet.SetChatList;
import org.michat.messenger.mcnet.SetSearchResult;
import org.michat.messenger.mcnet.SetSentMessageId;
import org.michat.messenger.mcnet.User;

import java.util.Map;

public class GsonParser {
    private static final Map<String, Class<? extends NetworkObject>> map = Map.<String, Class<? extends NetworkObject>>ofEntries(
            Map.entry(NetworkObjectNames.CHAT_MESSAGE_NAME, ChatMessage.class),
            Map.entry(NetworkObjectNames.CREATE_ACCOUNT_NAME, CreateAccount.class),
            Map.entry(NetworkObjectNames.FIND_USER_NAME, FindUser.class),
            Map.entry(NetworkObjectNames.GET_CHAT_NAME, GetChat.class),
            Map.entry(NetworkObjectNames.GET_LOG_IN_RESULT_TEXT, GetLogInResult.class),
            Map.entry(NetworkObjectNames.MARK_AS_READ_NAME, MarkAsRead.class),
            Map.entry(NetworkObjectNames.SEND_PHONE_NUMBER_NAME, SendPhoneNumber.class),
            Map.entry(NetworkObjectNames.SEND_SMS_CODE_NAME, SendSmsCode.class),
            Map.entry(NetworkObjectNames.SET_CHAT_LIST, SetChatList.class),
            Map.entry(NetworkObjectNames.SET_SEARCH_RESULT_NAME, SetSearchResult.class),
            Map.entry(NetworkObjectNames.SET_SENT_MESSAGE_ID_NAME, SetSentMessageId.class),
            Map.entry("User", User.class)
    );
    private static Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapter(NetworkObject.class, (JsonDeserializer<NetworkObject>) (json, type, context) -> {
        JsonObject jsonObject = json.getAsJsonObject();

        String typeA = jsonObject.get("type").getAsString();
        Class<? extends NetworkObject> classA = map.get(typeA);

        return context.deserialize(jsonObject, classA);
    }).create();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static String toJson(NetworkObject networkObject) {
        return gson.toJson(networkObject);
    }

    public static Object fromJson(String json, Class<?> classA) {
        return gson.fromJson(json, classA);
    }

    public static NetworkObject fromJson(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        return gson.fromJson(json, map.get(type));
    }
}
