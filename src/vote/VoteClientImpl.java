package vote;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Client implementation for the Vote system.
 */
public class VoteClientImpl implements VoteClientInterface, Serializable {
    private String receivedMessage;         // Store the received message
    private boolean notificationReceived;   // Flag to indicate notification

    /**
     * Constructs a new VoteClientImpl object.
     */
    public VoteClientImpl() throws RemoteException {
        super( );
        notificationReceived = false;
    }

    /**
     * Notifies the client with a message.
     * @param message The message to notify the client with.
     * @return The received message.
     */
    public String notifyMe(String message) throws RemoteException {
        synchronized (this) {
            receivedMessage = "Received Callback message: " + message;
            System.out.println("Sending message - " + receivedMessage);
            notificationReceived = true;
        }
        return receivedMessage;
    }

    /**
     * Retrieves the received message.
     * @return The received message.
     */
    public String getMessage() throws RemoteException {
        return receivedMessage;
    }

    /**
     * Checks if a notification has been received.
     * @return True or False if a notification has been received.
     */
    public boolean isNotificationReceived() throws RemoteException {
        return notificationReceived;
    }
}
