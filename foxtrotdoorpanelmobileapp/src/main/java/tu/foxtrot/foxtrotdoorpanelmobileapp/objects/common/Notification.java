package tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common;

public class Notification {
    private String date;
    private String time;
    private String type;

    public Notification(String date, String time, String type) {
        this.date = date;
        this.time = time;
        this.type = type;
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

}

