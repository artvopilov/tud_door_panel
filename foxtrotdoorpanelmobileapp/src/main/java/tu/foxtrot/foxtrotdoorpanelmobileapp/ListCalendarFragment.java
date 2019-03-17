package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;
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
                title.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        PopupMenu menu = new PopupMenu(getActivity(), title);
                        menu.inflate(R.menu.menu_calendar);
                        menu.show();
                        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getItemId() == R.id.action_delete){
                                    deleteEvent(event);
                                }
                                return true;
                            }
                        });
                        return true;
                    }
                });
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

    private void deleteEvent(Event event){
        WorkersAPI workersApi = RetrofitClient.getRetrofitInstance().create(WorkersAPI.class);

        int myID = ((MobileApplication)getActivity().getApplicationContext()).getWorkerID();

        Call<String> call = workersApi.removeWorkerTimeslot(myID, Integer.toString(event.getId()));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d("remove Timeslot", response.message());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("remove Timeslot", t.getMessage());
            }
        });
    }
}
