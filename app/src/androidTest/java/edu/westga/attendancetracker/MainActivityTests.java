package edu.westga.attendancetracker;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import edu.westga.attendancetracker.model.Student;

/**
 * Validating MainActivity works correctly.  Note: This relies on the default test data.
 */
public class MainActivityTests extends ActivityInstrumentationTestCase2<MainActivity> {
    public MainActivityTests() {
        super(MainActivity.class);
    }

    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }

    @Override
    public void setUp() {
        final MainActivity activity = getActivity();
        activity.cleanDatabaseRecords();
    }

    // Should switch from Software 1 (default) to DB 1, which should have 5 students
    public void testStudentListChangesWhenCourseSpinnerChanges() {
        final MainActivity activity = getActivity();
        final ListView list = (ListView) activity.findViewById(R.id.listView);
        //int initialListSize = list.getAdapter().getCount();
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Spinner spinner = (Spinner) activity.findViewById(R.id.courseSpinner);
                spinner.setSelection(1, true);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        int newListSize = list.getAdapter().getCount();
        ArrayList<Student> newStudentList = activity.getArrayOfStudents();
        assertEquals(newListSize, 5);
    }

    public void testStudentCanBeToggledToBeMadeAbsent() {
        final MainActivity activity = getActivity();
        final ListView list = (ListView) activity.findViewById(R.id.listView);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {

                list.performItemClick(list.getAdapter().getView(1, null, null),
                        1, list.getAdapter().getItemId(1));
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Student> newStudentList = activity.getArrayOfStudents();
        assertEquals("(Absent) - Bill Donovan", newStudentList.get(1).toString());
    }

    public void testSubmittingRecordsTwiceShowsErrorMessage() {
        final MainActivity activity = getActivity();
        TouchUtils.clickView(this, (Button) activity.findViewById(R.id.submitButton));
        TouchUtils.clickView(this, (Button) activity.findViewById(R.id.submitButton));
        TextView resultsTextView = (TextView) activity.findViewById(R.id.resultTextView);
        assertEquals("Attendance already entered for this date", resultsTextView.getText().toString());
    }
}