package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.google.firebase.messaging.FirebaseMessaging;

import android.widget.ImageButton;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.UpdateReceiver;

import java.util.List;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.models.Tablet;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;




public class MainActivity extends AppCompatActivity {

    private final String UPDATE_GUI_FILTER = "de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.updateGUI";
    private final String TAG = "MainTabletActivity";
    private GridView gridView;
    private ImageButton calendarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(new UpdateReceiver(), new IntentFilter(UPDATE_GUI_FILTER));

        gridView = (GridView) findViewById(R.id.workersGrid);
        gridView.setAdapter(new WorkerAdapter(this));
        ((TabletApplication)getApplicationContext()).pullWorkers();
        subscribeToTopic80b();
    }

    private void subscribeToTopic80b() {
        String topic = "80b";
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.d(TAG, "Unsuccessful subscription to topic: " + topic);
                    }
                    Log.d(TAG, "Subscribed to topic " + topic);
                });
    }


        public void openCreateEvent () {
            calendarButton = findViewById(R.id.landingPagePinButton10);
            calendarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                    startActivity(intent);
                }
            });
        }

}
