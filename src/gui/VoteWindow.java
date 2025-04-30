package gui;

import vote.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.*;

/**
 * Main voting window for the Vote client.
 */
public class VoteWindow extends JFrame {
    private JButton voteButton;
    private JButton registerButton;
    private JButton getCountsButton;
    private JTextArea messageArea;

    private VoteServerInterface voteServer;
    private VoteClientInterface callbackObj;

    /**
     * Constructs a new VoteWindow object.
     * @param voteServer The Vote server interface.
     * @param callbackObj The callback object for the client.
     */
    public VoteWindow(VoteServerInterface voteServer, VoteClientInterface callbackObj) {
        setTitle("Vote");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLayout(new GridLayout(4, 1));

        this.voteServer  = voteServer;
        this.callbackObj = callbackObj;

        voteButton      = new JButton("Vote");
        registerButton  = new JButton("Register for Callback");
        getCountsButton = new JButton("Get Vote Counts");
        messageArea = new JTextArea();
        messageArea.setEditable(false);

        add(voteButton);
        add(registerButton);
        add(getCountsButton);
        add(new JScrollPane(messageArea));

        voteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vote();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerForCallback();
            }
        });
        
        getCountsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getVoteCounts(); // Call method to get vote counts
            }
        });
    }

    /**
     * Handles the voting process.
     */
    private void vote() {
        int voteValue = JOptionPane.showOptionDialog(null,
                "Cast your vote by selecting an option:", "Vote", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new String[] { "Yes", "No", "Don't Know" }, 0);
        if (voteServer != null) {
            try {
                String message = voteServer.sendVote(voteValue);
                messageArea.setText(message);
            } catch (Exception ex) {
                ex.printStackTrace();
                messageArea.setText("Error: Failed to send vote");
            }
        } else {
            messageArea.setText("Error: Vote server is not initialized.");
        }
    }

    /**
     * Registers the client for receiving callback notifications.
     */
    private void registerForCallback() {
        if (voteServer != null && callbackObj != null) {
            try {
                voteServer.registerCallback(callbackObj);
                messageArea.setText("Registered for callback. Waiting for notification...");
            } catch (Exception ex) {
                ex.printStackTrace();
                messageArea.setText("Error: Failed to register for callback");
            }
        } else {
            messageArea.setText("Error: Vote server or callback object is not initialized.");
        }
    }

    /**
     * Retrieves the current vote counts from the server.
     */
    private void getVoteCounts() {
        if (voteServer != null) {
            try {
                String counts = voteServer.getVoteCounts();
                messageArea.setText("Vote Counts:\n" + counts);
            } catch (Exception ex) {
                ex.printStackTrace();
                messageArea.setText("Error: Failed to retrieve vote counts");
            }
        } else {
            messageArea.setText("Error: Vote server is not initialized.");
        }
    }

    /**
     * Performs cleanup before closing the window.
     */
    @Override
    public void dispose() {
        if (voteServer != null && callbackObj != null) {
            try {
                voteServer.unregisterCallback(callbackObj);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
        super.dispose();
    }
}
