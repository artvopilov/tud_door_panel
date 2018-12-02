package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationsListAdapter extends ArrayAdapter<Notification> {

    private Context mContext;
    int mResource;

    public NotificationsListAdapter(@NonNull Context context, int resource,
                                    @NonNull ArrayList<Notification> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String date = getItem(position).getDate();
        String time = getItem(position).getTime();
        String details = getItem(position).getDetails();
        String type = getItem(position).getType();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView tvTime = (TextView) convertView.findViewById(R.id.notification_time);
        TextView tvType = (TextView) convertView.findViewById(R.id.notification_type);
        TextView tvDetails = (TextView) convertView.findViewById(R.id.notification_details);

        tvTime.setText(time);
        tvDetails.setText(details);
        if (type.equals("Message")) {
            String name = ((MessageNotification) getItem(position)).getName();
            String email = ((MessageNotification) getItem(position)).getEmail();
            tvType.setText(String.format("%s from %s", type, name));
            tvType.setTextColor(Color.parseColor("#AABC45"));
        } else {
            tvType.setText(type);
        }

        return convertView;
    }
}
