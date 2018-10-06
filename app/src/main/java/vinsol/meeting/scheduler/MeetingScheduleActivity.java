package vinsol.meeting.scheduler;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MeetingScheduleActivity extends Activity implements View.OnClickListener {


    EditText meetingDateEditText, startTimeEditText, endTimeEditText;
    Button backButton, submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_schedule);

        meetingDateEditText = (EditText) findViewById(R.id.meetingDate);
        startTimeEditText = (EditText) findViewById(R.id.startTime);
        endTimeEditText = (EditText) findViewById(R.id.endTime);
        backButton = (Button) findViewById(R.id.backButton);
        submitButton = (Button) findViewById(R.id.submitButton);

        meetingDateEditText.setOnClickListener(this);
        startTimeEditText.setOnClickListener(this);
        endTimeEditText.setOnClickListener(this);
        backButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == meetingDateEditText) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            meetingDateEditText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == startTimeEditText) {

            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            startTimeEditText.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();

        }

        if (v == endTimeEditText) {
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            endTimeEditText.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();

        }

        if (v == backButton) {
            onBackPressed();
        }

        if (v == submitButton) {

            if (meetingDateEditText.getText().toString().isEmpty() || startTimeEditText.getText().toString().isEmpty() || endTimeEditText.getText().toString().isEmpty()) {

                Toast.makeText(getApplicationContext(), "All Fields are Mandatory", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Hurrah Data Submitted", Toast.LENGTH_SHORT).show();

            }
        }

    }
}

