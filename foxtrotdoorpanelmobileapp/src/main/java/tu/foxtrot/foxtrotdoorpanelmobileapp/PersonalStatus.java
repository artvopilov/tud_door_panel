package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.Utils;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;

public class PersonalStatus extends AppCompatActivity {

    private final String TAG = "PersonalStatusActivity";
    private Button submitButton;
    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_status);

        submitButton = (Button) findViewById(R.id.submitPersonalStatusButton);
        statusTextView = (TextView) findViewById(R.id.personalStatusTextView);

        submitStatus();
    }

    public void submitStatus() {
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String status = statusTextView.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", null);
                Utils.updateWorkerStatus(getApplicationContext(), token, status);

                Intent intent = new Intent(PersonalStatus.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
