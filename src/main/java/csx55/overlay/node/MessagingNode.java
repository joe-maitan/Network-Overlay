package csx55.overlay.node;

import csx55.overlay.dijkstra.ShortestPath;
import csx55.overlay.wireformats.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class MessagingNode extends Node  {

    public String msgNodeHostName;
    public String msgNodeIP;
    public int msgNodePortNumber;
    public int msgNodeIndex; /* This will be important for getting the connections established */

    /* These are variables used to collect information from a MessagingNodesList message */
    int numberOfConnections; /* how many nodes we are connecting to */
    ArrayList<RegisterRequest> peerMsgNodes; /* which nodes we are connecting to */

    public Socket messaging_node_socket;

    public ArrayList<String> msgNodeEdges;
    public HashMap<String, Integer> msgNodeMap;

    public int sendTracker = 0;
    public int receiveTracker = 0;
    
    public MessagingNode(String machineName, int portNum, int socketIndex) {
        msgNodeHostName = machineName;
        msgNodePortNumber = portNum;
    } // End MessagingNode() constructor
    
    public MessagingNode(String hostName, int portNum) {
        super(); /* Creates a Node associated with the MessagingNode */

        try {
            messaging_node_socket = new Socket(hostName, portNum); /* This allows the MessagingNode to connect to the Registry */
            
            node_server.add_socket(messaging_node_socket); /* Add this socket to our list of connections */
            msgNodeIndex = node_server.socket_connetions.indexOf(messaging_node_socket);

            System.out.println("[MsgNode] has connected to [Registry]");

            this.msgNodeIP = InetAddress.getLocalHost().toString();
            this.msgNodeHostName = msgNodeIP.substring(0, msgNodeIP.indexOf('/'));
            this.msgNodeIP = msgNodeIP.substring(msgNodeIP.indexOf('/') + 1);
            this.msgNodePortNumber = node_server.port_number;
            
            /* Validation that we have collected the right information */
            // System.out.println("[MsgNode] Host name: " + msgNodeHostName);
            System.out.println("[MsgNode] IP Address: " + msgNodeIP + " at socket port #: " + messaging_node_socket.getLocalPort());
            // System.out.println("[MsgNode] Port # of ServerSocket: " + msgNodePortNumber);
    
            RegisterRequest reg_request = new RegisterRequest(msgNodeIP, msgNodePortNumber); /* Created a new registry request */
            node_server.send_msg(0, reg_request.getBytes());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } // End try-catch block
    } // End MessagingNode() constructor
    
    @Override
    public void onEvent(Event event, int socketIndex) {
        if (event == null) { return; }
        
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

                receiveTracker++;
                break;
            case 3: /* Deregister Response */
                DeregisterResponse de_resp = (DeregisterResponse) event;
                status = de_resp.getStatus();

                if (status == 1) {
                    System.out.println("[MsgNode] Failed to deregister.");
                } else {
                    System.out.println("[MsgNode] Successfully deregistered.");
                } // End if-else statement

                receiveTracker++;
                this.node_server.close_server();
                
                try {
                    this.messaging_node_socket.close();
                } catch (IOException err) {
                    System.err.println(err.getMessage());
                } // End try-catch block

                break;
            case 4: /* Link weights */
                LinkWeights linkWeights = (LinkWeights) event;
                msgNodeMap = linkWeights.getMap();
                msgNodeEdges = linkWeights.getEdges();

                if (msgNodeMap != null && msgNodeEdges != null) {
                    System.out.println("[MsgNode] Link weights received and processed. Ready to send messages.");
                } else {
                    System.out.println("[MsgNode] Did not receive Link weights.");
                } // end if-else statement
                break;
            case 5: /* message */
                
                Message msg = (Message) event;
                break;
            case 6: /* MessagingNodesList */
                // System.out.println("[MsgNode] Received a MessaingNodesList event");
                MessagingNodesList msg_node_list = (MessagingNodesList) event;

                numberOfConnections = msg_node_list.getNumPeers();
                peerMsgNodes = msg_node_list.getMsgNodePeerList();

                Socket peerSocket;
                for (RegisterRequest r : peerMsgNodes) {
                    try {
                        peerSocket = new Socket(r.getAddress(), r.getPort());
                        System.out.println("[MsgNode] has connected to " + peerSocket.getInetAddress().getHostAddress());
                    } catch (IOException err) {
                        System.err.println(err.getMessage());
                    } // End try-catch block
                } // End for loop

                System.out.println("[MsgNode] has made " + numberOfConnections + " connections.");
                // System.out.println("[MsgNode] Exiting MessagingNodesList .onEvent()");

                receiveTracker++;
                break;
            case 7: /* Task Initiate */
                TaskInitiate initiate = (TaskInitiate) event;
                if (msgNodeEdges == null || msgNodeMap == null) {
                    System.out.println("Cannot initiate task. Do not have link weight information");
                } else {
                    System.out.println("[MsgNode] Task Initiated. # of rounds: " + initiate.getNumRounds());

                    /* START COMPUTING DIJKSTRAS*/
                    ShortestPath calculateShortPath;
                    for (int i = 0; i < initiate.getNumRounds(); ++i) {
                        System.out.println("Inside of taskInitaite: " + msgNodeEdges.get(i));
                        calculateShortPath = new ShortestPath(msgNodeEdges, msgNodeMap);

                        for (int j = 0; j < 5; ++j) { // generate 5 messages for each node to send. 5 messages for every round
                            Message m = new Message();
                            // calculateShortPath.calculateShortestPath(msgNodeIP, msgNodePortNumber);
                            // send_message(i, m, "");
                        }

                        
                        String currMsgNodeIP;    
                    }
                    

                //     if (calculateShortPath.getStatus() == 0) {
                //         TaskComplete complete = new TaskComplete(this);
                //         send_message(0, complete.getBytes(), "");
                //     } else {
                //         System.out.println("[MsgNode] did not finish task.");
                //     } // End if-else statement
                } // End if-else statement
                
                break;
            case 9:
                TaskSummaryRequest sum_req = (TaskSummaryRequest) event;

                // Send back a TaskSummaryResponse event
                break;
            default:
                System.out.println("MessagingNode.java - Unrecognized Event.");
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
                    /* the onEvent for DeregisterReponse closes the server thread and the nodes socket */
                    return;
                default:
                    System.out.println("Unrecognized command. Please try again");
                    break;
            } // End switch statement
        } // End while loop

        user_in.close();
    } // End main method

} // End MessagingNode class
