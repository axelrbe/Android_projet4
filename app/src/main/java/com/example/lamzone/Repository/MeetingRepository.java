package com.example.lamzone.Repository;

import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Room;
import com.example.lamzone.Service.MeetingApiService;

import java.util.List;

public class MeetingRepository {

    private final MeetingApiService apiService;

    public MeetingRepository(MeetingApiService meetingApiService) {apiService = meetingApiService;}

    public List<Meeting> getMeetingsList() {return apiService.getAllMeetings();}

    public void addMeeting(Meeting meeting) {
        apiService.createMeeting(meeting);
    }

    public void deleteMeeting(Meeting meeting) {
        apiService.deleteMeeting(meeting);
    }

    public List<Room> getMeetingsRoomsList() {return apiService.getAllRooms();}

    public List<String> getRoomsNamesList() {return apiService.getAllRoomsNames();}
}
