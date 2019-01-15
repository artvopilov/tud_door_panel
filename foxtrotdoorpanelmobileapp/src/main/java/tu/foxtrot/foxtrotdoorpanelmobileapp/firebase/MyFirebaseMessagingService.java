package tu.foxtrot.foxtrotdoorpanelmobileapp.firebase;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

//import tu.foxtrot.foxtrotdoorpanelmobileapp.TabletApplication;
import tu.foxtrot.foxtrotdoorpanelmobileapp.BookingNotification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.MessageActivity;
import tu.foxtrot.foxtrotdoorpanelmobileapp.MessageNotification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.MobileApplication;
import tu.foxtrot.foxtrotdoorpanelmobileapp.Notification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.NotificationsAllActivity;
import tu.foxtrot.foxtrotdoorpanelmobileapp.R;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.MobilesAPI;

import static android.app.Notification.VISIBILITY_PUBLIC;
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


            if (remoteMessage.getData().containsKey("timeslot")) {
                handleBooking(remoteMessage);
            } else {
                handleMessage(remoteMessage);
            }




        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void handleMessage(RemoteMessage remoteMessage){

        String message = remoteMessage.getData().get("message");
        String id = remoteMessage.getData().get("employeeId");

        Notification notification = new MessageNotification("22.02.2018",
                "10:23", "Message", message,
                "mister@gmail.com", "Anon");

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, MessageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("Name", ((MessageNotification) notification).getName());
        intent.putExtra("Email", ((MessageNotification) notification).getEmail());
        intent.putExtra("Details", ((MessageNotification) notification).getDetails());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        ((MobileApplication)getApplicationContext()).addNotification(notification);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_envelope)
                .setContentTitle("new message from door panel")
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVisibility(VISIBILITY_PUBLIC);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationID, mBuilder.build());
        notificationID++;

        Log.d(TAG, "Message: " + message);



        Log.d(TAG, "worker id: " + id);
    }

    private void handleBooking(RemoteMessage remoteMessage){

        String message = remoteMessage.getData().get("message");
        String id = remoteMessage.getData().get("employeeId");
        String timeslot = remoteMessage.getData().get("timeslot");
        String mail = remoteMessage.getData().get("mail");
        String number = remoteMessage.getData().get("number");
        String name = remoteMessage.getData().get("name");

        Notification notification = new BookingNotification("22.02.2018",
                "10:23", timeslot,  message,
                mail, number, name);

        ((MobileApplication)getApplicationContext()).addNotification(notification);

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, NotificationsAllActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_calendar_alt)
                .setContentTitle("new booking from door panel")
                .setContentText(name+" requested timeslot "+timeslot+" with reason: "+message)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVisibility(VISIBILITY_PUBLIC);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationID, mBuilder.build());
        notificationID++;
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
