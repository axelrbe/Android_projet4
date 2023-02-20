package com.example.lamzone.DI;

import com.example.lamzone.Service.DummyMeetingApiService;
import com.example.lamzone.Service.MeetingApiService;

public class DI {

    private static final MeetingApiService service = new DummyMeetingApiService();


    public static MeetingApiService getMeetingApiService() {
        return service;
    }

}
