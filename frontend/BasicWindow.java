import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import src.*;

public class BasicWindow extends JFrame {
    private JTextField inputField;
    private JButton findConnectionsButton;
    private JButton chatButton;
    private JButton visualizeGraphButton;
    private JTextArea outputArea;
    private ExecutorService executor;

    private StudentGraph graph; // Reference to the graph for visualization

    public BasicWindow() {
        setTitle("Longhorn Network");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        

        Color buttonColor = Color.decode("#f8971f");
        Color backgroundColor = Color.decode("#f2b480");
        Color panelColor = Color.decode("#bf5700");


        inputField = new JTextField(30);
        inputField.setFont(new Font("Arial", Font.BOLD, 14));


        findConnectionsButton = new JButton("Find Connections");
        findConnectionsButton.setBackground(buttonColor);
        findConnectionsButton.setFont(new Font("Arial", Font.BOLD, 12));


        chatButton = new JButton("Chat");
        chatButton.setBackground(buttonColor);
        chatButton.setFont(new Font("Arial", Font.BOLD, 12));

        visualizeGraphButton = new JButton("Adjacency Matrix");
        visualizeGraphButton.setBackground(buttonColor);
        visualizeGraphButton.setFont(new Font("Arial", Font.BOLD, 12));


        outputArea = new JTextArea();
        outputArea.setBackground(backgroundColor);
        outputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        //outputArea.setBounds(0, 200, 1000, 90);
        outputArea.setEditable(false);

        //LONGHORN NETWORK LABEL
        JPanel textArea = new JPanel();
        textArea.setPreferredSize(new Dimension(1000, 50));
        textArea.setBackground(panelColor);
        JLabel label = new JLabel("Longhorn Network");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        // label.setPreferredSize(new Dimension(1000, 70));
        textArea.add(label);

        // layout
        setLayout(new BorderLayout());

        
        //topPanel.setLayout(null);
        JPanel buttonPanel = new JPanel();
        // buttonPanel.setBounds(0, 300, 1000, 300);
        // buttonPanel.setSize(50, 50);
        buttonPanel.setPreferredSize(new Dimension(1000, 70));
        buttonPanel.setBackground(panelColor);
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(inputField);
        buttonPanel.add(chatButton);
        buttonPanel.add(findConnectionsButton);
        buttonPanel.add(visualizeGraphButton);


        // add components to the window
        //add(topPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(outputArea, BorderLayout.CENTER);
        add(textArea, BorderLayout.NORTH);



        // initialize executor ***********************
        executor = Executors.newFixedThreadPool(2);



        // action listeners
        findConnectionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                String result = findConnections(userInput);
                outputArea.setText(result);
            }
        });

        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                if (!message.isEmpty()) {
                    executor.submit(() -> sendMessage(message));
                    inputField.setText("");
                }
            }
        });

        visualizeGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (graph != null) {
                    String visualization = visualizeGraphAsAdjacencyList();
                    outputArea.setText(visualization);
                } else {
                    outputArea.setText("Please find connections first. Graph needs to be initialized. ");
                }
            }
        });
    }

    private void sendMessage(String message) {
        appendToOutput("You: " + message);
        String response = getRandomResponse();
        appendToOutput("Student: " + response);
    }

    private void appendToOutput(String text) {
        SwingUtilities.invokeLater(() -> outputArea.append(text + "\n"));
    }

    private String getRandomResponse() {
        String[] responses = {
                "Yes, definitely.",
                "As I see it, yes.",
                "Reply hazy, try again.",
                "Cannot predict now.",
                "Do not count on it.",
                "My sources say no.",
                "Outlook not so good.",
                "Very doubtful."
        };
        return responses[(int) (Math.random() * responses.length)];
    }




    private String findConnections(String input) {
        try {
            // Parse students from input (assuming input is a file path or data in string format)
            List<UniversityStudent> students = DataParser.parseStudents(input);

            // Use GaleShapley to assign roommates
            GaleShapley.assignRoommates(students);

            // Create the StudentGraph and PodFormation
            graph = new StudentGraph(students);
            PodFormation podFormation = new PodFormation(graph);
            podFormation.formPods(4);

            // Get the pods formed by PodFormation
            List<List<UniversityStudent>> pods = podFormation.getPods();



            String result = "";

            result += "Roommate Assignments: \n";

            for (UniversityStudent student : students) {
                if (student.getRoommate() != null) {
                    result += (student.getName() + " is roommates with " + student.getRoommate().getName());
                    result += "\n";
                }
            }

            result += "\nPod Assignments: \n";
            int podNumber = 1;
            for (List<UniversityStudent> pod : pods) {
                result += "Pod " + podNumber++ + ": ";
                for (UniversityStudent student : pod) {
                    result += student.getName() + ", ";
                }
                result = result.substring(0, result.length() - 2); // Remove last comma and space
                result += "\n";
            }
            return result + "\n Connections established successfully! Graph is ready for visualization.";

        } catch (Exception e) {
            return "Error finding connections: " + e.getMessage();
        }
    }



    // visualize the graph as an adjacency list
    private String visualizeGraphAsAdjacencyList() {
        StringBuilder sb = new StringBuilder();
        for (UniversityStudent student : graph.getStudents()) {
            sb.append(student.getName()).append(": ");
            List<UniversityStudent> neighbors = graph.getConnections(student);
            for (UniversityStudent neighbor : neighbors) {
                int strength = graph.getConnectionStrength(student, neighbor);
                sb.append(neighbor.getName())
                        .append(" (strength: ")
                        .append(strength)
                        .append("), ");
            }
            if (!neighbors.isEmpty()) {
                sb.setLength(sb.length() - 2); //remove last comma and space
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BasicWindow().setVisible(true));
    }
}
