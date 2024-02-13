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
    public int port_number; /* DO NOT DELETE THIS. THIS IS THE PORT OUR NODES SERVER WILL LAUNCH ON */
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
                    serverSocket = new ServerSocket(port_number);
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
            send = new TCPSender(s, socket_connetions.indexOf(s), serverThreadNode); 
            read = new TCPReceiverThread(s, socket_connetions.indexOf(s), serverThreadNode);
            
            senders.add(send);
            readers.add(read);

            Thread server_read_thread = new Thread(read); 
            
            /* starts the TCPReceiver thread and begins to read in information while it has information to read */
            server_read_thread.start(); 
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
                       
    } // End add_socket() method
    
} // End TCPServerThread class
