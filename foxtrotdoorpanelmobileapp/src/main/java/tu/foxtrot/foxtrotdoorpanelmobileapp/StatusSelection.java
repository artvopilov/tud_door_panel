package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StatusSelection extends AppCompatActivity {

    private Button statusCustomButton;
    private Button availableButton;
    private Button busyButton;
    private Button fiveMinButton;
    private Button tripButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_selection);

        openCustomized();
        submitAndBack();
    }

    public void openCustomized() {
        statusCustomButton = (Button) findViewById(R.id.button5);
        statusCustomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusSelection.this, PersonalStatus.class);
                startActivity(intent);
            }
        });
    }

    public void submitAndBack() {
        availableButton = (Button) findViewById(R.id.button1);
        availableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusSelection.this, MainActivity.class);
                startActivity(intent);
            }
        });

        busyButton = (Button) findViewById(R.id.button2);
        busyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusSelection.this, MainActivity.class);
                startActivity(intent);
            }
        });

        fiveMinButton = (Button) findViewById(R.id.button3);
        fiveMinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusSelection.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tripButton = (Button) findViewById(R.id.button4);
        tripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusSelection.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }



}
