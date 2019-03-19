package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.model;

/**
 * The type Message.
 */
public class Message {
    /**
     * The Text.
     */
    public String text;
    /**
     * The Date.
     */
    public String date;
    /**
     * The Time.
     */
    public String time;
    /**
     * The From.
     */
    public String from;
    /**
     * The To.
     */
    public String to;

    /**
     * Instantiates a new Message.
     *
     * @param text the text
     * @param date the date
     * @param time the time
     * @param from the from
     * @param to   the to
     */
    public Message(String text, String date, String time, String from, String to) {
        this.text = text;
        this.date = date;
        this.time = time;
        this.from = from;
        this.to = to;
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
}
