package csx55.overlay.transport;

import java.io.*;
import java.net.*;
import java.util.*;

import csx55.overlay.node.Registry;

public class TCPServerThread implements Runnable {

    static ServerSocket node_server;
    public ArrayList<Socket> other_node_sockets = new ArrayList<>();
    static volatile boolean connection_is_active = false;
    public int number_of_nodes_connected_to = 0;

    public static boolean is_active() {
        if (connection_is_active == true) {
            return true;
        } else {
            return false;
        }
    } // End is_active() method

    public static void set_connection(boolean status) {
        connection_is_active = status;
    } // End set_connection() method

    /* This TCPServerThread takes in a port number passed in from the MessagingNode/Node it has spawned from.
     * From there, we check if the PORT_NUM passed in is a valid port number, else, we increment the new port #
     * While our_server is not intialized and the new_port_num is less that 65546 we keep incrementing it until
     * a valid port is found and the ServerSocket is initialized.
     */
    public TCPServerThread(final int PORT_NUM) {
        if (PORT_NUM > 1024 && PORT_NUM < 65536) { /* given a valid port number create the ServerSocket */
            try {
                node_server = new ServerSocket(PORT_NUM); /* the given port is valid */
                // System.out.println("Registry host name: " + node_server.getInetAddress().getHostName());
                // System.out.println("Registry is up and running");
            } catch (IOException err) {
                System.out.println(err.getMessage());
            } // End try-catch block
        } else { 
            int new_port_num = 1025;

            while (node_server == null && new_port_num < 65536) {
                try {
                    node_server = new ServerSocket(new_port_num);
                } catch (IOException e) {
                    ++new_port_num; // Increment the port_num every time we cannot initialize our_server
                } // End try-catch block
            } // End the while loop
        } // End if-else statment
    } // End TCPServerThread

    public void run() {
        /* every node connects to a default number of 4 other nodes. NODES not MessagingNodes. Every MessagingNode will spawn its own node and follow this sequence */
        while (is_active()) {
            /* look for other nodes to connect to and keep track of the other nodes we will connect to */
            try {
                if (number_of_nodes_connected_to < 4) {
                    Socket s = node_server.accept(); // This connects the node (A) to the other node (B)
                    other_node_sockets.add(s);
                } else {
                    /* cannot add anymore connections to the node */
                } // End if-else statement
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        } // End while loop
    } // End run() method

} // End TCPServerThread class
