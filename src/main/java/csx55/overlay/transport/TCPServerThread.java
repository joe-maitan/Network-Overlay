package csx55.overlay.transport;
import csx55.overlay.node.Registry;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServerThread implements Runnable {

    static ServerSocket node_server;
    public ArrayList<Socket> other_node_sockets = new ArrayList<>();
    static volatile boolean connection_is_active = false;
    public int number_of_nodes_connected_to = 0;

    // comment out?
    public TCPServerThread() {
        System.out.println("TCPServerThread(): Creating new server");
        /* this creates a server thread with no specifed port # */
        int new_port_num = 1025;
        while (node_server == null && new_port_num < 65536) {
            try {
                node_server = new ServerSocket(new_port_num);
                connection_is_active = true;
            } catch (IOException e) {
                ++new_port_num; // Increment the port_num every time we cannot initialize our_server
            } // End try-catch block
        } // End the while loop 

        System.out.println("TCPServerThread(): Finished creating new server");
        System.out.println("host name: " + node_server.getInetAddress().getHostName());
    } // End TCPReceiverThread() default constructor

    public TCPServerThread(final int PORT_NUM) {
        System.out.println("TCPServerThread(port): Creating a new server");
        /* when spawing in the server thread, the Registry can hold a minimum of 10 connections
         * and the messagingNodes can be connected to 4 other nodes. How are we supposed to differentiate?
         */
        if (PORT_NUM > 1024 && PORT_NUM < 65536) { /* given a valid port number create the ServerSocket */
            /* is there a way we can differentiate what object is creating a server thread?
             * That way I can tell it, okay we only create 1 Registry
             * Then a messaging node server can accept a minimum of 10 connections
             */
            try {
                /* the given port is valid */
                node_server = new ServerSocket(PORT_NUM); 
                connection_is_active = true;
            } catch (IOException err) {
                System.out.println(err.getMessage());
            } // End try-catch block
        } // End if-statement 

        System.out.println("TCPServerThread(port): Finished creating new server");
        System.out.println("host name: " + node_server.getInetAddress().getHostName());
    } // End TCPServerThread(port) constructor

    public void run() {
        /* every node connects to a default number of 4 other nodes. NODES not MessagingNodes. Every MessagingNode will spawn its own node and follow this sequence */
        while (connection_is_active) {
            /* look for other nodes to connect to and keep track of the other nodes we will connect to */
            try {
                if (number_of_nodes_connected_to < 4) {
                    Socket s = node_server.accept(); // This connects the node (A) to the other node (B)
                    
                    System.out.println("Connected to: " +  s.getInetAddress().getHostName());
                    
                    // ++number_of_nodes_connected_to;
                    // other_node_sockets.add(s);
                } else {
                    /* cannot add anymore connections to the node */
                } // End if-else statement
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        } // End while loop
    } // End run() method

} // End TCPServerThread class
