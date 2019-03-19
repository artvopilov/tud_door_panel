package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.model.Event;

import java.io.IOException;
import java.util.Arrays;

import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.BookingNotification;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;

public class BookingActivity extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private TextView text;
    private TextView eventStart;
    private TextView eventEnd;
    private Button buttonAcceptBooking;
    private Event timeslot;
    private String calendarId;
    private String timeslotCalendarId;
    private String slotID;
    private String sname;
    private String semail;
    private String stext;

    private com.google.api.services.calendar.Calendar mService = null;

    private BookingNotification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        calendarId = ((MobileApplication) getApplicationContext()).getmCalendar();
        timeslotCalendarId = ((MobileApplication) getApplicationContext()).getTimeslotsCalendar();

        name = (TextView) findViewById(R.id.message_name);
        email = (TextView) findViewById(R.id.message_email);
        text = (TextView) findViewById(R.id.message_text);
        eventStart = (TextView) findViewById(R.id.event_start);
        eventEnd = (TextView) findViewById(R.id.event_end);
        buttonAcceptBooking = (Button) findViewById(R.id.buttonAcceptBooking);

        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();





        Intent intent = getIntent();
        if (intent.hasExtra("notificationID")) {
            int notificationID = intent.getIntExtra("notificationID", 0);
            notification = (BookingNotification) ((MobileApplication) getApplicationContext()).getNotificationsList().get(notificationID);
            slotID = notification.getTimeslot();

            sname = notification.getName();
            semail = notification.getEmail();
            stext = notification.getMessage();
        } else if (intent.hasExtra("name")
                &&intent.hasExtra("email")
                &&intent.hasExtra("message")
                &&intent.hasExtra("slotID")){
            sname = intent.getStringExtra("name");
            semail = intent.getStringExtra("email");
            stext = intent.getStringExtra("message");
            slotID = intent.getStringExtra("slotID");
        }

        name.setText(stext);
        email.setText(semail);
        text.setText(stext);


        if (((MobileApplication) getApplicationContext()).isCredentialReady(this)){
            GoogleAccountCredential credential = ((MobileApplication) getApplicationContext()).getmCredential();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
            getEventAsync(slotID);
        }



        buttonAcceptBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Event event = new Event()
                        .setSummary("meeting with "+sname)
                        .setLocation(timeslot.getLocation())
                        .setDescription("meeting with "+sname+"\n"
                        +"message: "+stext);

                event.setStart(timeslot.getStart());

                event.setEnd(timeslot.getEnd());

                String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
                event.setRecurrence(Arrays.asList(recurrence));


                //event.send
                setEventAsync(event);

                WorkersAPI workersApi = RetrofitClient.getRetrofitInstance().create(WorkersAPI.class);

                SharedPreferences sharedPreferences = getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", null);
                Call<String> call = workersApi.removeWorkerTimeslot("Bearer " + token,
                        slotID);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("remove Timeslot", t.getMessage());
                    }
                });

                Intent gmail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",semail, null));
                //gmail.setClassName("com.google.android.gm","com.google.android.gm.ComposeActivityGmail");
                gmail.putExtra(Intent.EXTRA_EMAIL, new String[] { semail });
                //gmail.setData(Uri.parse(notification.getEmail()));
                gmail.putExtra(Intent.EXTRA_SUBJECT, "enter something");
                //gmail.setType("plain/text");
                gmail.putExtra(Intent.EXTRA_TEXT, "hello "+sname+"\n"+
                        "I have accepted your request to meet from "+timeslot.getStart().getDateTime().toString()
                        +" until "+timeslot.getEnd().getDateTime().toString()+" in/at "+ timeslot.getLocation()+"\n"+
                        "Please be on time");
                startActivity(Intent.createChooser(gmail, "Send Email"));
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void getEventAsync(String slotID) {

        new AsyncTask<Void, Void, String>() {
            private Exception mLastError = null;
            private boolean FLAG = false;


            @Override
            protected String doInBackground (Void...voids){
                try {
                    timeslot = mService.events().get(timeslotCalendarId,slotID).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute (String s){
                super.onPostExecute(s);
                if (timeslot != null) {
                    eventStart.setText("starts " + timeslot.getStart().getDateTime().toString());
                    eventEnd.setText("ends " + timeslot.getEnd().getDateTime().toString());
                } else {
                    showErrorDialog();
                }
                //getResultsFromApi();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void setEventAsync(Event event) {

        new AsyncTask<Void, Void, String>() {
            private Exception mLastError = null;
            private boolean FLAG = false;


            @Override
            protected String doInBackground (Void...voids){
                if(mService!=null) {
                    try {
                        mService.events().insert(calendarId, event).setSendNotifications(true).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        name.setText(((MobileApplication) getApplicationContext()).processActivityResult(requestCode,resultCode,data));
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        if (((MobileApplication) getApplicationContext()).isCredentialReady(this)){
            GoogleAccountCredential credential = ((MobileApplication) getApplicationContext()).getmCredential();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
            getEventAsync(slotID);
        }
    }

    private void showErrorDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(BookingActivity.this);
        dialog.setMessage("It seems that you didn't set up your calendars correctly. Do you want to set up your calendars or inform the visitor?");
        dialog.setPositiveButton("set calendars", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(BookingActivity.this, SetCalendarActivity.class);
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("inform visitor", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent gmail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",notification.getEmail(), null));
                //gmail.setClassName("com.google.android.gm","com.google.android.gm.ComposeActivityGmail");
                gmail.putExtra(Intent.EXTRA_EMAIL, new String[] { notification.getEmail() });
                //gmail.setData(Uri.parse(notification.getEmail()));
                gmail.putExtra(Intent.EXTRA_SUBJECT, "Your booking");
                //gmail.setType("plain/text");
                gmail.putExtra(Intent.EXTRA_TEXT, "Hello "+notification.getName()+"\n"+
                        "I regret to inform you, that there was a problem with your booking. Please contact me for further information.");
                startActivity(Intent.createChooser(gmail, "Send Email"));
            }
        });
        dialog.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        dialog.show();
    }
}
