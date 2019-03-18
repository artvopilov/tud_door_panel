package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SetCalendarActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private GoogleAccountCredential mCredential;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { CalendarScopes.CALENDAR_READONLY, CalendarScopes.CALENDAR };

    private TextView mOutputText;
    private RadioGroup radioGroupTimeslots;
    private RadioGroup radioGroupMain;
    private Button submitButton;

    CalendarList calendarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_calendar);

        mOutputText = findViewById(R.id.textView3);
        radioGroupMain = findViewById(R.id.radioGroupMain);
        radioGroupTimeslots = findViewById(R.id.radioGroupTimeslots);
        submitButton = findViewById(R.id.button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCalendar();
            }
        });

        if (((MobileApplication) getApplicationContext()).isCredentialReady(this)){
            mCredential = ((MobileApplication) getApplicationContext()).getmCredential();
            new SetCalendarActivity.MakeRequestTask(mCredential,this).execute();
        }

    }

    private void saveCalendar() {
        List<CalendarListEntry> items = calendarList.getItems();
        ((MobileApplication) getApplicationContext()).setmCredential(mCredential);
        RadioButton mainActiveButton = radioGroupMain.findViewById(radioGroupMain.getCheckedRadioButtonId());
        int idxMain = radioGroupMain.indexOfChild(mainActiveButton);
        ((MobileApplication) getApplicationContext()).setmCalendar(items.get(idxMain).getId());
        RadioButton timeslotsActiveButton = radioGroupTimeslots.findViewById(radioGroupTimeslots.getCheckedRadioButtonId());
        int idxTimeslot = radioGroupTimeslots.indexOfChild(timeslotsActiveButton);
        ((MobileApplication) getApplicationContext()).setTimeslotsCalendar(items.get(idxTimeslot).getId());
        Intent intent = new Intent(SetCalendarActivity.this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mOutputText.setText(((MobileApplication) getApplicationContext()).processActivityResult(requestCode,resultCode,data));
        if (((MobileApplication) getApplicationContext()).isCredentialReady(this)){
            mCredential = ((MobileApplication) getApplicationContext()).getmCredential();
            new SetCalendarActivity.MakeRequestTask(mCredential,this).execute();
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
     * An asynchronous task that handles the Google Calendar API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private com.google.api.services.calendar.Calendar mService = null;

    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {

        private Exception mLastError = null;
        private boolean FLAG = false;

        private String message = "";

        private List<String> ids;

        private Context context;

        public MakeRequestTask(GoogleAccountCredential credential, Context context) {
            this.context = context;
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }

        /**
         * Background task to call Google Calendar API.
         *
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

        protected void getDataFromApi() {
            try {
                calendarList = mService.calendarList().list().execute();
                List<CalendarListEntry> items = calendarList.getItems();
                ids = new ArrayList<String>();
                for (CalendarListEntry item : items){
                    message = message +"\n"+ item.getId();
                    ids.add(item.getId());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(List<String> output) {
            mOutputText.setText("Found calendars");
            List<CalendarListEntry> items = calendarList.getItems();
            for (CalendarListEntry item : items) {
                RadioButton radioButton = new RadioButton(context);
                radioButton.setText(item.getSummary());
                radioGroupMain.addView(radioButton);
                radioButton = new RadioButton(context);
                radioButton.setText(item.getSummary());
                radioGroupTimeslots.addView(radioButton);
            }

        }
    }

}
