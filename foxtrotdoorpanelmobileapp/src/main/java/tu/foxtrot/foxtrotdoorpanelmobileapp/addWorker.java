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

public class addWorker extends AppCompatActivity {

    employee e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("DoorPanel", "open ");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DoorPanel", "onClick ");
                String name = "testname";
                String email = "testmail";
                int age = 42;
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
        Retrofit.Builder builder = temp.baseUrl("http://10.0.2.2:5000/employees").addConverterFactory(GsonConverterFactory.create());
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
