package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Context;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;

/**
 * Settings Activity for Mobile App. Allows to access other activities
 * to customize your data, set calendar and add new workers.
 */
public class Settings extends AppCompatActivity {
    private final String TAG = "SettingsActivity";

    private Button photoButton;
    private Button roomButton;
    private Button emailButton;
    private Button phoneButton;
    private Button summaryButton;
    private Button calendarButton;
    private Button defTimeSlotsButton;
    private Button addWorkerButton;
    private Button backtomainbutton;
    private Button logoutButton;
    private Button personalInfoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        logoutButton = (Button) findViewById(R.id.logoutButton);

        openCustomizedPhoto();
        openCustomizedSummary();
        openSetCalendar();
        openAddWorker();
        logoutWorker();
        //openCreateTimeslot();
        backToMainWIndow();
        openPersonalInfo();


/*


        defTimeSlotsButton = (Button) findViewById(R.id.button6);
        defTimeSlotsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventDialog eventDialog = new EventDialog();
                eventDialog.show(getFragmentManager(), "dialog");

            }
        });*/

    }


    private void logoutWorker() {
        logoutButton.setOnClickListener(v -> {
            SharedPreferences.Editor sharedPrefEditor = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
            sharedPrefEditor.remove("token");
            sharedPrefEditor.remove("name");
            sharedPrefEditor.remove("topic");
            sharedPrefEditor.apply();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Open view to customize personal photo.
     */
    public void openCustomizedPhoto() {
        photoButton = (Button) findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, PersonalPhoto.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Open view to customize personal summary.
     */
    public void openCustomizedSummary() {
        summaryButton = (Button) findViewById(R.id.button5);
        summaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, PersonalSummary.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Open view to set calendar.
     */
    public void openSetCalendar() {
        calendarButton = (Button) findViewById(R.id.button7);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, SetCalendarActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Open view to add new worker.
     */
    public void openAddWorker() {
        addWorkerButton = (Button) findViewById(R.id.button8);
        addWorkerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, CreatingWorkerActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Open view to create timeslots.
     */
    public void openCreateTimeslot() {
        defTimeSlotsButton = (Button) findViewById(R.id.button6);
        defTimeSlotsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, CreateTimeslotActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Back to Main Activity.
     */
    public void backToMainWIndow() {
        backtomainbutton = (Button) findViewById(R.id.button10);
        backtomainbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Open view to customize personal information.
     */
    public void openPersonalInfo() {
        personalInfoButton = (Button) findViewById(R.id.button9);
        personalInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, PersonalInfo.class);
                startActivity(intent);
            }
        });
    }

}
