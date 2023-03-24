package com.example.lamzone.Service;

import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Participant;
import com.example.lamzone.Model.Room;

import java.lang.reflect.Parameter;
import java.util.List;

/**
 * Meeting API client
 */
public interface MeetingApiService {

    /**
     * Get all the meetings
     * @return {@link List}
     */
    List<Meeting> getAllMeetings();

    /**
     * Get all the meetings, useful for test
     * @return {@link List}
     */
    List<Meeting> getAllMeetingsForTest();

    /**
     * Get all the rooms
     */
    List<Room> getAllRooms();

    /**
     * Get all the participants
     */
    List<Participant> getAllParticipants();

    /**
     * Deletes a neighbour
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a meeting
     * @param meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * Filter the list of meetings by date
     */
    List<Meeting> filterMeetingsByDate(String date);

    /**
     * Filter the list of meetings by room
     */
    List<Meeting> filterMeetingsByRoom(String roomName);
}
