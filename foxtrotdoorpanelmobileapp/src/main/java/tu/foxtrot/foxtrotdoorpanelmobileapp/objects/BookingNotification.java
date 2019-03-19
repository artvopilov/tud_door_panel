package tu.foxtrot.foxtrotdoorpanelmobileapp.objects;

import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common.Notification;

/**
 * The type Booking notification.
 */
public class BookingNotification extends Notification {
    private String number;
    private String timeslot;

    /**
     * Instantiates a new Booking notification.
     *
     * @param date     the date
     * @param time     the time
     * @param timeslot the timeslot
     * @param message  the message
     * @param email    the email
     * @param number   the number
     * @param name     the name
     */
    public BookingNotification(String date, String time, String timeslot, String message, String email,
                               String number, String name) {
        super(date, time, "booking", name, email, message);
        this.number = number;
        this.timeslot = timeslot;
    }

    /**
     * Gets timeslot.
     *
     * @return the timeslot
     */
    public String getTimeslot() {
        return timeslot;
    }

    /**
     * Sets timeslot.
     *
     * @param timeslot the timeslot
     */
    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    /**
     * Gets number.
     *
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets number.
     *
     * @param number the number
     */
    public void setNumber(String number) {
        this.number = number;
    }
}
