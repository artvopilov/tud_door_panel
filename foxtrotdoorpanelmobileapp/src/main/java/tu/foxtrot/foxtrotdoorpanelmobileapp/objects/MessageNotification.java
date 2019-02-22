package tu.foxtrot.foxtrotdoorpanelmobileapp.objects;

import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common.Notification;

public class MessageNotification extends Notification {
    private int messageId;

    public MessageNotification(String date, String time, String type, String email, String name,
                               String text) {
        super(date, time, type, name, email, text);
        this.messageId = 1;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
