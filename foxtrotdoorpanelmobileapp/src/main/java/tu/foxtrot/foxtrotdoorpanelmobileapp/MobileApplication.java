package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects.LoginResponse;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects.Worker;

import static android.app.Activity.RESULT_OK;
import static tu.foxtrot.foxtrotdoorpanelmobileapp.SetCalendarActivity.REQUEST_ACCOUNT_PICKER;
import static tu.foxtrot.foxtrotdoorpanelmobileapp.SetCalendarActivity.REQUEST_GOOGLE_PLAY_SERVICES;
import static tu.foxtrot.foxtrotdoorpanelmobileapp.SetCalendarActivity.REQUEST_PERMISSION_GET_ACCOUNTS;

public class MobileApplication extends Application {

    private static final String MyPREFERENCES = "Foxtrot";
    private List<Notification> notificationsList = new ArrayList<Notification>();
    private String workerName;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { CalendarScopes.CALENDAR_READONLY, CalendarScopes.CALENDAR };

    private Activity activity;

    public List<Notification> getNotificationsList() {
        return notificationsList;
    }

    public void addNotification(Notification notification){
        notificationsList.add(notification);
    }

    public void pullWorkerName() {
        int workerId = getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE).getInt("topic", 0);
        WorkersAPI workersAPI = RetrofitClient.getRetrofitInstance().create(WorkersAPI.class);
        Call<Worker> call = workersAPI.getWorkerById(workerId);
        call.enqueue(new Callback<Worker>() {
            @Override
            public void onResponse(Call<Worker> call, Response<Worker> response) {
                Worker worker = response.body();
                workerName = worker.getName();
                Log.d("Application", "New worker name got: " + workerName);
            }

            @Override
            public void onFailure(Call<Worker> call, Throwable t) {

            }
        });
    }

    public String getWorkerName() {
        return workerName;
    }

    private int workerID;

    private GoogleAccountCredential mCredential;
    private String mCalendar;
    private String timeslotsCalendar;

    public int getWorkerID() {
        return workerID;
    }

    public void setWorkerID(int workerID) {
        this.workerID = workerID;
    }

    public GoogleAccountCredential getmCredential() {
        return mCredential;
    }

    public void setmCredential(GoogleAccountCredential mCredential) {
        this.mCredential = mCredential;
    }

    public String getTimeslotsCalendar() {
        return getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("timeslotsCalendar",timeslotsCalendar);
    }


    public String getmCalendar() {
        return getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("mCalendar",mCalendar);

    }

    public void setmCalendar(String mCalendar) {
        getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit().putString("mCalendar",mCalendar).apply();
        this.mCalendar = mCalendar;
    }

    public void setTimeslotsCalendar(String timeslotsCalendar) {
        getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit().putString("timeslotsCalendar",timeslotsCalendar).apply();
        this.timeslotsCalendar = timeslotsCalendar;
    }


    private boolean isReady;

    public boolean isCredentialReady(Activity activity) {
        isReady = false;
        this.activity = activity;
        if (mCredential == null) {
            mCredential = GoogleAccountCredential.usingOAuth2(
                    getApplicationContext(), Arrays.asList(SCOPES))
                    .setBackOff(new ExponentialBackOff());
        }
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!isDeviceOnline()) {
            //mOutputText.setText("No network connection available.");
        } else {
            isReady = true;
        }
        return isReady;
    }

    /**
     * Checks whether the device currently has a network connection.
     *
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
     *
     * @param connectionStatusCode code describing the presence (or lack of)
     *                             Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                activity,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                isCredentialReady(activity);
            } else {
                // Start a dialog from which the user can choose an account
                activity.startActivityForResult(
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

    protected String processActivityResult(
            int requestCode, int resultCode, Intent data) {
        String result = "";
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    result =
                            "This app requires Google Play Services. Please install " +
                                    "Google Play Services on your device and relaunch this app.";
                } else {
                    isCredentialReady(activity);
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        isCredentialReady(activity);
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    isCredentialReady(activity);
                }
                break;
        }
        return result;
    }
}
