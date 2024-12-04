package src;

import java.io.*;
import java.util.*;

/**
 * The DataParser class reads student data from the input file and returns a list of UniversityStudent objects
 */
public class DataParser {

    /**
     * Reads a student data file and creates a list of UniversityStudent objects.
     *
     * @param inputFile The name of the input file with the student data
     * @return A list of UniversityStudent objects created from the file's data
     * @throws IOException if there is an issue with reading the file
     */
    public static List<UniversityStudent> parseStudents(String inputFile) throws IOException {
        List<UniversityStudent> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            UniversityStudent currentStudent = null;

            // read file
            while ((line = reader.readLine()) != null) { // skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // students
                if (line.startsWith("Student:")) {
                    if (currentStudent != null) {
                        students.add(currentStudent); // add student to the list
                       // System.out.println(currentStudent);
                    }
                    // new student object
                    currentStudent = new UniversityStudent();
                } else if (currentStudent != null) {
                    // System.out.println(currentStudent);
                    String[] parts = line.split(":"); // add their attributes, split around the : symbol
                    if (parts.length != 2) continue;
                    String attribute = parts[0].trim();
                    String value = parts[1].trim();

                    // switch case to manage the different possible attributes in the file
                    switch (attribute) {
                        case "Name":
                            currentStudent.setName(value);
                            break;
                        case "Age":
                            currentStudent.setAge(Integer.parseInt(value));
                            break;
                        case "Gender":
                            currentStudent.setGender(value);
                            break;
                        case "Year":
                            currentStudent.setYear(Integer.parseInt(value));
                            break;
                        case "Major":
                            currentStudent.setMajor(value);
                            break;
                        case "GPA":
                            currentStudent.setGpa(Double.parseDouble(value));
                            break;
                        case "RoommatePreferences":
                            currentStudent.setRoommatePreferences(parseList(value));
                            break;
                        case "PreviousInternships":
                            currentStudent.setPreviousInternships(parseList(value));
                            break;
                        default:
                            System.out.println("Unknown attribute: " + attribute);
                    }
                }
            }

            // add the last student
            if (currentStudent != null) {
                students.add(currentStudent);
            }
        }

        return students;
    }

    /**
     * Helper method to parse comma-separated values into a single list
     *
     * @param value The comma-separated string
     * @return A List of strings
     */
    private static List<String> parseList(String value) {
        if (value.isEmpty()) {
            return new ArrayList<>();
        }
        String[] items = value.split(",");
        List<String> list = new ArrayList<>();
        for (String item : items) {
            list.add(item.trim());
        }
        return list;
    }
}
