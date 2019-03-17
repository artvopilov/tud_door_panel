package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import tu.foxtrot.foxtrotdoorpanelmobileapp.network.models.Event;

public class ListCalendarFragment extends Fragment {
    private static final String Tag = "ListCalendarFragment";

    private TextView listCalendarText;

    private List<Event> events;

    public ListCalendarFragment(List<Event> events){
        this.events = events;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_calendar_fragment, container, false);

        listCalendarText = (TextView) view.findViewById(R.id.listCalendarText);
        listCalendarText.setText("Today");

        Bundle bundle = getArguments();



        Date today = new Date();
        String day          = (String) DateFormat.format("dd",   today );
        String monthNumber  = (String) DateFormat.format("MM",   today );
        String year         = (String) DateFormat.format("yyyy", today );
        String currentDate =  day + "/" + monthNumber + "/" +year;


        if (bundle != null){

            currentDate = getArguments().getString("date");
        }

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.listCalendarLinearLayout);

        for (Event event : events) {
            Date start = event.getStart();
            day          = (String) DateFormat.format("dd",   start);
            monthNumber  = (String) DateFormat.format("MM",   start);
            year         = (String) DateFormat.format("yyyy", start);
            String date = day + "/" + monthNumber + "/" +year;
            if (date.equals(currentDate)){
                View eventFragment = inflater.inflate(R.layout.list_calendar_event_fragment, container, false);
                TextView time = eventFragment.findViewById(R.id.timeFirstListCalendar);
                time.setText((String) DateFormat.format("HH:mm", start));
                TextView title = eventFragment.findViewById(R.id.titleFirstListCalendar);
                title.setText(event.getName());
                layout.addView(eventFragment);
            }
        }


        if(bundle!= null)
        {
            String value = getArguments().getString("date");
            listCalendarText.setText(value);
        }

        return view;
    }
}
