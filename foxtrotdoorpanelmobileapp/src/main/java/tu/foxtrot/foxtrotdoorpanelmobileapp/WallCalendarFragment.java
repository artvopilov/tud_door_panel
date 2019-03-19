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
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;
import sun.bob.mcalendarview.vo.MarkedDates;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.models.Event;

/**
 * The type Wall calendar fragment.
 */
public class WallCalendarFragment extends Fragment {
    private static final String Tag = "WallCalendarFragment";

    private MCalendarView mCalendarView;
    private ViewPager mViewPager;
    private List<Event> events;

    /**
     * Instantiates a new Wall calendar fragment.
     */
    public WallCalendarFragment(){
        super();
    }

    /**
     * Sets the list of events to be displayed.
     *
     * @param events the list of events to be displayed
     */
    public void setEvents (List<Event> events) {
        this.events = events;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wall_calendar_fragment, container, false);
        mViewPager = (ViewPager) getActivity().findViewById(R.id.container);

        mCalendarView =(MCalendarView) view.findViewById(R.id.wallCalendarObject);
        mCalendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData datedata) {
                int year = datedata.getYear();
                int month = datedata.getMonth();
                int dayOfMonth = datedata.getDay();
                DecimalFormat formatter = new DecimalFormat("00");
                String date = formatter.format(dayOfMonth) + "/" + formatter.format(month) + "/" +year;

                ListCalendarFragment listCalendarFragment = new ListCalendarFragment();
                listCalendarFragment.setEvents(events);
                Bundle bundle = new Bundle();
                bundle.putString("date", date);
                listCalendarFragment.setArguments(bundle);
                ((ViewGroup)getActivity().findViewById(R.id.listCalendarFragment)).removeAllViews();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.listCalendarFragment, listCalendarFragment).commit();
                mViewPager.setCurrentItem(1);
            }
        });

        mCalendarView.setMarkedStyle(MarkStyle.BACKGROUND);
        MarkedDates marked = mCalendarView.getMarkedDates();
        marked.removeAdd();

        for (Event event : events){
            Calendar cal = Calendar.getInstance();
            cal.setTime(event.getStart());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            mCalendarView.markDate(year,
                    month+1,
                    day);
        }
        return view;
    }
}
