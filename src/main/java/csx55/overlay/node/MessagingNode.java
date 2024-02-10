package csx55.overlay.node;

import csx55.overlay.transport.*;
import csx55.overlay.wireformats.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MessagingNode extends Node  {

    public String msgNodeHostName;
    public String msgNodeIP;
    public int msgNodePortNumber;
    public int msgNodeIndex; /* This will be important for getting the connections established */

    public Socket messaging_node_socket;

    public int numberOfMsgsReceived;
    public int numberOfMsgsSent;
    
    public MessagingNode(String machineName, int portNum, int socketIndex) {
        msgNodeHostName = machineName;
        msgNodePortNumber = portNum;
    } // End MessagingNode() constructor
    
    public MessagingNode(String hostName, int portNum) {
        super(); /* Creates a Node associated with the MessagingNode */

        try {
            messaging_node_socket = new Socket(hostName, portNum); /* This allows the MessagingNode to connect to the Registry */
            
            /* Add this socket to our list of connections */
            node_server.add_socket(messaging_node_socket); 
            msgNodeIndex = node_server.socket_connetions.indexOf(messaging_node_socket);

            System.out.println("[MsgNode] has connected to [Registry]");

            /* DO NOT DELETE THESE 3 LINES. THESE LINES PARSE IN INFORMATION ABOUT
             * THE MESSAGING NODE THAT HAS JUST CONNECTED TO THE REGISTRY.
             * 
             * THIS INFORMATION WILL THEN BE USED TO MAKE OUR REGISTER REQUEST
             */
            
            this.msgNodeIP = InetAddress.getLocalHost().toString();
            this.msgNodeHostName = msgNodeIP.substring(0, msgNodeIP.indexOf('/'));
            this.msgNodeIP = msgNodeIP.substring(msgNodeIP.indexOf('/') + 1);
            this.msgNodePortNumber = node_server.port_number;
            
            /* Validation that we have collected the right information */
            // System.out.println("[MsgNode] Host name: " + msgNodeHostName);
            System.out.println("[MsgNode] IP Address: " + msgNodeIP + " at socket port #: " + messaging_node_socket.getLocalPort());
            System.out.println("[MsgNode] Port # of ServerSocket: " + msgNodePortNumber);
    
            RegisterRequest reg_request = new RegisterRequest(msgNodeIP, msgNodePortNumber); /* Created a new registry request */
            node_server.send_msg(0, reg_request.getBytes());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } // End try-catch block
    } // End MessagingNode() constructor
    
    @Override
    public void onEvent(Event event, int socketIndex) {
        System.out.println("Entering MessagingNode.onEvent()");
        
        if (event == null) {
            System.out.println("Event being passed in is null");
            return;
        }
        
        int messageProtocol = 0;
        messageProtocol = event.getType();
       
        byte status;
        switch(messageProtocol) {
            case 1: /* Register Response */
                RegisterResponse reg_resp = (RegisterResponse) event;
                status = reg_resp.getStatus();

                if (status == 1) {
                    System.out.println("[MsgNode] Failed to register.");
                } else {
                    System.out.println("[MsgNode] Successfully registered.");
                } // End if-else statement

                break;
            case 3:
                /* Deregister Response */
                DeregisterResponse de_resp = (DeregisterResponse) event;
                status = de_resp.getStatus();

                if (status == 1) {
                    System.out.println("[MsgNode] Failed to deregister.");
                } else {
                    System.out.println("[MsgNode] Successfully deregistered.");
                } // End if-else statement

                this.node_server.close_server();
                break;
            case 4:
                /* Link weights */
                LinkWeights linkWeights = (LinkWeights) event;
                break;
            case 5:
                /* message */
                Message msg = (Message) event;
                break;
            case 6: /* MessagingNodesList */
                System.out.println("[MsgNode] Received a MessaingNodesList event");
                MessagingNodesList msg_node_list = (MessagingNodesList) event;

                int numberOfConnections;
                ArrayList<RegisterRequest> peerMsgNodes;

                numberOfConnections = msg_node_list.getNumPeers();
                peerMsgNodes = msg_node_list.getMsgNodePeerList();

                for (RegisterRequest r : peerMsgNodes) {
                    try {
                        messaging_node_socket = new Socket(r.getAddress(), r.getPort());
                    } catch (IOException err) {
                        System.err.println(err.getMessage());
                    } // End try-catch block
                } // End for loop

                System.out.println("[MsgNode] Exiting MessagingNodesList .onEvent()");
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

    public void printShortestPath() {

    } // End printShortestPath()
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 2) {
            System.out.println("MessagingNode - Invalid number of arguments. Exiting program.");
            System.exit(1);
        } // End if-else statement
        
        final String REGISTRY_HOST_NAME = args[0];
        final int PORT_NUM = Integer.parseInt(args[1]);

        MessagingNode newMessagingNode = new MessagingNode(REGISTRY_HOST_NAME, PORT_NUM);
        newMessagingNode.node_server_thread.start(); /* start our TCPServerThread associated with our new_messaging_node object */

        Scanner user_in = new Scanner(System.in);
        String line = null;

        while (line != "exit") {
            line = user_in.nextLine();

            switch(line) {
                case "register":
                    RegisterRequest register = new RegisterRequest(newMessagingNode.msgNodeIP, newMessagingNode.msgNodePortNumber);
                    newMessagingNode.node_server.send_msg(0, register.getBytes());
                    break;
                case "print-shortest-path":
                    newMessagingNode.printShortestPath();
                    break;
                case "exit-overlay":
                    DeregisterRequest deregister = new DeregisterRequest(newMessagingNode.msgNodeIP, newMessagingNode.msgNodePortNumber);
                    newMessagingNode.node_server.send_msg(0, deregister.getBytes());

                    /* Sends this message to the Registry, the Registry will send a response and go into the
                     * MessagingNodes onEvent() method. This is where the deregistration will be handled.
                     */
                    break;
                default:
                    System.out.println("Unrecognized command. Please try again");
                    break;
            } // End switch statement
        } // End while loop

        user_in.close();
    } // End main method

} // End MessagingNode class
