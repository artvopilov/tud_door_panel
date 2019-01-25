package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button calendarButton;
    private Button statusButton;
    private Button notificationButton;
    private Button settingsButton;
    private TextView workerNameMain;

    private static final String CHANNEL_ID = "FoxtrottNotifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        calendarButton = (Button) findViewById(R.id.button2);
        calendarButton.setOnClickListener(this::openCalendar);
        statusButton = (Button) findViewById(R.id.button3);
        statusButton.setOnClickListener(this::openStatus);
        notificationButton = (Button) findViewById(R.id.button4);
        notificationButton.setOnClickListener(this::openNotifications);
        settingsButton = (Button) findViewById(R.id.button5);
        settingsButton.setOnClickListener(this::openSettings);

        workerNameMain = findViewById(R.id.workerNameMain);
//        ((MobileApplication)getApplicationContext()).pullWorkerName();
//        String workerName = ((MobileApplication)getApplicationContext()).getWorkerName();
//        workerNameMain.setText(workerName);
    }

    public void openCalendar(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    public void openStatus(View view) {
        Intent intent = new Intent(this, StatusSelection.class);
        startActivity(intent);
    }

    public void openNotifications(View view) {
        Intent intent = new Intent(this, NotificationsAllActivity.class);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
