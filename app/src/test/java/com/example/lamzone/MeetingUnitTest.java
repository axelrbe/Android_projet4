package com.example.lamzone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Room;
import com.example.lamzone.Repository.MeetingRepository;
import com.example.lamzone.Service.DummyMeetingApiService;

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
        List<Meeting> meetingsForTest = meetingRepository.getMeetingsListForTest();
        List<String> participantsListForTest = meetingsForTest.get(1).getParticipants();
        List<Room> allRooms = meetingRepository.getMeetingsRoomsList();

        Meeting meeting = new Meeting("Test", dateForTest, "30min", participantsListForTest, allRooms.get(0));
        meetingRepository.addMeeting(meeting);
        assertTrue(meetingRepository.getMeetingsList().contains(meeting));
    }

    // Verifier la suppréssion d'une réunion
    @Test
    public void deleteMeetingWithSuccess() {
        Calendar calendar = Calendar.getInstance();

        Meeting meeting = new Meeting("Test", calendar.getTime(), "30min", meetingRepository.getMeetingsListForTest().get(1).getParticipants(), meetingRepository.getMeetingsRoomsList().get(0));
        meetingRepository.addMeeting(meeting);
        assertTrue(meetingRepository.getMeetingsList().contains(meeting));
        meetingRepository.deleteMeeting(meeting);
        assertFalse(meetingRepository.getMeetingsList().contains(meeting));
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

        Calendar calendar = Calendar.getInstance();
        Date dateForTest = calendar.getTime();
        List<Meeting> meetingsForTest = meetingRepository.getMeetingsListForTest();
        List<String> participantsListForTest = meetingsForTest.get(1).getParticipants();
        List<Room> allRooms = meetingRepository.getMeetingsRoomsList();

        Meeting meeting1 = new Meeting("Test1", dateForTest, "30min", participantsListForTest, allRooms.get(1));
        Meeting meeting2 = new Meeting("Test2", dateForTest, "30min", participantsListForTest, allRooms.get(3));
        Meeting meeting3 = new Meeting("Test3", dateForTest, "30min", participantsListForTest, allRooms.get(3));
        Meeting meeting4 = new Meeting("Test4", dateForTest, "30min", participantsListForTest, allRooms.get(6));
        meetingRepository.addMeeting(meeting1);
        meetingRepository.addMeeting(meeting2);
        meetingRepository.addMeeting(meeting3);
        meetingRepository.addMeeting(meeting4);


        String expectedMeetingRoom = meetingRepository.getMeetingsList().get(2).getRoom().getRoomName();
        assertEquals(meetingRepository.filterMeetingByRoom(expectedMeetingRoom).get(0).getRoom().getRoomName(), expectedMeetingRoom);
        assertEquals(meetingRepository.filterMeetingByRoom(expectedMeetingRoom).get(1).getRoom().getRoomName(), expectedMeetingRoom);
        assertEquals(2, meetingRepository.filterMeetingByRoom(expectedMeetingRoom).size());
    }


    // Verifier la fonctionnalité de filter par date
    @Test
    public void filterByDateWithSuccess() {
        assertEquals(0, meetingRepository.getMeetingsList().size());

        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.YEAR, 2023);
        cal1.set(Calendar.MONTH, Calendar.JANUARY);
        cal1.set(Calendar.DAY_OF_MONTH, 9);
        Date dateForTest1 = cal1.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, 2023);
        cal2.set(Calendar.MONTH, Calendar.MARCH);
        cal2.set(Calendar.DAY_OF_MONTH, 16);
        Date dateForTest2 = cal2.getTime();

        Calendar cal3 = Calendar.getInstance();
        cal3.set(Calendar.YEAR, 2023);
        cal3.set(Calendar.MONTH, Calendar.DECEMBER);
        cal3.set(Calendar.DAY_OF_MONTH, 4);
        Date dateForTest3 = cal3.getTime();

        Calendar cal4 = Calendar.getInstance();
        cal4.set(Calendar.YEAR, 2023);
        cal4.set(Calendar.MONTH, Calendar.MARCH);
        cal4.set(Calendar.DAY_OF_MONTH, 16);
        Date dateForTest4 = cal4.getTime();

        List<Meeting> meetingsForTest = meetingRepository.getMeetingsListForTest();
        List<String> participantsListForTest = meetingsForTest.get(1).getParticipants();
        List<Room> allRooms = meetingRepository.getMeetingsRoomsList();

        Meeting meeting1 = new Meeting("Test1", dateForTest1, "30min", participantsListForTest, allRooms.get(1));
        Meeting meeting2 = new Meeting("Test2", dateForTest2, "30min", participantsListForTest, allRooms.get(3));
        Meeting meeting3 = new Meeting("Test3", dateForTest3, "30min", participantsListForTest, allRooms.get(3));
        Meeting meeting4 = new Meeting("Test4", dateForTest4, "30min", participantsListForTest, allRooms.get(6));
        meetingRepository.addMeeting(meeting1);
        meetingRepository.addMeeting(meeting2);
        meetingRepository.addMeeting(meeting3);
        meetingRepository.addMeeting(meeting4);

        String expectedMeetingDate = meetingRepository.getMeetingsList().get(1).getDateFormatted();
        assertEquals(meetingRepository.filterMeetingByDate(expectedMeetingDate).get(0).getDateFormatted(), expectedMeetingDate);
        assertEquals(meetingRepository.filterMeetingByDate(expectedMeetingDate).get(1).getDateFormatted(), expectedMeetingDate);
        assertEquals(2, meetingRepository.filterMeetingByDate(expectedMeetingDate).size());
    }
}