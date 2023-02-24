package com.example.lamzone.Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Meeting implements Serializable {

    private String subject;
    private Date date;
    private String participants;
    private Room room;

    public Meeting(String subject, Date date, String participants, Room room) {
        this.subject = subject;
        this.date = date;
        this.participants = participants;
        this.room = room;
    }

    public String getName() {
        return subject;
    }

    public void setName(String name) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public Room getRoom() { return room; }

    public void setRoom(Room room) { this.room = room; }

    public String getDateFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public StringBuilder getTimeFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String time = sdf.format(this.getDate());
        StringBuilder formattedTime = new StringBuilder(time);
        formattedTime.setCharAt(2, 'H');
        return formattedTime;
    }
}