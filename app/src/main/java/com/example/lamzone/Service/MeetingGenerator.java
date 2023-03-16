package com.example.lamzone.Service;

import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Participant;
import com.example.lamzone.Model.Room;
import com.example.lamzone.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MeetingGenerator {
    public static List<Meeting> DUMMY_MEETINGS = Collections.emptyList();

    public static List<Room> ROOM_LIST = Arrays.asList(
            new Room("Goomba", R.drawable.color_brown, 3),
            new Room("Waluigi", R.drawable.color_purple, 5),
            new Room("Harmonie", R.drawable.color_blue, 8),
            new Room("Wario", R.drawable.color_yellow, 4),
            new Room("Toad", R.drawable.color_grey, 5),
            new Room("Luigi", R.drawable.color_green, 3),
            new Room("Peach", R.drawable.color_pink, 6),
            new Room("Mario", R.drawable.color_red, 7),
            new Room("Bowser", R.drawable.color_orange, 10),
            new Room("Bob-omb", R.drawable.color_black, 6)
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

    public static Date generateRandomDate() {
        Random random = new Random();
        long startMillis = new Date(0L).getTime(); // start from the beginning of the epoch
        long endMillis = System.currentTimeMillis(); // end at current time
        long randomMillisSinceEpoch = startMillis + (long) (random.nextDouble() * (endMillis - startMillis));
        return new Date(randomMillisSinceEpoch);
    }

    public static List<String> PARTICIPANTS = Arrays.asList(
            "Leo@Lamzone.com",
            "Chloe@Lamzone.com",
            "Paul@Lamzone.com"
    );

    public static List<Meeting> DUMMY_MEETINGS_FOR_TEST = Arrays.asList(
            new Meeting("subject 1", generateRandomDate(), "30min", PARTICIPANTS, ROOM_LIST.get(0)),
            new Meeting("subject 2", generateRandomDate(), "1h30min", PARTICIPANTS, ROOM_LIST.get(1)),
            new Meeting("subject 3", generateRandomDate(), "2h00min", PARTICIPANTS, ROOM_LIST.get(2))
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
    static List<Room> generateRooms() {return new ArrayList<>(ROOM_LIST);}
    static List<Participant> generateParticipants() {return new ArrayList<>(PARTICIPANT_LIST);}
    static List<Meeting> generateMeetingsForTest() {return new ArrayList<>(DUMMY_MEETINGS_FOR_TEST);}
}
