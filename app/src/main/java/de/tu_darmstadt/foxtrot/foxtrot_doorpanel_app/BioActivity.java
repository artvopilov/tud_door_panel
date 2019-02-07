package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.RetrofitClient;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.interfacesApi.WorkerAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BioActivity extends AppCompatActivity {

    private WorkerAPI workerApi;
    private final String TAG = "BioActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);

        workerApi = RetrofitClient.getRetrofitInstance().create(WorkerAPI.class);

        int workerID = getIntent().getIntExtra("workerID",0);

        Worker worker = ((TabletApplication)getApplicationContext()).getWorkerByID(workerID);

        if (worker != null) {
            TextView nameView = findViewById(R.id.b19);
            nameView.setText(worker.getName());
            TextView positionView = findViewById(R.id.b20);
            positionView.setText(worker.getPosition());
            TextView statusView = findViewById(R.id.b21);
            statusView.setText(worker.getStatus());
        }

        Button sendButton = findViewById(R.id.button_send);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = ((EditText)findViewById(R.id.message_field)).getText().toString();
                String email = ((EditText)findViewById(R.id.email_field)).getText().toString();
                String name = ((EditText)findViewById(R.id.name_filed)).getText().toString();
                Calendar calendar = Calendar.getInstance();
                String time = DateFormat.getTimeInstance().format(calendar.getTime());
                String date = DateFormat.getDateInstance().format(calendar.getTime());
                Call<String> call = workerApi.sendWorkerMessage(workerID, message,
                        date, time, email, name);
                Log.d(TAG, "Message request sent");

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d(TAG, "Successful response");
                        Toast.makeText(getApplicationContext(), "Message sent",
                                Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d(TAG, "Unsuccessful response");
                        Log.d(TAG, t.getMessage());
                    }
                });
                Intent intent = new Intent(BioActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
