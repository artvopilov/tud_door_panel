package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class WorkerAdapter extends BaseAdapter {

    public WorkerAdapter(Context context) {
        this.context = context;
    }

    private Context context;
    @Override
    public int getCount() {
        return 14;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.content_worker, null);
        ImageButton calendarButton = linearLayout.findViewById(R.id.calendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), makeAppointment.class);
                context.startActivity(intent);
            }
        });
        return linearLayout;
    }
}
