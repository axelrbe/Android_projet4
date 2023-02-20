package com.example.lamzone.Model;

import java.io.Serializable;

public class Meeting implements Serializable {

    private final long id;
    private String subject;
    private String time;
    private String location;
    private String participants;
    private Room room;

    public Meeting(long id, String subject, String time, String location, String participants, Room room) {
        this.id = id;
        this.subject = subject;
        this.time = time;
        this.location = location;
        this.participants = participants;
        this.room = room;
    }

    public String getName() {
        return subject;
    }

    public void setName(String name) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public Room getRoom() { return room; }

    public void setRoom(Room room) { this.room = room; }
}