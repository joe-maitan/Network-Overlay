package csx55.overlay.transport;

// import csx55.overlay.node.*;
// import csx55.overlay.wireformats.RegisterRequest;

import java.net.*;
import java.io.*;
import java.util.*;

import csx55.overlay.node.*;

public class TCPServerThread implements Runnable {

    private static ServerSocket server = null;
    private static TCPSender send = null;
    private static TCPReceiverThread read = null;
    public volatile boolean done = false;

    /* THESE TWO STRINGS WILL BE USED TO HELP FILL OUT MESSAGENODE DATA */
    public int port_number; /* DO NOT DELETE THIS. THIS IS THE PORT OUR SERVER WILL LAUNCH ON */
    public String ipAddress;
    public Node serverThreadNode;

    public ArrayList<Socket> socket_connetions = new ArrayList<>();
    public ArrayList<TCPSender> senders = new ArrayList<>();
    public ArrayList<TCPReceiverThread> readers = new ArrayList<>();

    public TCPServerThread(Node node) {
        serverThreadNode = node;
        boolean not_set = false;

            port_number = 1024; /* start at 1024 */

            while (!not_set && port_number < 65536) {
                try {
                    server = new ServerSocket(port_number);
                    not_set = !not_set;
                } catch (IOException err) {
                    ++port_number;
                } // End try-catch block
            } // End while loop
    } // End TCPServerThread() default constructor

    public TCPServerThread(Node node, final int PORT_NUM) {
        serverThreadNode = node;
        
        if (PORT_NUM > 1024 && PORT_NUM < 65536) { /* given a valid port number create the ServerSocket */
            try {
                port_number = PORT_NUM;
                server = new ServerSocket(port_number);
            } catch (IOException err) {
                System.out.println(err.getMessage());
            } // End try-catch block
        } // End if statement 
    } // End TCPServerThread(port) constructor

    public void close_server() {
        done = true;
        
        try {
            server.close();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End close_server() method

    public void run() {
        if (server != null) {
            while (!done) {
                try {
                    Socket clientSocket = server.accept();
                    
                    // Print out the machines host name: clientSocket.getInetAddress().getHostName()
                    System.out.println("[TCPServerThread]: " + clientSocket.getInetAddress() + " has connected at port: " + clientSocket.getPort()); /* validation that something that has connected to the registry */
                    
                    
                    /* Adds our clientSocket to the list of our connections and spawns a TCPSender obj and TCPReceiverThread for the socket.
                     * This is what will allow our nodes to communciate
                    */
                    add_socket(clientSocket);
                } catch (IOException err) {
                    System.out.println(err.getMessage());
                } // End try-catch block
                } // End while loop
            
        } // End if statment
    } // End run() method

    public void send_msg(int i, byte[] arr) {
        try {
            senders.get(i).sendData(arr);
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }
    } // End send_msg

    public int get_socket_index(Socket s) {
        return socket_connetions.indexOf(s);
    } // End get_socket_index() method

    public void add_socket(Socket s) {
        socket_connetions.add(s); /* Add the socket to the ArrayList containing them */

        try {
            send = new TCPSender(s, socket_connetions.indexOf(s), serverThreadNode); /* Assign the TCPSender obj to send messages  */
            read = new TCPReceiverThread(s, socket_connetions.indexOf(s), serverThreadNode); /* Assign the TCPReceieverThread obj to read messages */
            
            senders.add(send); /* Keep track of how many nodes have TCPSender objects */
            readers.add(read); /* Keep track of how many nodes have TCPReceiver objects */

            Thread server_read_thread = new Thread(read); /* Create TCP Receiver start the thread for the new reader to check that if the thread */
            server_read_thread.start(); /* starts the TCPReceiver thread and begins to read in information while it has information to read */
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
                       
    } // End add_socket() method
    
} // End TCPServerThread class
