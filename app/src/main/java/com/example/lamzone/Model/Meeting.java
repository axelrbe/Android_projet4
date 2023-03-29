package com.example.lamzone.Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Meeting implements Serializable {
    /** Subject */
    private String subject;
    /** Date */
    private Date date;
    /** Duration */
    private String duration;
    /** List of participants */
    private List<String> participants;
    /** Room */
    private Room room;

    /**
     * Constructor + Getters and Setters
     */
    public Meeting(String subject, Date date, String duration, List<String> participants, Room room) {
        this.subject = subject;
        this.date = date;
        this.duration = duration;
        this.participants = participants;
        this.room = room;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {return date;}

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDuration() {return duration;}

    public void setDuration(String duration) {this.duration = duration;}

    public List<String> getParticipants() {return participants;}

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public Room getRoom() { return room; }

    public void setRoom(Room room) { this.room = room; }

    public String getDateFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(date);
    }

    public StringBuilder getTimeFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String time = sdf.format(this.getDate());
        StringBuilder formattedTime = new StringBuilder(time);
        formattedTime.setCharAt(2, 'H');
        return formattedTime;
    }
}