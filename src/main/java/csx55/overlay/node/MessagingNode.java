package csx55.overlay.node;

import csx55.overlay.transport.*;
import csx55.overlay.wireformats.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MessagingNode extends Node  {

    public String messageNodeIP;
    public int messageNodePort;
    public int mn_type_of_message; /* the message protocol that the Node wants to send to another Node */
    
    static Socket messaging_node_socket;

    public MessagingNode(String hostName, int portNum) {
        super(); /* Creates a Node associated with the MessagingNode */

        try {
            Socket messaging_node_socket = new Socket(hostName, portNum); /* This allows the MessagingNode to connect to the Registry */
            
            node_server.add_socket(messaging_node_socket);

            System.out.println("[MsgNode] has connected to [Registry]");
            
            /* THIS GRABS THE REGISTRY SERVERS INFORMATION. WE NEED TO GRAB THE NODES NAME AND PORT */
            // messageNodeIP = messaging_node_socket.getInetAddress().toString();
            // messageNodePort = messaging_node_socket.getPort();

            messageNodeIP = node_server.node_ip;
            messageNodePort = node_server.node_port_num;

            System.out.println("MsgNode IP: " + messageNodeIP);
            System.out.println("MsgNode Port: " + messageNodePort);
        
            System.out.println("Creating a new register request");
            RegisterRequest reg_request = new RegisterRequest(this); /* Created a new registry request */
            
            node_server.send_msg(0, reg_request.getBytes());
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
        new_messaging_node.node_server_thread.start(); /* start our TCPServerThread associated with our new_messaging_node object */

    } // End main method

} // End MessagingNode class
