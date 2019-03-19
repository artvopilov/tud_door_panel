package tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common;

/**
 * The type Notification.
 */
public class Notification {
    private String date;
    private String time;
    private String type;
    private String name;
    private String email;
    private String message;

    /**
     * Instantiates a new Notification.
     *
     * @param date    the date
     * @param time    the time
     * @param type    the type
     * @param name    the name
     * @param email   the email
     * @param message the message
     */
    public Notification(String date, String time, String type, String name, String email,
                        String message) {
        this.date = date;
        this.time = time;
        this.type = type;
        this.name = name;
        this.email = email;
        this.message = message;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}

