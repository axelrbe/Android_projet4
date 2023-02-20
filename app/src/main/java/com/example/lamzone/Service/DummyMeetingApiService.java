package com.example.lamzone.Service;

import com.example.lamzone.Model.Meeting;

import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private final List<Meeting> allMeetings = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getAllMeetings() {
        return allMeetings;
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
