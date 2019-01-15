package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects.LoginResponse;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivityTag";
    private EditText emailInput;
    private EditText passwordInput;
    private Button button;
    private WorkersAPI workersApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        workersApi = RetrofitClient.getRetrofitInstance().create(WorkersAPI.class);

        emailInput = (EditText)findViewById(R.id.login_email_input);
        passwordInput = (EditText)findViewById(R.id.login_password_input);
        button = (Button)findViewById(R.id.login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == button){
                    loginUser();
                }
            }
        });
    }

    private void loginUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        Call<LoginResponse> call = workersApi.login(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse.getStatus().equals("ok")) {
                    int workerId = loginResponse.getWorker().getId();
                    SharedPreferences sharedPreferences = getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    int previousWorkerId = sharedPreferences.getInt("workerId", 0);
                    if (previousWorkerId != 0) {
                        unsubscribeFromTopic(String.valueOf(previousWorkerId));
                    }
                    subscribeToTopic(String.valueOf(workerId));

                    ((MobileApplication)getApplicationContext()).workerID = workerId;

                    String token = loginResponse.getToken();
                    Log.d(TAG, "Token got: " + token);

                    SharedPreferences.Editor sharedPrefEditor = getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
                    sharedPrefEditor.putString("token", token);
                    sharedPrefEditor.putInt("workerId", workerId);
                    sharedPrefEditor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login error",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    private void subscribeToTopic(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        //TODO
                    }
                    Log.d(TAG, "Subscribed to topic " + topic);
                });
    }

    private void unsubscribeFromTopic(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        //TODO
                    }
                    Log.d(TAG, "Unsubscribed from topic " + topic);
                });
    }
}
