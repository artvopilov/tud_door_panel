package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WorkerAdapter extends BaseAdapter {
    MainActivity mActivity;
    employee e;

    int N; // total number of textviews to add

    final TextView[] myTextViews = new TextView[N]; // create an empty array;


    public WorkerAdapter(Context context) {
        this.context = context;
    }

    private Context context;

    public WorkerAdapter(MainActivity mainActivity, employee e) {
        this.mActivity=mainActivity;
        this.e=e;
    }

    @Override
    public int getCount() {
        return e.getList().size();
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
        N=getCount();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.content_worker, null);
        TextView tv1= linearLayout.findViewById(R.id.b19);
        TextView tv2= linearLayout.findViewById(R.id.b20);
        TextView tv3= linearLayout.findViewById(R.id.b21);
        ImageButton calendarButton = linearLayout.findViewById(R.id.calendarButton);



            // set some properties of rowTextView or something
            tv1.setText(e.getList().get(position).getAge());
            tv2.setText(e.getList().get(position).getEmail());
            tv3.setText(e.getList().get(position).getName());



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
