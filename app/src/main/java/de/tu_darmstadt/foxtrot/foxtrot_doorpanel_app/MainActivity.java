package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;




public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = (GridView) findViewById(R.id.workersGrid);
        //gridView.setAdapter(new WorkerAdapter(this));
        getIndividualEmployee(gridView);

    }

    public void getIndividualEmployee(View v){
        Retrofit r= new Retrofit.Builder().baseUrl("http://10.0.2.2:5000/").addConverterFactory(GsonConverterFactory.create()).build();
        retrieveEmployee employee_=r.create(retrieveEmployee.class);
        Call<List<employee_single>> call= employee_.getIndividualEmployee();
        call.enqueue(new Callback<List<employee_single>>() {
            @Override
            public void onResponse(Call<List<employee_single>> call, Response<List<employee_single>> response) {
                List<employee_single> e= response.body();
                GridView gridView = (GridView) findViewById(R.id.workersGrid);
                gridView.setAdapter(new WorkerAdapter(MainActivity.this,e));
            }

            @Override
            public void onFailure(Call<List<employee_single>> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }
}
