package tu.foxtrot.foxtrotdoorpanelmobileapp.objects;

import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common.Notification;

public class BookingNotification extends Notification {
    private String email;
    private String name;
    private String number;
    private String timeslot;
    private String message;

    public BookingNotification(String date, String time, String timeslot, String details, String email,
                               String number, String name) {
        super(date, time, "booking");
        this.email = email;
        this.setNumber(number);
        this.name = name;
        this.message = details;
        this.setTimeslot(timeslot);
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

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
