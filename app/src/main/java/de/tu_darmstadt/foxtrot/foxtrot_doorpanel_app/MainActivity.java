package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.UpdateReceiver;

import java.util.List;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.models.Tablet;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;




public class MainActivity extends AppCompatActivity {

    final String UPDATE_GUI_FILTER = "de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.updateGUI";

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(new UpdateReceiver(), new IntentFilter(UPDATE_GUI_FILTER));

        gridView = (GridView) findViewById(R.id.workersGrid);
        gridView.setAdapter(new WorkerAdapter(this));
        ((TabletApplication)getApplicationContext()).pullEmployees();

    }


}
