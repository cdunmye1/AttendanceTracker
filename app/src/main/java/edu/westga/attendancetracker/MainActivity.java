package edu.westga.attendancetracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().hide();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

//        final ArrayList<Student> arrayOfUsers = new ArrayList<Student>();
//        arrayOfUsers.add(new Student(1, "Chris Dunmyer"));
//        arrayOfUsers.add(new Student(2, "Bill Donovan"));
//        arrayOfUsers.add(new Student(3, "Henry Williams"));
//        arrayOfUsers.add(new Student(4, "Chad Smith"));
//        arrayOfUsers.add(new Student(5, "List Saunders"));
//        arrayOfUsers.add(new Student(6, "Kaitlyn Carlisle"));
//        arrayOfUsers.add(new Student(7, "Carly Snyder"));
//        arrayOfUsers.add(new Student(8, "Mark Jackson"));
//        arrayOfUsers.add(new Student(9, "Steven Robinson"));


        final ArrayList<Course> arrayOfCourses = dbHandler.getCourses();
        final ArrayAdapter<Course> spinnerAdapter = new ArrayAdapter<Course>(this,
                android.R.layout.simple_spinner_item, arrayOfCourses);
        final Spinner spinner = (Spinner) findViewById(R.id.courseSpinner);
        //spinner.setOnItemClickListener(new AdapterView);
        spinner.setAdapter(spinnerAdapter);


        // Create student list
        Course course = (Course) spinner.getSelectedItem();
        final ArrayList<Student> arrayOfUsers = dbHandler.getStudentsFromCourse(course.getClassID());
        final ArrayAdapter<Student> adapter = new ArrayAdapter<Student>(this,
                android.R.layout.simple_list_item_1, arrayOfUsers);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                arrayOfUsers.get(position).toggleIsPresent();
                adapter.notifyDataSetChanged();
            }
        });
        listView.setAdapter(adapter);



        dateView = (TextView) findViewById(R.id.dateTextView);
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
        dateView.setText(new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year));
    }
}
