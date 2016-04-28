package edu.westga.attendancetracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import edu.westga.attendancetracker.model.Course;
import edu.westga.attendancetracker.model.Student;

/**
 * MainActivity allows a teacher to enter the attendence for his or her students
 * by toggling the student to be present or absent (default to present)
 */
public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private TextView resultTextView;
    private int year, month, day;
    private Spinner courseSpinner;
    private ArrayList<Student> arrayOfStudents;
    private ArrayList<Course> arrayOfCourses;
    private ListView studentListView;
    private AttendanceTrackDBHandler dbHandler;
    private Course currentCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.dbHandler = new AttendanceTrackDBHandler(this, null, null, 1);
        // set up course spinner and student list view
        this.arrayOfCourses = dbHandler.getCourses();
        ArrayAdapter<Course> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayOfCourses);
        this.courseSpinner = (Spinner) findViewById(R.id.courseSpinner);
        this.courseSpinner.setAdapter(spinnerAdapter);
        Course course = (Course) courseSpinner.getSelectedItem();
        this.arrayOfStudents = dbHandler.getStudentsFromCourse(course.getCourseID());
        final ArrayAdapter<Student> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, this.arrayOfStudents);
        final ListView studentListView = (ListView) findViewById(R.id.listView);
        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.arrayOfStudents.get(position).toggleIsPresent();
                adapter.notifyDataSetChanged();
                resultTextView.setText("");
            }
        });
        studentListView.setAdapter(adapter);
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.arrayOfStudents.clear();
                MainActivity.this.arrayOfStudents = dbHandler.getStudentsFromCourse(arrayOfCourses.get(position).getCourseID());
                MainActivity.this.currentCourse = arrayOfCourses.get(position);
                adapter.clear();
                adapter.addAll(MainActivity.this.arrayOfStudents);
                adapter.notifyDataSetChanged();
                resultTextView.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.arrayOfStudents = dbHandler.getStudentsFromCourse(course.getCourseID());
        dateView = (TextView) findViewById(R.id.dateTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        resultTextView.setText("");
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(month).append("-")
                .append(day).append("-").append(year));
    }

    public void didTapSubmit(View view) {
        if (!dbHandler.isDateForCourseEmpty(currentCourse.getCourseID(), dateView.getText().toString())) {
            resultTextView.setTextColor(Color.RED);
            resultTextView.setText("Attendance already entered for this date");
            return;
        }
        dbHandler.addAttendanceRecord(this.currentCourse, this.arrayOfStudents, dateView.getText().toString());
        resultTextView.setTextColor(Color.BLUE);
        resultTextView.setText("Submitted!");
    }

    public void didTapViewStats(View view) {
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }

    public ArrayList<Student> getArrayOfStudents() {
        return this.arrayOfStudents;
    }
}
