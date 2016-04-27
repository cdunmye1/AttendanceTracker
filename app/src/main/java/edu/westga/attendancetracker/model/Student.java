package edu.westga.attendancetracker.model;

/**
 * Created by Chris on 4/19/2016.
 */
public class Student {

    private int studentID;
    private String name;
    private boolean isPresent;

    public Student(int studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.isPresent = true;
    }

    public void toggleIsPresent() {
        if (this.isPresent == true) {
            this.isPresent = false;
        } else {
            this.isPresent = true;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getStudentID() {
        return this.studentID;
    }

    public int isPresent() {
        if (this.isPresent) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        if (this.isPresent == true) {
            return this.name;
        }
        return "(Absent) - " + this.name;
    }


}
