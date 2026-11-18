package org.michat.messenger;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.michat.messenger.mcnet.AuthController;
import org.michat.messenger.mcnet.MessagesController;
import org.michat.messenger.mcnet.NetworkManager;
import org.michat.messenger.mcnet.NetworkObject;
import org.michat.messenger.mcnet.SetChatList;
import org.michat.messenger.mcnet.Update;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        StorageManager storageManager = new StorageManager();
        NetworkManager networkManager = new NetworkManager();
        AuthController authController = new AuthController();
        MessagesController messagesController = new MessagesController();
        ChatListViewController chatListViewController = new ChatListViewController();

        storageManager.init(this);
        networkManager.init(authController, storageManager);
        authController.init(networkManager, storageManager);
        messagesController.init(networkManager, storageManager);
        chatListViewController.init(networkManager);

//        ArrayList<NetworkObject> networkObjects = new ArrayList<NetworkObject>(List.of(new SetChatList(new long[]{1, 2, 3}, new String[]{"a", "b", "c"}, 1)));
//        Update update = new Update(networkObjects);
//        String json = "{\"networkObjects\":[{\"displayNames\":[\"a\",\"b\",\"c\"],\"peerIds\":[1,2,3],\"type\":\"SetChatList\",\"version\":1}]}";
//        Update update1 = (Update) GsonParser.fromJson(json, Update.class);
//
//        update1.getNetworkObjects().forEach((networkObject -> {
//            Log.d("app", ((SetChatList) networkObject).getDisplayNames()[0]);
//        }));
    }
}
