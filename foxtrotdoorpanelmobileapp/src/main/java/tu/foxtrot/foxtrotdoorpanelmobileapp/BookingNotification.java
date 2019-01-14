package tu.foxtrot.foxtrotdoorpanelmobileapp;

public class BookingNotification extends Notification {
    private String email;
    private String name;
    private String number;
    private int timeslot;

    public BookingNotification(String date, String time, int timeslot, String details, String email,
                               String number, String name) {
        super(date, time, "booking", details);
        this.email = email;
        this.number = number;
        this.name = name;
        this.timeslot = timeslot;
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
