package edu.westga.attendancetracker;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import edu.westga.attendancetracker.model.Student;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "studentAttendanceDB.db";
    public static final String TABLE_STUDENTS = "students";

    public static final String STUDENT_COLUMN_ID = "_id";
    public static final String STUDENT_COLUMN_NAME = "name";

    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        //super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        //calling it with null to create it in memory
        super(context, null, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_STUDENTS + "("
                + STUDENT_COLUMN_ID + " INTEGER PRIMARY KEY," + STUDENT_COLUMN_NAME
                + " TEXT" + ")";
        String INSERT_STUDENT_DATA = "INSERT INTO "+ TABLE_STUDENTS + " (_id, name) VALUES (1, 'Chad Stevens')";
        String INSERT_STUDENT_DATA2 = "INSERT INTO "+ TABLE_STUDENTS + " (_id, name) VALUES (2, 'Chris Dunmyer')";
        String INSERT_STUDENT_DATA3 = "INSERT INTO "+ TABLE_STUDENTS + " (_id, name) VALUES (3, 'Chris Dunmyer')";
        String INSERT_STUDENT_DATA4= "INSERT INTO "+ TABLE_STUDENTS + " (_id, name) VALUES (4, 'Chris Dunmyer')";
        String INSERT_STUDENT_DATA5 = "INSERT INTO "+ TABLE_STUDENTS + " (_id, name) VALUES (5, 'Chris Dunmyer')";
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(INSERT_STUDENT_DATA);
        db.execSQL(INSERT_STUDENT_DATA2);
        db.execSQL(INSERT_STUDENT_DATA3);
        db.execSQL(INSERT_STUDENT_DATA4);
        db.execSQL(INSERT_STUDENT_DATA5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public ArrayList<Student> getStudents() {
        String query = "Select _id, name FROM " + TABLE_STUDENTS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Student> studentList = new ArrayList<Student>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int studentID = Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1);
            studentList.add(new Student(studentID, name));
            System.out.println(studentID + name);
        }

        db.close();
        return studentList;
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
