package edu.westga.attendancetracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import edu.westga.attendancetracker.model.Course;
import edu.westga.attendancetracker.model.Student;

public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private TextView resultTextView;
    private int year, month, day;
    private Spinner courseSpinner;
    private ArrayList<Student> arrayOfStudents;
    private ArrayList<Course> arrayOfCourses;
    //private ArrayAdapter<Course> spinnerAdapter;
    //private ArrayAdapter<Student> adapter;
    private ListView studentListView;
    private MyDBHandler dbHandler;
    private Course currentCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.dbHandler = new MyDBHandler(this, null, null, 1);
        this.arrayOfCourses = dbHandler.getCourses();
        ArrayAdapter<Course> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayOfCourses);
        this.courseSpinner = (Spinner) findViewById(R.id.courseSpinner);
        this.courseSpinner.setAdapter(spinnerAdapter);
        // Create student list
        Course course = (Course) courseSpinner.getSelectedItem();
        this.arrayOfStudents = dbHandler.getStudentsFromCourse(course.getCourseID());
        final ArrayAdapter<Student> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, this.arrayOfStudents);
        final ListView studentListView = (ListView) findViewById(R.id.listView);
        //studentListView.setScrollbarFadingEnabled(true);
        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                MainActivity.this.arrayOfStudents.get(position).toggleIsPresent();
                adapter.notifyDataSetChanged();
            }
        });
        studentListView.setAdapter(adapter);
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //System.out.println("got here");
                MainActivity.this.arrayOfStudents.clear();
                MainActivity.this.arrayOfStudents = dbHandler.getStudentsFromCourse(arrayOfCourses.get(position).getCourseID());
                MainActivity.this.currentCourse = arrayOfCourses.get(position);
                for (Student student : MainActivity.this.arrayOfStudents) {
                    System.out.println(student.toString());
                }
                adapter.clear();
                adapter.addAll(MainActivity.this.arrayOfStudents);
                adapter.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        //Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
        //        .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(month).append("-")
                .append(day).append("-").append(year));
    }

    public void didTapSubmit(View view) {
        //System.out.println(this.currentCourse + dateView.getText().toString());
        if (!dbHandler.isDateForCourseEmpty(currentCourse.getCourseID(), dateView.getText().toString())) {
            resultTextView.setTextColor(Color.RED);
            resultTextView.setText("Attendance already entered for this date");
            return;
        }
        dbHandler.addAttendanceRecord(this.currentCourse, this.arrayOfStudents, dateView.getText().toString());
        resultTextView.setTextColor(Color.GREEN);
        resultTextView.setText("Submitted!");
    }

    public void didTapViewStats(View view) {
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }

    public ArrayList<Student> getArrayOfStudents() {
        return this.arrayOfStudents;
    }

    public void cleanDatabaseRecords() {
        dbHandler.deleteRecords();
    }
}
