package tu.foxtrot.foxtrotdoorpanelmobileapp.objects;

import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common.Notification;

public class MessageNotification extends Notification {
    private int id;

    public MessageNotification(String date, String time, String type, String email, String name,
                               String text, int messageId) {
        super(date, time, type, name, email, text);
        this.id = messageId;
    }

    public int getMessageId() {
        return id;
    }

    public void setMessageId(int messageId) {
        this.id = messageId;
    }
}
