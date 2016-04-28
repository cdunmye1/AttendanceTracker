package edu.westga.attendancetracker;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import edu.westga.attendancetracker.model.Course;
import edu.westga.attendancetracker.model.Student;

/**
 * AttendanceTrackDBHandler class performs database tasks in the AttendanceTracker application
 */
public class AttendanceTrackDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "studentAttendanceDB.db";
    public static final String TABLE_STUDENTS = "Students";
    public static final String STUDENT_COLUMN_ID = "_id";
    public static final String STUDENT_COLUMN_NAME = "name";

    public AttendanceTrackDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " +
                TABLE_STUDENTS + "("
                + STUDENT_COLUMN_ID + " INTEGER PRIMARY KEY," + STUDENT_COLUMN_NAME
                + " TEXT" + ")";

        String CREATE_COURSE_TABLE = "CREATE TABLE " +
                "Course " + "("
                + "_id" + " INTEGER PRIMARY KEY," + " name TEXT"
                + ")";

        String CREATE_STUDENT_COURSE_TABLE = "CREATE TABLE " +
                "StudentCourse " + "(StudentID "
                + " INTEGER, CourseID INTEGER, PRIMARY KEY (StudentID, CourseID))";

        String CREATE_STUDENT_ATTENDANCE_TABLE = "CREATE TABLE " +
                "StudentAttendance " + "("
                + "StudentID" + " INTEGER," + " CourseID INTEGER"
                + " INTEGER, Date TEXT, Present INTEGER, PRIMARY KEY (StudentID, CourseID, Date))";
        db.execSQL(CREATE_STUDENTS_TABLE);
        db.execSQL(CREATE_COURSE_TABLE);
        db.execSQL(CREATE_STUDENT_COURSE_TABLE);
        db.execSQL(CREATE_STUDENT_ATTENDANCE_TABLE);
        addStudentAndCourseRecords(db);
        addStudentAttendanceRecords(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public ArrayList<Student> getStudentsFromCourse(int courseID) {
        String query = "Select s._id, s.name FROM StudentCourse sc " +
                "JOIN Students s on s._id=sc.StudentID WHERE " +
                "CourseID=" + courseID + " ORDER BY s.name ASC;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Student> studentList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int studentID = Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1);
            studentList.add(new Student(studentID, name));
        }
        db.close();
        return studentList;
    }

    public ArrayList<Course> getCoursesFromStudent(int studentID) {
        String query = "Select c._id, c.name FROM StudentCourse sc JOIN Course c on c._id=sc.CourseID" +
                " WHERE StudentID=" + studentID + " ORDER BY c.name ASC;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Course> courseList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int courseID = Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1);
            courseList.add(new Course(courseID, name));
        }
        db.close();
        return courseList;
    }

    public ArrayList<Student> getStudents() {
        String query = "Select s._id, s.name FROM Students s ORDER BY s.name ASC;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Student> studentList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int studentID = Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1);
            studentList.add(new Student(studentID, name));
        }
        db.close();
        return studentList;
    }

    public ArrayList<Course> getCourses() {
        String query = "Select _id, name FROM Course; ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Course> courseList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int courseID = Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1);
            courseList.add(new Course(courseID, name));
        }

        db.close();
        return courseList;
    }

    public double getStudentAttendanceRecordForCourse(int courseID, int studentID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String totalQuery = "Select CourseID from StudentAttendance WHERE CourseID=" + courseID + " AND StudentID=" + studentID + ";";
        String attendedQuery = "Select CourseID from StudentAttendance WHERE CourseID=" + courseID + " AND StudentID=" + studentID + " AND Present=1;";
        Cursor totalCursor = db.rawQuery(totalQuery, null);
        Cursor attendedCursor = db.rawQuery(attendedQuery, null);
        double percentageAttended;
        double totalRecords = totalCursor.getCount();
        double totalAttended = attendedCursor.getCount();
        if (totalAttended == 0.0) {
            return 0;
        }
        percentageAttended = (totalAttended / totalRecords);
        percentageAttended = percentageAttended * 100;
        return percentageAttended;
    }

    public double getCourseAttendanceRecord(int courseID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String totalQuery = "Select CourseID from StudentAttendance WHERE CourseID=" + courseID + ";";
        String attendedQuery = "Select CourseID from StudentAttendance WHERE CourseID=" + courseID + " AND Present=1;";
        Cursor totalCursor = db.rawQuery(totalQuery, null);
        Cursor attendedCursor = db.rawQuery(attendedQuery, null);
        double percentageAttended;
        double totalRecords = totalCursor.getCount();
        double totalAttended = attendedCursor.getCount();
        if (totalAttended == 0.0) {
            return 0;
        }
        percentageAttended = (totalAttended / totalRecords);
        percentageAttended = percentageAttended * 100;
        return percentageAttended;
    }

    public double getStudentAttendanceRecord(int studentID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String totalQuery = "Select CourseID from StudentAttendance WHERE StudentID=" + studentID + ";";
        String attendedQuery = "Select CourseID from StudentAttendance WHERE StudentID=" + studentID + " AND Present=1;";
        Cursor totalCursor = db.rawQuery(totalQuery, null);
        Cursor attendedCursor = db.rawQuery(attendedQuery, null);
        double percentageAttended;
        double totalRecords = totalCursor.getCount();
        double totalAttended = attendedCursor.getCount();
        if (totalAttended == 0.0) {
            return 0;
        }
        percentageAttended = (totalAttended / totalRecords);
        percentageAttended = percentageAttended * 100;
        return percentageAttended;
    }

    private void addStudentAndCourseRecords(SQLiteDatabase db) {
        final ArrayList<Student> arrayOfUsers = new ArrayList<>();
        arrayOfUsers.add(new Student(1, "Chris Dunmyer"));
        arrayOfUsers.add(new Student(2, "Bill Donovan"));
        arrayOfUsers.add(new Student(3, "Henry Williams"));
        arrayOfUsers.add(new Student(4, "Chad Smith"));
        arrayOfUsers.add(new Student(6, "Kaitlyn Carlisle"));
        arrayOfUsers.add(new Student(7, "Carly Snyder"));
        arrayOfUsers.add(new Student(8, "Mark Jackson"));
        arrayOfUsers.add(new Student(9, "Steven Robinson"));
        for (Student student: arrayOfUsers)
        {
            String INSERT_STUDENT_DATA = "INSERT INTO "+ TABLE_STUDENTS + " (_id, name) VALUES (" + student.getStudentID() + ", '" + student.getName() +"')";
            db.execSQL(INSERT_STUDENT_DATA);
        }

        db.execSQL("INSERT INTO Course (_id, name) VALUES (1, 'Software Development 101'); ");
        db.execSQL("INSERT INTO Course (_id, name) VALUES (2, 'Database Design 101'); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (1, 1); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (2, 1); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (3, 1); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (4, 1); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (5, 1); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (6, 2); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (7, 2); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (8, 2); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (9, 2); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (1, 2); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (2, 2); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (3, 2); ");
    }

    private void addStudentAndCourseRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        final ArrayList<Student> arrayOfUsers = new ArrayList<>();
        arrayOfUsers.add(new Student(1, "Chris Dunmyer"));
        arrayOfUsers.add(new Student(2, "Bill Donovan"));
        arrayOfUsers.add(new Student(3, "Henry Williams"));
        arrayOfUsers.add(new Student(4, "Chad Smith"));
        arrayOfUsers.add(new Student(6, "Kaitlyn Carlisle"));
        arrayOfUsers.add(new Student(7, "Carly Snyder"));
        arrayOfUsers.add(new Student(8, "Mark Jackson"));
        arrayOfUsers.add(new Student(9, "Steven Robinson"));
        for (Student student: arrayOfUsers)
        {
            String INSERT_STUDENT_DATA = "INSERT INTO "+ TABLE_STUDENTS + " (_id, name) VALUES (" + student.getStudentID() + ", '" + student.getName() +"')";
            db.execSQL(INSERT_STUDENT_DATA);
        }

        db.execSQL("INSERT INTO Course (_id, name) VALUES (1, 'Software Development 101'); ");
        db.execSQL("INSERT INTO Course (_id, name) VALUES (2, 'Database Design 101'); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (1, 1); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (2, 1); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (3, 1); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (4, 1); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (5, 1); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (6, 2); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (7, 2); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (8, 2); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (9, 2); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (1, 2); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (2, 2); ");
        db.execSQL("INSERT INTO StudentCourse (StudentID, CourseID) VALUES (3, 2); ");
    }

    public void addStudentAttendanceRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Student attendence Day 1 Class 1
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (1, 1, 01-02-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (2, 1, 01-02-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (3, 1, 01-02-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (4, 1, 01-02-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (5, 1, 01-02-2016, 1); ");
        // Student attendence Day 1 Class 2
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (1, 2, 01-02-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (2, 2, 01-02-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (3, 2, 01-02-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (6, 2, 01-02-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (7, 2, 01-02-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (8, 2, 01-02-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (9, 2, 01-02-2016, 0); ");
        // Student attendence Day 2 Class 1
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (1, 1, 01-03-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (2, 1, 01-03-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (3, 1, 01-03-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (4, 1, 01-03-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (5, 1, 01-03-2016, 1); ");
        // Student attendence Day 2 Class 2
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (1, 2, 01-03-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (2, 2, 01-03-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (3, 2, 01-03-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (6, 2, 01-03-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (7, 2, 01-03-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (8, 2, 01-03-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (9, 2, 01-03-2016, 0); ");
        // Student attendence Day 3 Class 1
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (1, 1, 01-04-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (2, 1, 01-04-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (3, 1, 01-04-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (4, 1, 01-04-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (5, 1, 01-04-2016, 1); ");
        // Student attendence Day 3 Class 2
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (1, 2, 01-04-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (2, 2, 01-04-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (3, 2, 01-04-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (6, 2, 01-04-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (7, 2, 01-04-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (8, 2, 01-04-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (9, 2, 01-04-2016, 1); ");
    }

    public void addStudentAttendanceRecords(SQLiteDatabase db) {
        // Student attendence Day 1 Class 1
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (1, 1, 01-02-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (2, 1, 01-02-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (3, 1, 01-02-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (4, 1, 01-02-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (5, 1, 01-02-2016, 1); ");
        // Student attendence Day 1 Class 2
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (1, 2, 01-02-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (2, 2, 01-02-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (3, 2, 01-02-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (6, 2, 01-02-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (7, 2, 01-02-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (8, 2, 01-02-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (9, 2, 01-02-2016, 0); ");
        // Student attendence Day 2 Class 1
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (1, 1, 01-03-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (2, 1, 01-03-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (3, 1, 01-03-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (4, 1, 01-03-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (5, 1, 01-03-2016, 1); ");
        // Student attendence Day 2 Class 2
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (1, 2, 01-03-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (2, 2, 01-03-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (3, 2, 01-03-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (6, 2, 01-03-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (7, 2, 01-03-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (8, 2, 01-03-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (9, 2, 01-03-2016, 0); ");
        // Student attendence Day 3 Class 1
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (1, 1, 01-04-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (2, 1, 01-04-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (3, 1, 01-04-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (4, 1, 01-04-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (5, 1, 01-04-2016, 1); ");
        // Student attendence Day 3 Class 2
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (1, 2, 01-04-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (2, 2, 01-04-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (3, 2, 01-04-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (6, 2, 01-04-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (7, 2, 01-04-2016, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (8, 2, 01-04-2016, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (9, 2, 01-04-2016, 1); ");
    }

    public boolean isDateForCourseEmpty(int courseID, String date) {
            boolean retValue = true;
            String query = "Select CourseID FROM StudentAttendance " +
                    " WHERE " +
                    "Date=\"" + date + "\" AND CourseID=" + courseID + " LIMIT 1;";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                retValue = false;
            }
            db.close();
        return retValue;
    }

    public void addAttendanceRecord(Course course, ArrayList<Student> students, String date) {
        for (Student student: students) {
            ContentValues values = new ContentValues();
            values.put("StudentID", student.getStudentID());
            values.put("CourseID", course.getCourseID());
            values.put("Date", date);
            values.put("Present", student.isPresent());
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert("StudentAttendance", null, values);
            db.close();
        }
    }

    public void deleteRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("StudentAttendance", null, null);
        db.close();
    }
}
