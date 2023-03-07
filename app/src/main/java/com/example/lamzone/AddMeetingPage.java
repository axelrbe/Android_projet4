package com.example.lamzone;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.lamzone.DI.DI;
import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Participant;
import com.example.lamzone.Model.Room;
import com.example.lamzone.Repository.MeetingRepository;
import com.example.lamzone.Service.MeetingApiService;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddMeetingPage extends AppCompatActivity {

    public static final int Theme_Material_Dialog_Alert = 16974374;
    EditText meetingSubject;
    ChipGroup meetingParticipants;
    Button meetingTime, meetingDate, meetingRoomBtn;
    ImageView arrowBack;
    private Room meetingRoom;
    String roomName;
    FloatingActionButton addMeetingBtn;
    private MeetingApiService meetingApiService;
    MeetingRepository meetingRepository;
    Spinner spinner;
    int year, month, day, hour, minute;
    Calendar calendar;
    Date date;
    List<Participant> allParticipants;
    List<String> selectedParticipants;
    int duration = 45;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting_page);

        calendar = Calendar.getInstance();

        meetingApiService = DI.getMeetingApiService();
        meetingRepository = DI.createMeetingRepository();

        meetingSubject = findViewById(R.id.editText_subject);
        meetingDate = findViewById(R.id.showDatePickerBtn);
        meetingTime = findViewById(R.id.showTimePickerBtn);
        meetingParticipants = findViewById(R.id.chip_group);
        spinner = findViewById(R.id.spinner_rooms);
        meetingRoomBtn = findViewById(R.id.open_spinner_room_btn);
        addMeetingBtn = findViewById(R.id.add_meeting_btn);
        arrowBack = findViewById(R.id.arrow_back);

        arrowBack.setOnClickListener(v -> {
            Intent intent = new Intent(AddMeetingPage.this, MainActivity.class);
            startActivity(intent);
        });

        addMeetingBtn.setOnClickListener(v -> addNewMeeting());

        meetingRoomBtn.setOnClickListener(v -> {
            spinner.setVisibility(View.VISIBLE);
            setRoomsInSpinner();
        });

        meetingTime.setOnClickListener(v -> setTimePickerData());
        meetingDate.setOnClickListener(v -> setDatePickerData());
        setParticipantsToChipGroup();
    }

    private void setRoomsInSpinner() {
        List<Room> rooms = meetingRepository.getMeetingsRoomsList();
        List<String> roomsNames = meetingRepository.getRoomsNamesList();
        spinner.setAdapter(new ArrayAdapter<>(getApplicationContext()
                , R.layout.spinner_items_design, roomsNames));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                roomName = (String) spinner.getSelectedItem();

                for (int i = 0; i < rooms.size(); i++) {
                    if(roomName.equals(rooms.get(i).getRoomName())) {
                        meetingRoom = rooms.get(i);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setTimePickerData() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (view, selectedHour, selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;
            meetingTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, Theme_Material_Dialog_Alert, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    private void setDatePickerData() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, selectedMonth, selectedDay) -> {
            month = selectedMonth;
            day = selectedDay;
            String date = String.format(Locale.getDefault(), "%02d/%02d/%d", day, month + 1, year);
            meetingDate.setText(date);
        };

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, Theme_Material_Dialog_Alert, dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    private void setParticipantsToChipGroup() {
        ChipGroup chipGroup = findViewById(R.id.chip_group);

        // Create a list of participants
        allParticipants = meetingApiService.getAllParticipants();
        selectedParticipants = new ArrayList<>();

        // Add a chip for each participant
        for (Participant participant : allParticipants) {
            Chip chip = new Chip(this);
            chip.setText(participant.getEmail());
            chip.setChipIcon(ContextCompat.getDrawable(this, participant.getImage()));
            chip.setCheckedIcon(ContextCompat.getDrawable(this, R.drawable.baseline_check_24));
            chip.setChipIconSize(110);
            chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.header_blue)));
            chip.setTextColor(ContextCompat.getColor(this , R.color.white));
            chip.setTextSize(18);
            chip.setCheckable(true);
            chipGroup.addView(chip);
        }
    }

    private void getAllSelectedParticipants() {
        selectedParticipants = new ArrayList<>();

        for (int i = 0; i < meetingParticipants.getChildCount(); i++) {
            View participant = meetingParticipants.getChildAt(i);
            if (participant instanceof Chip) {
                Chip chip = (Chip) participant;
                if (chip.isChecked()) {
                    selectedParticipants.add(chip.getText().toString());
                }
            }
        }
    }

    private void addNewMeeting() {
        getAllSelectedParticipants();

         if(meetingRoom == null || meetingTime == null || meetingDate == null || meetingSubject == null || meetingParticipants == null) {
            Toast.makeText(this, R.string.form_error, Toast.LENGTH_SHORT).show();
        } else {
             calendar.set(year, month, day, hour, minute);
             date = calendar.getTime();

             Meeting meeting = new Meeting(
                    meetingSubject.getText().toString(),
                    date,
                    duration,
                    selectedParticipants,
                    meetingRoom
            );
            meetingApiService.createMeeting(meeting);
            startActivity(new Intent(AddMeetingPage.this, MainActivity.class));
        }
    }
}