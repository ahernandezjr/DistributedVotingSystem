package gui;

import vote.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;

/**
 * Graphical user interface (GUI) for the Vote client.
 */
public class VoteGUI extends JFrame {
    private JTextField hostField, portField;
    private JButton connectButton;

    /**
     * Constructs a new VoteGUI object.
     */
    public VoteGUI() {
        setTitle("Vote Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new GridLayout(4, 1));

        // Set default text for host field
        hostField = new JTextField("localhost");
        hostField.setBorder(BorderFactory.createTitledBorder("Host"));
        
        // Set default text for port field
        portField = new JTextField("1234");
        portField.setBorder(BorderFactory.createTitledBorder("Port"));
        
        connectButton = new JButton("Connect");

        add(hostField);
        add(portField);
        add(connectButton);

        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String host = hostField.getText();
                String port = portField.getText();
                connectToServer(host, port);
            }
        });

        setVisible(true);
    }

    /**
     * Connects to the Vote server using the specified host and port.
     * @param host The host address.
     * @param port The port number.
     */
    private void connectToServer(String host, String port) {
        try {
            String registryURL = String.format("rmi://" + host + ":" + port + "/vote", host, port);
            VoteServerInterface voteServer = (VoteServerInterface) Naming.lookup(registryURL);
            VoteClientInterface callbackObj = new VoteClientImpl();

            openVoteWindow(voteServer, callbackObj);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Opens the main Vote window.
     * @param voteServer The Vote server interface.
     * @param callbackObj The callback object for the client.
     */
    private void openVoteWindow(VoteServerInterface voteServer, VoteClientInterface callbackObj) {
        // Create a new JFrame for voting and registration
        VoteWindow voteWindow = new VoteWindow(voteServer, callbackObj);
        voteWindow.setVisible(true);
    }

    /**
     * Main method to run the VoteGUI application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new VoteGUI();
            }
        });
    }
}
