package tu.foxtrot.foxtrotdoorpanelmobileapp.objects;

import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common.Notification;

public class MessageNotification extends Notification {
    public MessageNotification(String date, String time, String type,
                               String email, String name, String text) {
        super(date, time, type, name, email, text);
    }
}
