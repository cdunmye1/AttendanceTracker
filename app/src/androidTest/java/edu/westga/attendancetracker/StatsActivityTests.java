package edu.westga.attendancetracker;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.ListView;
import android.widget.Spinner;

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
        AttendanceTrackDBHandler dbHandler = new AttendanceTrackDBHandler(activity, null, null, 1);
        dbHandler.deleteRecords();

        // Insert StudentAttendance Data
        dbHandler.addStudentAttendanceRecords();
    }

    public void testActivityExists() {
        StatsActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testStudentListRePopulatesWhenCourseSpinnerChanges() {
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
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(8, activity.getArrayOfStudentsPerCourse().size());
    }

    // By default Software Development 1 student percentages will be shown
    public void testTheTotalCourseAverage() {
        final StatsActivity activity = getActivity();
        assertEquals("All Students: 73.33%", activity.getArrayOfStudentsPerCourse().get(0));
    }

    // By default Software Development 1 student percentages will be shown
    public void testStudentStatisticsAreShownForUserWhoHasAttendedAllCourses() {
        final StatsActivity activity = getActivity();
        assertEquals("Bill Donovan - 100.00%", activity.getArrayOfStudentsPerCourse().get(1));
    }

    // By default Software Development 1 student percentages will be shown
    public void testStudentStatisticsAreShownForUserWhoHasAttendedOddAmountOfCourses() {
        final StatsActivity activity = getActivity();
        assertEquals("Chad Smith - 33.33%", activity.getArrayOfStudentsPerCourse().get(2));
    }

    // By default Bill Donovan will be shown
    // This will show the 2 Courses the student is in + all of the courses combined
    public void testStudentInTwoCoursesShowsThreeTotalValuesForStudentListByCourse() {
        final StatsActivity activity = getActivity();
        assertEquals(3, activity.getArrayOfCoursesPerStudent().size());
    }

    public void testAllCoursesCombinedPercentageShowsUpForStudent() {
        final StatsActivity activity = getActivity();
        assertEquals("All Courses: 83.33%", activity.getArrayOfCoursesPerStudent().get(0));
    }

    // By default Bill Donovan will be shown
    // This will show the 2 Courses the student is in + all of the courses combined
    public void testCoursePercentageShowsUpForStudent() {
        final StatsActivity activity = getActivity();
        assertEquals("Database Design 101 - 66.67%", activity.getArrayOfCoursesPerStudent().get(1));
    }
}