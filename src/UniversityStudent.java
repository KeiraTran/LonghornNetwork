import java.util.*;


/**
 * The UniversityStudent class extends the abstract Student class and represents a student at a university.
 * This class can store additional information and implement the connection strength calculation specific to university students.
 */
public class UniversityStudent extends Student {
    /**
     * Constructor for creating a UniversityStudent object with the given attributes.
     *
     * @param name The student's name
     * @param age The student's age
     * @param gender The student's gender
     * @param year The student's year in school
     * @param major The student's major
     * @param gpa The student's GPA
     * @param roommatePreferences List of preferred roommates
     * @param previousInternships List of internships the student has previously completed
     */
    // TODO: Constructor implementation

    /**
     * Calculates the connection strength between this student and another student
     * The calculation takes into account various factors such as shared majors, internships, and roommate status
     *
     * @param other The other student to calculate the connection strength with.
     * @return The connection strength as an integer, based on the shared attributes.
     */
    @Override
    public int calculateConnectionStrength(Student other) {
        // TODO: Implement connection strength logic
        return 0;
    }
}

