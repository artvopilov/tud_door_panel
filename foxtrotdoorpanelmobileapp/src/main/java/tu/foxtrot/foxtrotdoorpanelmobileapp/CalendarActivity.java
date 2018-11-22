package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";

    private CalendarPageAdapter mCalendarPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Log.d(TAG, "onCreate: Starting");

        mCalendarPageAdapter = new CalendarPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        CalendarPageAdapter adapter = new CalendarPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new WallCalendarFragment(), "Calendar");
        adapter.addFragment(new ListCalendarFragment(), "List");
        viewPager.setAdapter(adapter);
    }
}
