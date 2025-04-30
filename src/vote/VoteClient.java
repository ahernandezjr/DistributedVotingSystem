package vote;

import java.rmi.*;
import java.util.Scanner;

/**
 * Client for the Vote system.
 */
public class VoteClient {
    static VoteServerInterface h;
    static VoteClientInterface callbackObj;

    /**
     * Main method to run the VoteClient.
     * @param args Given more than 2 arguments, the first argument is the host and the second is the port.
     */
    public static void main(String args[]) {
        try (Scanner scanner = new Scanner(System.in)) {
            String hostName;
            String portNum;
            int time = 20 * 1000;

            // Check if command-line arguments are provided
            if (args.length >= 2) {
                hostName = args[0];
                portNum  = args[1];
            } else {
                // Prompt the user for host and port
                System.out.print("Enter host (default is localhost): ");
                hostName = scanner.nextLine().trim();
                if (hostName.isEmpty()) {
                    hostName = "localhost"; // Set default value
                }

                System.out.print("Enter port (default is 1234): ");
                portNum = scanner.nextLine().trim();
                if (portNum.isEmpty()) {
                    portNum = "1234"; // Set default value
                }
            }

            // Look up the remote object
            String registryURL = String.format("rmi://" + hostName + ":" + portNum + "/vote");
            h = (VoteServerInterface) Naming.lookup(registryURL);
            callbackObj = new VoteClientImpl();

            // Prompt user for vote
            int voteValue = getUserInput(scanner,
                    "Cast your vote by entering the corresponding number:\n1. Yes\n2. No\n3. Don't Know",
                    1, 3);
            String message = h.sendVote(voteValue - 1);
            System.out.println(message);

            while(true){
                // Prompt user for vote counts, to register for callback, or to quit
                int choice = getUserInput(scanner,
                        "Do you want to:\n1. See vote counts\n2. Register for callback and wait\n3. Quit", 1, 3);
                // Perform registration tasks
                if (choice == 1) {
                    String voteCounts = h.getVoteCounts();
                    System.out.println("Vote counts:");
                    System.out.println(voteCounts + "\n----------");
                } else if (choice == 2) {
                    // Register for callback
                    h.registerCallback(callbackObj);

                    System.out.println("Registered for callback. Waiting for notification...");

                    // Wait for notification or timeout
                    long startTime = System.currentTimeMillis();
                    while (System.currentTimeMillis() - startTime < time) {
                        // Check if notification received
                        if (callbackObj.isNotificationReceived()) {
                            // Retrieve and print the message
                            message = callbackObj.getMessage();
                            System.out.println(message);
                            h.unregisterCallback(callbackObj);
                            break; // Exit loop if notification received
                        }
                    }

                    h.unregisterCallback(callbackObj);
                    System.out.println("Unregistered for callback.");
                } else if (choice == 3) {
                    h.unregisterCallback(callbackObj);
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            } finally {
            if (h != null && callbackObj != null) {
                try {
                    h.unregisterCallback(callbackObj);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Retrieves user input within a specified range.
     * @param scanner Scanner object for input.
     * @param prompt The prompt to display to the user.
     * @param min The minimum value allowed.
     * @param max The maximum value allowed.
     * @return The user's input value.
     */
    private static int getUserInput(Scanner scanner, String prompt, int min, int max) {
        int input = 0;
        boolean isValid = false;
        while (!isValid) {
            System.out.println(prompt);
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input >= min && input <= max) {
                    isValid = true;
                } else {
                    System.out.println(
                            "\nError: Input out of range! Please enter a number between " + min + " and " + max + "\n");
                }
            } else {
                System.out.println("\nError: Invalid input! Please enter a valid integer.\n");
                scanner.next();
            }
        }
        return input;
    }

    /**
     * Prints a callback message to the console.
     * @param yesCount The count of "Yes" votes.
     */
    public void printCallbackMessage(int yesCount) {
        System.out.println("Notification: Yes count has reached " + yesCount); // Print on client side
    }
}
