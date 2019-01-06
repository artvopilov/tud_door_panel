package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

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
            if (candidate.getId()==id){
                worker = candidate;
            }
        }

        if (key.equals("status")) {
            if (worker != null) {
                worker.setStatus((String) value);
                Intent i = new Intent(UPDATE_GUI_FILTER);
                sendBroadcast(i);
            }
        }
    }


    public void pullEmployees(){
        Retrofit r= new Retrofit.Builder().baseUrl("http://10.0.2.2:5000/").addConverterFactory(GsonConverterFactory.create()).build();
        retrieveEmployee employee_=r.create(retrieveEmployee.class);
        Call<List<Worker>> call= employee_.getIndividualEmployee();
        call.enqueue(new Callback<List<Worker>>() {
            @Override
            public void onResponse(Call<List<Worker>> call, Response<List<Worker>> response) {
                workerList = response.body();
                Intent i = new Intent(UPDATE_GUI_FILTER);
                sendBroadcast(i);

            }

            @Override
            public void onFailure(Call<List<Worker>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
