package vote;

import java.rmi.*;

/**
 * Interface for the Vote Client.
 */
public interface VoteClientInterface extends Remote {
    
    /**
     * Notifies the client with a message.
     * 
     * @param message The message to notify the client with.
     * @return The received message.
     */
    String notifyMe(String message) throws RemoteException;
    
    /**
     * Retrieves the received message.
     * @return The received message.
     */
    String getMessage() throws RemoteException;
    
    /**
     * Checks if a notification has been received.
     * @return True if a notification has been received, otherwise false.
     */
    boolean isNotificationReceived() throws RemoteException;
}