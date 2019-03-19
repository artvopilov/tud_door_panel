package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.model.Message;

/**
 * The type Chat activity.
 */
public class ChatActivity extends AppCompatActivity {
    private final String UPD_CHAT_FILTER = "de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.updateChat";
    private BroadcastReceiver updMessagessReceiver = new UpdateChatReceiver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        List<Message> messageList = ((TabletApplication)getApplication()).getMessages();
        ListView messagesListView = findViewById(R.id.list_of_messages);
        ChatMessagesAdapter messagesAdapter = new ChatMessagesAdapter(this,
                R.layout.single_message, messageList);
        messagesListView.setAdapter(messagesAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(updMessagessReceiver, new IntentFilter(UPD_CHAT_FILTER));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(updMessagessReceiver);
    }
}
