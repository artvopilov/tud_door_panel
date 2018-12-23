package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.BatchUpdateException;

public class WallCalendarFragment extends Fragment {
    private static final String Tag = "WallCalendarFragment";

    private CalendarView mCalendarView;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wall_calendar_fragment, container, false);
        mViewPager = (ViewPager) getActivity().findViewById(R.id.container);

        mCalendarView =(CalendarView) view.findViewById(R.id.wallCalendarObject);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" +year;

                ListCalendarFragment listCalendarFragment = new ListCalendarFragment();
                Bundle bundle = new Bundle();
                bundle.putString("date", date);
                listCalendarFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.listCalendarFragment, listCalendarFragment).commit();
                mViewPager.setCurrentItem(1);
            }
        });
        return view;
    }
}
