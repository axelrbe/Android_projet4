package com.example.lamzone.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lamzone.Events.DeleteMeetingEvent;
import com.example.lamzone.Model.Meeting;
import com.example.lamzone.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder>{

    /**
     * Adapter to put the infos in the right position
     */
    List<Meeting> allMeetings;
    public MeetingAdapter(List<Meeting> meeting) {
        this.allMeetings = meeting;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_model, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = allMeetings.get(position);

        String formatParticipants = String.format("%s,", meeting.getParticipants()).replace("[", "").replace("]", "");

        holder.meetingInfo.setText(String.format("%s-%s-%s-%s", meeting.getName(), meeting.getDateFormatted(), meeting.getTimeFormatted(), meeting.getRoom().getRoomName()));
        holder.meetingParticipants.setText(formatParticipants);
        Glide.with(holder.color.getContext())
                .load(meeting.getRoom().getColor())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.color);

        holder.deleteBtn.setOnClickListener(v -> EventBus.getDefault().post(new DeleteMeetingEvent(meeting)));
    }

    @Override
    public int getItemCount() {
        return allMeetings.size();
    }

    /**
     * View holder to bind the views
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView meetingInfo, meetingParticipants;
        ImageView color, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            meetingParticipants = itemView.findViewById(R.id.meeting_participants);
            meetingInfo = itemView.findViewById(R.id.meeting_infos);
            color = itemView.findViewById(R.id.meeting_color);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }
    }
}
