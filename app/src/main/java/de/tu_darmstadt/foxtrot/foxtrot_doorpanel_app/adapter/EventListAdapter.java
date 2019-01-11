package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.R;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.model.ScheduledEvents;

import java.util.List;

public class EventListAdapter extends BaseAdapter {
    private Context context;
    private List<ScheduledEvents> scheduledEvents;
    private LayoutInflater inflater;
   public EventListAdapter(Context context, List<ScheduledEvents> scheduledEvents){
        this.context = context;
        this.scheduledEvents = scheduledEvents;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return scheduledEvents.size();
    }

    @Override
    public Object getItem(int i) {
        return scheduledEvents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventHolder eventHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.event_view_layout, parent, false);
            eventHolder = new EventHolder(convertView);
            convertView.setTag(eventHolder);
        } else {
            eventHolder = (EventHolder) convertView.getTag();
        }
        ScheduledEvents scheduledEvents = (ScheduledEvents) getItem(position);

        eventHolder.eventTitle.setText(scheduledEvents.getEventSummary());
        eventHolder.eventDes.setText(scheduledEvents.getDescription());
        eventHolder.eventAttendee.setText(scheduledEvents.getAttendees());
        eventHolder.eventStart.setText(scheduledEvents.getStartDate());
        eventHolder.eventEnd.setText(scheduledEvents.getEndDate());
        eventHolder.eventLocation.setText(scheduledEvents.getLocation());

        return convertView;
    }
    private class EventHolder {
        TextView eventTitle, eventDes, eventAttendee, eventStart, eventEnd, eventLocation;

        public EventHolder(View item) {
            eventTitle = (TextView) item.findViewById(R.id.eventTitle);
            eventDes = (TextView) item.findViewById(R.id.eventDes);
            eventAttendee = (TextView) item.findViewById(R.id.eventAttendee);
            eventStart = (TextView) item.findViewById(R.id.eventStart);
            eventEnd = (TextView) item.findViewById(R.id.eventEnd);
            eventLocation = (TextView) item.findViewById(R.id.eventLocation);
        }
    }
}
