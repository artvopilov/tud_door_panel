package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MessageActivity extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private TextView text;

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
    }
}
