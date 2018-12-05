package tu.foxtrot.foxtrotdoorpanelmobileapp;

import java.sql.Time;
import java.util.Date;

public class MessageNotification extends Notification {
    private String email;
    private String name;

    public MessageNotification(String date, String time, String type, String details, String email,
                               String name) {
        super(date, time, type, details);
        this.email = email;
        this.name = name;
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
}
