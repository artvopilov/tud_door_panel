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

/**
 * Activity for personal information.
 */
public class PersonalInfo extends AppCompatActivity {

    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        (findViewById(R.id.personal_info_button)).setOnClickListener(this::onClick);

    }

    /**
     * onClick method.
     *
     * @param view the view
     */
    public void onClick(View view) {
        TextInputEditText roomInput = findViewById(R.id.pers_current_room);
        TextInputEditText emailInput = findViewById(R.id.pers_current_email);
        TextInputEditText phoneInput = findViewById(R.id.pers_current_phone);
        String room = roomInput.getText() == null ? "" : roomInput.getText().toString().trim();
        String email = roomInput.getText() == null ? "" : emailInput.getText().toString().trim();
        String phone = roomInput.getText() == null ? "" : phoneInput.getText().toString().trim();

        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        if (email.equals("") && phone.equals("") && room.equals("")) {
            return;
        }
        Utils.updatePersonalInfo(getApplicationContext(), token, phone, email, room);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
