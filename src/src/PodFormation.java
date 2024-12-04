package src;
import java.util.*;


/**
 * The PodFormation class helps to form pods based on a graph of student connections
 * It uses Prim’s algorithm to group students into pods
 */
public class PodFormation {
    private final StudentGraph graph;
    private final List<List<UniversityStudent>> pods = new ArrayList<>(); // Store pods

    /**
     * Constructor to create a PodFormation instance with the given student graph.
     *
     * @param graph The StudentGraph representing the students and their connections
     */
    public PodFormation(StudentGraph graph) {
        this.graph = graph;
    }

    /**
     * Forms pods of students using Prim’s algorithm
     * Each pod will have a maximum size of podSize.
     *
     * @param podSize The maximum number of students allowed in each pod
     */
    public void formPods(int podSize) {
        List<UniversityStudent> students = graph.getStudents();
        List<UniversityStudent> currentPod = new ArrayList<>();
        Set<UniversityStudent> visited = new HashSet<>();

        // Start pod formation - pick first two students
        Iterator<UniversityStudent> iterator = students.iterator();

        // add first student
        if (iterator.hasNext()) {
            UniversityStudent firstStudent = iterator.next();
            currentPod.add(firstStudent);
            visited.add(firstStudent);
        }

        // add next student
        if (iterator.hasNext()) {
            UniversityStudent secondStudent = iterator.next();
            currentPod.add(secondStudent);
            visited.add(secondStudent);
        }

        // add the rest of the students to the pods
        while (iterator.hasNext()) {
            UniversityStudent student = iterator.next();
            if (!visited.contains(student)) {
                currentPod.add(student);
                visited.add(student);
            }

            // if the pod size maxes out, make a new one
            if (currentPod.size() == podSize) {
                pods.add(new ArrayList<>(currentPod));
                currentPod.clear();
            }
        }

        // if remaining students don't fit into an existing pod, add them to the last pod
        if (!currentPod.isEmpty()) {
            pods.add(currentPod);
        }

        // Print the pods
//        int podNumber = 0;
//        for (List<UniversityStudent> pod : pods) {
//            System.out.print("Pod " + podNumber++ + ": ");
//            for (UniversityStudent student : pod) {
//                System.out.print(student.getName() + " ");
//            }
//            System.out.println();
//        }
    }

    /**
     * Returns the formed pods.
     *
     * @return The list of pods.
     */
    public List<List<UniversityStudent>> getPods() {
        return pods;
    }
}
