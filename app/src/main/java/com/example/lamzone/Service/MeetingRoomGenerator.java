package com.example.lamzone.Service;

import com.example.lamzone.Model.Room;
import com.example.lamzone.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeetingRoomGenerator {

    private static final List<Room> LIST_OF_ROOMS = Arrays.asList(

            new Room("Peach", R.drawable.color_pink),
            new Room("Mario", R.drawable.color_red),
            new Room("Luigi", R.drawable.color_green),
            new Room("Toad", R.drawable.color_grey),
            new Room("Wario", R.drawable.color_yellow),
            new Room("Waluigi", R.drawable.color_purple),
            new Room("Harmonie", R.drawable.color_blue),
            new Room("Bowser", R.drawable.color_brown)

    );

    public static List<Room> generateRoom() {
       return new ArrayList<>(LIST_OF_ROOMS);
    }
}
