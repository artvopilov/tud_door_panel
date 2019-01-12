package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainEmptyActivity extends AppCompatActivity {
    private final String TAG = "MainEmptyActivity";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        Intent activityIntent;

        // go straight to main if a token is stored
        if (getToken() != null) {
            activityIntent = new Intent(this, MainActivity.class);
        } else {
            activityIntent = new Intent(this, LoginActivity.class);
        }

        Log.d(TAG, "Starting next activity");
        startActivity(activityIntent);
        finish();
    }

    private String getToken() {
        return sharedPreferences.getString("token", null);
    }
}
