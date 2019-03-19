package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.MessageNotification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common.Notification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.receivers.UpdateNotificationsReceiver;

/**
 * The type Notifications all activity.
 */
public class NotificationsAllActivity extends AppCompatActivity {
    private final String UPD_NOTIF_FILTER = "tu.foxtrot.foxtrotdoorpanelmobileapp.UPDATE_NOTIFICATIONS";
    private BroadcastReceiver updNotificationsReceiver = new UpdateNotificationsReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notifications);

        List<Notification> notifications = ((MobileApplication)getApplicationContext()).getNotificationsList();
        ListView mListView = (ListView) findViewById(R.id.notifications_list);
        NotificationsListAdapter adapter = new NotificationsListAdapter(this,
                R.layout.single_notification, notifications);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notification notification = notifications.get(position);
                if (notification.getType().equals("message")) {
                    Intent intent = new Intent(NotificationsAllActivity.this,
                            MessageActivity.class);
                    intent.putExtra("Name", notification.getName());
                    intent.putExtra("Email", notification.getEmail());
                    intent.putExtra("Details", notification.getMessage());
                    intent.putExtra("MessageId",
                            ((MessageNotification)notification).getMessageId());
                    startActivity(intent);
                }
                if (notification.getType().equals("booking")) {
                    Intent intent = new Intent(NotificationsAllActivity.this,
                            BookingActivity.class);
                    intent.putExtra("notificationID", position);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(updNotificationsReceiver, new IntentFilter(UPD_NOTIF_FILTER));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(updNotificationsReceiver);
    }
}
