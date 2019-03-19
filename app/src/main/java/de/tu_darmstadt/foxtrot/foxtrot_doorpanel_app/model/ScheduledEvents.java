package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.model;

/**
 * The type Scheduled events.
 */
public class ScheduledEvents {
    private String eventId;
    private String eventSummary;
    private String description;
    private String attendees;
    private String location;

    private String startDate;
    private String endDate;

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets event id.
     *
     * @return the event id
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Sets event id.
     *
     * @param eventId the event id
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * Gets event summary.
     *
     * @return the event summary
     */
    public String getEventSummary() {
        return eventSummary;
    }

    /**
     * Sets event summary.
     *
     * @param eventSummary the event summary
     */
    public void setEventSummary(String eventSummary) {
        this.eventSummary = eventSummary;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets attendees.
     *
     * @return the attendees
     */
    public String getAttendees() {
        return attendees;
    }

    /**
     * Sets attendees.
     *
     * @param attendees the attendees
     */
    public void setAttendees(String attendees) {
        this.attendees = attendees;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
