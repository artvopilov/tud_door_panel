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

public class Utils {
    private final static String TAG = "UTILS";
    private static WorkersAPI workersApi = RetrofitClient.getRetrofitInstance().create(WorkersAPI.class);

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

    public static void updateWorkerRoom(Context context, String token, String room) {
        Call<String> call = workersApi.updateWorkerRoom("Bearer " + token, room);
        Log.d(TAG, "Room update request sent | room:" + room);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String newRoom = response.body();
                Log.d(TAG, "Room updated: " + newRoom);
                Toast.makeText(context, "Room updated: " + newRoom, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "Room update error: " + t.getMessage());            }
        });
    }

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

    public static void updateWorkerPhone(Context context, String token, String phone) {
        Call<String> call = workersApi.updateWorkerPhone("Bearer " + token, phone);
        Log.d(TAG, "Phone update request sent");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String newPhone = response.body();
                Log.d(TAG, "Phone updated: " + newPhone);
                Toast toast = Toast.makeText(context, "Status updated: " + newPhone,
                        Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "Phone update error: " + t.getMessage());
            }
        });
    }

    public static void updateWorkerEmail(Context context, String token, String email) {
        Call<String> call = workersApi.updateWorkerEmail("Bearer " + token, email);
        Log.d(TAG, "Email update request sent");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String newEmail = response.body();
                Log.d(TAG, "Email updated: " + newEmail);
                Toast toast = Toast.makeText(context, "Status updated: " + newEmail,
                        Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "Email update error: " + t.getMessage());
            }
        });
    }



    public static void getMessages(MobileApplication mobileApplication, String token) {
        MessagesAPI messagesAPI = RetrofitClient.getRetrofitInstance().create(MessagesAPI.class);
        Call<List<MessageNotification>> call = messagesAPI.getMessages("Bearer " + token);
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

    public static void getBookings(MobileApplication mobileApplication, String token) {
        MessagesAPI messagesAPI = RetrofitClient.getRetrofitInstance().create(MessagesAPI.class);
        Call<List<BookingNotification>> call = messagesAPI.getBookings("Bearer " + token);
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
}
