package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;

/**
 * The type Auto timeslot receiver.
 */
public class AutoTimeslotReceiver extends BroadcastReceiver {

    private static class AutoTimeslot{
        /**
         * The Summary.
         */
        String summary;
        /**
         * The Location.
         */
        String location;
        /**
         * The Des.
         */
        String des;
        /**
         * The Start date.
         */
        DateTime startDate;
        /**
         * The End date.
         */
        DateTime endDate;
        /**
         * The Slot length.
         */
        int slotLength;
        /**
         * The Event attendees.
         */
        EventAttendee[] eventAttendees;
    }

    private static Map<String,AutoTimeslot> autoTimeslotMap = new HashMap<String, AutoTimeslot>();

    private com.google.api.services.calendar.Calendar mService = null;

    private static WorkersAPI workersApi;

    private static String string_preference_file_key;


    /**
     * Set auto timeslot.
     *
     * @param weekday             the weekday
     * @param slotLength          the slot length
     * @param summary             the summary
     * @param location            the location
     * @param des                 the des
     * @param startDate           the start date
     * @param endDate             the end date
     * @param eventAttendees      the event attendees
     * @param preference_file_key the preference file key
     */
    public static void setAutoTimeslot(String weekday, int slotLength, final String summary, final String location, final String des, final DateTime startDate, final DateTime endDate, final EventAttendee[]
            eventAttendees, String preference_file_key){
        AutoTimeslot autoTimeslot = new AutoTimeslot();
        autoTimeslot.summary = summary;
        autoTimeslot.location = location;
        autoTimeslot.des = des;
        autoTimeslot.startDate = startDate;
        autoTimeslot.endDate = endDate;
        autoTimeslot.eventAttendees = eventAttendees;
        autoTimeslot.slotLength = slotLength;
        autoTimeslotMap.put(weekday,autoTimeslot);

        workersApi = RetrofitClient.getRetrofitInstance().create(WorkersAPI.class);

        string_preference_file_key = preference_file_key;

    }

    @Override
    public void onReceive(Context context, Intent intent) {


        com.google.api.services.calendar.Calendar.Events.List events;
        com.google.api.services.calendar.Calendar.Events.List slots;

        GoogleAccountCredential credential = ((MobileApplication) context.getApplicationContext()).getmCredential();

        if (credential != null){
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
            Calendar cal = Calendar.getInstance();
            for (int i=1;i<=14;i++){
                cal.add(Calendar.DATE,1);
                SimpleDateFormat weekdayformat = new SimpleDateFormat("EEEE");
                AutoTimeslot timeslotThisDay = autoTimeslotMap.get(weekdayformat.format(cal.getTime()));

                if (timeslotThisDay!=null) {
                    Calendar start = Calendar.getInstance();
                    start.setTime(new Date(timeslotThisDay.startDate.getValue()));
                    start.set(Calendar.DATE, cal.get(Calendar.DATE));

                    Calendar end = Calendar.getInstance();
                    end.setTime(new Date(timeslotThisDay.endDate.getValue()));
                    end.set(Calendar.DATE, cal.get(Calendar.DATE));

                    for (Calendar time = start; time.before(end); ) {
                        Event slot = new Event();

                        EventDateTime startTime = new EventDateTime();
                        startTime.setDateTime(new DateTime(time.getTimeInMillis()));
                        startTime.setTimeZone("America/Los_Angeles");
                        slot.setStart(startTime);

                        time.add(Calendar.SECOND, timeslotThisDay.slotLength);

                        EventDateTime endTime = new EventDateTime();
                        endTime.setDateTime(new DateTime(time.getTimeInMillis()));
                        endTime.setTimeZone("America/Los_Angeles");
                        slot.setEnd(endTime);

                        slot.setSummary(timeslotThisDay.summary);
                        slot.setDescription(timeslotThisDay.des);
                        slot.setLocation(timeslotThisDay.location);

                        checkSlotAsync(slot, context);
                    }
                }
            }
        }


    }


    /**
     * Check slot async.
     *
     * @param slot    the slot
     * @param context the context
     */
    @SuppressLint("StaticFieldLeak")
    public void checkSlotAsync(Event slot, Context context) {

        new AsyncTask<Void, Void, String>() {
            private Exception mLastError = null;
            private boolean FLAG = false;

            com.google.api.services.calendar.model.Events events;
            com.google.api.services.calendar.model.Events slots;


            @Override
            protected String doInBackground (Void...voids){
                try {


                    String eventsID = ((MobileApplication) context.getApplicationContext()).getmCalendar();
                    com.google.api.services.calendar.Calendar.Events.List eventsRequest = mService.events().list(eventsID);
                    eventsRequest.setTimeMax(slot.getEnd().getDateTime());
                    eventsRequest.setTimeMin(slot.getStart().getDateTime());
                    events = eventsRequest.execute();

                    String slotsID = ((MobileApplication) context.getApplicationContext()).getTimeslotsCalendar();
                    com.google.api.services.calendar.Calendar.Events.List slotsRequest = mService.events().list(eventsID);
                    slotsRequest.setTimeMax(slot.getEnd().getDateTime());
                    slotsRequest.setTimeMin(slot.getStart().getDateTime());
                    slots = eventsRequest.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute (String s){
                super.onPostExecute(s);
                if (events.getItems().size()==0 && slots.getItems().size()==0) {
                    createEventAsync(slot, context);
                }
                //getResultsFromApi();
            }
        }.execute();
    }

    /**
     * Create event async.
     *
     * @param event   the event
     * @param context the context
     */
    @SuppressLint("StaticFieldLeak")
    public void createEventAsync(Event event, Context context) {

        new AsyncTask<Void, Void, String>() {
            private com.google.api.services.calendar.Calendar mService = null;
            private Exception mLastError = null;
            private boolean FLAG = false;


            @Override
            protected String doInBackground (Void...voids){
                try {
                    insertEvent(event,context);
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

    /**
     * The Event.
     */
    Event event;
    /**
     * The Calendar id.
     */
    String calendarId;


    /**
     * Insert event.
     *
     * @param event   the event
     * @param context the context
     * @throws IOException the io exception
     */
    void insertEvent(Event event,Context context) throws IOException {

        tu.foxtrot.foxtrotdoorpanelmobileapp.network.models.Event ourEvent = new tu.foxtrot.foxtrotdoorpanelmobileapp.network.models.Event();

        ourEvent.setStart(new Date(event.getStart().getDateTime().getValue()));
        ourEvent.setEnd(new Date(event.getEnd().getDateTime().getValue()));
        ourEvent.setName(event.getSummary());
        ourEvent.setId(ourEvent.hashCode()); //TODO: this is probably not the best solution

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                string_preference_file_key, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        Call<String> call = workersApi.addWorkerTimeslot(token, ourEvent);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String newStatus = response.body();

                Toast toast = Toast.makeText(context, "Status updated: " + newStatus,
                        Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("add Timeslot", t.getMessage());
            }
        });



        String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
        event.setRecurrence(Arrays.asList(recurrence));

        event.setId(Integer.toString(ourEvent.getId()));



        calendarId = ((MobileApplication) context.getApplicationContext()).getmCalendar();

        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();


        //event.send
        if(mService!=null)
            event =  mService.events().insert(calendarId, event).setSendNotifications(true).execute();





    }
}
