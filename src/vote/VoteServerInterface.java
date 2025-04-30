package vote;

import java.rmi.*;
import java.util.Vector;

/**
 * Interface for the Vote Server.
 */
public interface VoteServerInterface extends Remote {
    /**
     * Sends a vote to the server.
     * @param vote The vote to send.
     * @return A message returning vote success.
     */
    String sendVote(int vote) throws RemoteException;

    /**
     * Retrieves the current vote counts.
     * @return A string containing the current vote counts.
     */
    String getVoteCounts() throws RemoteException;

    /**
     * Registers a callback object with the server.
     * @param voteClientObject The callback object to register.
     */
    void registerCallback(VoteClientInterface voteClientObject) throws RemoteException;

    /**
     * Unregisters a callback object from the server.
     * @param voteClientObject The callback object to unregister.
     */
    void unregisterCallback(VoteClientInterface voteClientObject) throws RemoteException;

    /**
     * Retrieves a vector containing all registered callback objects.
     * @return A vector containing all registered callback objects.
     */
    Vector<VoteClientInterface> getCallbackObjects() throws RemoteException;
}
