package com.example.lamzone.Model;


public class Room {
    /** Color */
    private final int color;
    /** Name of the room */
    private final String roomName;
    /** Number of available places */
    private final int availablePlaces;

    /**
     * Constructor + Getters and Setters
     */
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
