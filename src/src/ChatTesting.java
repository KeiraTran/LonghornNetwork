package src;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
// import frontend.BasicWindow;

//import src.UniversityStudent;
//import src.FriendRequestThread;
//import src.ChatThread;

public class ChatTesting {
    public static void main(String[] args) {
        // UniversityStudent instances
        UniversityStudent alice = new UniversityStudent("Alice", 20, "Computer Science", 2, "alice@example.com", 3.8, new ArrayList<>(), new ArrayList<>());
        UniversityStudent bob = new UniversityStudent("Bob", 21, "Mechanical Engineering", 3, "bob@example.com", 3.5, new ArrayList<>(), new ArrayList<>());
        UniversityStudent charlie = new UniversityStudent("Charlie", 22, "Electrical Engineering", 4, "charlie@example.com", 3.9, new ArrayList<>(), new ArrayList<>());

        // ExecutorService manages the threads (in this case, 3)
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // sending friend requests
        executor.submit(new FriendRequestThread(alice, bob));
        executor.submit(new FriendRequestThread(bob, charlie));

        // chatting
        executor.submit(new ChatThread(alice, bob, "Hello Bob!"));
        executor.submit(new ChatThread(bob, alice, "Hi Alice!"));

        executor.shutdown();
    }
}
