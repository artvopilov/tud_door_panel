package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import java.util.List;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.models.Event;

/**
 * The type Worker.
 */
public class Worker {
    private String name;
    private String email;
    private String phoneNumber;
    private int age;
    private String room;
    private String position;
    private String status;
    private String summary;
    private int id;
    private List<Event> timeslots;
    private String image;

    /**
     * Gets image.
     *
     * @return the image
     */
    public String getImage() { return image; }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(String image) { this.image = image; }

    /**
     * Gets room.
     *
     * @return the room
     */
    public String getRoom() {
        return room;
    }

    /**
     * Sets room.
     *
     * @param room the room
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public int getAge() {

        return age;
    }

    /**
     * Sets age.
     *
     * @param age the age
     */
    public void setAge(int age) {
        this.age = age;
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
     * Gets position.
     *
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets position.
     *
     * @param position the position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets summary.
     *
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets summary.
     *
     * @param summary the summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

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
     * Gets timeslots.
     *
     * @return the timeslots
     */
    public List<Event> getTimeslots() {
        return timeslots;
    }

    /**
     * Sets timeslots.
     *
     * @param timeslots the timeslots
     */
    public void setTimeslots(List<Event> timeslots) {
        this.timeslots = timeslots;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Delete timeslot.
     *
     * @param id the id
     */
    public void deleteTimeslot(int id) {
        for (Event slot : this.timeslots){
            if (slot.getId() == id){
                this.timeslots.remove(slot);
            }
        }
    }
}

