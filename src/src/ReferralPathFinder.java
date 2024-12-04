package src;
import java.util.*;


/**
 * The ReferralPathFinder class helps to find referral paths between students using their connections
 * Uses Dijkstra’s algorithm to find the shortest path to a student who interned at a specified company
 */
public class ReferralPathFinder {
    /**
     * Constructor to create a ReferralPathFinder instance with the given student graph.
     *
     * @param graph The StudentGraph representing the students and their connections.
     */
    private final StudentGraph graph;

    public ReferralPathFinder(StudentGraph graph) {

        this.graph = graph;

    }
    

    /**
     * Finds the referral path from the starting student to a student who interned at the target company.
     * It uses Dijkstra's algorithm to determine the shortest path based on connection strengths.
     *
     * @param startStudent The student who the referral path will start with
     * @param targetCompany The company where the target internship student worked
     * @return A list of UniversityStudents representing the path from the starting student to the target
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent startStudent, String targetCompany) {
        
        // initialize distances and priority queue for prims
        Map<UniversityStudent, Integer> distances = new HashMap<>();
        Map<UniversityStudent, UniversityStudent> parentMap = new HashMap<>();
        Set<UniversityStudent> visited = new HashSet<>();

        // distances for each student and start student distance = 0
        for (UniversityStudent student : graph.getStudents()) {
            distances.put(student, Integer.MAX_VALUE);
        }
        distances.put(startStudent, 0);
        
        PriorityQueue<UniversityStudent> pQueue = new PriorityQueue<>(Comparator.comparingInt(student -> distances.get(student)));

        pQueue.add(startStudent);

        while (!pQueue.isEmpty()) {
            UniversityStudent current = pQueue.poll();
            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);

            // check if the current student has an internship at the target company
            if (current.getPreviousInternships().contains(targetCompany)) {
                return reconstructPath(startStudent, current, parentMap);
            }

            // traverse the neighbors
            for (UniversityStudent neighbor : graph.getConnections(current)) {
                if (visited.contains(neighbor)) {
                    continue;
                }

                int newDist = distances.get(current) + graph.getConnectionStrength(current, neighbor);
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    parentMap.put(neighbor, current);
                    pQueue.add(neighbor);  // Re-add to queue to re-order based on new distance
                }
            }
        }

        // if no path found
        return new ArrayList<>();
    }

    // output the path from startStudent to the target student
    private List<UniversityStudent> reconstructPath(UniversityStudent start, UniversityStudent end, Map<UniversityStudent, UniversityStudent> parentMap) {
        List<UniversityStudent> path = new ArrayList<>();
        UniversityStudent current = end;

        while (current != null) {
            path.add(current);
            current = parentMap.get(current);
        }

        Collections.reverse(path);
        return path;
    }



    // Calculate the weight of the path
    public int calculatePathWeight(List<UniversityStudent> path) {
        int totalWeight = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            UniversityStudent current = path.get(i);
            UniversityStudent next = path.get(i + 1);

            // weight it by connection strength
            Integer strength = graph.getConnectionStrength(current, next);
            if (strength != null) {
                totalWeight += strength;
            }
        }

        return totalWeight;
    }
}
