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
    
    @Override
    public void onEvent(Event event, int socketIndex) {
        int messageProtocol = event.getType();
        
        switch(messageProtocol) {
            case 0:

                /* TODO: Since every node goes through the registration process upon connecting with 
                 * the registry do we really need to have this onEvent for a RegisterRequest?
                 */

                // RegisterRequest reg_rq = (RegisterRequest) event;
                // String ip_address = reg_rq.getAddress();
                // int port = reg_rq.getPort();
                
                // boolean value = register_node(socketIndex, reg_rq);
                // RegisterResponse response = new RegisterResponse(value, ip_address);

                // send_message(socketIndex, response.getBytes(), "");
                // break;
            case 1: /* Register Response */
                RegisterResponse reg_resp = (RegisterResponse) event;
                byte status = reg_resp.getStatus();

                if (status == 1) {
                    System.out.println("MessagingNode failed to register.");
                } else {
                    System.out.println("MessagingNode has been registered.");
                } // End if-else statement

                break;
            case 2:
                /* Deregister Request */
                DeregisterRequest de_rq = (DeregisterRequest) event;
                String ipAddress = de_rq.getAddress();
                int port = de_rq.getPort();

                // boolean value = deregister_node(socketIndex, de_rq)
                // DeregisterResponse response = new DeregisterReponse(value, )

                break;
            case 3:
                /* Deregister Response */
                DeregisterResponse de_resp = (DeregisterResponse) event;
                break;
            case 4:
                /* Link weights */
                LinkWeights linkWeights = (LinkWeights) event;
                break;
            case 5:
                /* message */
                Message msg = (Message) event;
                break;
            case 6:
                // Messaging Nodes list
                MessagingNodesList msg_node_list = (MessagingNodesList) event;
                break;
            case 7:
                // task initiate
                TaskInitiate initiate = (TaskInitiate) event;
                break;
            case 8:
                // task complete
                TaskComplete taskComplete = (TaskComplete) event;
                break;
            case 9:
                // task summary request
                TaskSummaryRequest sum_req = (TaskSummaryRequest) event;
                break;
            case 10:
                // task summary response
                TaskSummaryResponse sum_rsp = (TaskSummaryResponse) event;
                break;
        } // End switch statement
    } // End onEvent() method
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
        RegisterRequest reg_request = new RegisterRequest(newMessagingNode.msgNodeName, newMessagingNode.msgNodePortNumber); /* Created a new registry request */

        /* Sends a registry request to the Registry */
        newMessagingNode.node_server.send_msg(newMessagingNode.msgNodeIndex, reg_request.getBytes());

        // Take command line input
    } // End main method

} // End MessagingNode class
