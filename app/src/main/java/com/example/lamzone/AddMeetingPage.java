package com.example.lamzone;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.lamzone.DI.DI;
import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Model.Participant;
import com.example.lamzone.Model.Room;
import com.example.lamzone.Repository.MeetingRepository;
import com.example.lamzone.Service.MeetingApiService;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddMeetingPage extends AppCompatActivity {
    EditText subjectEditText;
    AutoCompleteTextView meetingParticipants;
    TextView timingTextView, dateTextView, showSpinnerBtn;
    ImageView arrowBack;
    private Room meetingRoom;
    String roomName, selectedDuration;
    FloatingActionButton addMeetingBtn;
    private MeetingApiService meetingApiService;
    MeetingRepository meetingRepository;
    Spinner spinnerRooms;
    int year, month, day, hour, minute;
    Calendar calendar;
    Date date;
    List<Participant> allParticipants;
    List<String> selectedParticipants;
    ListView participantsListView;
    ArrayAdapter<String> participantsListAdapter;
    ChipGroup durationChipGroup;
    ImageView warningTimeIcon, warningDateIcon, warningDurationIcon, warningParticipantsIcon, warningSubjectIcon, warningRoomIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting_page);

        calendar = Calendar.getInstance();

        meetingApiService = DI.getMeetingApiService();
        meetingRepository = DI.createMeetingRepository();

        allParticipants = meetingApiService.getAllParticipants();

        meetingParticipants = findViewById(R.id.participants);
        spinnerRooms = findViewById(R.id.spinner_rooms);
        showSpinnerBtn = findViewById(R.id.show_spinner_btn);
        addMeetingBtn = findViewById(R.id.save_meeting_btn);
        arrowBack = findViewById(R.id.arrow_back);
        dateTextView = findViewById(R.id.textview_date);
        timingTextView = findViewById(R.id.textview_timing);
        subjectEditText = findViewById(R.id.editText_subject);
        participantsListView = findViewById(R.id.participants_list);
        warningDateIcon = findViewById(R.id.warning_date_icon);
        warningTimeIcon = findViewById(R.id.warning_time_icon);
        warningDurationIcon = findViewById(R.id.warning_duration_icon);
        warningParticipantsIcon = findViewById(R.id.warning_participants_icon);
        warningRoomIcon = findViewById(R.id.warning_room_icon);
        warningSubjectIcon = findViewById(R.id.warning_subject_icon);

        arrowBack.setOnClickListener(v -> {
            Intent intent = new Intent(AddMeetingPage.this, MainActivity.class);
            startActivity(intent);
        });

        addMeetingBtn.setOnClickListener(v -> addNewMeeting());

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        timingTextView.setHint(currentTime);
        dateTextView.setHint(currentDate);

        timingTextView.setOnClickListener(v -> setTimePickerData());
        dateTextView.setOnClickListener(v -> setDatePickerData());
        showSpinnerBtn.setOnClickListener(v -> setRoomsInSpinner());
        setParticipants();
        setDurationToChipGroup();
    }

    /**
     * Set the list of participants in AutoCompleteTextView
     */
    private void setParticipants() {
        // Ajouter liste des emails a autoCompleteTextView
        List<String> allParticipantsEmails = new ArrayList<>();
        for (Participant participant:allParticipants) {
            allParticipantsEmails.add(participant.getEmail());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, allParticipantsEmails);

        AutoCompleteTextView participantsTextView = findViewById(R.id.participants);
        participantsTextView.setAdapter(adapter);

        // Ajouter les participants a la listView
        selectedParticipants = new ArrayList<>();
        List<String> textList = new ArrayList<>();

        participantsListAdapter = new ArrayAdapter<>(this, R.layout.list_view_item_design,R.id.list_view_item, textList);
        participantsListView.setAdapter(participantsListAdapter);

        participantsTextView.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                String enteredText = participantsTextView.getText().toString();
                for (String participant:textList) {
                    if(enteredText.equals(participant)) {
                        Toast.makeText(AddMeetingPage.this, R.string.same_participant_error, Toast.LENGTH_SHORT).show();
                        participantsTextView.setText("");
                        return false;
                    } else if (!enteredText.contains("@") || !enteredText.endsWith(".fr")) {
                        Toast.makeText(AddMeetingPage.this, R.string.false_participant_error, Toast.LENGTH_SHORT).show();
                        participantsTextView.setText("");
                        return false;
                    }
                }
                textList.add(participantsTextView.getText().toString());
                selectedParticipants.add(enteredText);
                participantsListAdapter.notifyDataSetChanged();
                participantsListView.setVisibility(View.VISIBLE);
                showSpinnerBtn.setVisibility(View.VISIBLE);
                spinnerRooms.setVisibility(View.GONE);
                participantsTextView.setText("");
                return true;
                }
            return false;
            });
    }

    /**
     * Set all the rooms in a spinner
     */
    private void setRoomsInSpinner() {
        showSpinnerBtn.setVisibility(View.GONE);
        spinnerRooms.setVisibility(View.VISIBLE);
        List<Room> rooms = meetingRepository.getMeetingsRoomsList();
        List<String> availableRooms = new ArrayList<>();

        for (Room room : rooms) {
            if(room.getAvailablePlaces() >= participantsListView.getCount()) {
                availableRooms.add(room.getRoomName());
            }
        }

        spinnerRooms.setAdapter(new ArrayAdapter<>(getApplicationContext()
                , R.layout.spinner_item_design, availableRooms));

        spinnerRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                roomName = (String) spinnerRooms.getSelectedItem();

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

    /**
     * Set the TimePicker management
     */
    private void setTimePickerData() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (view, selectedHour, selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;
            timingTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.MySpinnerTimePickerStyle, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle(getString(R.string.time_selection));
        timePickerDialog.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_access_time_filled_24, null));
        timePickerDialog.show();
    }

    /**
     * Set the DatePicker management
     */
    private void setDatePickerData() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, selectedYear, selectedMonth, selectedDay) -> {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            String date = String.format(Locale.getDefault(), "%02d/%02d/%d", day, month + 1, year);
            dateTextView.setText(date);
        };

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.MySpinnerDatePickerStyle, dateSetListener, year, month, day);
        datePickerDialog.setTitle(getString(R.string.date_selection));
        datePickerDialog.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_date_range_24, null));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    /**
     * Set the durations in a ChipGroup
     */
    private void setDurationToChipGroup() {
        durationChipGroup = findViewById(R.id.chip_group);

        List<String> durationList = new ArrayList<>();
        durationList.add("30min");
        durationList.add("1h00min");
        durationList.add("1h30min");
        durationList.add("2h00min");

        for (String duration : durationList) {
            Chip chip = new Chip(this);
            chip.setText(duration);
            chip.setCheckedIcon(ContextCompat.getDrawable(this, R.drawable.baseline_check_24));
            chip.setChipIconSize(110);
            chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.header_blue)));
            chip.setTextColor(ContextCompat.getColor(this , R.color.white));
            chip.setTextSize(23);
            chip.setCheckable(true);
            durationChipGroup.addView(chip);
            durationChipGroup.check(durationChipGroup.getChildAt(0).getId());
        }

        int selectedChipId = durationChipGroup.getCheckedChipId();
        if (selectedChipId != -1) {
            Chip selectedChip = findViewById(selectedChipId);
            selectedDuration = selectedChip.getText().toString();
        }
    }

    /**
     * method use to add a new meeting to the list
     */
    private void addNewMeeting() {
        int errors = 0;

        if(TextUtils.isEmpty(dateTextView.getText())) {
            errors++;
             warningDateIcon.setVisibility(View.VISIBLE);
         } else {
            warningDateIcon.setVisibility(View.GONE);
        }

        if(TextUtils.isEmpty(timingTextView.getText())) {
            errors++;
            warningTimeIcon.setVisibility(View.VISIBLE);
        } else {
            warningTimeIcon.setVisibility(View.GONE);
        }

        if(spinnerRooms.getSelectedItem() == null) {
            errors++;
            warningRoomIcon.setVisibility(View.VISIBLE);
        } else {
            warningRoomIcon.setVisibility(View.GONE);
        }

        if(TextUtils.isEmpty(subjectEditText.getText())) {
            errors++;
             warningSubjectIcon.setVisibility(View.VISIBLE);
         } else {
            warningSubjectIcon.setVisibility(View.GONE);
        }

        if(participantsListAdapter.getCount() <= 1) {
            errors++;
             warningParticipantsIcon.setVisibility(View.VISIBLE);
         } else {
            warningParticipantsIcon.setVisibility(View.GONE);
        }

        if(durationChipGroup.getCheckedChipId() == -1) {
            errors++;
             warningDurationIcon.setVisibility(View.VISIBLE);
         } else {
            warningDurationIcon.setVisibility(View.GONE);
        }

        if (errors > 0){
            Toast.makeText(this, R.string.form_errors, Toast.LENGTH_SHORT).show();
        } else {
            Log.d("error", "addNewMeeting: " + meetingParticipants.getText().toString());
            calendar.set(year, month, day, hour, minute);
            date = calendar.getTime();

            Meeting meeting = new Meeting(
                    subjectEditText.getText().toString(),
                    date,
                    selectedDuration,
                    selectedParticipants,
                    meetingRoom
            );
            meetingApiService.createMeeting(meeting);
            startActivity(new Intent(AddMeetingPage.this, MainActivity.class));
        }
    }
}