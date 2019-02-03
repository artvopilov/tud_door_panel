package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.R;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.TabletApplication;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.RetrofitClient;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.interfacesApi.TabletsAPI;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.models.Tablet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Map<String, String> payload = remoteMessage.getData();
        if (payload != null) {
            Log.d(TAG, "Message data payload: " + payload);

            switch (payload.get("subject")) {
                case "changeStatus":
                    String status = payload.get("status");
                    Log.d(TAG, "Message status: " + status);

                    String id = payload.get("workerId");
                    Log.d(TAG, "worker id: " + id);

                    if (status != null && id != null){
                        ((TabletApplication)getApplicationContext()).updateWorker(parseInt(id),
                                "status", status);
                    }
                    break;
                case "newWorker":
                    ((TabletApplication)getApplicationContext()).pullWorkers();
                    break;
            }
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        // from now we don't use tokens, but topics for Cloud Messaging
        // sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        TabletsAPI tabletsAPI = RetrofitClient.getRetrofitInstance().create(TabletsAPI.class);
        Call<String> call = tabletsAPI.registerTablet(1, token);
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String resp = response.body();
                Log.d(TAG, "Token registration: " + resp);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "Token registration: " + t.getMessage());
            }
        });
    }
}
