package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.Utils;
import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common.Notification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects.Worker;

import static java.util.Comparator.comparing;

public class MobileApplication extends Application {
    private final String UPD_NOTIF_FILTER = "tu.foxtrot.foxtrotdoorpanelmobileapp.UPDATE_NOTIFICATIONS";

    private List<Notification> notificationsList = new ArrayList<Notification>();
    private String workerName;

    public List<Notification> getNotificationsList() {
        Collections.sort(notificationsList, (o1, o2) -> {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            try {
                if (o1.getDate().compareTo(o2.getDate()) == 0) {
                    Date time1 = timeFormat.parse(o1.getTime());
                    Date time2 = timeFormat.parse(o2.getTime());
                    Log.d("MobileAppComparison", time1.toString());
                    Log.d("MobileAppComparison", time2.toString());
                    return time1.after(time2) ? -1 : 1;
                }
                Date date1 = dateFormat.parse(o1.getDate());
                Date date2 = dateFormat.parse(o2.getDate());
                return date1.after(date2) ? -1 : 1;
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });
        return notificationsList;
    }
    public void setNotificationsList(List<? extends Notification> notificationsList) {
        this.notificationsList = notificationsList != null ? (List<Notification>) notificationsList
                : new ArrayList<Notification>();
    }

    public void addNotification(Notification notification){
        notificationsList.add(0, notification);
        Intent intent = new Intent(UPD_NOTIF_FILTER);
        sendBroadcast(intent);
    }

    public void pullNotifications() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        Utils.getMessages(this, token);
        Utils.getBookings(this, token);
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

    public String getmCalendar() {
        return mCalendar;
    }

    public void setmCalendar(String mCalendar) {
        this.mCalendar = mCalendar;
    }
}
