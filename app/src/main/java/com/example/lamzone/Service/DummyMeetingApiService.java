package com.example.lamzone.Service;

import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Participant;
import com.example.lamzone.Model.Room;

import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private final List<Meeting> allMeetings = MeetingGenerator.generateMeetings();
    private final List<Room> allRooms = MeetingGenerator.generateRooms();
    private final List<Participant> allParticipants = MeetingGenerator.generateParticipants();

    @Override
    public List<Meeting> getAllMeetings() {
        return allMeetings;
    }

    @Override
    public List<Room> getAllRooms() {return allRooms;}

    @Override
    public List<String> getAllRoomsNames() {
        List<String> roomsNames = new ArrayList<>();

        for (int i = 0; i < allRooms.size(); i++) {
            roomsNames.add(allRooms.get(i).getRoomName());
        }
        return roomsNames;
    }

    @Override
    public List<Participant> getAllParticipants() {
        return allParticipants;
    }

    @Override
    public List<String> getAllParticipantsEmails() {
        List<String> participantsEmails = new ArrayList<>();

        for (int i = 0; i < allParticipants.size(); i++) {
            participantsEmails.add(allParticipants.get(i).getEmail());
        }
        return participantsEmails;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        allMeetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        allMeetings.add(meeting);
    }
}
