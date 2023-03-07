package com.example.lamzone.Service;

import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Participant;
import com.example.lamzone.Model.Room;
import com.example.lamzone.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList();

    public static List<Room> ROOM_LIST = Arrays.asList(
            new Room("Salle 1", R.drawable.color_brown),
            new Room("Salle 2", R.drawable.color_purple),
            new Room("Salle 3", R.drawable.color_blue),
            new Room("Salle 4", R.drawable.color_yellow),
            new Room("Salle 5", R.drawable.color_grey),
            new Room("Salle 6", R.drawable.color_green),
            new Room("Salle 7", R.drawable.color_pink),
            new Room("Salle 8", R.drawable.color_red),
            new Room("Salle 9", R.drawable.color_orange),
            new Room("Salle 10", R.drawable.color_cyan)
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

    public static List<Participant> PARTICIPANT_LIST = Arrays.asList(
            new Participant("Paul", "Paul@lamzone.fr"),
            new Participant("Léa", "Léa@lamzone.fr"),
            new Participant("Chloé", "Chloé@lamzone.fr"),
            new Participant("Sandra", "Sandra@lamzone.fr"),
            new Participant("Arthur","Arthur@lamzone.fr"),
            new Participant("Clément", "Clément@lamzone.fr"),
            new Participant("patrick", "patrick@lamzone.fr"),
            new Participant("Josette", "Josette@lamzone.fr"),
            new Participant("Martine", "Martine@lamzone.fr"),
            new Participant("Julien", "Julien@lamzone.fr"),
            new Participant("José", "José@lamzone.fr"),
            new Participant("Alexandra", "Alexandra@lamzone.fr"),
            new Participant("Luciette", "Luciette@lamzone.fr"),
            new Participant("Clodine","Clodine@lamzone.fr")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
    static List<Room> generateRooms() {return new ArrayList<>(ROOM_LIST);}
    static List<Participant> generateParticipants() {return new ArrayList<>(PARTICIPANT_LIST);}
}
