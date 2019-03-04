package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tu.foxtrot.foxtrotdoorpanelmobileapp.network.Utils;

public class PersonalInfo extends AppCompatActivity {

    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        submitInfo();

    }

    public void submitInfo() {
        submitButton = (Button) findViewById(R.id.button7);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TextInputEditText phoneInput = findViewById(R.id.current_room);
                String phone = phoneInput.getText() == null ? "" : phoneInput.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", null);
                Utils.updateWorkerPhone(getApplicationContext(), token, phone);

                TextInputEditText emailInput = findViewById(R.id.current_email);
                String email = emailInput.getText() == null ? "" : emailInput.getText().toString();
                sharedPreferences = getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                token = sharedPreferences.getString("token", null);
                Utils.updateWorkerEmail(getApplicationContext(), token, email);

                TextInputEditText roomInput = findViewById(R.id.current_room);
                String room = roomInput.getText() == null ? "" : roomInput.getText().toString();
                sharedPreferences = getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                token = sharedPreferences.getString("token", null);
                Utils.updateWorkerRoom(getApplicationContext(), token, room);


                Intent intent = new Intent(PersonalInfo.this, Settings.class);
                startActivity(intent);
            }
        });
    }
}
