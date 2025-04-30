package vote;

import java.rmi.*;
import java.rmi.registry.*;

/**
 * The main class for the Vote Server.
 */
public class VoteServer {

    /**
     * The main method to start the Vote Server.
     * @param args Command-line arguments.
     */
    public static void main(String args[]) {
        String portNum = "1234";
        String registryURL;
        try {
            startRegistry(1234);
            VoteServerImpl exportedObj = new VoteServerImpl();
            registryURL = "rmi://localhost:" + portNum + "/vote";
            Naming.rebind(registryURL, exportedObj);
            System.out.println("Vote Server ready.");
        } catch (Exception re) {
            System.out.println("Exception in VoteServer.main: " + re);
        }
    }

    /**
     * Starts a RMI registry on the local host, if it does not already exist at the specified port number.
     * @param RMIPortNum The RMI port number.
     */
    private static void startRegistry(int RMIPortNum) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list();
        } catch (RemoteException ex) {
            System.out.println("RMI registry cannot be located at port " + RMIPortNum);
            Registry registry = LocateRegistry.createRegistry(RMIPortNum);
            System.out.println("RMI registry created at port " + RMIPortNum);
        }
    }
}
