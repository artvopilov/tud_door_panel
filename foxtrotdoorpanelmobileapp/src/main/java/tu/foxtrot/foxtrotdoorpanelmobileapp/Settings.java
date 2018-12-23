package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    private Button photoButton;
    private Button roomButton;
    private Button emailButton;
    private Button phoneButton;
    private Button summaryButton;
    private Button defTimeSlotsButton;
    private Button addWorkerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        openCustomizedPhoto();
        openCustomizedRoom();
        openCustomizedEmail();
        openCustomizedPhone();
        openCustomizedSummary();
        openAddWorker();


    }

    public void openCustomizedPhoto() {
        photoButton = (Button) findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, PersonalPhoto.class);
                startActivity(intent);
            }
        });
    }

    public void openCustomizedRoom() {
        roomButton = (Button) findViewById(R.id.button2);
        roomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, PersonalRoom.class);
                startActivity(intent);
            }
        });
    }

    public void openCustomizedEmail() {
        emailButton = (Button) findViewById(R.id.button3);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, PersonalEmail.class);
                startActivity(intent);
            }
        });
    }

    public void openCustomizedPhone() {
        phoneButton = (Button) findViewById(R.id.button4);
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, PersonalPhone.class);
                startActivity(intent);
            }
        });
    }

    public void openCustomizedSummary() {
        summaryButton = (Button) findViewById(R.id.button5);
        summaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, PersonalSummary.class);
                startActivity(intent);
            }
        });
    }

    public void openAddWorker() {
        addWorkerButton = (Button) findViewById(R.id.button7);
        addWorkerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, addWorker.class);
                startActivity(intent);
            }
        });
    }
}
