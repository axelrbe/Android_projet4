package com.example.lamzone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Participant;
import com.example.lamzone.Model.Room;
import com.example.lamzone.Repository.MeetingRepository;
import com.example.lamzone.Service.DummyMeetingApiService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MeetingUnitTest {
    private MeetingRepository meetingRepository;

    @Before
    public void setup() {
        meetingRepository = new MeetingRepository(new DummyMeetingApiService());
    }

    // Verifier que l'on récupère tout les meeting
    @Test
    public void getAllMeetingsWithSuccess() {
        assertEquals(0, meetingRepository.getMeetingsList().size());

        meetingRepository.addMeeting(meetingRepository.getMeetingsListForTest().get(0));
        meetingRepository.addMeeting(meetingRepository.getMeetingsListForTest().get(1));
        meetingRepository.addMeeting(meetingRepository.getMeetingsListForTest().get(2));

        assertEquals(3, meetingRepository.getMeetingsList().size());
    }

    // Verifier l'ajout d'une réunion
    @Test
    public void addNewMeetingWithSuccess() {
        Calendar calendar = Calendar.getInstance();
        Date dateForTest = calendar.getTime();
        List<Meeting> allMeetings = meetingRepository.getMeetingsList();
        List<Meeting> meetingsForTest = meetingRepository.getMeetingsListForTest();
        List<String> participantsListForTest = meetingsForTest.get(1).getParticipants();
        List<Room> allRooms = meetingRepository.getMeetingsRoomsList();

        Meeting meeting = new Meeting("Test", dateForTest, "30min", participantsListForTest, allRooms.get(0));
        meetingRepository.addMeeting(meeting);
        assertTrue(allMeetings.contains(meeting));
    }

    // Verifier la suppréssion d'une réunion
    @Test
    public void deleteMeetingWithSuccess() {
        Calendar calendar = Calendar.getInstance();
        List<Meeting> allMeetings = meetingRepository.getMeetingsList();

        Meeting meeting = new Meeting("Test", calendar.getTime(), "30min", meetingRepository.getMeetingsListForTest().get(1).getParticipants(), meetingRepository.getMeetingsRoomsList().get(0));
        meetingRepository.addMeeting(meeting);
        assertTrue(allMeetings.contains(meeting));
        meetingRepository.deleteMeeting(meeting);
        assertFalse(allMeetings.contains(meeting));
    }

    // Verifier que l'on récupère toutes les salles
    @Test
    public void getAllMeetingsRoomsWithSuccess() {
        assertEquals(10, meetingRepository.getMeetingsRoomsList().size());
    }

    // Vérifier la fonctionnalité de filtrer par salle
    @Test
    public void filterByRoomWithSuccess() {
        assertEquals(0, meetingRepository.getMeetingsList().size());

        meetingRepository.addMeeting(meetingRepository.getMeetingsListForTest().get(0));
        meetingRepository.addMeeting(meetingRepository.getMeetingsListForTest().get(1));
        meetingRepository.addMeeting(meetingRepository.getMeetingsListForTest().get(2));

        String expectedMeeting = meetingRepository.getMeetingsListForTest().get(2).getRoom().getRoomName();
        assertEquals(meetingRepository.filterMeetingByRoom("Harmonie").get(0).getRoom().getRoomName(), expectedMeeting);
    }


    // Verifier la fonctionnalité de filter par date
    @Test
    public void filterByDateWithSuccess() {
        assertEquals(0, meetingRepository.getMeetingsList().size());

        meetingRepository.addMeeting(meetingRepository.getMeetingsListForTest().get(0));
        meetingRepository.addMeeting(meetingRepository.getMeetingsListForTest().get(1));
        meetingRepository.addMeeting(meetingRepository.getMeetingsListForTest().get(2));

        String expectedMeeting = meetingRepository.getMeetingsListForTest().get(1).getDateFormatted();
        assertEquals(meetingRepository.filterMeetingByDate(meetingRepository.getMeetingsListForTest().get(1).getDateFormatted()).get(0).getDateFormatted(), expectedMeeting);
    }
}