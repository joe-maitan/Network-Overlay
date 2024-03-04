package csx55.overlay.node;

import java.io.*;
import java.net.*;
import csx55.overlay.transport.*;
import csx55.overlay.wireformats.*;

public class Node { 

    private String hostName;
    private int portNumber;
    
    private TCPServerThread nodeServerThread;
    private Thread serverThread;

    private TCPReceiverThread nodeRecieverThread;
    private Thread recieverThread;

    private TCPSender sender;

    public String getHostName() {
        return this.hostName;
    } // End getHostName() method
    
    public void setHostName(String machineName) {
        this.hostName = machineName;
    } // End setHostName() method

    public int getPortNumber() {
        return this.portNumber
    } // End getPortNumber() method

    public void setPortNumber(int port) {
        this.portNumber = port;
    } // End setPortNumber() method

    public TCPServerThread getNodeServerThread() {
        return this.nodeServerThread;
    } // End getNodeServerThread() method

    public TCPReceiverThread getNodeRecieverThread() {
        return this.nodeRecieverThread;
    } // End getNodeRecieverThread() method

    public TCPSender getNodeTCPSender() {
        return this.sender;
    } // End getNodeTCPSender() method

    public Node() {
        this.nodeServerThread = new TCPServerThread(this);
        this.serverThread = new Thread(nodeServerThread);
        this.hostName = nodeServerThread.getHostName();
        this.portNumber = nodeServerThread.getPortNumber();
    }

    public Node(final int PORT_NUMBER) {
        this.nodeServerThread = new TCPServerThread(this, PORT_NUMBER);
        this.serverThread = new Thread(nodeServerThread);
        this.hostName = nodeServerThread.getHostName();
        this.portNumber = nodeServerThread.getPortNumber();
    }

    /* Abstract because both Nodes will handle events differently */
    public abstract void onEvent(Event event, int socketIndex);
    
    public void send_message(int socketIndex, byte[] arr, String message) {
        this.node_send = this.node_server.senders.get(socket_index); /* constructs our TCPSender obj */
            
        try {
            this.node_send.sendData(arr);
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End send_message() method

    public void addSocket(Socket s) {
        nodeServerThread.add_socket(s);
    } // End add_socket() method
    
} // End Node class
