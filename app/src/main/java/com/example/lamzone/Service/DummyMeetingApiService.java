package com.example.lamzone.Service;

import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Participant;
import com.example.lamzone.Model.Room;

import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private final List<Meeting> allMeetings = MeetingGenerator.generateMeetings();
    private final List<Meeting> allMeetingsForTest = MeetingGenerator.generateMeetingsForTest();
    private final List<Room> allRooms = MeetingGenerator.generateRooms();
    private final List<Participant> allParticipants = MeetingGenerator.generateParticipants();

    @Override
    public List<Meeting> getAllMeetings() {
        return allMeetings;
    }
    public List<Meeting> getAllMeetingsForTest() {return allMeetingsForTest;}

    @Override
    public List<Room> getAllRooms() {return allRooms;}

    @Override
    public List<Participant> getAllParticipants() {
        return allParticipants;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        allMeetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        allMeetings.add(meeting);
    }

    @Override
    public List<Meeting> filterMeetingsByDate(String date) {
        List<Meeting> filteredListByDate = new ArrayList<>();
        for (Meeting meeting : allMeetings) {
            if(meeting.getDateFormatted().equals(date)) {
                filteredListByDate.add(meeting);
            }
        }
        return filteredListByDate;
    }

    @Override
    public List<Meeting> filterMeetingsByRoom(String roomName) {
        List<Meeting> filteredListByRoom = new ArrayList<>();
        for (Meeting meeting : allMeetings) {
            if(meeting.getRoom().getRoomName().equals(roomName)) {
                filteredListByRoom.add(meeting);
            }
        }
        return filteredListByRoom;
    }
}
