package tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common;

public class Notification {
    private String date;
    private String time;
    private String type;
    private String name;
    private String email;
    private String message;

    public Notification(String date, String time, String type, String name, String email,
                        String message) {
        this.date = date;
        this.time = time;
        this.type = type;
        this.name = name;
        this.email = email;
        this.message = message;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

