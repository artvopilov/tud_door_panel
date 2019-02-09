package tu.foxtrot.foxtrotdoorpanelmobileapp.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.ListView;

import tu.foxtrot.foxtrotdoorpanelmobileapp.R;

public class UpdateNotificationsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ListView listView = ((Activity)context).findViewById(R.id.notifications_list);
        listView.invalidateViews();
    }
}
