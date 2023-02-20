package com.example.lamzone.Service;

import com.example.lamzone.Model.Meeting;

import java.util.List;

public interface MeetingApiService {

    // Get all meetings
    List<Meeting> getAllMeetings();

    // Delete a meeting
    void deleteMeeting(Meeting meeting);

    // Create a meeting
    void createMeeting(Meeting meeting);

}
