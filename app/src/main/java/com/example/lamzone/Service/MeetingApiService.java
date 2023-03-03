package com.example.lamzone.Service;

import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Participant;
import com.example.lamzone.Model.Room;

import java.lang.reflect.Parameter;
import java.util.List;

public interface MeetingApiService {

    // Get all meetings
    List<Meeting> getAllMeetings();

    List<Room> getAllRooms();

    List<String> getAllRoomsNames();
    List<Participant> getAllParticipants();
    List<String> getAllParticipantsEmails();

    // Delete a meeting
    void deleteMeeting(Meeting meeting);

    // Create a meeting
    void createMeeting(Meeting meeting);

}
