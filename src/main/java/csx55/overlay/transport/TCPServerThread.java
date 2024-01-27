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

    public TCPServerThread(final String machine_host_name, final int PORT_NUM) {
        if (PORT_NUM > 1024 && PORT_NUM < 65536) { /* given a valid port number create the ServerSocket */
            try {
                /* the given port is valid */
                /* create the node_server. This server will listen for up to 10 connections */
                node_server = new ServerSocket(PORT_NUM, 10); 
                connection_is_active = true;
            } catch (IOException err) {
                System.out.println(err.getMessage());
            } // End try-catch block
        } else if (PORT_NUM == 0) { 
            int new_port_num = 1025;

            /* while the node_server is not initialzied and the port number is less than what is allowed 
             * increment until we find a port number to host the node server
            */
            while (node_server == null && new_port_num < 65536) {
                try {
                    node_server = new ServerSocket(new_port_num);
                    connection_is_active = true;
                } catch (IOException e) {
                    ++new_port_num; // Increment the port_num every time we cannot initialize our_server
                } // End try-catch block
            } // End the while loop
        } // End if-else statment
    } // End TCPServerThread

    public void run() {
        /* every node connects to a default number of 4 other nodes. NODES not MessagingNodes. Every MessagingNode will spawn its own node and follow this sequence */
        while (connection_is_active) {
            /* look for other nodes to connect to and keep track of the other nodes we will connect to */
            try {
                if (number_of_nodes_connected_to < 4) {
                    Socket s = node_server.accept(); // This connects the node (A) to the other node (B)
                    ++number_of_nodes_connected_to;
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
