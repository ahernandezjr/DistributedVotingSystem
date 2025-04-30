package tests;

import vote.*;

import java.rmi.Naming;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for VoteClient functionality.
 */
public class VoteClientTest {
    private VoteServerInterface voteServer;

    /**
     * Set up the VoteServer and lookup the VoteServer before each test.
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
     * Test method for VoteClient registration and unregistration.
     */
    @Test
    public void testVoteClientRegistrationAndUnregistration() throws Exception {
        VoteClientInterface voteClient = new VoteClientImpl();
        voteServer.registerCallback(voteClient);
        System.out.println(voteServer.getCallbackObjects());
        assertTrue(voteServer.getCallbackObjects().contains(voteClient), "VoteClient registration failed");

        voteServer.unregisterCallback(voteClient);
        assertTrue(!voteServer.getCallbackObjects().contains(voteClient), "VoteClient unregistration failed");
    }
}
