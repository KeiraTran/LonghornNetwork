package src;
import java.util.List;


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



    // constructor
    public Student(String name, int age, String gender, int year, String major, double gpa,
                   List<String> roommatePreferences, List<String> previousInternships) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.year = year;
        this.major = major;
        this.gpa = gpa;
        this.roommatePreferences = roommatePreferences;
        this.previousInternships = previousInternships;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public int getYear() {
        return year;
    }

    public String getMajor() {
        return major;
    }

    public double getGpa() {
        return gpa;
    }

    public List<String> getRoommatePreferences() {
        return roommatePreferences;
    }

    public List<String> getPreviousInternships() {
        return previousInternships;
    }



    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public void setRoommatePreferences(List<String> roommatePreferences) {
        this.roommatePreferences = roommatePreferences;
    }

    public void setPreviousInternships(List<String> previousInternships) {
        this.previousInternships = previousInternships;
    }



    /**
     * Calculates the connection strength between this student and another student
     *
     * @param other The other student to calculate the connection strength with
     * @return The calculated connection strength
     */
    public abstract int calculateConnectionStrength(Student other);
}
