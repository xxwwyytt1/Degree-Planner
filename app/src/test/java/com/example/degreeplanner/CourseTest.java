package com.example.degreeplanner;

import com.example.degreeplanner.classes.Course;
import com.example.degreeplanner.enums.GradingOption;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CourseTest {
    Course COGS1;
    Course CSE11;
    Course JAP1A;
    Course CSE12;
    Course DSC10;
    Course JAP1B;
    Course CSE20;
    Course DSC20;
    Course JAP1C;

    @Before
    public void setUp() {
        COGS1 = new Course("COGS", "1", GradingOption.LETTER, 4, "");
        CSE11 = new Course("CSE", "11", GradingOption.LETTER, 4, "");
        JAP1A = new Course("JAP", "1A", GradingOption.LETTER, 5, "");
        CSE12 = new Course("CSE", "12", GradingOption.LETTER, 4, "");
        DSC10 = new Course("DSC", "10", GradingOption.LETTER, 4, "");
        JAP1B = new Course("JAP", "1B", GradingOption.LETTER, 5, "");
        CSE20 = new Course("CSE", "20", GradingOption.LETTER, 4, "");
        DSC20 = new Course("DSC", "20", GradingOption.LETTER, 4, "");
        JAP1C = new Course("JAP", "1C", GradingOption.LETTER, 5, "");
    }

    @Test
    public void testMinConstructor() { // test getters and default values for course
        Course course = new Course("COGS", "102C");
        assertEquals("COGS", course.getDept());
        assertEquals("102C", course.getCode());
        assertEquals(0.0, course.getUnits());
        assertEquals(GradingOption.UNCOUNTED, course.getOption());
        assertEquals("", course.getNotes());
        assertEquals(new ArrayList<Course>(), course.getPrereqs());
    }

    @Test
    public void testOldMaxConstructor() { // test getters and default values for course
        Course course = new Course("COGS", "102C", GradingOption.LETTER, 6.0, "Why is this class so many units?");
        assertEquals("COGS", course.getDept());
        assertEquals("102C", course.getCode());
        assertEquals(6.0, course.getUnits());
        assertEquals(GradingOption.LETTER, course.getOption());
        assertEquals("Why is this class so many units?", course.getNotes());
        assertEquals(new ArrayList<Course>(), course.getPrereqs());
    }

    @Test
    public void testMaxConstructor() { // test getters and default values for course
        ArrayList<Course> prereqs1 = new ArrayList<Course>();
        Course prereq1 = new Course("COGS", "102A");
        Course prereq2 = new Course("COGS", "102B");
        prereqs1.add(prereq1);
        prereqs1.add(prereq2);

        Course course = new Course("COGS", "102C", GradingOption.LETTER, 6.0, "Why is this class so many units?", prereqs1);
        assertEquals("COGS", course.getDept());
        assertEquals("102C", course.getCode());
        assertEquals(6.0, course.getUnits());
        assertEquals(GradingOption.LETTER, course.getOption());
        assertEquals("Why is this class so many units?", course.getNotes());
        assertEquals(prereqs1, course.getPrereqs());
    }

    @Test
    public void testSetters() { // test setters for course
        // setup
        Course course = new Course("COGS", "102C");
        course.setDept("CSE");
        course.setCode("110");
        course.setUnits(4.0);
        course.setOption(GradingOption.LETTER);
        course.setNotes("please let me pass");

        ArrayList<Course> prereqs = new ArrayList<Course>();
        Course prereq1 = new Course("CSE", "11");
        Course prereq2 = new Course("CSE", "30");
        prereqs.add(prereq1);
        prereqs.add(prereq2);

        course.setPrereqs(prereqs);

        // testing
        assertEquals("CSE", course.getDept());
        assertEquals("110", course.getCode());
        assertEquals(4.0, course.getUnits());
        assertEquals(GradingOption.LETTER, course.getOption());
        assertEquals("please let me pass", course.getNotes());
        assertEquals(prereqs, course.getPrereqs());

        Course prereq3 = new Course("CSE", "100");
        course.addPrereq(prereq3);

        ArrayList<Course> prereqs2= new ArrayList<Course>();
        prereqs2.add(prereq1);
        prereqs2.add(prereq2);
        prereqs2.add(prereq3);
        assertEquals(prereqs2, course.getPrereqs());
    }

    @Test
    public void testAddPrereq() {
        CSE20.addPrereq(CSE11);
        assertTrue(CSE20.getPrereqs().contains(CSE11));

        assertFalse(CSE12.getPrereqs().contains(CSE11));
        CSE12.addPrereq(CSE11);
        assertTrue(CSE12.getPrereqs().contains(CSE11));
    }

    @Test
    public void testRemovePrereq() {
        CSE20.addPrereq(CSE11);
        assertTrue(CSE20.getPrereqs().contains(CSE11));
        CSE20.removePrereq(CSE11);
        assertFalse(CSE20.getPrereqs().contains(CSE11));

    }

    @Test
    public void testSameCourseNameAs() {
        Course CSE20pnp = new Course("CSE", "20", GradingOption.PNP, 4, "");
        assertTrue(CSE20.sameCourseNameAs(CSE20pnp));
    }

    @Test
    public void testCompareCourses() {
        Course CSE20pnp = new Course("CSE", "20", GradingOption.PNP, 4, "");
        assertFalse(CSE20.compareCourses(CSE20pnp));
        Course CSE20letter = new Course("CSE", "20", GradingOption.LETTER, 4, "retaking");
        assertTrue(CSE20.compareCourses(CSE20letter));
    }

    // test the prereq fulfillment method with more completed courses
    @Test
    public void testPrereqFulfillmentMore() {
        // setup
        Course course = new Course("CSE", "100");
        Course prereq1 = new Course("CSE", "12");
        Course prereq2 = new Course("CSE", "30");
        Course bonus1 = new Course("CSE", "150B");

        ArrayList<Course> prereqs = new ArrayList<Course>();
        prereqs.add(prereq1);
        prereqs.add(prereq2);

        ArrayList<Course> completed = new ArrayList<Course>();
        completed.add(prereq1);
        completed.add(prereq2);
        completed.add(bonus1);

        course.setPrereqs(prereqs);

        assertEquals(prereqs, course.getPrereqs());
        assertNotEquals(completed, course.getPrereqs());
        assertTrue(course.prereqsFulfilled(completed));
    }


    // test the prereq fulfillment method with matching completed courses
    @Test
    public void testPrereqFulfillmentSame() {
        // setup
        Course course = new Course("CSE", "100");
        Course prereq1 = new Course("CSE", "12");
        Course prereq2 = new Course("CSE", "30");

        ArrayList<Course> prereqs = new ArrayList<Course>();
        prereqs.add(prereq1);
        prereqs.add(prereq2);

        ArrayList<Course> completed = new ArrayList<Course>();
        completed.add(prereq1);
        completed.add(prereq2);

        course.setPrereqs(prereqs);

        assertEquals(prereqs, course.getPrereqs());
        assertEquals(completed, course.getPrereqs());
        assertTrue(course.prereqsFulfilled(completed));
    }

    // test the prereq fulfillment method with fewer completed courses
    @Test
    public void testPrereqFulfillmentFewer() {
        // setup
        Course course = new Course("CSE", "100");
        Course prereq1 = new Course("CSE", "12");
        Course prereq2 = new Course("CSE", "30");
        Course bonus1 = new Course("CSE", "150B");

        ArrayList<Course> prereqs = new ArrayList<Course>();
        prereqs.add(prereq1);
        prereqs.add(prereq2);
        prereqs.add(bonus1);

        ArrayList<Course> completed = new ArrayList<Course>();
        completed.add(prereq1);
        completed.add(prereq2);

        course.setPrereqs(prereqs);

        assertEquals(prereqs, course.getPrereqs());
        assertNotEquals(completed, course.getPrereqs());
        assertFalse(course.prereqsFulfilled(completed));
    }

}