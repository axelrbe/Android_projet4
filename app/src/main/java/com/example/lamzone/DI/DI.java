package com.example.lamzone.DI;

import com.example.lamzone.Repository.MeetingRepository;
import com.example.lamzone.Service.DummyMeetingApiService;
import com.example.lamzone.Service.MeetingApiService;

public class DI {

    private static final MeetingApiService meetingService = new DummyMeetingApiService();

    /**
     * Get an instance on @{@link MeetingApiService}
     */

    public static MeetingApiService getMeetingApiService() {
        return meetingService;
    }

    /**
     * Create a new Meeting repository @{@link MeetingRepository} Useful for using all the method of Meeting service
     */

    public static MeetingRepository createMeetingRepository() {
        return new MeetingRepository(new DummyMeetingApiService());
    }

}
