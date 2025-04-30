package vote;

import java.rmi.*;
import java.rmi.server.*;
import java.util.Vector;

/**
 * Implementation class for the Vote Server.
 */
public class VoteServerImpl extends UnicastRemoteObject implements VoteServerInterface {
    private int yesCount, noCount, dontCareCount;
    private Vector<VoteClientInterface> callbackObjects;

    /**
     * Constructor for the VoteServerImpl class.
     */
    public VoteServerImpl() throws RemoteException {
        super();
        callbackObjects = new Vector<>();
    }

    /**
     * Sends a vote to the server and updates the count.
     * @param vote The vote to be sent.
     * @return A message returning vote success.
     */
    public synchronized String sendVote(int vote) throws RemoteException {
        switch (vote) {
            case 0:
                yesCount++;
                break;
            case 1:
                noCount++;
                break;
            case 2:
                dontCareCount++;
                break;
            default:
                return "Invalid vote!";
        }
        if (yesCount >= 2)
            doCallbacks();
        return "Vote received successfully!";
    }

    /**
     * Retrieves the current vote counts.
     * @return Current vote counts as a string.
     */
    public synchronized String getVoteCounts() throws RemoteException {
        return "Yes: " + yesCount + ", No: " + noCount + ", Don't Care: " + dontCareCount;
    }

    /**
     * Registers a callback object for receiving notifications.
     * @param voteClientObject The callback object to register.
     */
    public synchronized void registerCallback(VoteClientInterface voteClientObject) throws RemoteException {
        if (!callbackObjects.contains(voteClientObject))
            callbackObjects.addElement(voteClientObject);
        System.out.println("Registered callback object: " + voteClientObject);
    }

    /**
     * Unregisters a callback object.
     * @param voteClientObject The callback object to unregister.
     */
    public synchronized void unregisterCallback(VoteClientInterface voteClientObject) throws RemoteException {
        if (callbackObjects.removeElement(voteClientObject)) {
            System.out.println("Unregistered callback object: " + voteClientObject);
        } else {
            System.out.println("Unregister callback error - client wasn't registered: " + voteClientObject);
        }
    }

    /**
     * Retrieves a list of callback objects registered with the server.
     * @return A vector containing the registered callback objects.
     */
    public Vector<VoteClientInterface> getCallbackObjects() throws RemoteException {
        return callbackObjects;
    }

    /**
     * Initiates callbacks to all registered clients.
     */
    private synchronized void doCallbacks() throws RemoteException {
        System.out.println("**************************************\n" +
                "Server initiating callbacks ---");
        for (int i = 0; i < callbackObjects.size(); i++) {
            System.out.println("doing " + i + "-th callback\n");
            VoteClientInterface callbackClient = (VoteClientInterface)callbackObjects.elementAt(i);
            try {
                callbackClient.notifyMe("Number of yes votes = " + yesCount);
            } catch (RemoteException re) {
                System.out.println("Exception in VoteServer callback: " + re);
            }
        }
        System.out.println("**************************************\n" +
                "Server completed callbacks ---");
    }
}
