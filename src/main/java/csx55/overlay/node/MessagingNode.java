package csx55.overlay.node;

import csx55.overlay.dijkstra.ShortestPath;
import csx55.overlay.wireformats.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class MessagingNode extends Node  {

    // Can probably delete these 3 variables
    // public String msgNodeHostName;
    // public String msgNodeIP;
    // public int msgNodePortNumber;
    private int msgNodeIndex; /* This will be important for getting the connections established */

    /* These are variables used to collect information from a MessagingNodesList message */
    private int numberOfConnections; /* how many nodes we are connecting to */
    private ArrayList<RegisterRequest> peerMsgNodes; /* which nodes we are connecting to */

    private Socket messaging_node_socket;

    private ArrayList<String> msgNodeEdges;
    private HashMap<String, Integer> msgNodeMap;

    private RegisterRequest msgNodeRegisterRequest; /* from this RegisterRequest we can get its host name and port # */

    /* used for Statistics Collection and Display. Is fine if public */
    public int numberOfMsgsSent;
    public int sumOfMsgsSent;
    public int numberOfMsgsReceived;
    public int sumOfMsgsReceived;
    public int numberOfMsgsRelayed;

    public int sendTracker = 0;
    public int receiveTracker = 0;

    private ArrayList<Socket> peerSockets = new ArrayList<>();

    public void setRegisterRequest(RegisterRequest req) {
        this.msgNodeRegisterRequest = req;
    } // End setRegisterRequest(req) method

    public RegisterRequest getRegisterRequest() {
        return this.msgNodeRegisterRequest;
    } // End getRegisterRequest() method
    
    public MessagingNode(String registryHostName, int portNum) {
        super(); /* Creates a Node associated with the MessagingNode */

        try {
            messaging_node_socket = new Socket(registryHostName, portNum); /* This allows the MessagingNode to connect to the Registry */
            
            this.nodeServerThread.add_socket(messaging_node_socket); /* Add this socket to our list of connections */
            msgNodeIndex = nodeServerThread.socket_connetions.indexOf(messaging_node_socket);

            // System.out.println("[MsgNode] has connected to [Registry]");

            setHostName(InetAddress.getLocalHost().toString().substring(0, msgNodeIP.indexOf('/')));
            // this.msgNodeIP = msgNodeIP.substring(msgNodeIP.indexOf('/') + 1);
            setPortNumber(this.nodeServerThread.getPortNumber());
            
            /* Validation that we have collected the right information */
            System.out.println("[MsgNode] Host name: " + getHostName());
            // System.out.println("[MsgNode] IP Address: " + msgNodeIP + " - Port #: " + messaging_node_socket.getLocalPort());
            System.out.println("[MsgNode] Port #: " + messaging_node_socket.getLocalPort());
            System.out.println("[MsgNode] Port # of ServerSocket: " + getPortNumber());
    
            RegisterRequest reg_request = new RegisterRequest(getHostName(), getPortNumber()); /* Created a new registry request */
            setRegisterRequest(reg_request);
            this.nodeServerThread.send_msg(0, getRegisterRequest().getBytes());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } // End try-catch block
    } // End MessagingNode() constructor
    
    public RegisterRequest getMsgNodeRegisterRequest() {
        return this.msgNodeRegisterRequest;
    } // End getMsgNodeRegisterRequest() method

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

                break;
            case 3: /* Deregister Response */
                DeregisterResponse de_resp = (DeregisterResponse) event;
                status = de_resp.getStatus();

                if (status == 1) {
                    System.out.println("[MsgNode] Failed to deregister.");
                } else {
                    System.out.println("[MsgNode] Successfully deregistered.");
                } // End if-else statement

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
                receiveTracker++;
                Message msg = (Message) event;
                sumOfMsgsReceived += msg.getPayload();
                numberOfMsgsRelayed++;
                break;
            case 6: /* MessagingNodesList */
                MessagingNodesList msg_node_list = (MessagingNodesList) event;

                numberOfConnections = msg_node_list.getNumPeers();
                peerMsgNodes = msg_node_list.getMsgNodePeerList();

                for (RegisterRequest r : peerMsgNodes) {
                    try {
                        Socket peerSocket;
                        peerSocket = new Socket(r.getAddress(), r.getPort());
                        peerSockets.add(peerSocket);
                        addSocket(peerSocket);

                        // System.out.println("This is the index of the node in TCPSenders: " + );
                        // System.out.println("[MsgNode] has connected to " + peerSocket.getInetAddress().getHostName() + " at " + peerSockets.indexOf(peerSocket));
                    } catch (IOException err) {
                        System.err.println(err.getMessage());
                    } // End try-catch block
                } // End for loop

                System.out.println("[MsgNode] All connections are established. Number of connections: " + numberOfConnections);
                break;
            case 7: /* Task Initiate */
                TaskInitiate initiate = (TaskInitiate) event;
                Random index = new Random();

                if (msgNodeEdges == null || msgNodeMap == null) {
                    System.out.println("Cannot initiate task. Do not have link weight information");
                } else {
                    System.out.println("[MsgNode] Task Initiated. # of rounds: " + initiate.getNumRounds());

                    /* START COMPUTING DIJKSTRAS */
                    for (int i = 0; i < initiate.getNumRounds(); ++i) {
                        Random gen = new Random();
            
                        for (int j = 1; j < 6; ++j) { // generate 5 messages for each node to send. 5 messages for every round
                            int payload = gen.nextInt();
                            
                            Message m = new Message(payload);
                            Socket s = peerSockets.get(gen.nextInt(peerSockets.size()));
                            // System.out.println("[MsgNode] Sending message to " + s.getInetAddress().getHostName() + " at index: " + peerSockets.indexOf(s));
                            send_message(node_server.socket_connetions.indexOf(s), m.getBytes(), "");

                            sendTracker++;
                            sumOfMsgsSent += payload;
                            numberOfMsgsRelayed++;
                        } // End for loop    
                    } // End for loop
                } // End if-else statement

                System.out.println("[MsgNode] Task completed. Sending TaskComplete to Registry.");
                TaskComplete complete = new TaskComplete(getMsgNodeRegisterRequest());
                send_message(0, complete.getBytes(), node_ip_address);
                break;
            case 9:
                System.out.println("[MsgNode] Received TaskSummaryRequest");
                TaskSummaryRequest sum_req = (TaskSummaryRequest) event;
                // Send back a TaskSummaryResponse event
                int[] msgs = new int[5];
                
                msgs[0] = sendTracker;
                msgs[1] = sumOfMsgsSent;
                msgs[2] = receiveTracker;
                msgs[3] = sumOfMsgsReceived;
                msgs[4] = numberOfMsgsRelayed;

                System.out.println("[MsgNode] Sending TaskSummaryResponse");
                TaskSummaryResponse rsp = new TaskSummaryResponse(msgNodeRegisterRequest, msgs);

                // System.out.println("[MsgNode] Number of messages Recieved: " + msgs[2]);
                
                send_message(0, rsp.getBytes(), "");

                sendTracker = 0;
                sumOfMsgsSent = 0;
                receiveTracker = 0;
                sumOfMsgsReceived = 0;
                numberOfMsgsRelayed = 0;
                break;
            default:
                System.out.println("MessagingNode.java - Unrecognized Event.");
                break;
        } // End switch statement
    } // End onEvent() method

    public void printShortestPath() {
        ShortestPath calc = new ShortestPath(msgNodeEdges, msgNodeMap);
        // shortestPath = calc.getShortestPath()
        // System.out.println(shorestPath)
        System.out.println("Unimplemented method - printShortestPath()");
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

        while (line != "exit-overlay") {
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
                    user_in.close();
                    return;
                default:
                    System.out.println("[MsgNode] Unrecognized command. Please try again");
                    break;
            } // End switch statement
        } // End while loop

        user_in.close();
    } // End main method

} // End MessagingNode class
