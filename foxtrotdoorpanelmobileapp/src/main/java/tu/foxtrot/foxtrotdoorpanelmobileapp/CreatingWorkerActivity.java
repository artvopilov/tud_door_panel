package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects.Worker;

import static java.lang.Integer.parseInt;

public class CreatingWorkerActivity extends AppCompatActivity {
    private final String TAG = "CreatingWorkerActivity";

    private EditText nameEdit;
    private EditText emailEdit;
    private EditText passwordEdit;
    private EditText roomEdit;
    private FloatingActionButton createWorkerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        nameEdit = findViewById(R.id.newWorkerName);
        emailEdit = findViewById(R.id.newWorkerEmail);
        passwordEdit = findViewById(R.id.newWorkerPassword);
        roomEdit = findViewById(R.id.newWorkerRoom);

        createWorkerButton = (FloatingActionButton) findViewById(R.id.fab);
        createWorkerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String room = roomEdit.getText().toString();
                Worker worker = new Worker();
                worker.setRoom(room);
                worker.setName(name);
                worker.setEmail(email);
                worker.setPassword(password);

                createNewWorker(worker);

            }
        });
    }

    public void createNewWorker(Worker worker)
    {
        WorkersAPI workersApi = RetrofitClient.getRetrofitInstance().create(WorkersAPI.class);
        Call <Worker> call= workersApi.createWorker(worker);
        Log.d(TAG, "Creating new worker request sent");

        call.enqueue(new Callback<Worker>() {
            @Override
            public void onResponse(Call<Worker> call, Response<Worker> response) {
                Log.d(TAG, "Successfully created new worker");
                Toast.makeText(CreatingWorkerActivity.this, "New worker <" +
                                worker.getName() + "> created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreatingWorkerActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Worker> call, Throwable t) {
                Log.d(TAG, "Failed creating new worker: " + t.getMessage());
            }
        });
    }

}
