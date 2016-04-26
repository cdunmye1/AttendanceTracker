package edu.westga.attendancetracker.model;

import java.util.ArrayList;

/**
 * The Course class which will keep track of students in a course
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

    public int getCourseID() {
        return this.courseID;
    }
    public String getName() { return this.name; }

    @Override
    public String toString() {
        return this.name;
    }
}
