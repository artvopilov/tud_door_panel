package tu.foxtrot.foxtrotdoorpanelmobileapp.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.MobileApplication;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.MessagesAPI;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;
import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.BookingNotification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.MessageNotification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common.Notification;

/**
 * The type Utils.
 */
public class Utils {
    private final static String TAG = "UTILS";
    private static WorkersAPI workersApi = RetrofitClient.getRetrofitInstance().create(WorkersAPI.class);
    private static MessagesAPI messagesApi = RetrofitClient.getRetrofitInstance().create(MessagesAPI.class);

    /**
     * Update worker status.
     *
     * @param context the context
     * @param token   the token
     * @param status  the status
     */
    public static void updateWorkerStatus(Context context, String token, String status) {
        Call<String> call = workersApi.updateWorkerStatus("Bearer " + token, status);
        Log.d(TAG, "Status update request sent");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String newStatus = response.body();
                Log.d(TAG, "Status updated: " + newStatus);
                Toast toast = Toast.makeText(context, "Status updated: " + newStatus,
                        Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "Status update error: " + t.getMessage());
            }
        });
    }

    /**
     * Update worker photo.
     *
     * @param context the context
     * @param token   the token
     * @param image   the image
     */
    public static void updateWorkerPhoto(Context context, String token, String image) {
        Call<String> call = workersApi.updateWorkerPhoto("Bearer " + token, image);
        Log.d(TAG, "Photo update request sent ");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String newRoom = response.body();
                Log.d(TAG, "Photo updated ");
                Toast.makeText(context, "photo updated:" + newRoom, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "Photo update error: " + t.getMessage());            }
        });
    }

    /**
     * Gets messages.
     *
     * @param mobileApplication the mobile application
     * @param token             the token
     */
    public static void getMessages(MobileApplication mobileApplication, String token) {
        Call<List<MessageNotification>> call = messagesApi.getMessages("Bearer " + token);
        Log.d(TAG, "Sent request for messages");

        call.enqueue(new Callback<List<MessageNotification>>() {
            @Override
            public void onResponse(Call<List<MessageNotification>> call,
                                   Response<List<MessageNotification>> response) {
                Log.d(TAG, "Response received: " + response.toString());
                List<MessageNotification> messageNotifications = response.body();
                if (messageNotifications == null) {
                    Log.d(TAG, "No messages");
                    return;
                }
                for (MessageNotification messageNotification : messageNotifications) {
                    messageNotification.setType("message");
                }
                mobileApplication.getNotificationsList().addAll(messageNotifications);
            }

            @Override
            public void onFailure(Call<List<MessageNotification>> call, Throwable t) {
                Log.d(TAG, "Messages pulling error: " + t.getMessage());
            }
        });
    }

    /**
     * Gets bookings.
     *
     * @param mobileApplication the mobile application
     * @param token             the token
     */
    public static void getBookings(MobileApplication mobileApplication, String token) {
        Call<List<BookingNotification>> call = messagesApi.getBookings("Bearer " + token);
        Log.d(TAG, "Sent request for bookings");

        call.enqueue(new Callback<List<BookingNotification>>() {
            @Override
            public void onResponse(Call<List<BookingNotification>> call,
                                   Response<List<BookingNotification>> response) {
                Log.d(TAG, "Response received: " + response.toString());
                List<BookingNotification> bookingNotifications = response.body();
                if (bookingNotifications == null) {
                    Log.d(TAG, "No bookings");
                    return;
                }
                for (BookingNotification bookingNotification : bookingNotifications) {
                    bookingNotification.setType("booking");
                }
                mobileApplication.getNotificationsList().addAll(bookingNotifications);
            }

            @Override
            public void onFailure(Call<List<BookingNotification>> call, Throwable t) {
                Log.d(TAG, "Bookings pulling error: " + t.getMessage());
            }
        });
    }

    /**
     * Update personal info.
     *
     * @param applicationContext the application context
     * @param token              the token
     * @param phone              the phone
     * @param email              the email
     * @param room               the room
     */
    public static void updatePersonalInfo(Context applicationContext, String token, String phone,
                                          String email, String room) {
        Call<String> call = workersApi.updatePersonalInfo("Bearer " + token,
                phone, email, room);
        Log.d(TAG, "Personal info update request sent");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String status = response.body() != null ? response.body() : "failed";
                if (status.equals("ok")) {
                    Log.d(TAG, "Personal info was SUCCESSFULLY updated");
                    Toast.makeText(applicationContext, "Personal info updated: " + status,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(applicationContext, "Personal info updated: " + status,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "Personal info update error: " + t.getMessage());
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Update worker summary.
     *
     * @param context the context
     * @param token   the token
     * @param summary the summary
     */
    public static void updateWorkerSummary(Context context, String token, String summary) {
        Call<String> call = workersApi.updateWorkerSummary("Bearer " + token, summary);
        Log.d(TAG, "Personal summary update request sent");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String status = response.body() != null ? response.body() : "failed";
                if (status.equals("ok")) {
                    Log.d(TAG, "Personal summary was SUCCESSFULLY updated");
                    Toast.makeText(context, "Personal summary updated: " + status,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Personal summary updated: " + status,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "Personal info update error: " + t.getMessage());
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
