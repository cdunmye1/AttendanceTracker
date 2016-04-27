package edu.westga.attendancetracker;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import java.util.ArrayList;
import edu.westga.attendancetracker.model.Course;
import edu.westga.attendancetracker.model.Student;

/**
 * This class keeps track of the attendance record for students and courses
 */
public class StatsActivity extends AppCompatActivity {

    private Spinner courseSpinner;
    private ArrayList<String> arrayOfStudentsPerCourse;
    private ArrayList<Course> arrayOfCourses;
    private ArrayList<String> arrayOfCoursesPerStudent;
    private ArrayList<Student> arrayOfStudents;
    private ListView courseByStudentListView;
    private Spinner studentSpinner;
    private MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        this.dbHandler = new MyDBHandler(this, null, null, 1);
        // set up course spinner and student listview
        this.arrayOfCourses = dbHandler.getCourses();
        ArrayAdapter<Course> courseSpinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayOfCourses);
        this.courseSpinner = (Spinner) findViewById(R.id.courseSpinner);
        this.courseSpinner.setAdapter(courseSpinnerAdapter);
        Course course = (Course) courseSpinner.getSelectedItem();
        this.arrayOfStudentsPerCourse = new ArrayList<String>();
        final ArrayAdapter<String> studentsByCourseAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, this.arrayOfStudentsPerCourse);
        final ListView studentListView = (ListView) findViewById(R.id.studentsByCourseListView);
        studentListView.setAdapter(studentsByCourseAdapter);
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StatsActivity.this.arrayOfStudentsPerCourse.clear();
                int courseID = arrayOfCourses.get(position).getCourseID();
                for (Student student: dbHandler.getStudentsFromCourse(courseID)) {
                    String studentAttendance = String.format("%.2f", dbHandler.getStudentAttendanceRecordForCourse(courseID, student.getStudentID()));
                    StatsActivity.this.arrayOfStudentsPerCourse.add(student.getName() + " - " + studentAttendance + "%");
                }
                studentsByCourseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // set up student spinner and course listview
        this.arrayOfStudents = dbHandler.getStudents();
        ArrayAdapter<Student> studentSpinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayOfStudents);
        this.studentSpinner = (Spinner) findViewById(R.id.studentSpinner);
        this.studentSpinner.setAdapter(studentSpinnerAdapter);
        Student student = (Student) studentSpinner.getSelectedItem();
        this.arrayOfCoursesPerStudent = new ArrayList<String>();
        final ArrayAdapter<String> coursesByStudentAdatper = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, this.arrayOfCoursesPerStudent);
        final ListView courseListView = (ListView) findViewById(R.id.coursesByStudentListView);
        courseListView.setAdapter(coursesByStudentAdatper);
        studentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StatsActivity.this.arrayOfCoursesPerStudent.clear();
                int studentID = arrayOfStudents.get(position).getStudentID();
                for (Course course: dbHandler.getCoursesFromStudent(studentID)) {
                    String courseAttendance = String.format("%.2f", dbHandler.getStudentAttendanceRecordForCourse(course.getCourseID(), studentID));
                    StatsActivity.this.arrayOfCoursesPerStudent.add(course.getName() + " - " + courseAttendance + "%");
                }
                coursesByStudentAdatper.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void didTapEnterAttendance(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
