package edu.westga.attendancetracker;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import edu.westga.attendancetracker.model.Course;
import edu.westga.attendancetracker.model.Student;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "studentAttendanceDB.db";
    public static final String TABLE_STUDENTS = "Students";

    public static final String STUDENT_COLUMN_ID = "_id";
    public static final String STUDENT_COLUMN_NAME = "name";

    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        //calling it with null to create it in memory
        //super(context, null, factory, DATABASE_VERSION);
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

        String CREATE_STUDENT_ATTENDANCE_TABLE = "CREATE TABLE " +
                "StudentAttendance " + "("
                + "StudentID" + " INTEGER," + " CourseID INTEGER"
                + " INTEGER, Date TEXT, Present INTEGER, PRIMARY KEY (StudentID, CourseID))";

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL(CREATE_STUDENTS_TABLE);
        db.execSQL(CREATE_COURSE_TABLE);
        db.execSQL(CREATE_STUDENT_ATTENDANCE_TABLE);
        addRecords(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public ArrayList<Student> getStudentsFromClass(int CourseID) {
        String query = "Select DISTINCT s._id, s.name FROM StudentAttendance sa " +
                "JOIN Students s on s._id=sa.StudentID WHERE " +
                "CourseID=" + CourseID;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Student> studentList = new ArrayList<Student>();
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
        ArrayList<Course> courseList = new ArrayList<Course>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int courseID = Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1);
            courseList.add(new Course(courseID, name));
        }

        db.close();
        return courseList;
    }

    private void addRecords(SQLiteDatabase db) {
        final ArrayList<Student> arrayOfUsers = new ArrayList<Student>();
        arrayOfUsers.add(new Student(1, "Chris Dunmyer"));
        arrayOfUsers.add(new Student(2, "Bill Donovan"));
        arrayOfUsers.add(new Student(3, "Henry Williams"));
        arrayOfUsers.add(new Student(4, "Chad Smith"));
        arrayOfUsers.add(new Student(5, "List Saunders"));
        arrayOfUsers.add(new Student(6, "Kaitlyn Carlisle"));
        arrayOfUsers.add(new Student(7, "Carly Snyder"));
        arrayOfUsers.add(new Student(8, "Mark Jackson"));
        arrayOfUsers.add(new Student(9, "Steven Robinson"));
        for (Student student: arrayOfUsers)
        {
            String INSERT_STUDENT_DATA = "INSERT INTO "+ TABLE_STUDENTS + " (_id, name) VALUES (" + student.getStudentID() + ", '" + student.getName() +"')";
            db.execSQL(INSERT_STUDENT_DATA);
        }

        String insertClassData = "INSERT INTO Course (_id, name) VALUES (1, 'Software Development 101'); ";

        String insertClassStudentData = "INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (1, 1, 2016-01-02, 0); " +
                "INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (2, 1, 2016-01-02, 1); " +
                "INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (3, 1, 2016-01-02, 1); " +
                "INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (4, 1, 2016-01-02, 0); " +
                "INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (5, 1, 2016-01-02, 1); ";

        db.execSQL(insertClassData);
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (1, 1, 2016-01-02, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (2, 1, 2016-01-02, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (3, 1, 2016-01-02, 1); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (4, 1, 2016-01-02, 0); ");
        db.execSQL("INSERT INTO StudentAttendance (StudentID, CourseID, Date, Present) VALUES (5, 1, 2016-01-02, 1); ");
    }

//    public void addProduct(Product product) {
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_PRODUCTNAME, product.getProductName());
//        values.put(COLUMN_QUANTITY, product.getQuantity());
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        db.insert(TABLE_PRODUCTS, null, values);
//        db.close();
//    }

//    public void updateProduct(Product product) {
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_PRODUCTNAME, product.getProductName());
//        values.put(COLUMN_QUANTITY, product.getQuantity());
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.update(TABLE_PRODUCTS, values, COLUMN_ID + "=" + product.getID(), null);
//        db.close();
//    }

//    public Product findProduct(String productname) {
//        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " =  \"" + productname + "\"";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor cursor = db.rawQuery(query, null);
//
//        Product product = new Product();
//
//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst();
//            product.setID(Integer.parseInt(cursor.getString(0)));
//            product.setProductName(cursor.getString(1));
//            product.setQuantity(Integer.parseInt(cursor.getString(2)));
//            cursor.close();
//        } else {
//            product = null;
//        }
//        db.close();
//        return product;
//    }

//    public boolean deleteProduct(String productname) {
//
//        boolean result = false;
//
//        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " =  \"" + productname + "\"";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor cursor = db.rawQuery(query, null);
//
//        Product product = new Product();
//
//        if (cursor.moveToFirst()) {
//            product.setID(Integer.parseInt(cursor.getString(0)));
//            db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?",
//                    new String[] { String.valueOf(product.getID()) });
//            cursor.close();
//            result = true;
//        }
//        db.close();
//        return result;
//    }

//    public void deleteAllProducts() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_PRODUCTS, null, null);
//        db.close();
//    }
}
