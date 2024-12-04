package src;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;


/**
 * The UniversityStudent class extends the abstract Student class and represents a student at a university.
 * This class can store additional information and implement the connection strength calculation specific to university students.
 */
public class UniversityStudent extends Student {

    private UniversityStudent roommate;

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
    public UniversityStudent(String name, int age, String gender, int year,
                             String major, double gpa, List<String> roommatePreferences,
                             List<String> previousInternships) {
        super(name, age, gender, year, major, gpa, roommatePreferences, previousInternships);
        this.roommate = null;  // by default, no roommate assigned
    }


    public UniversityStudent() {
        super("", 0, "", 0, "", 0.0, new ArrayList<>(), new ArrayList<>());
        this.roommate = null;  // No roommate by default
    }


    // getters
    public UniversityStudent getRoommate() {
        return roommate;
    }

    // setters
    public void setRoommate(UniversityStudent roommate) {
        this.roommate = roommate;
    }


    /**
     * Calculates the connection strength between this student and another student
     * The calculation takes into account various factors such as shared majors, internships, and roommate status
     *
     * @param other The other student to calculate the connection strength with.
     * @return The connection strength as an integer, based on the shared attributes.
     */
    @Override
    public int calculateConnectionStrength(Student other) {
        int strength = 0; // default strength is 0

        // roommates
        if (this.roommate != null && this.roommate.equals(other)) {
            strength += 5;
        }

        // same internship
        for (String internship : this.previousInternships) {
            if (other.getPreviousInternships().contains(internship)) {
                strength += 4;
            }
        }

        // same major
        if (this.major.equalsIgnoreCase(other.getMajor())) {
            strength += 3;
        }

        // same age
        if (this.age == other.getAge()) {
            strength += 2;
        }

        return strength;
    }




    // receive a message
    public String receiveMessage(UniversityStudent sender, String message) {
        return this.name + " received a message from " + sender.getName() + ": " + message;
    }

    // accept the friend requests
    public boolean receiveFriendRequest(UniversityStudent sender) {
        return true;
    }


    // compare preferences for students and roommates
    public boolean prefers(UniversityStudent student, UniversityStudent currentRoommate) {
        int studentPreference = roommatePreferences.indexOf(student.getName());
        int currentRoommatePreference = roommatePreferences.indexOf(currentRoommate.getName());

        return studentPreference < currentRoommatePreference;
    }


    @Override
    public String toString() {
        return "Name: " + this.name + ", Age: " + this.age + ", Major: " + this.major +
                ", Year: " + this.year + ", GPA: " + this.gpa;
    }
}
