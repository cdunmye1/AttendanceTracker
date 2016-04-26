package edu.westga.attendancetracker;

import org.junit.Test;

import java.util.ArrayList;

import edu.westga.attendancetracker.model.Course;
import edu.westga.attendancetracker.model.Student;

import static org.junit.Assert.assertEquals;

/**
 * Unit Tests for the Course Class
 */
public class CourseUnitTests {
    private ArrayList<Student> arrayOfStudents;

    @Test
    public void validateGetCourseName() {
        Course course = new Course(1, "Software 101");
        assertEquals("Software 101", course.getName());
    }

    @Test
    public void validateGetCourseID() {
        Course course = new Course(1, "Software 101");
        assertEquals(1, course.getCourseID());
    }

    @Test
    public void validateSetAndGetStudentList() {
        this.setArrayOfStudents();
        Course course = new Course(1, "Software 101");
        course.setStudents(this.arrayOfStudents);
        assertEquals(9, course.getStudents().size());
    }

    @Test
    public void validateToStringShowsCourseName() {
        Course course = new Course(1, "Software 101");
        assertEquals("Software 101", course.getName());
    }

    public void setArrayOfStudents() {
        arrayOfStudents = new ArrayList<>();
        arrayOfStudents.add(new Student(1, "Chris Dunmyer"));
        arrayOfStudents.add(new Student(2, "Bill Donovan"));
        arrayOfStudents.add(new Student(3, "Henry Williams"));
        arrayOfStudents.add(new Student(4, "Chad Smith"));
        arrayOfStudents.add(new Student(5, "List Saunders"));
        arrayOfStudents.add(new Student(6, "Kaitlyn Carlisle"));
        arrayOfStudents.add(new Student(7, "Carly Snyder"));
        arrayOfStudents.add(new Student(8, "Mark Jackson"));
        arrayOfStudents.add(new Student(9, "Steven Robinson"));
    }

}