package com.example.lamzone.Model;


public class Room {

    private final int color;
    private final String roomName;
    private final int availablePlaces;

    public Room(String roomName, int color, int availablePlaces) {
        this.color = color;
        this.roomName = roomName;
        this.availablePlaces = availablePlaces;
    }

    public int getColor() {
        return color;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getAvailablePlaces() { return availablePlaces; }
}
