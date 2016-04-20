package edu.westga.attendancetracker.model;

import java.util.ArrayList;

/**
 * Created by Chris on 4/20/2016.
 */
public class Class {

    private String name;
    private ArrayList<Student> students;

    public Class(String name) {
        this.name = name;
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String getName() {
        return this.name;
    }
}
