package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.models;

import java.util.Date;

/**
 * The type Event.
 */
public class Event {
    private int id;
    private String name;
    private Date start;
    private Date end;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
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
     * Gets start.
     *
     * @return the start
     */
    public Date getStart() {
        return start;
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * Gets end.
     *
     * @return the end
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Sets end.
     *
     * @param end the end
     */
    public void setEnd(Date end) {
        this.end = end;
    }
}
