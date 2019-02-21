package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MessageActivity extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private TextView text;
    private int previousMessageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        name = (TextView) findViewById(R.id.message_name);
        email = (TextView) findViewById(R.id.message_email);
        text = (TextView) findViewById(R.id.message_text);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("Name").toString());
        email.setText(intent.getStringExtra("Email").toString());
        text.setText(intent.getStringExtra("Details").toString());
        previousMessageId = Integer.parseInt(intent.getStringExtra("MessageId"));

        findViewById(R.id.answer_button).setOnClickListener(this::sendAnswer);
    }

    private void sendAnswer(View v) {
        String message = ((EditText)findViewById(R.id.message_answer)).getText().toString().trim();
        Date currentDateTime = Calendar.getInstance().getTime();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String time = timeFormat.format(currentDateTime);
        String date = dateFormat.format(currentDateTime);
        String visitorName = name.getText().toString();
        String visitorEmail = email.getText().toString();
    }
}
