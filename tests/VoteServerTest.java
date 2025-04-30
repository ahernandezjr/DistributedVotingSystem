package tests;

import vote.*;

import java.rmi.Naming;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for VoteServer functionality.
 */
public class VoteServerTest {
    private VoteServerInterface voteServer;

    /**
     * Set up method to start the VoteServer and lookup the VoteServer before each test.
     */
    @BeforeEach
    public void setUp() throws Exception {
        // Start the VoteServer
        VoteServer.main(new String[]{});
        // Lookup the VoteServer
        String registryURL = String.format("rmi://localhost:1234/vote");
        voteServer = (VoteServerInterface) Naming.lookup(registryURL);
    }

    /**
     * Test method for client registration and unregistration.
     */
    @Test
    public void testRegistrationAndUnregistration() throws Exception {
        VoteClientInterface client1 = new VoteClientImpl();
        VoteClientInterface client2 = new VoteClientImpl();

        // Register client1 and client2
        voteServer.registerCallback(client1);
        assertTrue(voteServer.getCallbackObjects().contains(client1), "Client1 registration failed");
        voteServer.registerCallback(client2);
        assertTrue(voteServer.getCallbackObjects().contains(client2), "Client2 registration failed");

        // Check that both clients are registered
        assertEquals(2, voteServer.getCallbackObjects().size(), "Incorrect number of registered clients");

        // Unregister client1
        voteServer.unregisterCallback(client1);
        assertFalse(voteServer.getCallbackObjects().contains(client1), "Client1 unregistration failed");

        // Check for client2 remaining
        assertEquals(1, voteServer.getCallbackObjects().size(), "Incorrect number of registered clients after unregistration");
        assertTrue(voteServer.getCallbackObjects().contains(client2), "Client2 should still be registered");
    }
}
