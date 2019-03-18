package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.firebase;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.ChatActivity;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.R;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.TabletApplication;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.model.Message;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.RetrofitClient;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.interfacesApi.TabletsAPI;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.models.Tablet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Date;

import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import static android.app.Notification.VISIBILITY_PUBLIC;
import static java.lang.Integer.parseInt;
import android.text.format.DateUtils;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private final String CHANNEL_ID = "FoxtrotTabletNotifications";

    private int notificationId = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        Date date1 = new Date();
        long curTimeInMs = date1.getTime();
        Date date_f = new Date(curTimeInMs + (5 * ONE_MINUTE_IN_MILLIS));
        String strDateFormat = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date_f);

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Map<String, String> payload = remoteMessage.getData();
        if (payload != null) {
            Log.d(TAG, "Message data payload: " + payload);
            TabletApplication tabletApplication = ((TabletApplication)getApplicationContext());
            switch (payload.get("subject")) {
                case "changeStatus":
                    String status = payload.get("status");
                    if(status.equals("Back in 5 min")){status="Would be back untill "+formattedDate;}
                    Log.d(TAG, "Message status: " + status);

                    String id = payload.get("workerId");
                    Log.d(TAG, "worker id: " + id);

                    if (status != null && id != null){
                        tabletApplication.updateWorker(parseInt(id),
                                "status", status);
                    }
                    break;
                case "newWorker":
                    tabletApplication.pullWorkers();
                    break;
                case "workerOutRoom":
                    int workerId = Integer.parseInt(payload.get("workerId"));
                    tabletApplication.excludeWorker(workerId);
                    break;
                case "workerInRoom":
                    tabletApplication.pullWorkers();
                    break;
                case "workerPhoto":
                    tabletApplication.pullWorkers();
                    break;
                case "addTimeslot":
                    tabletApplication.pullWorkers();
                    break;
                case "messageFromWorker":
                    String from = payload.get("from_worker");
                    String to = payload.get("to_visitor");
                    String date = payload.get("date");
                    String time = payload.get("time");
                    String text = payload.get("text");
                    tabletApplication.addMessage(new Message(text, date, time, from, to));
                    Log.d(TAG, String.format("Message from worker %s added", from));

                    createNotification(from, to);
                    break;
                default:
                    Log.d(TAG, "Unknown subject");
            }
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void createNotification(String from, String to) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_envelope)
                .setContentTitle(String.format("Message from %s to %s", from, to))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId++, builder.build());
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
