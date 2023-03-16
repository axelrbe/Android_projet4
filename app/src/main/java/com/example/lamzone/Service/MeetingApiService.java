package com.example.lamzone.Service;

import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Participant;
import com.example.lamzone.Model.Room;

import java.lang.reflect.Parameter;
import java.util.List;

public interface MeetingApiService {

    // Get all meetings
    List<Meeting> getAllMeetings();
    List<Meeting> getAllMeetingsForTest();

    List<Room> getAllRooms();

    List<Participant> getAllParticipants();

    // Delete a meeting
    void deleteMeeting(Meeting meeting);

    // Create a meeting
    void createMeeting(Meeting meeting);

    List<Meeting> filterMeetingsByDate(String date);

    List<Meeting> filterMeetingsByRoom(String roomName);
}
