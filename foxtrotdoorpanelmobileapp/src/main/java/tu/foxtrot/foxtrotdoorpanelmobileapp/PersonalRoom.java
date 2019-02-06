package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;

import tu.foxtrot.foxtrotdoorpanelmobileapp.network.Utils;


public class PersonalRoom extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_room);
        findViewById(R.id.submit_new_room).setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        TextInputEditText roomInput = findViewById(R.id.current_room);
        String room = roomInput.getText() == null ? "" : roomInput.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        Utils.updateWorkerRoom(getApplicationContext(), token, room);

        Intent intent = new Intent(PersonalRoom.this, Settings.class);
        startActivity(intent);
    }
}

