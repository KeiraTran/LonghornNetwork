package src;
//import ..frontend.*;
/**
 * The ChatThread class implements Runnable to simulate a chat message exchange between two students
 * Single thread of execution for sending a message from one student (sender) to another (receiver)
 */
public class ChatThread implements Runnable {
    private UniversityStudent sender;   // The student sending the message
    private UniversityStudent receiver; // The student receiving the message
    private String message;             // The message being sent

    // Constructor
    /**
     * Constructor to create a new chat thread.
     *
     * @param sender The student who is sending the message.
     * @param receiver The student who will receive the message
     * @param message The message being sent
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * Runs the chat message exchange when the thread starts
     * Simulates sending a message from sender to receiver
     */
    @Override
    public void run() {
        try {
            // sender types message
            System.out.println(sender.getName() + " is typing a message...");

            // fake delay in message sending (e.g., typing time or network delay)
            Thread.sleep(1000);  // 1 second delay

            // message sends
            System.out.println(sender.getName() + " sent to " + receiver.getName() + ": " + message);

            // delay for reading
            Thread.sleep(1000);

            // receiver responds
            String response = receiver.receiveMessage(sender, message);
            System.out.println(receiver.getName() + " responded: " + response);
        } catch (InterruptedException e) {
            System.out.println("The chat process was interrupted.");
        }
    }
}
