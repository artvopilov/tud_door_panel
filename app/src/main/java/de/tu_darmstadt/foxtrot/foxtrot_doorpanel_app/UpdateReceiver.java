package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.GridView;

public class UpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        GridView gridView = ((Activity)context).findViewById(R.id.workersGrid);
        gridView.invalidateViews();
    }
}
