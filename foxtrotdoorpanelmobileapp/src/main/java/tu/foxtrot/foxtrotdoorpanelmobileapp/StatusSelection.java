package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.EmployeesAPI;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.models.Employee;

public class StatusSelection extends AppCompatActivity {

    private final String TAG = "StatusSelectionActivity";
    private List<Button> statusButtons = new ArrayList<>();
    private Button statusCustomButton;
    private EmployeesAPI employeesApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_selection);

        employeesApi = RetrofitClient.getRetrofitInstance().create(EmployeesAPI.class);

        statusButtons.add((Button) findViewById(R.id.button1));
        statusButtons.add((Button) findViewById(R.id.button2));
        statusButtons.add((Button) findViewById(R.id.button3));
        statusButtons.add((Button) findViewById(R.id.button4));

        statusCustomButton = (Button) findViewById(R.id.button5);

        openCustomized();
        submitStatus();
    }

    public void openCustomized() {
        statusCustomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusSelection.this, PersonalStatus.class);
                startActivity(intent);
            }
        });
    }

    public void submitStatus() {
        for (Button btn : statusButtons) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateStatus(btn);
                    Intent intent = new Intent(StatusSelection.this,
                            MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void updateStatus(Button button) {
        String status = button.getText().toString();
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
    }
}
