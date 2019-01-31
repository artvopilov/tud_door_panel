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
import tu.foxtrot.foxtrotdoorpanelmobileapp.dialog.EventDialog;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;

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
    private Button logoutButton;

    private WorkersAPI workersApi;

    private com.google.api.services.calendar.Calendar mService = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        logoutButton = (Button) findViewById(R.id.logoutButton);

        openCustomizedPhoto();
        openCustomizedRoom();
        openCustomizedEmail();
        openCustomizedPhone();
        openCustomizedSummary();
        openSetCalendar();
        openAddWorker();
        logoutWorker();


        workersApi = RetrofitClient.getRetrofitInstance().create(WorkersAPI.class);

        defTimeSlotsButton = (Button) findViewById(R.id.button6);
        defTimeSlotsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventDialog eventDialog = new EventDialog();
                eventDialog.show(getFragmentManager(), "dialog");

               /* Dialog dialog=new Dialog(CalendarActivity.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialog.setContentView(R.layout.create_event_layout);
                dialog.show();*/
            }
        });

    }


    private void logoutWorker() {
        logoutButton.setOnClickListener(v -> {
            SharedPreferences.Editor sharedPrefEditor = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
            sharedPrefEditor.remove("token");
            sharedPrefEditor.apply();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

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

    public void openCustomizedRoom() {
        roomButton = (Button) findViewById(R.id.button2);
        roomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, PersonalRoom.class);
                startActivity(intent);
            }
        });
    }

    public void openCustomizedEmail() {
        emailButton = (Button) findViewById(R.id.button3);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, PersonalEmail.class);
                startActivity(intent);
            }
        });
    }

    public void openCustomizedPhone() {
        phoneButton = (Button) findViewById(R.id.button4);
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, PersonalPhone.class);
                startActivity(intent);
            }
        });
    }

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

    @SuppressLint("StaticFieldLeak")
    public void createEventAsync(final String summary, final String location, final String des, final DateTime startDate, final DateTime endDate, final EventAttendee[]
            eventAttendees) {

        new AsyncTask<Void, Void, String>() {
            private com.google.api.services.calendar.Calendar mService = null;
            private Exception mLastError = null;
            private boolean FLAG = false;


            @Override
            protected String doInBackground (Void...voids){
                try {
                    insertEvent(summary, location, des, startDate, endDate, eventAttendees);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute (String s){
                super.onPostExecute(s);
                //getResultsFromApi();
            }
        }.execute();
    }
    void insertEvent(String summary, String location, String des, DateTime startDate, DateTime endDate, EventAttendee[] eventAttendees) throws IOException {



        tu.foxtrot.foxtrotdoorpanelmobileapp.network.models.Event ourEvent = new tu.foxtrot.foxtrotdoorpanelmobileapp.network.models.Event();

        ourEvent.setStart(new Date(startDate.getValue()));
        ourEvent.setEnd(new Date(endDate.getValue()));
        ourEvent.setName(summary);
        ourEvent.setId(ourEvent.hashCode()); //TODO: this is probably not the best solution

        int workerID = ((MobileApplication) getApplicationContext()).getWorkerID();

        Call<String> call = workersApi.addWorkerTimeslot(workerID, ourEvent);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String newStatus = response.body();

                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, "Status updated: " + newStatus,
                        Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("add Timeslot", t.getMessage());
            }
        });

        Event event = new Event()
                .setSummary(summary)
                .setLocation(location)
                .setDescription(des);

        EventDateTime start = new EventDateTime()
                .setDateTime(startDate)
                .setTimeZone("America/Los_Angeles");
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(endDate)
                .setTimeZone("America/Los_Angeles");
        event.setEnd(end);

        String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
        event.setRecurrence(Arrays.asList(recurrence));

        event.setId(Integer.toString(ourEvent.getId()));



        String calendarId = ((MobileApplication) getApplicationContext()).getmCalendar();

        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, ((MobileApplication) getApplicationContext()).getmCredential())
                .setApplicationName("Google Calendar API Android Quickstart")
                .build();


        //event.send
        if(mService!=null)
            event =  mService.events().insert(calendarId, event).setSendNotifications(true).execute();

    }
}
