package com.example.lamzone.DI;

import com.example.lamzone.Repository.MeetingRepository;
import com.example.lamzone.Service.DummyMeetingApiService;
import com.example.lamzone.Service.MeetingApiService;

public class DI {

    private static final MeetingApiService meetingService = new DummyMeetingApiService();


    public static MeetingApiService getMeetingApiService() {
        return meetingService;
    }

    public static MeetingRepository createMeetingRepository() {
        return new MeetingRepository(new DummyMeetingApiService());
    }

}
