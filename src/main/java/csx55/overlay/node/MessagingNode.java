package csx55.overlay.node;

import csx55.overlay.transport.*;
import csx55.overlay.wireformats.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MessagingNode extends Node  {

    public String msgNodeName;
    public int msgNodePortNumber;
    public int msgNodeIndex; /* This will be important for getting the connections established */

    public Socket messaging_node_socket;

    public MessagingNode(String hostName, int portNum) {
        super(); /* Creates a Node associated with the MessagingNode */

        try {
            messaging_node_socket = new Socket(hostName, portNum); /* This allows the MessagingNode to connect to the Registry */
            
            /* Add this socket to our list of connections */
            /* TODO: We will then use this list of connections to allow the other MessagingNodes to connect to eachother? */
            node_server.add_socket(messaging_node_socket); 
            msgNodeIndex = node_server.socket_connetions.indexOf(messaging_node_socket);

            System.out.println("[MsgNode]: has connected to [Registry]");

            /* DO NOT DELETE THESE 3 LINES. THESE LINES PARSE IN INFORMATION ABOUT
             * THE MESSAGING NODE THAT HAS JUST CONNECTED TO THE REGISTRY.
             * 
             * THIS INFORMATION WILL THEN BE USED TO MAKE OUR REGISTER REQUEST
             */
            this.msgNodeName = InetAddress.getLocalHost().toString();
            this.msgNodeName = msgNodeName.substring(msgNodeName.indexOf('/') + 1);
            this.msgNodePortNumber = messaging_node_socket.getLocalPort();
            
            /* Validation that we have collected the right information */
            System.out.println("[MsgNode] IP Address: " + msgNodeName);
            System.out.println("[MsgNode] Port: " + msgNodePortNumber);
    
            // System.out.println("Creating a new register request");
            // RegisterRequest reg_request = new RegisterRequest(this); /* Created a new registry request */
            
            // node_server.send_msg(0, reg_request.getBytes());
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

        MessagingNode newMessagingNode = new MessagingNode(REGISTRY_HOST_NAME, PORT_NUM);
        newMessagingNode.node_server_thread.start(); /* start our TCPServerThread associated with our new_messaging_node object */

        /* Was originally placed in the constructor */
        System.out.println("Creating a new register request");
        RegisterRequest reg_request = new RegisterRequest(newMessagingNode); /* Created a new registry request */

        /* TODO: Figure out where the data is going and how to parse it correctly */
        newMessagingNode.node_server.send_msg(newMessagingNode.msgNodeIndex, reg_request.getBytes());
    } // End main method

} // End MessagingNode class
