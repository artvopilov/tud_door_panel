package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.MessageNotification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common.Notification;

public class NotificationsListAdapter extends ArrayAdapter<Notification> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    @SuppressWarnings({"PackageVisibleField", "PackageVisibleInnerClass"})
    static class ViewHolder {
        TextView date;
        TextView time;
        TextView details;
        TextView type;
        TextView name;
        TextView email;
    }

    public NotificationsListAdapter(@NonNull Context context, int resource,
                                    @NonNull List<Notification> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String time = getItem(position).getTime();
        String details = getItem(position).getMessage();
        String type = getItem(position).getType();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        ViewHolder holder = new ViewHolder();
        holder.time = (TextView) convertView.findViewById(R.id.notification_time);
        holder.type = (TextView) convertView.findViewById(R.id.notification_type);
        holder.details = (TextView) convertView.findViewById(R.id.notification_details);
        convertView.setTag(holder);

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        convertView.startAnimation(animation);

        lastPosition = position;

        holder.time.setText(time);
        holder.type.setText(String.format("%s from %s", type, name));
        holder.type.setTextColor(Color.parseColor("#AABC45"));
        holder.details.setText(details);
        return convertView;
    }
}
