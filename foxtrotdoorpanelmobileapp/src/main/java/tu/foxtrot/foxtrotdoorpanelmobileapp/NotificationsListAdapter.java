package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationsListAdapter extends ArrayAdapter<Notification> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    static class ViewHolder {
        TextView date;
        TextView time;
        TextView details;
        TextView type;
        TextView name;
        TextView email;
    }

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

        ViewHolder holder = new ViewHolder();
        holder.time = (TextView) convertView.findViewById(R.id.notification_time);
        holder.type = (TextView) convertView.findViewById(R.id.notification_type);
        holder.details = (TextView) convertView.findViewById(R.id.notification_details);

        //final View resultView = convertView;
        convertView.setTag(holder);

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);

        //resultView.startAnimation(animation);
        convertView.startAnimation(animation);
        lastPosition = position;

        holder.time.setText(time);
        holder.details.setText(details);
        if (type.equals("Message")) {
            String name = ((MessageNotification) getItem(position)).getName();
            String email = ((MessageNotification) getItem(position)).getEmail();
            holder.type.setText(String.format("%s from %s", type, name));
            holder.type.setTextColor(Color.parseColor("#AABC45"));
        } else {
            holder.type.setText(type);
        }

        return convertView;
    }
}
