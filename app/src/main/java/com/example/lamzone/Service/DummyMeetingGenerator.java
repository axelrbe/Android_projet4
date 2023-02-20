package com.example.lamzone.Service;

import com.example.lamzone.Model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(1, "Peach", "14h00", "Réunion A", "maxime@lamzone.com, alex@lamzone.com", MeetingRoomGenerator.generateRoom().get(0)),
            new Meeting(2, "Mario", "16h00", "Réunion B", "paul@lamzone.com, viviane@lamzone.com", MeetingRoomGenerator.generateRoom().get(1)),
            new Meeting(3, "Luigi", "19h00", "Réunion C", "amandine@lamzone.com, luv@lamzone.com", MeetingRoomGenerator.generateRoom().get(2)),
            new Meeting(4, "Toad", "19h00", "Réunion D", "lea@lamzone.com, alexandre@lamzone.com", MeetingRoomGenerator.generateRoom().get(3)),
            new Meeting(5, "Wario", "19h00", "Réunion E", "ines@lamzone.com, axel@lamzone.com", MeetingRoomGenerator.generateRoom().get(4)),
            new Meeting(6, "Waluigi", "19h00", "Réunion F", "samantha@lamzone.com, sandra@lamzone.com", MeetingRoomGenerator.generateRoom().get(5)),
            new Meeting(7, "Harmonie", "19h00", "Réunion G", "clara@lamzone.com, marleine@lamzone.com", MeetingRoomGenerator.generateRoom().get(6)),
            new Meeting(8, "Bowser", "19h00", "Réunion H", "paula@lamzone.com, eden@lamzone.com", MeetingRoomGenerator.generateRoom().get(7))
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
