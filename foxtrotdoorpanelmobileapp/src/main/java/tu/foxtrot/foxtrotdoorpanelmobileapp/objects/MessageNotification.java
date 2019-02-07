package tu.foxtrot.foxtrotdoorpanelmobileapp.objects;

import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common.Notification;

public class MessageNotification extends Notification {
    private String email;
    private String name;
    private String text;

    public MessageNotification(String date, String time, String type,
                               String email, String name, String text) {
        super(date, time, type);
        this.email = email;
        this.name = name;
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
