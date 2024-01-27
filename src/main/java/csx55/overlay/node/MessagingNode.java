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
        // this.messaging_node_name = machine_name;
    } // End MessagingNode() constructor

    @Override
    public void onEvent(Event type_of_event) {
        
    } // End onEvent() method
    public static void main(String[] args) {
        if (args.length < 2) {
            System.exit(1);
        } // End if-else statement
        
        final String REGISTRY_HOST_NAME = args[0];
        final int PORT_NUM = Integer.parseInt(args[1]);

        // initialize_connection_to_registry(REGISTRY_HOST_NAME, PORT_NUM);
        MessagingNode new_messaging_node = new MessagingNode(REGISTRY_HOST_NAME, PORT_NUM);
    } // End main method

} // End MessagingNode class
