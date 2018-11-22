package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ListCalendarFragment extends Fragment {
    private static final String Tag = "ListCalendarFragment";

    private TextView listCalendarText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_calendar_fragment, container, false);

        listCalendarText = (TextView) view.findViewById(R.id.listCalendarText);
        listCalendarText.setText("Today");

        Bundle bundle = getArguments();
        if(bundle!= null)
        {
            String value = getArguments().getString("date");
            listCalendarText.setText(value);
        }

        return view;
    }
}
