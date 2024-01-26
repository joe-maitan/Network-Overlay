package csx55.overlay.node;
import csx55.overlay.wireformats.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Registry implements Node {

    static ServerSocket registry_server;
    static Socket[] messaging_node_sockets; /* Keep track of how many messaging nodes connect to the regsitry */

    static ArrayList<MessagingNode> registered_messaging_nodes = new ArrayList<>();

    public static void register_node(MessagingNode new_node) {
        if (!registered_messaging_nodes.contains(new_node)) {
            registered_messaging_nodes.add(new_node);
        } /* else we do not add it to the registry */
    } // End register_node() method

    public static void deregister_node(MessagingNode node) {
        if (registered_messaging_nodes.contains(node)) {
            registered_messaging_nodes.remove(node);
        } /* else do not remove it from the registry */
    } // End deregister_node

    public static void start_registry_server(final int PORT_NUM) {
        /* validate the port number read in from the terminal is within bounds */
        try {
            if (PORT_NUM < 1024 || PORT_NUM > 65535) {
                throw new Exception("Resigstry.java - main method - Port number is out of bounds");
            } // End if statement
        } catch (Exception e) {
            e.printStackTrace();
        } // End try-catch block

        try {
            /* Create our server socket, wait for connections from other machines (nodes)
             * and then start having them communciate */
            System.out.println("Registry host name: " + InetAddress.getLocalHost());
            // System.out.println("Registry IP address: " + InetAddress.getLoopbackAddress());

            registry_server = new ServerSocket(PORT_NUM);

            System.out.println("Waiting for clients to join...");
            
            /* at minimum those messaging nodes will connect with 4 other nodes. */
            
            final int NUMBER_OF_MESSAGING_NODES = 2; /* minimum of 10 messaging nodes will join the Registry */
            messaging_node_sockets = new Socket[NUMBER_OF_MESSAGING_NODES]; /* hard code 2 right now but change to 10 when done */
            /* need to get two MessagingNodes connected to the registry */
            for (int i = 0; i < NUMBER_OF_MESSAGING_NODES; ++i) {
                if (i < NUMBER_OF_MESSAGING_NODES) {
                    messaging_node_sockets[i] = registry_server.accept();
                    MessagingNode new_node = new MessagingNode(messaging_node_sockets[i].getInetAddress().getHostName(), i);
                    register_node(new_node);
                } else {
                    System.out.println("Cannot add another messaging node to the registry");
                } // End if-else statement
            } // End for loop
            
            System.out.println("MessagingNodes are connected and registered.");
       
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } // End try-catch block
    } // End start_server() method

    public void onEvent(Event type_of_event) {

    } // End onEvent() method

    public static void main(String[] args) {
        if (args.length < 1) {
            System.exit(1);
        }

        int PORT_NUM = Integer.parseInt(args[0]);
        start_registry_server(PORT_NUM);
    }


} // End Registry class