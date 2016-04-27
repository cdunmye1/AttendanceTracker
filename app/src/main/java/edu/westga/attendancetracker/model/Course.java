package edu.westga.attendancetracker.model;

import java.util.ArrayList;

/**
 * The Course class which will keep track of students in a course
 */
public class Course {

    private String name;
    private int courseID;
    private ArrayList<Student> students;
    private double attendancePercentage;

    public Course(int courseID, String name) {
        this.courseID = courseID;
        this.name = name;
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    public void setStudents(ArrayList<Student> students) {
        if (students == null) {
            throw new IllegalArgumentException("students cannot be null");
        }
        this.students = students;
    }

    public int getCourseID() {
        return this.courseID;
    }
    public String getName() { return this.name; }

    public void setAttendancePercentage(double percentage) {
        this.attendancePercentage = percentage;
    }

    public double getAttendancePercentage() {
        return this.attendancePercentage;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
