package csx55.overlay.node;

import java.io.*;
import java.net.*;
import java.util.*;

import csx55.overlay.wireformats.*;
import csx55.overlay.util.*;
import csx55.overlay.transport.*;

public class Registry extends Node {

    /* this tracks the socket index of the messaging node as well as the RegisterRequest */
    HashMap<Integer, RegisterRequest> registered_messaging_nodes = new HashMap<>();
    
    // ArrayList<MessagingNode> listOfMessagingNodes = new ArrayList<>();

    public int numberOfRegisteredNodes;

    public Registry() {} // End Registry default constructor

    public Registry(int port_number) {
        super(port_number); /* creates a node object with a ServerThread. Effectively creating the registry */
    } // End Registry(PORT) constructor

    public boolean register_node(int socket_index, RegisterRequest rq) {
        if (!registered_messaging_nodes.containsKey(socket_index)) {
            registered_messaging_nodes.put(socket_index, rq); /* Add the node to the Registry */
            ++numberOfRegisteredNodes;
            String success = String.format("Registration request successful. The number of messaaging nodes currently constituting the overlay is (%d).", numberOfRegisteredNodes);
            System.out.println(success);
            return true;
        } else { /* We do not add it to the registry */
            return false;
            // try{
            //     throw new Exception("Cannot add node to the registry.");
            // } catch (Exception e) {
            //     System.err.println(e.getMessage());
            // }
        } // End if-else statement

        // return false;
    } // End register_node() method

    public boolean deregister_node(int socket_index, DeregisterRequest dereg_rq) {
        if (registered_messaging_nodes.containsKey(socket_index)) {
            registered_messaging_nodes.remove(socket_index);
            --numberOfRegisteredNodes;
            String success = String.format("Deregistration request successful. The number of messaging nodes currently constituting the overlay is (%d)", numberOfRegisteredNodes);
            System.out.println(success);
            return true;
        } else {
            return false;
            // try {
            //     throw new Exception("Cannot deregister node.");
            // } catch (Exception e) {
            //     System.err.println(e.getMessage());
            // } // End try-catch block
        } // End if-else statement

        // return false;
    } // End deregister_node() method

    public void start(int numberOfRounds) {} // End start() method

    public void construct_overlay(int numberOfConnections) {
        /* This is what connects the other MessagingNodes to one another, given a list of other messaging node sockets, they would connect to each. */
        Vertex newVertex;
    
        Socket nodeSocket;
        int nodeSocketIndex;

        /* Loop through all of the registered nodes */
        for (int i = 0; i < numberOfRegisteredNodes; ++i) {
            nodeSocket = this.node_server.socket_connetions.get(i);
            nodeSocketIndex = this.node_server.socket_connetions.indexOf(nodeSocket);

            Vertex node_vertex = new Vertex(nodeSocketIndex);

            /* For each vertex, create neighbors for the vertices */
            for (int j = 0; j < numberOfConnections; ++j) {
                Vertex nodeNeighbor = new Vertex(nodeSocketIndex + 1);
                node_vertex.addNeighbor(nodeNeighbor);
            } // End for loop
            
        } // End for loop

    } // End construct_overlay() method

    public void list_messaging_nodes() {
        /* Assignment says it should be the MessagingNodes host(machine) name */
        MessagingNode temp = new MessagingNode("Joe", 72);
        // listOfMessagingNodes.add(temp);
        
        /* TODO: Figure out how to make a collection of messagingNodes to be able to list over them  */
        
        // for (MessagingNode mn : listOfMessagingNodes) {
        //     System.out.println(mn.msgNodeName + " " + mn.msgNodePortNumber);
        // } // End for loop
    } // End list_messaging_nodes() method

    public void list_weights() {} // End list_weights() method

    @Override
    public void onEvent(Event event, int socketIndex) {
        int messageProtocol = event.getType();

        boolean value = false;
        String info = "";
        
        switch(messageProtocol) {
            case 0: /* Register Request */
                RegisterRequest reg_rq = (RegisterRequest) event;
                
                value = register_node(socketIndex, reg_rq);
                if (value == true) {
                    info = "Node has been registered";
                } else {
                    info = "Node could not be registered";
                } // End if-else statement

                RegisterResponse reg_response = new RegisterResponse(value, info);
                send_message(socketIndex, reg_response.getBytes(), "");
                break;
            case 2: /* Deregister Request */
                DeregisterRequest de_rq = (DeregisterRequest) event;

                value = deregister_node(socketIndex, de_rq);
                if (value == true) {
                    info = "Node successfully deregistered";
                } else {
                    info = "Node could not be deregistered";
                } // End if-else statement
                
                DeregisterResponse dereg_response = new DeregisterResponse(value, info);
                send_message(socketIndex, dereg_response.getBytes(), info);
                break;
            case 4: /* Link weights */
                LinkWeights linkWeights = (LinkWeights) event;
                break;
            case 5: /* message */
                Message msg = (Message) event;
                break;
            case 6: /* List Messaging Nodes */
                MessagingNodesList msg_node_list = (MessagingNodesList) event;
                break;
            case 7: /* Task Initiate */
                TaskInitiate initiate = (TaskInitiate) event;
                break;
            case 8: /* Task Complete */
                TaskComplete taskComplete = (TaskComplete) event;
                break;
            case 9: /* Task Summary Request */
                TaskSummaryRequest sum_req = (TaskSummaryRequest) event;
                break;
            case 10: /* Task Summary Response */
                TaskSummaryResponse sum_rsp = (TaskSummaryResponse) event;
                break;
        } // End switch statement
    } // End onEvent() method

    public static void main(String[] args) {
        if (args.length < 1 || args.length > 1) {
            System.out.println("Registry - Invalid number of arguments. Exiting program.");
            System.exit(1);
        } // End if-statement
        
        int port = Integer.parseInt(args[0]);

        Registry our_registry = new Registry(port); /* creates a new Node object that will host our Registry Server */
        our_registry.node_server_thread.start(); /* starts our TCPServerThread that was created in our_registry obj */
        
        System.out.println("[Registry]: Registry Node is up and running"); /* The registry TCPServerThread is up and is looking for active connections */
        
        Scanner user_in = new Scanner(System.in);
        String line = null;
        
        while (line != "exit") {
            line = user_in.nextLine();
 
            if (line.equals("list-messaging-nodes")) {
                our_registry.list_messaging_nodes();
            } else if (line.equals("list-weights")) {
                
            } else if (line.contains("setup-overlay")) {
                int connections_required = 4; /* Connections Required by default are 4 */
            
                String[] command = line.split(" ");
            
                if (command.length == 2) {
                    connections_required = Integer.parseInt(command[1]);
                    // System.out.println("Connections required: " + connections_required);
                    our_registry.construct_overlay(connections_required);
                } else {
                    // System.out.println("Connections required: " + connections_required);
                    our_registry.construct_overlay(connections_required);
                } // End if-else statement
            } else if (line.equals("send-overlay-link-weights")) {
                our_registry.list_weights();
            }  else if (line.contains("start")) {
                String [] command = line.split(" ");

                int rounds = 0;
                if (command.length < 2) {
                    System.out.println("Invalid command for start, please include # of rounds.");
                } else {
                    rounds = Integer.parseInt(command[1]);
                    our_registry.start(rounds);
                } // End if-else statement
            } else if (line.equals("exit")) {
                our_registry.node_server.close_server();
                break;
            } else {
                System.out.println("Unrecognized command. Please try again");
            } // End if-else statements
        } // End while loop

        user_in.close();
    } // End main method

} // End Registry class