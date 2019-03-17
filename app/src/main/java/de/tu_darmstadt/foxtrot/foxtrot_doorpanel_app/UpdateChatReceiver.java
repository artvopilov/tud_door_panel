package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;

public class UpdateChatReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ListView messagesListView = ((Activity)context).findViewById(R.id.list_of_messages);
        messagesListView.invalidateViews();
    }
}
