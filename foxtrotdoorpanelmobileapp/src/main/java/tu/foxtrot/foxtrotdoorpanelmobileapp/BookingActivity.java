package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.model.Event;

import java.io.IOException;
import java.util.Arrays;

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

    private com.google.api.services.calendar.Calendar mService = null;

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

        mService = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, ((MobileApplication) getApplicationContext()).getmCredential())
                .setApplicationName("Google Calendar API Android Quickstart")
                .build();

        Intent intent = getIntent();
        int notificationID = intent.getIntExtra("notificationID",0);
        BookingNotification notification = (BookingNotification) ((MobileApplication)getApplicationContext()).getNotificationsList().get(notificationID);
        String slotID = notification.getTimeslot();

        getEventAsync(slotID);
        name.setText(notification.getName());
        email.setText(notification.getEmail());
        text.setText(notification.getMessage());

        buttonAcceptBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Event event = new Event()
                        .setSummary("meeting with "+notification.getName())
                        .setLocation(timeslot.getLocation())
                        .setDescription("meeting with "+notification.getName()+"\n"
                        +"message: "+notification.getMessage());

                event.setStart(timeslot.getStart());

                event.setEnd(timeslot.getEnd());

                String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
                event.setRecurrence(Arrays.asList(recurrence));


                //event.send
                setEventAsync(event);

                Intent gmail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",notification.getEmail(), null));
                //gmail.setClassName("com.google.android.gm","com.google.android.gm.ComposeActivityGmail");
                gmail.putExtra(Intent.EXTRA_EMAIL, new String[] { notification.getEmail() });
                //gmail.setData(Uri.parse(notification.getEmail()));
                gmail.putExtra(Intent.EXTRA_SUBJECT, "enter something");
                //gmail.setType("plain/text");
                gmail.putExtra(Intent.EXTRA_TEXT, "hello "+notification.getName()+"\n"+
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
                } catch (IOException e) {
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
}
