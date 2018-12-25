package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.models;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.MainActivity;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.R;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.Worker;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.WorkerAdapter;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.retrieveEmployee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tablet extends Application {
    public int id;
    public String roomNumber;
    private List<Worker> workerList = new ArrayList<Worker>();

    final String UPDATE_GUI = "de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.updateGUI";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Worker getWorker(int position) {
        return workerList.get(position);
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
        if (worker == null){
            return;
        }
        if (key == "status") {
            worker.setStatus((String) value);
            Intent i = new Intent(UPDATE_GUI);
            sendBroadcast(i);
            return;
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
                Intent i = new Intent(UPDATE_GUI);
                sendBroadcast(i);

            }

            @Override
            public void onFailure(Call<List<Worker>> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

}
