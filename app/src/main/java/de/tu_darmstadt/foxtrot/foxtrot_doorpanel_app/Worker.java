package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import java.util.List;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.models.Event;

public class Worker {
    private String name;
    private String email;
    private int age;
    private String room;
    private String position;
    private String status;
    private String summary;
    private int id;
    private List<Event> timeslots;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getAge() {

        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Event> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(List<Event> timeslots) {
        this.timeslots = timeslots;
    }

    public void deleteTimeslot(int id) {
        for (Event slot : this.timeslots){
            if (slot.getId() == id){
                this.timeslots.remove(slot);
            }
        }
    }
}

