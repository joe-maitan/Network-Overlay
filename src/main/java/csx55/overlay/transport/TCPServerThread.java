package csx55.overlay.transport;

import java.net.*;
import java.io.*;
import java.util.*;

import csx55.overlay.node.*;

public class TCPServerThread implements Runnable {

    private static ServerSocket serverSocket = null;
    private static TCPSender send = null;
    private static TCPReceiverThread read = null;
    public volatile boolean done = false;

    /* THESE TWO STRINGS WILL BE USED TO HELP FILL OUT MESSAGENODE DATA */
    private String hostName;
    private int portNumber; /* DO NOT DELETE THIS. THIS IS THE PORT OUR NODES SERVER WILL LAUNCH ON */
    
    private Node instanceOfNode; /* tells us whether it is a Registry node or a MessagingNode */

    private ArrayList<Socket> socket_connetions = new ArrayList<>();
    private ArrayList<TCPSender> senders = new ArrayList<>();
    private ArrayList<TCPReceiverThread> readers = new ArrayList<>();

    public String getHostName() { return this.hostName; }

    public int getPortNumber() { return this.portNumber; }

    public ArrayList<Socket> getPeerSockets() {
        return this.socket_connetions;
    } // End getPeerSockets() method

    public ArrayList<TCPSender> getSenders() {
        return this.senders;
    } // End getSenders() method

    public ArrayList<TCPReceiverThread> getReaders() {
        return this.readers;
    } // End getReaders() method

    public TCPServerThread(Node node) {
        this.serverThreadNode = node;
        boolean connectionNotEstablished = false;

            port = 1024; /* start at 1024 */

            while (!connectionNotEstablished && port < 65536) {
                try {
                    serverSocket = new ServerSocket(port);
                    connectionNotEstablished = !connectionNotEstablished;
                } catch (IOException err) {
                    ++port;
                } // End try-catch block
            } // End while loop
    } // End TCPServerThread() default constructor

    public TCPServerThread(Node node, final int PORT_NUM) {
        serverThreadNode = node;
        
        if (PORT_NUM > 1024 && PORT_NUM < 65536) { /* given a valid port number create the ServerSocket */
            try {
                port_number = PORT_NUM;
                serverSocket = new ServerSocket(port_number);
            } catch (IOException err) {
                System.out.println(err.getMessage());
            } // End try-catch block
        } // End if statement 
    } // End TCPServerThread(port) constructor

    public void run() {
        if (serverSocket != null) {
            while (!done) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    
                    String clientIPAddress = clientSocket.getInetAddress().toString();
                    clientIPAddress = clientIPAddress.substring(clientIPAddress.indexOf('/') + 1);
                    // System.out.println("[TCPServerThread]: " + clientIPAddress + " has connected at port: " + clientSocket.getPort()); /* validation that something that has connected to the registry */
                    
                    add_socket(clientSocket);
                } catch (IOException err) {
                    System.out.println(err.getMessage());
                } // End try-catch block
            } // End while loop
            
        } // End if statment
    } // End run() method

    public void close_server() {
        done = true;
        
        try {
            serverSocket.close();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End close_server() method

    public void send_msg(int toIndex, byte[] arr) {
        try {
            getSenders().get(toIndex).sendData(arr);
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }
    } // End send_msg

    public int get_socket_index(Socket search) {
        return getPeerSockets().indexOf(search);
    } // End get_socket_index() method

    public void add_socket(Socket s) {
        if (!getPeerSockets().contains(s)) {
            getPeerSockets().add(s); /* Add the socket to the ArrayList containing them */

            try {
                send = new TCPSender(s, socket_connetions.indexOf(s), serverThreadNode); 
                read = new TCPReceiverThread(s, socket_connetions.indexOf(s), serverThreadNode);
                
                getSenders().add(send);
                getReaders().add(read);

                Thread server_read_thread = new Thread(read); 
            
                /* starts the TCPReceiver thread and begins to actively look for information to read */
                server_read_thread.start(); 
            } catch (IOException err) {
                System.err.println(err.getMessage());
            } // End try-catch block
        }            
    } // End add_socket() method
    
} // End TCPServerThread class
