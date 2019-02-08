package tu.foxtrot.foxtrotdoorpanelmobileapp.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.R;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;

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
}
