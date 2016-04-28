package edu.westga.attendancetracker;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import edu.westga.attendancetracker.model.Student;

/**
 * Validating StatsActivy works correctly.  Note: This relies on the default test data.
 */
public class StatsActivityTests extends ActivityInstrumentationTestCase2<StatsActivity> {
    public StatsActivityTests() {
        super(StatsActivity.class);
    }

    @Override
    public void setUp() {
        // Delete database
        StatsActivity activity = getActivity();
        MyDBHandler dbHandler = new MyDBHandler(activity, null, null, 1);
        dbHandler.deleteRecords();

        // Insert StudentAttendance Data
        dbHandler.addStudentAttendanceRecords();
    }

    public void testActivityExists() {
        StatsActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testStudentListPopulatesWhenCourseSpinnerChanges() {
        final StatsActivity activity = getActivity();
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
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(7, activity.getArrayOfStudentsPerCourse().size());
    }
    // By default Software Development 1 student percentages will be shown
    public void testStudentStatisticsAreShownForUserWhoHasAttendedAllClasses() {
        final StatsActivity activity = getActivity();
        assertEquals("Bill Donovan - 100.00%", activity.getArrayOfStudentsPerCourse().get(0));
    }

    // By default Software Development 1 student percentages will be shown
    public void testStudentStatisticsAreShownForUserWhoHasAttendedOddAmountOfClasses() {
        final StatsActivity activity = getActivity();
        assertEquals("Chad Smith - 33.33%", activity.getArrayOfStudentsPerCourse().get(1));
    }

    // By default Software Development 1 student percentages will be shown
    public void testStudentInBothClassesShowsTwoValuesForStudentListByClass() {
        final StatsActivity activity = getActivity();
        assertEquals(2, activity.getArrayOfCoursesPerStudent().size());
    }

    

}