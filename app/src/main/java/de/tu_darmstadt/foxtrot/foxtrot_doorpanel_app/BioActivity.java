package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.RetrofitClient;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.interfacesApi.EmployeesAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BioActivity extends AppCompatActivity {

    private EmployeesAPI employeesApi;
    private final String TAG = "BioActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);

        employeesApi = RetrofitClient.getRetrofitInstance().create(EmployeesAPI.class);

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

        Button sendButton = findViewById(R.id.button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textEdit = findViewById(R.id.editText);
                String message = textEdit.getText().toString();
                Call<String> call = employeesApi.sendEmployeeMessage(17, message);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String newStatus = response.body();

                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, "Status updated: " + newStatus,
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
                Intent intent = new Intent(BioActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
