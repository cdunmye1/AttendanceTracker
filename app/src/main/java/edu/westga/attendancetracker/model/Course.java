package edu.westga.attendancetracker.model;

import java.util.ArrayList;

/**
 * Created by Chris on 4/20/2016.
 */
public class Course {

    private String name;
    private int classID;
    private ArrayList<Student> students;

    public Course(int classID, String name) {
        this.classID = classID;
        this.name = name;
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public int getClassID() {
        return this.classID;
    }
    public String getName() { return this.name; }

    @Override
    public String toString() {
        return this.name;
    }
}
