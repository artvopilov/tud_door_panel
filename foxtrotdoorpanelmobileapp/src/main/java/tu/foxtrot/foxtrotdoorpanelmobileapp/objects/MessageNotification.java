package tu.foxtrot.foxtrotdoorpanelmobileapp.objects;

import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common.Notification;

/**
 * The type Message notification.
 */
public class MessageNotification extends Notification {
    private int id;

    /**
     * Instantiates a new Message notification.
     *
     * @param date      the date
     * @param time      the time
     * @param type      the type
     * @param email     the email
     * @param name      the name
     * @param text      the text
     * @param messageId the message id
     */
    public MessageNotification(String date, String time, String type, String email, String name,
                               String text, int messageId) {
        super(date, time, type, name, email, text);
        this.id = messageId;
    }

    /**
     * Gets message id.
     *
     * @return the message id
     */
    public int getMessageId() {
        return id;
    }

    /**
     * Sets message id.
     *
     * @param messageId the message id
     */
    public void setMessageId(int messageId) {
        this.id = messageId;
    }
}
