package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.models.Tablet;

public class WorkerAdapter extends BaseAdapter {



    public WorkerAdapter(Context context) {
        this.context = context;
    }

    private Context context;

    public WorkerAdapter(MainActivity mainActivity, List<Worker> e) {
        this.context=mainActivity;
    }

    @Override
    public int getCount() {
        return ((TabletApplication)context.getApplicationContext()).getWorkerNum();
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
        Worker worker = ((TabletApplication)context.getApplicationContext()).getWorker(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.content_worker, null);
        TextView tv1= linearLayout.findViewById(R.id.b19);
        TextView tv2= linearLayout.findViewById(R.id.b20);
        TextView tv3= linearLayout.findViewById(R.id.b21);
        ImageButton calendarButton = linearLayout.findViewById(R.id.calendarButton);



        // set some properties of rowTextView or something
        tv1.setText(worker.getName());
        tv2.setText(worker.getPosition());
        tv3.setText(worker.getStatus());

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BioActivity.class);
                intent.putExtra("workerID",worker.getId());
                context.startActivity(intent);
            }
        });



        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), makeAppointment.class);
                intent.putExtra("workerID",worker.getId());
                context.startActivity(intent);
            }
        });
        return linearLayout;
    }
}
