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
        Set<UniversityStudent> visited = new HashSet<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(e -> -e.weight));

        for (UniversityStudent student : students) {
            if (!visited.contains(student)) {
                List<UniversityStudent> currentPod = new ArrayList<>();
                visited.add(student);
                currentPod.add(student);

                // starting edges
                for (UniversityStudent neighbor : students) {
                    if (!visited.contains(neighbor)) {
                        int weight = student.calculateConnectionStrength(neighbor);
                        priorityQueue.add(new Edge(student, neighbor, weight));
                    }
                }

                // build pod
                while (!priorityQueue.isEmpty() && currentPod.size() < podSize) {
                    Edge edge = priorityQueue.poll();
                    if (!visited.contains(edge.to)) {
                        visited.add(edge.to);
                        currentPod.add(edge.to);

                        // add edges from the new student
                        for (UniversityStudent neighbor : students) {
                            if (!visited.contains(neighbor)) {
                                int weight = edge.to.calculateConnectionStrength(neighbor);
                                priorityQueue.add(new Edge(edge.to, neighbor, weight));
                            }
                        }
                    }
                }

                pods.add(currentPod);// Add the pod to the list


            }

        }
    }

    /**
     * Returns the formed pods.
     *
     * @return The list of pods.
     */
    public List<List<UniversityStudent>> getPods() {
        return pods;
    }

    /**
     * helper class that represents each weighted edge
     */
    private static class Edge {
        UniversityStudent from;
        UniversityStudent to;
        int weight;

        public Edge(UniversityStudent from, UniversityStudent to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
}
