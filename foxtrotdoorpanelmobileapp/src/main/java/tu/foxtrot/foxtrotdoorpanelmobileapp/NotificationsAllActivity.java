package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class NotificationsAllActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notifications);
        ListView mListView = (ListView) findViewById(R.id.notifications_list);

        Notification notification1 = new Notification("20.12.2018", "12:20",
                "New Meeting", "Blablabla");
        Notification notification2 = new Notification("13.01.2018", "09:20",
                "New Meeting", "tatatatata");
        Notification notification3 = new Notification("22.02.2018", "12:45",
                "Door Info Changed", "Don't disturb");
        MessageNotification messageNotification1 = new MessageNotification("22.02.2018",
                "10:23", "Message", "Could I come in?",
                "mister@gmail.com", "Matt Matt");
        MessageNotification messageNotification2 = new MessageNotification("22.02.2018",
                "10:23", "Message", "Could I come in?",
                "mister@gmail.com", "Matt Brown");
        MessageNotification messageNotification3 = new MessageNotification("22.02.2018",
                "10:23", "Message", "Could I come in?",
                "mister@gmail.com", "Piter Matt");
        Notification notification4 = new Notification("19.02.2018", "12:45",
                "Door Info Changed", "Don't disturb");
        Notification notification5 = new Notification("09.12.2018", "12:45",
                "Door Info Changed", "Party for everybody");
        Notification notification6 = new Notification("22.07.2018", "12:45",
                "Door Info Changed", "Don't disturb");

        ArrayList<Notification> notifications = new ArrayList<>();
        notifications.add(notification1);
        notifications.add(notification2);
        notifications.add(notification3);
        notifications.add(messageNotification1);
        notifications.add(messageNotification2);
        notifications.add(messageNotification3);
        notifications.add(notification4);
        notifications.add(notification5);
        notifications.add(notification6);

        NotificationsListAdapter adapter = new NotificationsListAdapter(this,
                R.layout.single_notification, notifications);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notification notification = notifications.get(position);
                if (notification.getType().equals("Message")) {
                    Intent intent = new Intent(NotificationsAllActivity.this,
                            MessageActivity.class);
                    intent.putExtra("Name", ((MessageNotification) notification).getName());
                    intent.putExtra("Email", ((MessageNotification) notification).getEmail());
                    intent.putExtra("Details", notification.getDetails());
                    startActivity(intent);
                }
            }
        });
    }
}
