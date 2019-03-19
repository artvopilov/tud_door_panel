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
 * Activity for personalize summary.
 */
public class PersonalSummary extends AppCompatActivity {

    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_summary);

        (findViewById(R.id.button7)).setOnClickListener(this::onClick);
    }

    /**
     * onClick method.
     *
     * @param view the view
     */
    public void onClick(View view) {
        TextInputEditText summaryInput = findViewById(R.id.summary_text);
        String summary = summaryInput.getText() == null ? "" :
                summaryInput.getText().toString().trim();
        if (summary.equals("")) {
            return;
        }
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        Utils.updateWorkerSummary(getApplicationContext(), token, summary);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

