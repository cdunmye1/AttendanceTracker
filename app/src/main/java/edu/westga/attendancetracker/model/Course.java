package edu.westga.attendancetracker.model;

import java.util.ArrayList;

/**
 * Created by Chris on 4/20/2016.
 */
public class Course {

    private String name;
    private int courseID;
    private ArrayList<Student> students;

    public Course(int courseID, String name) {
        this.courseID = courseID;
        this.name = name;
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public int getClassID() {
        return this.courseID;
    }
    public String getName() { return this.name; }

    @Override
    public String toString() {
        return this.name;
    }
}
