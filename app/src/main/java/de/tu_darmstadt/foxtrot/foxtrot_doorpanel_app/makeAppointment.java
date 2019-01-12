package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.adapter.EventListAdapter;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.model.ScheduledEvents;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class makeAppointment extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    GoogleAccountCredential mCredential;
    private TextView mOutputText;
    private ImageButton mCallApiButton;
    private ImageButton scheduleMeeting;
    ProgressDialog mProgress;
    private List<ScheduledEvents> scheduledEventsList = new ArrayList<ScheduledEvents>();
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;


    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { CalendarScopes.CALENDAR_READONLY, CalendarScopes.CALENDAR };
    private ListView eventListView;
    private EventListAdapter eventListAdapter;

    private WeekView mWeekView;


    private WeekViewEvent activeEvent = null;
    private int slotColor = 0x77770000;
    private int activeSlotColor = 0xFF550000;
    private List<WeekViewEvent> events;
    private  boolean nameEdited = false;
    private  boolean mailEdited = false;
    private  boolean messageEdited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        events = new ArrayList<WeekViewEvent>();
        WeekViewEvent testEvent = new WeekViewEvent();
        testEvent.setStartTime(Calendar.getInstance());
        testEvent.setName("free slot");
        Calendar end = Calendar.getInstance();
        end.add(Calendar.HOUR,1);
        testEvent.setEndTime(end);
        testEvent.setColor(getResources().getColor(R.color.colorSlot));
        events.add(testEvent);
        WeekViewEvent testEvent2 = new WeekViewEvent();
        Calendar start2 = Calendar.getInstance();
        start2.add(Calendar.HOUR,2);
        testEvent2.setStartTime(start2);
        testEvent2.setName("free slot");
        Calendar end2 = Calendar.getInstance();
        end2.add(Calendar.HOUR,3);
        testEvent2.setEndTime(end2);
        testEvent2.setColor(getResources().getColor(R.color.colorSlot));
        events.add(testEvent2);

        WeekViewEvent testEvent3 = new WeekViewEvent();
        Calendar start3 = Calendar.getInstance();
        start3.add(Calendar.DATE,1);
        testEvent3.setStartTime(start3);
        testEvent3.setName("team meeting");
        Calendar end3 = Calendar.getInstance();
        end3.add(Calendar.HOUR,1);
        end3.add(Calendar.DATE,1);
        testEvent3.setEndTime(end3);
        testEvent3.setColor(getResources().getColor(R.color.colorEvent));
        events.add(testEvent3);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab.setEnabled(false);

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {


                if (event.getColor()==getResources().getColor(R.color.colorSlot)) {

                    if (!(activeEvent == null)) {
                        activeEvent.setColor(getResources().getColor(R.color.colorSlot));
                    }
                    activeEvent = event;
                    activeEvent.setColor(getResources().getColor(R.color.colorSlotActive));
                    mWeekView.notifyDatasetChanged();


                    if (nameEdited && mailEdited && messageEdited) {

                        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorSlotActive)));
                        fab.setEnabled(true);
                    }

                }



            }
        });

        ((EditText) findViewById(R.id.editName)).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                nameEdited = true;
                Log.v("DoorPanel", "name edited" );
                if (activeEvent != null && mailEdited && messageEdited){
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorSlotActive)));
                    fab.setEnabled(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        ((EditText) findViewById(R.id.editEMail)).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                mailEdited = true;
                Log.v("DoorPanel", "mail edited" );
                if (activeEvent != null && nameEdited && messageEdited){
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorSlotActive)));
                    fab.setEnabled(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        ((EditText) findViewById(R.id.editMessage)).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                messageEdited = true;
                Log.v("DoorPanel", "message edited" );
                if (activeEvent != null && mailEdited && nameEdited){
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorSlotActive)));
                    fab.setEnabled(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

                List<WeekViewEvent> theseEvents = new ArrayList<WeekViewEvent>();

                for (WeekViewEvent event: events) {
                    int year = event.getStartTime().get(Calendar.YEAR);
                    int month = event.getStartTime().get(Calendar.MONTH)+1;
                    if (year==newYear && month==newMonth){
                        theseEvents.add(event);
                    }
                }

                return theseEvents;
            }
        });

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

            }
        });

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Syncing with calendar..");


        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
        getResultsFromApi();


    }

    private void getResultsFromApi() {
        if (! isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (! isDeviceOnline()) {
            mOutputText.setText("No network connection available.");
        } else {
            new makeAppointment.MakeRequestTask(mCredential).execute();
        }
    }

    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    mOutputText.setText(
                            "This app requires Google Play Services. Please install " +
                                    "Google Play Services on your device and relaunch this app.");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }


    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                makeAppointment.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    /**
     * An asynchronous task that handles the Google Calendar API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private com.google.api.services.calendar.Calendar mService = null;
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {

        private Exception mLastError = null;
        private boolean FLAG = false;

        public MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }

        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                getDataFromApi();
            } catch (Exception e) {
                e.printStackTrace();
                mLastError = e;
                cancel(true);
                return null;
            }
            return null;
        }

        /**
         * Fetch a list of the next 10 events from the primary calendar.
         * @return List of Strings describing returned events.
         * @throws IOException
         */
        private void getDataFromApi() throws IOException {
            // List the next 10 events from the primary calendar.
            DateTime now = new DateTime(System.currentTimeMillis());
            List<String> eventStrings = new ArrayList<String>();
            Events googleEvents = mService.events().list("mlgh7qd6skb30ppnvogghbk2pc@group.calendar.google.com")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = googleEvents.getItems();
            ScheduledEvents scheduledEvents;
            scheduledEventsList.clear();
            for (Event event : items) {
                WeekViewEvent weekViewEvent = new WeekViewEvent();

                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                DateTime end = event.getEnd().getDateTime();
                if (end == null) {
                    end = event.getEnd().getDate();
                }

                Calendar startCalendar = Calendar.getInstance();
                startCalendar.setTime(new Date(start.getValue()));

                weekViewEvent.setStartTime(startCalendar);
                weekViewEvent.setName(event.getSummary());
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(new Date(end.getValue()));
                weekViewEvent.setEndTime(endCalendar);
                weekViewEvent.setColor(getResources().getColor(R.color.colorSlot));
                events.add(weekViewEvent);

                mWeekView.notifyDatasetChanged();


                System.out.println("-----"+event.getDescription()+", "+event.getId()+", "+event.getLocation());
                System.out.println(event.getAttendees());
                eventStrings.add(String.format("%s (%s)", event.getSummary(), start));
                eventStrings.add(String.format("%s (%s)", event.getSummary(), end));
            }
        }

        @Override
        protected void onPreExecute() {
            //mOutputText.setText("");
            mProgress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {
            mProgress.hide();
            System.out.println("--------------------"+scheduledEventsList.size());
            if (scheduledEventsList.size()<=0) {
                //mOutputText.setText("No results returned.");
            } else {
                //eventListAdapter = new EventListAdapter(makeAppointment.this, scheduledEventsList);
                //eventListView.setAdapter(eventListAdapter);
            }
        }

        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            CalendarActivity.REQUEST_AUTHORIZATION);
                } else {
                    //mOutputText.setText("The following error occurred:\n"
                    //        + mLastError.getMessage());
                }
            } else {
                //mOutputText.setText("Request cancelled.");
            }
        }
    }

}
