package edu.westga.attendancetracker;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
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

    @Override
    public void setUp() {
        MainActivity activity = getActivity();
        AttendanceTrackDBHandler dbHandler = new AttendanceTrackDBHandler(activity, null, null, 1);
        dbHandler.deleteRecords();
    }

    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testStudentListPopulatesWhenCourseSpinnerChanges() {
        final MainActivity activity = getActivity();
        final ListView list = (ListView) activity.findViewById(R.id.listView);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Spinner spinner = (Spinner) activity.findViewById(R.id.courseSpinner);
                spinner.setSelection(1, true);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        getInstrumentation().waitForIdleSync();
        Spinner spinner = (Spinner) activity.findViewById(R.id.courseSpinner);
        TouchUtils.tapView(this, spinner);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(7, activity.getArrayOfStudents().size());
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
        assertEquals("(Absent) - Chad Smith", newStudentList.get(1).toString());
    }

    public void testSubmittingRecordsOnceShowsSuccessMessage() {
        this.setUp();
        final MainActivity activity = getActivity();
        TouchUtils.clickView(this, activity.findViewById(R.id.submitButton));
        TextView resultsTextView = (TextView) activity.findViewById(R.id.resultTextView);
        assertEquals("Submitted!", resultsTextView.getText().toString());
    }

    public void testSubmittingRecordsTwiceShowsErrorMessage() {
        final MainActivity activity = getActivity();
        TouchUtils.clickView(this, activity.findViewById(R.id.submitButton));
        TouchUtils.clickView(this, activity.findViewById(R.id.submitButton));
        TextView resultsTextView = (TextView) activity.findViewById(R.id.resultTextView);
        assertEquals("Attendance already entered for this date", resultsTextView.getText().toString());
    }
}