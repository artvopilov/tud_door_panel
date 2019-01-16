package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects.LoginResponse;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects.Worker;

public class MobileApplication extends Application {

    List<Notification> notificationsList = new ArrayList<Notification>();
    private String workerName;

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
}
