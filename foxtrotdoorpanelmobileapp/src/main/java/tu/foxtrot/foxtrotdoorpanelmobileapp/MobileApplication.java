package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.app.Application;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.ArrayList;
import java.util.List;

public class MobileApplication extends Application {

    List<Notification> notificationsList = new ArrayList<Notification>();

    public List<Notification> getNotificationsList() {
        return notificationsList;
    }

    public void addNotification(Notification notification){
        notificationsList.add(notification);
    }

    public GoogleAccountCredential mCredential; //TODO: this needs to be encapsulated
}
