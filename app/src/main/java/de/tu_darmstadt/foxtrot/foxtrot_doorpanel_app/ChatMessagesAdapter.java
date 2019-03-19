package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.model.Message;


/**
 * The type Chat messages adapter.
 */
public class ChatMessagesAdapter extends ArrayAdapter<Message> {
    private final String TAG = "ChatMessagesAdapter";
    private Context mContext;
    private int mResource;

    // View lookup cache: for improving performance
    private static class ViewHolder {
        /**
         * The Text.
         */
        TextView text;
        /**
         * The Date.
         */
        TextView date;
        /**
         * The Time.
         */
        TextView time;
        /**
         * The Actors.
         */
        TextView actors;
    }

    /**
     * Instantiates a new Chat messages adapter.
     *
     * @param context  the context
     * @param resource the resource
     * @param objects  the objects
     */
    public ChatMessagesAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message message = getItem(position);
        Log.d(TAG, message == null ? "NULL" : message.date == null ? "Date null" : message.date);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
            viewHolder.text = (TextView) convertView.findViewById(R.id.message_text);
            viewHolder.date = (TextView) convertView.findViewById(R.id.message_date);
            viewHolder.time = (TextView) convertView.findViewById(R.id.message_time);
            viewHolder.actors = (TextView) convertView.findViewById(R.id.message_actors);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.d(TAG, viewHolder == null ? "NULL HOLDER" : "HOLDER");
        viewHolder.text.setText(message.text);
        viewHolder.date.setText(message.date);
        viewHolder.time.setText(message.time);
        viewHolder.actors.setText(String.format("From: %s | To: %s", message.from, message.to));

        return convertView;
    }
}
