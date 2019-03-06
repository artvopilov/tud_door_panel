package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.app.Application;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.model.Message;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.RetrofitClient;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.interfacesApi.MessagesAPI;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.interfacesApi.WorkerAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabletApplication extends MultiDexApplication {
    private final String TAG = "TabletApplicationTAG";

    private List<Worker> workerList = new ArrayList<Worker>();
    private List<Message> messageList = new ArrayList<>();

    final String UPDATE_GUI_FILTER = "de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.updateGUI";
    final String UPD_CHAT_FILTER = "de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.updateChat";

    public String room = "80b";

    public String getRoom() {
        return room;
    }

    public Worker getWorker(int position) {
        return workerList.get(position);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        pullMessages();
        pullWorkers();
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
        //TODO: When the server is off, the code below causes error
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

    public void excludeWorker(int id) {
        for (Worker candidate: workerList) {
            if (candidate.getId() == id) {
                workerList.remove(candidate);
                break;
            }
        }
        Intent intent = new Intent(UPDATE_GUI_FILTER);
        sendBroadcast(intent);
    }

    private void sortMessages() {
        if (!messageList.isEmpty()) {
            Collections.sort(messageList, (o1, o2) -> {
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
        }
    }

    public List<Message> getMessages() {
        return messageList;
    }

    public void addMessage(Message message) {
        messageList.add(0, message);
        if (messageList.size() > 30) {
            messageList.remove(29);
        }
        Intent intent = new Intent(UPD_CHAT_FILTER);
        sendBroadcast(intent);
    }

    public void pullMessages() {
        MessagesAPI messagesAPI = RetrofitClient.getRetrofitInstance().create(MessagesAPI.class);
        Call<List<Message>> call = messagesAPI.getRecentMessages(room);
        Log.d(TAG, "Automatic request for messages is sent");
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                Log.d(TAG, "Successful response");
                messageList = response.body();
                sortMessages();
                Intent intent = new Intent(UPD_CHAT_FILTER);
                sendBroadcast(intent);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.d(TAG, "Bad response");
            }
        });
    }

    public void pullWorkers(){
        WorkerAPI workerAPI = RetrofitClient.getRetrofitInstance().create(WorkerAPI.class);
        Call<List<Worker>> call= workerAPI.getAllWorkers(room);
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
