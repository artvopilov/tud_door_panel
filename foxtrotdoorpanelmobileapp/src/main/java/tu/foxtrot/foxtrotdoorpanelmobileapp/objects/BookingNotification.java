package tu.foxtrot.foxtrotdoorpanelmobileapp.objects;

import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common.Notification;

public class BookingNotification extends Notification {
    private String number;
    private String timeslot;

    public BookingNotification(String date, String time, String timeslot, String message, String email,
                               String number, String name) {
        super(date, time, "booking", name, email, message);
        this.number = number;
        this.timeslot = timeslot;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
