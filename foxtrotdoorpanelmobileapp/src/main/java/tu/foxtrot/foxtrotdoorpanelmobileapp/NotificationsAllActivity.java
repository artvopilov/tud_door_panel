package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.MessageNotification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common.Notification;

public class NotificationsAllActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notifications);
        ListView mListView = (ListView) findViewById(R.id.notifications_list);

        List<Notification> notifications = ((MobileApplication)getApplicationContext()).getNotificationsList();

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
                    intent.putExtra("Name", ((MessageNotification) notification).getName());
                    intent.putExtra("Email", ((MessageNotification) notification).getEmail());
                    intent.putExtra("Details", ((MessageNotification) notification).getText());
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
}
