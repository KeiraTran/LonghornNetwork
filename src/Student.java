import java.util.*;

/**
 * The Student class represents a university student with attributes like name, age, gender, major, etc
 * Provides an abstract method to calculate the connection strength between students based on these criteria
 */
public abstract class Student {
    protected String name;
    protected int age;
    protected String gender;
    protected int year;
    protected String major;
    protected double gpa;
    protected List<String> roommatePreferences;
    protected List<String> previousInternships;


    /**
     * Calculates the connection strength between this student and another student
     *
     * @param other The other student to calculate the connection strength with
     * @return The calculated connection strength
     */
    public abstract int calculateConnectionStrength(Student other);
}
