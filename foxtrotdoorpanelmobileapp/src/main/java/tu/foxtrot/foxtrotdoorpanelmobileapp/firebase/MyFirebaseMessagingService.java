package tu.foxtrot.foxtrotdoorpanelmobileapp.firebase;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

//import tu.foxtrot.foxtrotdoorpanelmobileapp.TabletApplication;
import java.util.Map;

import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.BookingNotification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.MessageActivity;
import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.MessageNotification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.MobileApplication;
import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common.Notification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.NotificationsAllActivity;
import tu.foxtrot.foxtrotdoorpanelmobileapp.R;

import static android.app.Notification.VISIBILITY_PUBLIC;
import static java.lang.Integer.parseInt;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private static final String CHANNEL_ID = "FoxtrottNotifications";
    private int notificationID = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "Firebase notification from: " + remoteMessage.getFrom());
        Map<String, String> payload = remoteMessage.getData();
        if (payload != null) {
            Log.d(TAG, "Notification payload: " + payload);

            if (payload.get("type").equals("booking")) {
                handleBooking(payload);
            } else if (payload.get("type").equals("message")) {
                handleMessage(payload);
            }
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void handleMessage(Map<String, String> data){
        Log.d(TAG, "Message notification processing...");
        String message = data.get("message");
        String email = data.get("email");
        String name = data.get("name");
        String time = data.get("time");
        String date = data.get("date");
        int messageId = data.get("message_id") != null ? parseInt(data.get("message_id")) : -1;
        Log.d(TAG, "Got message id: " + messageId);
        ((MobileApplication)getApplicationContext()).addNotification(new MessageNotification(
                date, time, "message", email, name, message, messageId));

        Intent intent = new Intent(this, MessageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("Name", name);
        intent.putExtra("Email", email);
        intent.putExtra("Details", message);
        intent.putExtra("MessageId", messageId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

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
        notificationManager.notify(notificationID, mBuilder.build());
        notificationID++;

        Log.d(TAG, "Message notification processed: " + message);
    }

    private void handleBooking(Map<String, String> data){
        Log.d(TAG, "Booking notification processing...");

        String mail = data.get("email");
        String message = data.get("message");
        String timeslot = data.get("eventId");
        String email = data.get("email");
        String phone = data.get("phone");
        String name = data.get("name");
        String time = data.get("time");
        String date = data.get("date");
        ((MobileApplication)getApplicationContext()).addNotification(new BookingNotification(date,
                time, timeslot, message, email, phone, name));

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
}
