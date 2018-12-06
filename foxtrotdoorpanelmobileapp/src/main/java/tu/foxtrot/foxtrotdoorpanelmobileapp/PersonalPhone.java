package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PersonalPhone extends AppCompatActivity {

    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_phone);

        submitPhone();
    }

    public void submitPhone() {
        submitButton = (Button) findViewById(R.id.button7);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalPhone.this, Settings.class);
                startActivity(intent);
            }
        });
    }
}

