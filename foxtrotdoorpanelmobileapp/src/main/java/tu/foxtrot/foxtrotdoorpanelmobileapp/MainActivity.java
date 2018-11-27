package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button calendarButton;
    private Button statusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarButton = (Button) findViewById(R.id.button2);
        calendarButton.setOnClickListener(this::openCalendar);

        statusButton = (Button) findViewById(R.id.button3);
        statusButton.setOnClickListener(this::openStatus);
    }

    public void openCalendar(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    public void openStatus(View view) {
        Intent intent = new Intent(this, StatusSelection.class);
        startActivity(intent);
    }

}
