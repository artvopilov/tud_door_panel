package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.MessagesAPI;

public class MessageActivity extends AppCompatActivity {

    private final String TAG = "MessageActivity";
    private TextView name;
    private TextView email;
    private TextView text;
    private int previousMessageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        name = (TextView) findViewById(R.id.message_name);
        email = (TextView) findViewById(R.id.message_email);
        text = (TextView) findViewById(R.id.message_text);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("Name").toString());
        email.setText(intent.getStringExtra("Email").toString());
        text.setText(intent.getStringExtra("Details").toString());
        previousMessageId = Integer.parseInt(intent.getStringExtra("MessageId"));

        findViewById(R.id.answer_button).setOnClickListener(this::sendAnswer);
    }

    private void sendAnswer(View v) {
        String message = ((EditText)findViewById(R.id.message_answer)).getText().toString().trim();
        Date currentDateTime = Calendar.getInstance().getTime();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String time = timeFormat.format(currentDateTime);
        String date = dateFormat.format(currentDateTime);
        String visitorName = name.getText().toString();
        String visitorEmail = email.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        sendMessageToVisitor(token, message, date, time, visitorEmail, visitorName, previousMessageId);
    }

    private void sendMessageToVisitor(String token, String message, String date, String time,
                                      String email, String name, int previousMessageId) {
        MessagesAPI messagesAPI = RetrofitClient.getRetrofitInstance().create(MessagesAPI.class);
        Call<String> call = messagesAPI.sendMessageAnswer("Bearer " + token, message, date,
                time, email, name, previousMessageId);
        Log.d(TAG, String.format("Request for answering the visitor %s sent", ""));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String responseStatus = response.body() != null ? response.body() : "error";
                Log.d(TAG, "Got response with status: " + responseStatus);
                if (responseStatus.equals("Ok")) {
                    Toast.makeText(getApplicationContext(), "Message sent",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Error",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "Error while sending a message: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
