package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.EmployeesAPI;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.models.Employee;

public class PersonalStatus extends AppCompatActivity {

    private final String TAG = "PersonalStatusActivity";
    private Button submitButton;
    private TextView statusTextView;
    private EmployeesAPI employeesApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_status);

        employeesApi = RetrofitClient.getRetrofitInstance().create(EmployeesAPI.class);
        submitButton = (Button) findViewById(R.id.submitPersonalStatusButton);
        statusTextView = (TextView) findViewById(R.id.personalStatusTextView);

        submitStatus();
    }

    public void submitStatus() {
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String status = statusTextView.getText().toString();
                Call<Employee> call = employeesApi.updateEmployeeStatus(1, status);

                call.enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        Employee employee = response.body();

                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, "Status updated: " + status,
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });

                Intent intent = new Intent(PersonalStatus.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
