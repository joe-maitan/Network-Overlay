package csx55.overlay.node;
import csx55.overlay.wireformats.*;

import java.io.*;
import java.net.*;

public class MessagingNode extends Node  {

    private String messaging_node_name; /* The machine name of the messagingNode (Client) */
    private String message;
    
    private int type_of_message; /* the message protocol that the Node wants to send to another Node */
    private String ip_address;
    private int PORT_NUM;

    // static Socket messaging_node_socket;

    public MessagingNode(String machine_name, final int PORT_NUM) {
        super(PORT_NUM);
        this.messaging_node_name = machine_name;
    } // End MessagingNode() constructor
    public static void main(String[] args) {
        if (args.length < 2) {
            System.exit(1);
        } // End if-else statement
        
        final String REGISTRY_HOST_NAME = args[0];
        final int PORT_NUM = Integer.parseInt(args[1]);

        // initialize_connection_to_registry(REGISTRY_HOST_NAME, PORT_NUM);
        MessagingNode new_messaging_node = new MessagingNode(REGISTRY_HOST_NAME, PORT_NUM);
    } // End main method

    @Override
    public void onEvent(Event type_of_event) {
        
    } // End onEvent() method

    // public static void initialize_connection_to_registry(final String HOST_NAME, final int PORT_NUM) {
    //     try {
    //         messaging_node_socket = new Socket(HOST_NAME, PORT_NUM); /* the Registry's ServerSocket is listening for this connection. Hopefully connects to the server */
    //         System.out.println(messaging_node_socket.getInetAddress().getHostName() + " successfully connected to Registry");
            
    //         /* This new_node object will spawn a Node object that is seperate from the MessagingNode */
    //         Node new_node = new Node(PORT_NUM);
    //     } catch (IOException e) {
    //         System.err.println(e.getMessage());
    //     } // End try-catch block
    // } // End initialize_connection_to_server() method

    // public static void start_messaging_node_server(final int PORT_NUM) {
    //     /* validate the port number read in from the terminal is within bounds */
    //     try {
    //         if (PORT_NUM < 1024 || PORT_NUM > 65535) {
    //             throw new Exception("Resigstry.java - main method - Port number is out of bounds");
    //         } // End if statement
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     } // End try-catch block

    //     try {
    //         /* Create our server socket, wait for connections from other machines (nodes)
    //          * and then start having them communciate */
    //         System.out.println("Registry host name: " + InetAddress.getLocalHost());
    //         // System.out.println("Registry IP address: " + InetAddress.getLoopbackAddress());

    //         registry_server = new ServerSocket(PORT_NUM);

    //         System.out.println("Waiting for clients to join...");
            
    //         /* at minimum those messaging nodes will connect with 4 other nodes. */
            
    //         final int NUMBER_OF_MESSAGING_NODES = 2; /* minimum of 10 messaging nodes will join the Registry */
    //         messaging_node_sockets = new Socket[NUMBER_OF_MESSAGING_NODES]; /* hard code 2 right now but change to 10 when done */
    //         /* need to get two MessagingNodes connected to the registry */
    //         for (int i = 0; i < NUMBER_OF_MESSAGING_NODES; ++i) {
    //             if (i < NUMBER_OF_MESSAGING_NODES) {
    //                 messaging_node_sockets[i] = registry_server.accept();
    //                 MessagingNode new_node = new MessagingNode(messaging_node_sockets[i].getInetAddress().getHostName(), i);
    //                 register_node(new_node);
    //             } else {
    //                 System.out.println("Cannot add another messaging node to the registry");
    //             } // End if-else statement
    //         } // End for loop
            
    //         System.out.println("MessagingNodes are connected and registered.");
       
    //     } catch (IOException e) {
    //         System.err.println(e.getMessage());
    //     } // End try-catch block
    // } // End start_server() method
    
} // End MessagingNode class
