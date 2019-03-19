package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;

public class CreateTimeslotActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private View eventLengthLayout;
    private View weekdayLayout;
    private Spinner unitsSpinner;
    private Spinner weekdaySpinner;
    private Spinner timeUnitSpinner;
    private EditText timeslotLength;

    private TimePicker startTime;
    private DatePicker startDate;
    private TimePicker endTime;
    private DatePicker endDate;
    private final Calendar c = Calendar.getInstance();
    private int hour = c.get(Calendar.HOUR_OF_DAY);
    private int minute = c.get(Calendar.MINUTE);
    private Button createEvent;
    private Button cancelEvent;
    private EditText eventTitle;
    private EditText eventDes;
    private EditText eventLocation;
    private EditText eventAttendee;
    private EventAttendee eventAttendeeEmail[];

    private String mode;

    private boolean changedEndTime;
    private boolean changedEndTimeInternal;

    private WorkersAPI workersApi;

    private com.google.api.services.calendar.Calendar mService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timeslot);

        workersApi = RetrofitClient.getRetrofitInstance().create(WorkersAPI.class);

        Spinner staticSpinner = (Spinner) findViewById(R.id.static_spinner);
        unitsSpinner = (Spinner) findViewById(R.id.spinnerTimeUnit);
        weekdaySpinner = (Spinner) findViewById(R.id.spinnerWeekday);
        timeUnitSpinner = (Spinner) findViewById(R.id.spinnerTimeUnit);
        eventLengthLayout = findViewById(R.id.eventLengthLayout);
        weekdayLayout = findViewById(R.id.eventWeekdayLayout);
        timeslotLength = (EditText) findViewById(R.id.eventLength);

        eventTitle = (EditText) findViewById(R.id.eventTitle);
        eventDes = (EditText) findViewById(R.id.eventDes);
        eventLocation = (EditText) findViewById(R.id.eventLocation);

        startDate = (DatePicker) findViewById(R.id.startDate);

        Intent intent = getIntent();
        if (intent.hasExtra("year")&&intent.hasExtra("month")&&intent.hasExtra("day")){
            startDate.updateDate(intent.getIntExtra("year",2019),
                    intent.getIntExtra("month",1),
                    intent.getIntExtra("day",1));
        }

        startTime = (TimePicker) findViewById(R.id.startTime);
        endTime = (TimePicker) findViewById(R.id.endTime);
        endDate = startDate;

        endTime.setCurrentHour(startTime.getCurrentHour()+1);
        endTime.setCurrentMinute(startTime.getCurrentMinute());

        changedEndTime = false;
        changedEndTimeInternal = false;

        endTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (changedEndTimeInternal){
                    changedEndTimeInternal = false;
                }else {
                    changedEndTime = true;
                }
            }
        });

        startTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (!changedEndTime){
                    changedEndTimeInternal = true;
                    endTime.setCurrentHour(startTime.getCurrentHour()+1);
                    endTime.setCurrentMinute(startTime.getCurrentMinute());
                }
            }
        });



        createEvent = (Button) findViewById(R.id.createEvent);
        cancelEvent = (Button) findViewById(R.id.cancelEvent);

        createEvent.setOnClickListener(this);
        cancelEvent.setOnClickListener(this);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.createTimeslot_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSpinnerChange((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        onSpinnerChange("single timeslot");
    }

    private void onSpinnerChange(String selected){
        mode = selected;
        switch (selected){
            case "Single timeslot":
                eventLengthLayout.setVisibility(View.GONE);
                weekdayLayout.setVisibility(View.GONE);
                startDate.setVisibility(View.VISIBLE);
                endDate.setVisibility(View.VISIBLE);
                break;
            case "Automatic repeating timeslots":
                eventLengthLayout.setVisibility(View.VISIBLE);

                ArrayAdapter<CharSequence> unitsAdapter = ArrayAdapter
                        .createFromResource(this, R.array.TimeUnits_array,
                                android.R.layout.simple_spinner_item);

                unitsAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                unitsSpinner.setAdapter(unitsAdapter);

                weekdayLayout.setVisibility(View.VISIBLE);

                ArrayAdapter<CharSequence> weekdayAdapter = ArrayAdapter
                        .createFromResource(this, R.array.Weekday_array,
                                android.R.layout.simple_spinner_item);

                weekdayAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                weekdaySpinner.setAdapter(weekdayAdapter);

                startDate.setVisibility(View.GONE);
                endDate.setVisibility(View.GONE);
        }
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

    Event event;
    String calendarId;


    void insertEvent(String summary, String location, String des, DateTime startDate, DateTime endDate, EventAttendee[] eventAttendees) throws IOException {

        tu.foxtrot.foxtrotdoorpanelmobileapp.network.models.Event ourEvent = new tu.foxtrot.foxtrotdoorpanelmobileapp.network.models.Event();

        ourEvent.setStart(new Date(startDate.getValue()));
        ourEvent.setEnd(new Date(endDate.getValue()));
        ourEvent.setName(summary);
        ourEvent.setId(ourEvent.hashCode()); //TODO: this is probably not the best solution

        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        Call<String> call = workersApi.addWorkerTimeslot("Bearer " + token, ourEvent);

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

        event = new Event()
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



        calendarId = ((MobileApplication) getApplicationContext()).getTimeslotsCalendar();

        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        if (((MobileApplication) getApplicationContext()).isCredentialReady(this)){
            GoogleAccountCredential credential = ((MobileApplication) getApplicationContext()).getmCredential();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
            //event.send
            if(mService!=null)
                event =  mService.events().insert(calendarId, event).setSendNotifications(true).execute();

        }




    }

    void insertAutoTimeslot(String weekday,int slotLength, String summary, String location, String des, DateTime startDate, DateTime endDate, EventAttendee[] eventAttendees) {
        AutoTimeslotReceiver.setAutoTimeslot(weekday, slotLength, summary, location, des, startDate, endDate, eventAttendees, getString(R.string.preference_file_key));
        Intent alarmIntent = new Intent(getApplicationContext(), AutoTimeslotReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        try {
            manager.cancel(pendingIntent);
        } catch (Exception e) {

        }
        int interval = 10000;//3600000;//every 10h
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("add Timeslot",((MobileApplication) getApplicationContext()).processActivityResult(requestCode,resultCode,data)); //TODO: show this to the user somehow
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        if (((MobileApplication) getApplicationContext()).isCredentialReady(this)){
            GoogleAccountCredential credential = ((MobileApplication) getApplicationContext()).getmCredential();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
            //event.send
            if(mService!=null) {
                try {
                    event =  mService.events().insert(calendarId, event).setSendNotifications(true).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.cancelEvent:
                intent = new Intent(CreateTimeslotActivity.this, Settings.class);
                startActivity(intent);
                break;
            case R.id.createEvent:
                int startTimeHour = startTime.getCurrentHour();
                int startTimeMinute = startTime.getCurrentMinute();
                Calendar startCalendar = Calendar.getInstance();
                startCalendar.set(Calendar.DAY_OF_MONTH, startDate.getDayOfMonth());
                startCalendar.set(Calendar.MONTH, startDate.getMonth());
                startCalendar.set(Calendar.YEAR, startDate.getYear());
                startCalendar.set(Calendar.HOUR_OF_DAY, startTimeHour);
                startCalendar.set(Calendar.MINUTE, startTimeMinute);
                Date startDate = startCalendar.getTime();

                DateTime start = new DateTime(startDate);

                int endTimeHour = endTime.getCurrentHour();
                int endTimeMinute = endTime.getCurrentMinute();
                if (endTimeHour < startTimeHour) {
                    Toast.makeText(CreateTimeslotActivity.this,
                            "Time error | Try again", Toast.LENGTH_SHORT).show();
                    return;
                } else if (endTimeHour == startTimeHour && endTimeMinute < startTimeMinute) {
                    Toast.makeText(CreateTimeslotActivity.this,
                            "Time error | Try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                Calendar endCalendar = Calendar.getInstance();
                endCalendar.set(Calendar.DAY_OF_MONTH, endDate.getDayOfMonth());
                endCalendar.set(Calendar.MONTH, endDate.getMonth());
                endCalendar.set(Calendar.YEAR, endDate.getYear());
                endCalendar.set(Calendar.HOUR_OF_DAY, endTimeHour);
                endCalendar.set(Calendar.MINUTE, endTimeMinute);
                Date endDate = endCalendar.getTime();
                DateTime end = new DateTime(endDate);

                eventAttendeeEmail = new EventAttendee[3];

                StringBuffer buffer = new StringBuffer(eventTitle.getText().toString()+"\n");
                buffer.append("\n");
                buffer.append(eventDes.getText().toString());

                switch (mode){
                    case "Single timeslot":
                        createEventAsync(eventTitle.getText().toString(), eventLocation.getText().toString(), buffer.toString(), start, end, eventAttendeeEmail );
                        break;
                    case "Automatic repeating timeslots":
                        String weekday = weekdaySpinner.getSelectedItem().toString();
                        int length = Integer.parseInt(timeslotLength.getText().toString());
                        switch (timeUnitSpinner.getSelectedItem().toString()){
/*                            case "seconds":
                                break;*/
                            case "minutes":
                                length = length*60;
                                break;
                            case "h":
                                length = length*60*60;
                        }
                        insertAutoTimeslot(weekday, length, eventTitle.getText().toString(), eventLocation.getText().toString(), buffer.toString(), start, end, eventAttendeeEmail );
                }

                intent = new Intent(CreateTimeslotActivity.this, Settings.class);
                startActivity(intent);
                break;
        }
    }


    private String startDateString, endDateString;
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        switch (datePicker.getId()){
            case R.id.startDate:
                startDateString  = i+" , "+i1+" , "+i2;
                System.out.println(startDateString);
                break;
            default:
                break;
        }
    }

    private String startTimeString, endTimeString;
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        switch (timePicker.getId()){
            case R.id.startTime:
                startTimeString = i+", "+i1;
                System.out.println(startTimeString);
                break;
            case R.id.endTime:
                endTimeString = i+", "+i1;
                System.out.println(endTimeString);
                break;
        }
    }
}
