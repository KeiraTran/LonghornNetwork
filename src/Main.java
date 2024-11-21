import java.io.IOException;
import java.util.*;


/**
 * The Main class is the entry point for the Longhorn Network simulation.
 * It handles reading input data, roommate matching, pod formation, and finding internship referral paths
 */
public class Main {
    /**
     *Reads the input file, processes the students, and performs various operations like roommate assignment,
     * pod formation, and referral path finding.
     *
     * @param args Command-line arguments- the first argument should be the input file name containing student data.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the input file name as a command-line argument.");
            return;
        }
        String inputFile = args[0];
        try {
            List<UniversityStudent> students = DataParser.parseStudents(inputFile);

            // Roommate matching
            GaleShapley.assignRoommates(students);

            // Pod formation
            StudentGraph graph = new StudentGraph(students);
            PodFormation podFormation = new PodFormation(graph);
            podFormation.formPods(4);

            // Referral path finding
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph);
            // TODO: Implement user interaction for specifying a target company

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
