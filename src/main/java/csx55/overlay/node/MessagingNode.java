package csx55.overlay.node;
import csx55.overlay.wireformats.*;

import java.io.*;
import java.net.*;

public class MessagingNode implements Node  {

    private String messaging_node_name; /* The machine name of the messagingNode (Client) */
    private String message;
    
    private int type_of_message; /* the message protocol that the Node wants to send to another Node */
    private String ip_address;
    private int PORT_NUM;

    static Socket messaging_node_socket;
    private int registry_index;

    public MessagingNode(String machine_name, int index) {
        this.messaging_node_name = machine_name;
        this.registry_index = index;

        // Node new_node = new Node(); /* Everytime a new messaging node is created, it spawns a node */
        // node_server = new ServerSocket(); /* instead of spawning a new socket do we create a new TCPServerThread? */
        // node_connections = new Socket();

        // spawn a TCPReceiverThread
        // spawn a TCPSender

    } // End MessagingNode() constructor

    @Override
    public void onEvent(Event type_of_event) {
        
    } // End onEvent() method

    public static void initialize_connection_to_registry(final String HOST_NAME, final int PORT_NUM) {
        try {
            messaging_node_socket = new Socket(HOST_NAME, PORT_NUM); /* the Registry's ServerSocket is listening for this connection. Hopefully connects to the server */
            System.out.println("Successfully connected to Registry");
            /* Should we create the Node here? */
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } // End try-catch block
    } // End initialize_connection_to_server() method


    public static void main(String[] args) {
        if (args.length < 2) {
            System.exit(1);
        }
        
        final String REGISTRY_HOST_NAME = args[0];
        final int PORT_NUM = Integer.parseInt(args[1]);

        initialize_connection_to_registry(REGISTRY_HOST_NAME, PORT_NUM);
    } // End main method

} // End MessagingNode class
