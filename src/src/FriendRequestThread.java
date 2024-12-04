package src;

/**
 * The FriendRequestThread class simulates a thread for sending a friend request from one student to another
 * This class implements Runnable so the friend request process can happen at the same time as other processes
 */

public class FriendRequestThread implements Runnable {
    private final UniversityStudent sender;   // The student sending the friend request
    private final UniversityStudent receiver; // The student receiving the friend request

    /**
     * Constructor to create a new friend request thread.
     *
     * @param sender The student sending the friend request.
     * @param receiver The student receiving the friend request.
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Where the friend request process happens when the thread starts
     */
    @Override
    public void run() {
        // sending a friend request
        try {
            System.out.println(sender.getName() + " sent a friend request to " + receiver.getName());

            Thread.sleep(2000); // 2 second delay


            boolean acceptedFriendRequest = receiver.receiveFriendRequest(sender);

            if (acceptedFriendRequest) {
                System.out.println(receiver.getName() + " accepted the friend request from " + sender.getName());
            } else {
                System.out.println(receiver.getName() + " declined the friend request from " + sender.getName());
            }

        } catch (InterruptedException e) {
            System.out.println("Friend request process interrupted for " + sender.getName() + " and " + receiver.getName());
        }
    }
}
