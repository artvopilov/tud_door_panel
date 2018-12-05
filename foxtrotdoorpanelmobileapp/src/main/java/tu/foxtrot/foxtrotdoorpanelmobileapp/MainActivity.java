package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button calendarButton;
    private Button statusButton;
    private Button notificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarButton = (Button) findViewById(R.id.button2);
        calendarButton.setOnClickListener(this::openCalendar);

        statusButton = (Button) findViewById(R.id.button3);
        statusButton.setOnClickListener(this::openStatus);

        notificationButton = (Button) findViewById(R.id.button4);
        notificationButton.setOnClickListener(this::openNotifications);
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
}
