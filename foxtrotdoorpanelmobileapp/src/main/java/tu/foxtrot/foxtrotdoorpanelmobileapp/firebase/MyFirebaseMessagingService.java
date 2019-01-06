package tu.foxtrot.foxtrotdoorpanelmobileapp.firebase;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

//import tu.foxtrot.foxtrotdoorpanelmobileapp.TabletApplication;
import tu.foxtrot.foxtrotdoorpanelmobileapp.MessageNotification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.MobileApplication;
import tu.foxtrot.foxtrotdoorpanelmobileapp.Notification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.R;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.EmployeesAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.MobilesAPI;

import static java.lang.Integer.parseInt;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private static final String CHANNEL_ID = "FoxtrottNotifications";
    private int notificationID = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            String message = remoteMessage.getData().get("message");
            Log.d(TAG, "Message: " + message);

            String id = remoteMessage.getData().get("employeeId");

            Log.d(TAG, "worker id: " + id);

            Notification notification = new MessageNotification("22.02.2018",
                    "10:23", "Message", message,
                    "mister@gmail.com", "Anon");

            ((MobileApplication)getApplicationContext()).addNotification(notification);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_envelope)
                    .setContentTitle("new message from door panel")
                    .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(message))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationID, mBuilder.build());
            notificationID++;
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
        MobilesAPI tabletsAPI = RetrofitClient.getRetrofitInstance().create(MobilesAPI.class);
        Call<String> call = tabletsAPI.registerMobile(1, token);
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

    private void sendRegistrationToServer(String token) {

    }
}
