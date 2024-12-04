package src;
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
     * @param args Command-line arguments - the first argument should be the input file name containing student data.
     */

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the input file name.");
            return;
        }

        String inputFile = args[0];
        try {
            // parse student data
            List<UniversityStudent> students = DataParser.parseStudents(inputFile);

            if (students == null || students.isEmpty()) {
                System.out.println("No students found in the input file");
                return;
            }

            // roommate assignment
            GaleShapley.assignRoommates(students);

            System.out.println("Roommate Assignments:");
            for (UniversityStudent student : students) {
                // Ensure that students have roommates set
                if (student.getRoommate() != null) {
                    System.out.println(student.getName() + " is roommates with " + student.getRoommate().getName());
                }
            }



            // pod formation
            StudentGraph graph = new StudentGraph(students);
            PodFormation podFormation = new PodFormation(graph);
            podFormation.formPods(4); // Assume pod size of 4 students per pod

            System.out.println("\nPod Assignments:");
            List<List<UniversityStudent>> pods = podFormation.getPods();  // Assuming this method gives the pods
            int podNumber = 1;
            for (List<UniversityStudent> pod : pods) {
                System.out.print("Pod " + podNumber++ + ": ");
                for (UniversityStudent student : pod) {
                    System.out.print(student.getName() + ", ");
                }
                System.out.println();
            }



            // referral path
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph);

            Scanner scanner = new Scanner(System.in);

            System.out.print("\nEnter the starting student for the referral path search: ");
            String startStudentName = scanner.nextLine();

            UniversityStudent startStudent = null;
            for (UniversityStudent student : students) {
                if (student.getName().equalsIgnoreCase(startStudentName)) {
                    startStudent = student;
                    break;
                }
            }

            if (startStudent == null) {
                System.out.println("Student " + startStudentName + " not found.");
                return;
            }

            System.out.print("Enter the target company for the referral path search: ");
            String targetCompany = scanner.nextLine();

            System.out.println("\nFinding referral path...");
            List<UniversityStudent> referralPath = pathFinder.findReferralPath(startStudent, targetCompany);

            if (referralPath.isEmpty()) {
                System.out.println("No referral path found to a student with an internship at " + targetCompany);
            } else {
                int weight = pathFinder.calculatePathWeight(referralPath);
                System.out.print("Referral path to " + targetCompany + ": ");
                for (UniversityStudent student : referralPath) {
                    System.out.print(student.getName() + " -> ");
                }
                System.out.println("\nFound referral path starting at student " + startStudent.getName() + " with weight: " + weight);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
