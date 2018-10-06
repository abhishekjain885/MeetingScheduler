package vinsol.meeting.scheduler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {


    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mtextView;
    private Button mScheduleMeetingButton;
    private RecyclerView recyclerView;

    private String mTodaydate = "";
    private String mCurrentState = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mtextView = (TextView) findViewById(R.id.tv_display_date);
        recyclerView = (RecyclerView) findViewById(R.id.meeting_recycler);
        mScheduleMeetingButton = (Button) findViewById(R.id.schedule_meeting);


        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        mCurrentState = mTodaydate = sdf.format(date);
        mtextView.setText(mTodaydate);

        fetchDataAndPopulate(mTodaydate);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    public void onPreviousButtonClick(View v) {


        String prev_date = getDate(mCurrentState, -1);
        mCurrentState = prev_date;
        if (isPastDate(prev_date, mTodaydate)) {
            mScheduleMeetingButton.setClickable(false);
            mScheduleMeetingButton.setEnabled(false);
            mScheduleMeetingButton.setBackgroundColor(Color.parseColor("#c0c0c0"));

        } else {
            mScheduleMeetingButton.setClickable(true);
            mScheduleMeetingButton.setEnabled(true);
            mScheduleMeetingButton.setBackgroundColor(Color.parseColor("#00A787"));

        }


        mtextView.setText(mCurrentState);
        fetchDataAndPopulate(prev_date);

    }

    public void onNextButtonClick(View v) {

        String next_date = getDate(mCurrentState, 1);

        if (isPastDate(next_date, mTodaydate)) {
            mScheduleMeetingButton.setClickable(false);
            mScheduleMeetingButton.setEnabled(false);
            mScheduleMeetingButton.setBackgroundColor(Color.parseColor("#c0c0c0"));

        } else {
            mScheduleMeetingButton.setClickable(true);
            mScheduleMeetingButton.setEnabled(true);
            mScheduleMeetingButton.setBackgroundColor(Color.parseColor("#00A787"));

        }

        mCurrentState = next_date;
        mtextView.setText(mCurrentState);
        fetchDataAndPopulate(next_date);
    }


    public void onScheduleMeetingClicked(View v) {

        Intent i = new Intent(getApplicationContext(), MeetingScheduleActivity.class);
        startActivity(i);


    }


    public static String getDate(String curDate, int numOfDays) {

        try {
            final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            final Date date;
            date = format.parse(curDate);
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, numOfDays);
            return format.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "error";
    }


    public boolean isPastDate(String date1, String date2) {
        final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        final Date date;
        try {
            Date date_pre = format.parse(date1);
            Date date_next = format.parse(date2);
            if (date_pre.before(date_next)) {
                System.out.println("The date is older than current day");
                return true;

            } else {
                System.out.println("The date is future day");

                return false;

            }


        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }


    }


    private void fetchDataAndPopulate(String date) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Meeting>> call = apiService.getSchedule(date);
        call.enqueue(new Callback<List<Meeting>>() {
            @Override
            public void onResponse(Call<List<Meeting>> call, Response<List<Meeting>> response) {
                List<Meeting> meetings = response.body();
                Log.d(TAG, "Number of movies received: " + meetings.size());
                recyclerView.setAdapter(new MeetingAdapter(meetings, getApplicationContext()));

                if (meetings.size() == 0) {
                    Toast.makeText(getApplicationContext(), "No Meeting Scheduled", Toast.LENGTH_SHORT).show();

                }
                for (int i = 0; i < meetings.size(); i++) {

                    meetings.get(i).setStartTime(ConvertTime(meetings.get(i).getStartTime()));
                    meetings.get(i).setEndTime(ConvertTime(meetings.get(i).getEndTime()));

                }
            }

            @Override
            public void onFailure(Call<List<Meeting>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


    }

    private String ConvertTime(String time) {

        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateObj = sdf.parse(time);
            return new SimpleDateFormat("K:mm a").format(dateObj).toString();
        } catch (final ParseException e) {
            e.printStackTrace();

        }
        return "error";
    }
}
