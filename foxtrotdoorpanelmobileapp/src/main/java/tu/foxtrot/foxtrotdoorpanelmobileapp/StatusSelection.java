package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.Utils;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;

/**
 * Status Activity for Mobile App.
 * Allows to change status of worker.
 */
public class StatusSelection extends AppCompatActivity {

    private final String TAG = "StatusSelectionActivity";
    private List<Button> statusButtons = new ArrayList<>();
    private Button statusCustomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_selection);

        statusButtons.add((Button) findViewById(R.id.button1));
        statusButtons.add((Button) findViewById(R.id.button2));
        statusButtons.add((Button) findViewById(R.id.button3));
        statusButtons.add((Button) findViewById(R.id.button4));
        statusCustomButton = (Button) findViewById(R.id.button5);

        openCustomized();
        submitStatus();
    }

    /**
     * Open view to customize status.
     */
    public void openCustomized() {
        statusCustomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusSelection.this, PersonalStatus.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Submit status.
     */
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

    /**
     * Update status.
     */
    private void updateStatus(Button button) {
        String status = button.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        Utils.updateWorkerStatus(getApplicationContext(), token, status);
    }
}
