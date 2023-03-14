package com.example.lamzone.Service;

import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Participant;
import com.example.lamzone.Model.Room;
import com.example.lamzone.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Collections.emptyList();

    public static List<Room> ROOM_LIST = Arrays.asList(
            new Room("Goomba", R.drawable.color_brown),
            new Room("Waluigi", R.drawable.color_purple),
            new Room("Harmonie", R.drawable.color_blue),
            new Room("Wario", R.drawable.color_yellow),
            new Room("Toad", R.drawable.color_grey),
            new Room("Luigi", R.drawable.color_green),
            new Room("Peach", R.drawable.color_pink),
            new Room("Mario", R.drawable.color_red),
            new Room("Bowser", R.drawable.color_orange),
            new Room("Bob-omb", R.drawable.color_black)
    );

    public static List<Participant> PARTICIPANT_LIST = Arrays.asList(
            new Participant("Paul@lamzone.fr", "Paul", R.drawable.img_1),
            new Participant("Leo@lamzone.fr", "Leo", R.drawable.img_2),
            new Participant("Chloe@lamzone.fr", "Chloe", R.drawable.img_6),
            new Participant("Sandra@lamzone.fr", "Sandra", R.drawable.img_4),
            new Participant("Arthur@lamzone.fr","Arthur", R.drawable.img_5),
            new Participant("Clement@lamzone.fr", "Clement", R.drawable.img_3),
            new Participant("patrick@lamzone.fr", "patrick", R.drawable.img),
            new Participant("Josette@lamzone.fr", "Josette", R.drawable.img_8),
            new Participant("Martine@lamzone.fr", "Martine", R.drawable.img_9),
            new Participant("Julien@lamzone.fr", "Julien", R.drawable.img_13),
            new Participant("Daniel@lamzone.fr", "Daniel", R.drawable.img_11),
            new Participant("Alexandra@lamzone.fr", "Alexandra", R.drawable.img_12),
            new Participant("Lucette@lamzone.fr", "Lucette", R.drawable.img_10),
            new Participant("Anna@lamzone.fr","Anna", R.drawable.img_7)
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
    static List<Room> generateRooms() {return new ArrayList<>(ROOM_LIST);}
    static List<Participant> generateParticipants() {return new ArrayList<>(PARTICIPANT_LIST);}
}
