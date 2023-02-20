package com.example.lamzone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lamzone.DI.DI;
import com.example.lamzone.Events.DeleteMeetingEvent;
import com.example.lamzone.Model.Meeting;
import com.example.lamzone.RecyclerView.MeetingAdapter;
import com.example.lamzone.Service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addMeetingBtn;
    MeetingAdapter adapter;
    MeetingApiService apiService = DI.getMeetingApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MeetingApiService apiService = DI.getMeetingApiService();
        List<Meeting> allMeetings = apiService.getAllMeetings();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MeetingAdapter(allMeetings);
        recyclerView.setAdapter(adapter);

        addMeetingBtn = findViewById(R.id.add_meeting_btn);
        addMeetingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddMeetingPage.class);
            startActivity(intent);
        });
    }

    private void initList() {
        List<Meeting> Meetings = apiService.getAllMeetings();
        recyclerView.setAdapter(new MeetingAdapter(Meetings));
    }

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
     * @param event
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteMeetingEvent event) {
        apiService.deleteMeeting(event.meeting);
        initList();
    }

}