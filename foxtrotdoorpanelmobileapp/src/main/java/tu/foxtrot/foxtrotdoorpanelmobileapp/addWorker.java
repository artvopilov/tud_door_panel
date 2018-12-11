package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Retrofit;

import static java.lang.Integer.parseInt;

public class addWorker extends AppCompatActivity {

    employee e;
    EditText editName;
    EditText editMail;
    EditText editAge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editName = findViewById(R.id.editText);
        editAge = findViewById(R.id.editText2);
        editMail = findViewById(R.id.editText3);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DoorPanel", "onClick ");
                String name = editName.getText().toString();
                String email = editMail.getText().toString();
                Log.d("DoorPanel", "parsed strings ");
                int age = parseInt( editAge.getText().toString());
                Log.d("DoorPanel", "parsed age ");
               e=new employee();
               e.setAge(age);
               e.setName(name);
               e.setEmail(email);

            sendNetworkRequest(e);

            }
        });
    }

    public void sendNetworkRequest(employee e1)
    {

        Log.d("DoorPanel", "send1 ");
        Retrofit.Builder temp = new Retrofit.Builder();
        Log.d("DoorPanel", "send1.5 ");
        temp = temp.baseUrl("http://10.0.2.2:5000/");
        Log.d("DoorPanel", "send1.8 ");
        Retrofit.Builder builder   =     temp.addConverterFactory(GsonConverterFactory.create());
        Log.d("DoorPanel", "send2 ");
        Retrofit retrofit =builder.build();
        Log.d("DoorPanel", "send3 ");

        registrationAPI client = retrofit.create(registrationAPI.class);
        Call <employee> call= client.createEmployee(e1);
        client.createEmployee(e1);
        call.enqueue(new Callback<employee>() {
            @Override
            public void onResponse(Call<employee> call, Response<employee> response) {
                Log.d("DoorPanel", "onResponse: "+response);
            }

            @Override
            public void onFailure(Call<employee> call, Throwable t) {
                Log.d("DoorPanel", "onFailure: "+t.getMessage());
            }
        });
    }

}
