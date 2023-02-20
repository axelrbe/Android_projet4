package com.example.lamzone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lamzone.DI.DI;
import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Room;
import com.example.lamzone.Service.MeetingApiService;
import com.example.lamzone.Service.MeetingRoomGenerator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Random;

public class AddMeetingPage extends AppCompatActivity {

    EditText meetingSubject, meetingTime, meetingLocation, meetingParticipants;
    ImageView arrowBack;
    FloatingActionButton addMeetingBtn;
    private MeetingApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting_page);

        apiService = DI.getMeetingApiService();

        meetingSubject = findViewById(R.id.editText_subject);
        meetingTime = findViewById(R.id.editText_time);
        meetingLocation = findViewById(R.id.editText_location);
        meetingParticipants = findViewById(R.id.editText_participants);
        addMeetingBtn = findViewById(R.id.add_meeting_btn);
        arrowBack = findViewById(R.id.arrow_back);

        arrowBack.setOnClickListener(v -> {
            Intent intent = new Intent(AddMeetingPage.this, MainActivity.class);
            startActivity(intent);
        });

        List<Room> rooms = MeetingRoomGenerator.generateRoom();
        Random random = new Random();
        int random_room = random.nextInt(rooms.size());

        addMeetingBtn.setOnClickListener(v -> {
            Meeting meeting = new Meeting(
                    System.currentTimeMillis(),
                    meetingSubject.getText().toString(),
                    meetingTime.getText().toString(),
                    meetingLocation.getText().toString(),
                    meetingParticipants.getText().toString(),
                    MeetingRoomGenerator.generateRoom().get(random_room)
            );
            apiService.createMeeting(meeting);
            finish();
        });
    }
}