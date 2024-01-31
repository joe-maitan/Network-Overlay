package csx55.overlay.node;
import csx55.overlay.wireformats.*;

import java.io.*;
import java.net.*;

public class MessagingNode extends Node  {

    public int mn_type_of_message; /* the message protocol that the Node wants to send to another Node */
    public String mn_ip_address; /* Inet Address of the MessagingNode */
    public int mn_port_number;

    static Socket messaging_node_socket;

    public MessagingNode(String machine_name, final int PORT_NUM) {
        super(0); /* Creates a TCPServerThread for the messaging node. This is what we will use to connect other messaging nodes */
        
        /* after parsing the correct information in node we copy the data here */
        this.mn_port_number = this.node_port_number;
        this.mn_ip_address = this.node_ip_address;

        try {
            // System.out.println("Creating new MessagingNode socket");
            Socket messaging_node_socket = new Socket(machine_name, PORT_NUM); /* This allows the MessagingNode to connect to the Registry */

            Register register_request = new Register(this); /* we then create a new register request */
            this.send_message(0, register_request.getBytes(), null); /* and send it to the registry */

            // check the status of the returned message, if 0 success, if 1, no success
            // if


            /* Add the new messaging_node to the regsitry? registering its IP, Port, and MessageType */
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } // End try-catch block
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

        MessagingNode new_messaging_node = new MessagingNode(REGISTRY_HOST_NAME, PORT_NUM);

        /* do we start reading input for the messaging node now? */
    } // End main method

} // End MessagingNode class
