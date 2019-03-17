package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.model;

public class Message {
    public String text;
    public String date;
    public String time;
    public String from;
    public String to;

    public Message(String text, String date, String time, String from, String to) {
        this.text = text;
        this.date = date;
        this.time = time;
        this.from = from;
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
