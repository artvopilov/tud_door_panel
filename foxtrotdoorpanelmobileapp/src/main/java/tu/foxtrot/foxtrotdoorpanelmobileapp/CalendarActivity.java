package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.models.Event;

/**
 * The type Calendar activity.
 */
public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";

    private CalendarPageAdapter mCalendarPageAdapter;

    private ViewPager mViewPager;

    private List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Log.d(TAG, "onCreate: Starting");

        mCalendarPageAdapter = new CalendarPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);


        WorkersAPI workersApi = RetrofitClient.getRetrofitInstance().create(WorkersAPI.class);

        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        Call<List<Event>> call = workersApi.getTimeslots("Bearer " + token);

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                events = response.body();

                tabLayout.setupWithViewPager(mViewPager);
                setupViewPager(mViewPager);

            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d("remove Timeslot", t.getMessage());
            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {
        CalendarPageAdapter adapter = new CalendarPageAdapter(getSupportFragmentManager());
        WallCalendarFragment wallCalendarFragment = new WallCalendarFragment();
        wallCalendarFragment.setEvents(events);
        adapter.addFragment(wallCalendarFragment, "Calendar");
        ListCalendarFragment listCalendarFragment = new ListCalendarFragment();
        listCalendarFragment.setEvents(events);
        adapter.addFragment(listCalendarFragment, "List");
        viewPager.setAdapter(adapter);
    }
}
