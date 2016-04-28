package edu.westga.attendancetracker;

import org.junit.Test;

import edu.westga.attendancetracker.model.Student;

import static org.junit.Assert.*;

/**
 * Unit Tests for the Student Class
 */
public class StudentUnitTests {

    @Test
    public void validateGetNameMethod() {
        Student student = new Student(1, "Chris");
        assertEquals("Chris", student.getName());
    }

    @Test
    public void validateGetStudentIDMethod() {
        Student student = new Student(1, "Chris");
        assertEquals(1, student.getStudentID());
    }

    @Test
    public void validateStudentIsPresentByDefault() {
        Student student = new Student(1, "Chris");
        assertEquals(1, student.isPresent());
    }

    @Test
    public void validateToggleStudentIsPresentMethod() {
        Student student = new Student(1, "Chris");
        student.toggleIsPresent();
        assertEquals(0, student.isPresent());
    }

    @Test
     public void validateToStringWhenPresent() {
        Student student = new Student(1, "Chris");
        assertEquals("Chris", student.toString());
    }

    @Test
    public void validateToStringWhenAbsent() {
        Student student = new Student(1, "Chris");
        student.toggleIsPresent();
        assertEquals("(Absent) - Chris", student.toString());
    }
}