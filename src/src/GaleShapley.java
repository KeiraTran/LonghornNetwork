package src;

import java.util.*;

/**
 * The GaleShapley class implements the Gale-Shapley algorithm to assign roommates to students based on their preferences
 * Pairs students in a way that results in the most stable matching
 */
public class GaleShapley {

    /**
     * Assigns roommates to students using the Gale-Shapley algorithm
     *
     * @param students A list of UniversityStudent objects representing the students to be paired.
     *                 Each student has a preference list for possible roommates
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        // queue of students who haven't been assigned a roommate
        Queue<UniversityStudent> freeStudents = new LinkedList<>(students);

        // map of each student's preference queue with their potential roommates
        Map<UniversityStudent, Queue<UniversityStudent>> preferences = new HashMap<>();


        for (UniversityStudent student : students) {
            Queue<UniversityStudent> preferenceQueue = new LinkedList<>();
            List<String> roommatePreferences = student.getRoommatePreferences();

            if (roommatePreferences != null) {
                for (String preferredName : roommatePreferences) {
                    for (UniversityStudent potentialRoommate : students) {
                        if (potentialRoommate.getName().equals(preferredName)) {
                            preferenceQueue.add(potentialRoommate);
                        }
                    }
                }
            }
            preferences.put(student, preferenceQueue);
        }

        // find stable roommate assignments via gs
        while (!freeStudents.isEmpty()) {
            UniversityStudent proposer = freeStudents.poll(); // Get the next free student.

            // if the proposer has no more preferences, skip
            Queue<UniversityStudent> proposerPreferences = preferences.get(proposer);
            if (proposerPreferences == null || proposerPreferences.isEmpty()) {
                continue;
            }

            // propose to the next preferred roommate
            UniversityStudent preferredRoommate = proposerPreferences.poll();
            UniversityStudent currentRoommate = preferredRoommate.getRoommate();

            if (currentRoommate == null) {

                // If the preferred roommate is free, they accept (proposer optimal)
                proposer.setRoommate(preferredRoommate);
                preferredRoommate.setRoommate(proposer);
            }

            else if (preferredRoommate.prefers(proposer, currentRoommate)) {
                // if the preferred roommate prefers the new proposer over their current roommate,
                // Break the current match and assign the proposer as the new roommate
                freeStudents.add(currentRoommate); // roommate goes back into the pool of free students
                proposer.setRoommate(preferredRoommate);
                preferredRoommate.setRoommate(proposer);
            }

            else {
                // if the preferred roommate rejects the proposer (already with a more stable matching),
                // add the proposer back to free students
                freeStudents.add(proposer);
            }
        }
    }
}
