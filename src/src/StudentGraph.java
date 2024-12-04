package src;

import java.util.*;


public class StudentGraph {
    private List<UniversityStudent> students;
    private Map<UniversityStudent, Map<UniversityStudent, Integer>> connections;

    public StudentGraph(List<UniversityStudent> students) {
        this.students = students;
        this.connections = new HashMap<>();
        initializeConnections();
    }

    // Initializes the connections based on roommate preferences, internships, and connection strength
    private void initializeConnections() {
        for (UniversityStudent student : students) {
            connections.putIfAbsent(student, new HashMap<>());

            // Handle roommate preferences
            for (String preferenceName : student.getRoommatePreferences()) {
                UniversityStudent preferredStudent = findStudentByName(preferenceName);
                if (preferredStudent != null) {
                    int strength = student.calculateConnectionStrength(preferredStudent);
                    addConnection(student, preferredStudent, strength);
                }
            }

            // Add internship-based connections
            for (String internship : student.getPreviousInternships()) {
                for (UniversityStudent otherStudent : students) {
                    if (!otherStudent.equals(student) && otherStudent.getPreviousInternships().contains(internship)) {
                        int strength = student.calculateConnectionStrength(otherStudent);
                        addConnection(student, otherStudent, strength);
                    }
                }
            }
        }
    }

    // Adds a bidirectional connection with strength between two students
    // Add connection with strength based on roommate preferences and internships
    private void addConnection(UniversityStudent student1, UniversityStudent student2, int strength) {
        connections.putIfAbsent(student1, new HashMap<>());
        connections.putIfAbsent(student2, new HashMap<>());

        // Add the connection with strength to both students
        connections.get(student1).put(student2, strength);
        connections.get(student2).put(student1, strength);
    }


    // Returns a list of connections (neighbors) for a given student
    public List<UniversityStudent> getConnections(UniversityStudent student) {
        return new ArrayList<>(connections.getOrDefault(student, new HashMap<>()).keySet());
    }

    // Returns the connection strength between two students
    public Integer getConnectionStrength(UniversityStudent student1, UniversityStudent student2) {
        return connections.getOrDefault(student1, new HashMap<>()).get(student2);
    }

    // Finds a student by name from the list of students
    private UniversityStudent findStudentByName(String name) {
        for (UniversityStudent student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }

    // Get the list of all students in the graph
    public List<UniversityStudent> getStudents() {
        return students;
    }


}
