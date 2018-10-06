package vinsol.meeting.scheduler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder> {

    private List<Meeting> meetingList;
    private Context context;


    public static class MeetingViewHolder extends RecyclerView.ViewHolder {

        TextView meetingTiming;
        TextView meetingDescription;
        TextView meetingparticipants;

        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);
            meetingTiming = (TextView) itemView.findViewById(R.id.tv_timing);
            meetingDescription = (TextView) itemView.findViewById(R.id.tv_description);
            meetingparticipants = (TextView) itemView.findViewById(R.id.tv_participants);
        }
    }


    public MeetingAdapter(List<Meeting> meetings, Context context) {
        this.context = context;
        this.meetingList = meetings;
    }

    @NonNull
    @Override
    public MeetingAdapter.MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View view =
                inflater.inflate(R.layout.meetingitem, viewGroup, false);

        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingAdapter.MeetingViewHolder meetingViewHolder, int position) {

        meetingViewHolder.meetingTiming.setText(meetingList.get(position).getStartTime()+"-"+meetingList.get(position).getEndTime());
        meetingViewHolder.meetingDescription.setText(meetingList.get(position).getDescription());
        meetingViewHolder.meetingparticipants.setText(meetingList.get(position).getParticipants().toString());
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }
}
