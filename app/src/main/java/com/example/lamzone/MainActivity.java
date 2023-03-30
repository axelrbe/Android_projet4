package com.example.lamzone;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lamzone.Adapter.MeetingAdapter;
import com.example.lamzone.DI.DI;
import com.example.lamzone.Events.DeleteMeetingEvent;
import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Repository.MeetingRepository;
import com.example.lamzone.Service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addMeetingBtn;
    MeetingAdapter adapter;
    MeetingApiService meetingApiService;
    MeetingRepository meetingRepository;
    List<Meeting> allMeetings;
    ImageView iconFilter, resetFilterBtn, noMeetingIcon;
    SearchView searchView;
    String selectedDate;
    int year, month, day;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meetingRepository = DI.createMeetingRepository();
        meetingApiService = DI.getMeetingApiService();
        allMeetings = meetingApiService.getAllMeetings();

        iconFilter = findViewById(R.id.icon_filtered_list);
        searchView = findViewById(R.id.filter_search_view);
        resetFilterBtn = findViewById(R.id.reset_filter_btn);
        calendar = Calendar.getInstance();

        noMeetingIcon = findViewById(R.id.no_meeting_icon);
        if (allMeetings.size() == 0) {
            noMeetingIcon.setVisibility(View.VISIBLE);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MeetingAdapter(allMeetings);
        recyclerView.setAdapter(adapter);

        addMeetingBtn = findViewById(R.id.add_meeting_btn);
        addMeetingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddMeetingPage.class);
            startActivity(intent);
        });

        iconFilter.setOnClickListener(v -> setPopUpMenu());

        resetFilterBtn.setOnClickListener(v -> {
            resetFilterBtn.setVisibility(View.GONE);
            searchView.setVisibility(View.GONE);
            initList();
        });
    }

    /**
     * Set the pop-up menu to show the filters buttons
     */
    private void setPopUpMenu() {
        List<String> popUpItems = new ArrayList<>();
        popUpItems.add("Filtrer par date");
        popUpItems.add("Filtrer par salle");

        PopupMenu popupMenu = new PopupMenu(this, iconFilter);

        for (int i = 0; i < popUpItems.size(); i++) {
            popupMenu.getMenu().add(Menu.NONE, i, i, popUpItems.get(i));
        }

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 0:
                    filterByDate();
                    resetFilterBtn.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    searchView.setQueryHint("Recherche une salle...");
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String roomText) {
                            adapter = new MeetingAdapter(meetingApiService.filterMeetingsByRoom(roomText));
                            recyclerView.setAdapter(adapter);
                            return true;
                        }
                    });
                    searchView.setVisibility(View.VISIBLE);
                    resetFilterBtn.setVisibility(View.VISIBLE);
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    /**
     * Filter the list of meetings by date
     */
    @SuppressLint("NotifyDataSetChanged")
    private void filterByDate() {
         DatePickerDialog.OnDateSetListener dateSetListener = (view, year, selectedMonth, selectedDay) -> {
            month = selectedMonth;
            day = selectedDay;
            selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", day, month + 1, year);

             adapter = new MeetingAdapter(meetingApiService.filterMeetingsByDate(selectedDate));
             recyclerView.setAdapter(adapter);
        };

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.MySpinnerDatePickerStyle, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void initList() {recyclerView.setAdapter(new MeetingAdapter(allMeetings));}

    /**
     * Methods to manage the life cycle of the app
     */
    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        initList();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteMeetingEvent event) {
        meetingApiService.deleteMeeting(event.meeting);
        initList();
    }

}