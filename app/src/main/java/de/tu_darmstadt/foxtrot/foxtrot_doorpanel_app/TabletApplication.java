package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.app.Application;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.RetrofitClient;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.interfacesApi.EmployeesAPI;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.interfacesApi.TabletsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabletApplication extends Application {

    private List<Worker> workerList = new ArrayList<Worker>();

    final String UPDATE_GUI_FILTER = "de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.updateGUI";

    public Worker getWorker(int position) {
        return workerList.get(position);
    }

    public Worker getWorkerByID(int id){
        for (Worker worker : workerList){
            if (worker.getId()==id){
                return worker;
            }
        }
        return null;
    }

    public int getWorkerNum(){
        return workerList.size();
    }

    public void updateWorker(int id, String key, Object value){
        Worker worker = null;
        for (Worker candidate : workerList){
            if (candidate.getId() == id){
                worker = candidate;
            }
        }

        if (key.equals("status")) {
            if (worker != null) {
                worker.setStatus((String) value);
                Intent intent = new Intent(UPDATE_GUI_FILTER);
                sendBroadcast(intent);
            }
        }
    }

    public void pullEmployees(){
        EmployeesAPI employeesAPI = RetrofitClient.getRetrofitInstance().create(EmployeesAPI.class);
        Call<List<Worker>> call= employeesAPI.getAllEmployees();
        call.enqueue(new Callback<List<Worker>>() {
            @Override
            public void onResponse(Call<List<Worker>> call, Response<List<Worker>> response) {
                workerList = response.body();
                Intent intent = new Intent(UPDATE_GUI_FILTER);
                sendBroadcast(intent);
            }

            @Override
            public void onFailure(Call<List<Worker>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
