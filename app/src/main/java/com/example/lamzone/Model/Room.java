package com.example.lamzone.Model;


public class Room {

    private final int color;
    private final String roomName;

    public Room(String roomName, int color) {
        this.color = color;
        this.roomName = roomName;
    }

    public int getColor() {
        return color;
    }

    public String getRoomName() {
        return roomName;
    }
}
