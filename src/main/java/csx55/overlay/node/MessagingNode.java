package csx55.overlay.node;

import csx55.overlay.transport.*;
import csx55.overlay.wireformats.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MessagingNode extends Node  {

    public int mn_type_of_message; /* the message protocol that the Node wants to send to another Node */
    public String mn_ip_address; /* Inet Address of the MessagingNode */
    public int mn_port_number;

    static Socket messaging_node_socket;

    // static ArrayList<Socket> list_of_mn_sockets = new ArrayList<>();

    public MessagingNode(String machine_name, final int PORT_NUM) {
        super(0); /* Creates a TCPServerThread for the messaging node. This is what we will use to connect other messaging nodes */
        this.mn_port_number = this.node_port_number;
        this.mn_ip_address = this.node_ip_address;

        try {
            // System.out.println("Creating new MessagingNode socket");
            Socket messaging_node_socket = new Socket(machine_name, PORT_NUM); /* This allows the MessagingNode to connect to the Registry */
            
            this.node_server.add_socket(messaging_node_socket);

            Register register_request = new Register(this); /* we then create a new register request */
            
            node_server.send_msg(0, register_request.getBytes());

            /* TODO: Read the information sent from the registry back to the node */
            /* TODO: Read the status when we send the message and see what we have to do next */
            /* Add the new messaging_node to the regsitry? registering its IP, Port, and MessageType */
            
            // try {
            //     Registry.register_node(this);
            // } catch (Exception e) {
            //     System.err.println(e.getMessage());
            // }
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } // End try-catch block
    } // End MessagingNode() constructor
    public static void main(String[] args) {
        if (args.length < 2) {
            System.exit(1);
        } // End if-else statement
        
        final String REGISTRY_HOST_NAME = args[0];
        final int PORT_NUM = Integer.parseInt(args[1]);

        MessagingNode new_messaging_node = new MessagingNode(REGISTRY_HOST_NAME, PORT_NUM);
        new_messaging_node.t.start(); /* start our TCPServerThread associated with our new_messaging_node object */

        System.out.println("MessagingNode.java - My ip is: " + new_messaging_node.mn_ip_address);
        System.out.println("MessagingNode.java - Port #: " + new_messaging_node.mn_port_number);

        /* do we start reading input for the messaging node now? */
    } // End main method

} // End MessagingNode class
