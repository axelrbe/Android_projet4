package com.example.lamzone;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lamzone.DI.DI;
import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Room;
import com.example.lamzone.Repository.MeetingRepository;
import com.example.lamzone.Service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddMeetingPage extends AppCompatActivity {

    EditText meetingSubject, meetingParticipants;
    Button meetingTime, meetingDate;
    ImageView arrowBack;
    private Room room;
    FloatingActionButton addMeetingBtn;
    private MeetingApiService meetingApiService;
    MeetingRepository meetingRepository;
    Spinner spinner;
    int year, month, day, hour, minute;
    String selectedTime;
    Calendar calendar;
    Date date;

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
        meetingParticipants = findViewById(R.id.editText_participants);
        addMeetingBtn = findViewById(R.id.add_meeting_btn);
        arrowBack = findViewById(R.id.arrow_back);

        arrowBack.setOnClickListener(v -> {
            Intent intent = new Intent(AddMeetingPage.this, MainActivity.class);
            startActivity(intent);
        });

        addMeetingBtn.setOnClickListener(v -> {
            addNewMeeting();
        });

        setRoomsInSpinner();

        meetingTime.setOnClickListener(v -> setTimePickerData());
        meetingDate.setOnClickListener(v -> setDatePickerData());
    }

    private void setRoomsInSpinner() {
        spinner = findViewById(R.id.spinner_rooms);
        List<Room> rooms = meetingRepository.getMeetingsRoomsList();
        spinner.setAdapter(new ArrayAdapter<>(getApplicationContext()
                , android.R.layout.simple_spinner_dropdown_item, rooms));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                room = (Room) spinner.getSelectedItem();
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

        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

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

        int style = AlertDialog.THEME_HOLO_DARK;

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    private void addNewMeeting() {
        if(minute >= 10) {
            selectedTime = hour + "h" + minute;
        } else {
            selectedTime = hour + "h0" + minute;
        }

         if(meetingTime == null) {
            Toast.makeText(this, "Renseigner une heure", Toast.LENGTH_SHORT).show();
        } else if(meetingSubject == null) {
            Toast.makeText(this, "Renseigner le sujet de la réunion", Toast.LENGTH_SHORT).show();
        } else if(meetingParticipants == null) {
            Toast.makeText(this, "Indiquer les participants", Toast.LENGTH_SHORT).show();
        } else {
             calendar.set(year, month, day, hour, minute);
             date = calendar.getTime();

            Meeting meeting = new Meeting(
                    meetingSubject.getText().toString(),
                    date,
                    meetingParticipants.getText().toString(),
                    room
            );
            meetingApiService.createMeeting(meeting);
            startActivity(new Intent(AddMeetingPage.this, MainActivity.class));
        }
    }
}