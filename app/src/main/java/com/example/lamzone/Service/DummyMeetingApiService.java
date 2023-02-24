package com.example.lamzone.Service;

import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Room;

import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private final List<Meeting> allMeetings = MeetingGenerator.generateMeetings();
    private final List<Room> allRooms = MeetingGenerator.generateRooms();

    @Override
    public List<Meeting> getAllMeetings() {
        return allMeetings;
    }

    @Override
    public List<Room> getAllRooms() {return allRooms;}

    @Override
    public void deleteMeeting(Meeting meeting) {
        allMeetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        allMeetings.add(meeting);
    }
}
