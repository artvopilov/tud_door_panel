package tu.foxtrot.foxtrotdoorpanelmobileapp;

import java.sql.Time;
import java.util.Date;

public class Notification {
    private String date;
    private String time;
    private String type;
    private String details;

    public Notification(String date, String time, String type, String details) {
        this.date = date;
        this.time = time;
        this.type = type;
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

