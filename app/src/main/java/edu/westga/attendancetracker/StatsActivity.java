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

public class StatsActivity extends AppCompatActivity {

    private TextView resultTextView;
    private Spinner courseSpinner;
    private ArrayList<String> arrayOfStudents;
    private ArrayList<Course> arrayOfCourses;
    private ListView studentListView;
    private MyDBHandler dbHandler;
    private Course currentCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        this.dbHandler = new MyDBHandler(this, null, null, 1);
        this.arrayOfCourses = dbHandler.getCourses();
        ArrayAdapter<Course> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayOfCourses);
        this.courseSpinner = (Spinner) findViewById(R.id.courseSpinner);
        this.courseSpinner.setAdapter(spinnerAdapter);
        // Create student list
        Course course = (Course) courseSpinner.getSelectedItem();
        this.arrayOfStudents = new ArrayList<String>();
        for (Student student: dbHandler.getStudentsFromCourse(course.getCourseID())) {
            double studentAttendance = dbHandler.getStudentAttendanceRecordForCourse(course.getCourseID(), student.getStudentID());
            this.arrayOfStudents.add(student.getName() + " " + studentAttendance);
        }
        //this.arrayOfStudents = dbHandler.getStudentsFromCourse(course.getCourseID());
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, this.arrayOfStudents);
        final ListView studentListView = (ListView) findViewById(R.id.listView);
        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                //StatsActivity.this.arrayOfStudents.get(position).toggleIsPresent();
                adapter.notifyDataSetChanged();
            }
        });
        studentListView.setAdapter(adapter);
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StatsActivity.this.arrayOfStudents.clear();
                int courseID = arrayOfCourses.get(position).getCourseID();
                //StatsActivity.this.arrayOfStudents = dbHandler.getStudentsFromCourse(courseID);
                for (Student student: dbHandler.getStudentsFromCourse(courseID)) {
                    String studentAttendance = String.format("%.2f", dbHandler.getStudentAttendanceRecordForCourse(courseID, student.getStudentID()));
                    StatsActivity.this.arrayOfStudents.add(student.getName() + " " + studentAttendance + "%");
                    //System.out.println(student.getName() + " " + studentAttendance);
                }

                StatsActivity.this.currentCourse = arrayOfCourses.get(position);
                //for (Student student : StatsActivity.this.arrayOfStudents) {
                //    System.out.println(student.getName() + dbHandler.getStudentAttendanceRecordForCourse(courseID, student.getStudentID()));
                //}
                //adapter.clear();
                //adapter.addAll(StatsActivity.this.arrayOfStudents);
                adapter.notifyDataSetChanged();
                for (String studentString: StatsActivity.this.arrayOfStudents) {
                    System.out.println(studentString);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //this.arrayOfStudents = dbHandler.getStudentsFromCourse(course.getCourseID());
        resultTextView = (TextView) findViewById(R.id.resultTextView);
    }


    public void didTapEnterAttendance(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
