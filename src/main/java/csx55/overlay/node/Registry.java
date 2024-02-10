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

    ArrayList<Vertex> vertices = new ArrayList<>();
    
    public int numberOfRegisteredNodes; /* number of nodes constituting the overlay */

    public Registry() {} // End Registry default constructor

    public Registry(int port_number) {
        super(port_number); /* creates a node object with a ServerThread. Effectively creating the registry */
    } // End Registry(PORT) constructor

    public boolean register_node(int socket_index, RegisterRequest reg_rq) {
        if (!registered_messaging_nodes.containsKey(socket_index)) {
            registered_messaging_nodes.put(socket_index, reg_rq); /* Add the node to the Registry */
            ++numberOfRegisteredNodes;
            String success = String.format("Registration request successful. The number of messaaging nodes currently constituting the overlay is (%d).", numberOfRegisteredNodes);
            System.out.println(success);
            return true;
        } else { /* We do not add it to the registry */
            return false;
        } // End if-else statement
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
        } // End if-else statement
    } // End deregister_node() method

    public void start(int numberOfRounds) {} // End start() method

    public void construct_overlay(int numberOfConnections) {
        /* This is what connects the other MessagingNodes to one another, given a list of other messaging node sockets, they would connect to each. */
        Vertex currVertex = new Vertex(0, registered_messaging_nodes.get(0)); /* start at the first messaging node socket */
        Random linkWeightGenerator = new Random();

        if (numberOfRegisteredNodes <= numberOfConnections || numberOfConnections < 1 || (numberOfConnections*numberOfRegisteredNodes) % 2 != 0 || (numberOfConnections < 2 && numberOfRegisteredNodes > 2)) {
            System.out.println("Cannot set up overlay.");
            return;
        }

        // System.out.println("Number of registered nodes: " + numberOfRegisteredNodes);
        /* For each vertex, create neighbors for the vertices */
        for (int j = 1; j < numberOfRegisteredNodes; ++j) {
            int weight = linkWeightGenerator.nextInt(10) + 1;
           
            Vertex neighborVertex = new Vertex(j, registered_messaging_nodes.get(j));

            // System.out.println("currVertex index = " + currVertex.getIndex());
            // System.out.println("neighborVertex index = " + neighborVertex.getIndex());

            currVertex.addNeighbor(neighborVertex);
            neighborVertex.addNeighbor(currVertex);

           // System.out.println("weight between the two vertices = " + weight);
            
            currVertex.addWeight(weight);
            neighborVertex.addWeight(weight);
            
            vertices.add(currVertex);
            currVertex = neighborVertex;
        } // End for loop

        vertices.add(currVertex);

        currVertex = vertices.get(0);

        // System.out.println("About to determine how many connections are needed/remaining");
        // System.out.println("Size of our Vertices ArrayList: " + vertices.size());

        for (Vertex v : vertices) {
            currVertex = v;

            // System.out.println("Our current vertex[" + currVertex.getIndex() + "]");

            if (currVertex.getNeighborsSize() < numberOfConnections) {
                // int numberOfNeighborsNeeded = numberOfConnections - currVertex.getNeighborsSize();
                // System.out.println("Number of connections needed for vertex[" + currVertex.getIndex() + "]: " + numberOfNeighborsNeeded);

                int neighborIndex = currVertex.getIndex() + 1;
                
                
                while(currVertex.getNeighborsSize() < numberOfConnections && neighborIndex <= vertices.size()) {
                    Vertex neighbor = vertices.get(neighborIndex);
                    
                    if (currVertex.hasNeighbor(neighborIndex) == false && neighbor.hasNeighbor(currVertex.getIndex()) == false) {
                        // System.out.println("Our vertex[" + currVertex.getIndex() + "]: is adding neighbor[" + neighborIndex + "]");
                        
                        int weight = linkWeightGenerator.nextInt(10) + 1;
                        if (neighbor.getNeighborsSize() < numberOfConnections) {
                            currVertex.addNeighbor(neighbor);
                            neighbor.addNeighbor(currVertex);

                            currVertex.addWeight(weight);
                            neighbor.addWeight(weight);

                            // System.out.println("vertex[" + currVertex.getIndex() +"] and neighbor[" + neighbor.getIndex() + "] have connected to eachother");
                        } // End if statement

                    } // End if statement

                    ++neighborIndex;
                    
                } // End while loop

            } // End for loop

        } // End for-each loop loop

        // System.out.println("Vertices are all connected.");

        for (Vertex v : vertices) {
            System.out.println(v.getRegisterRequest().ipAddress + " " + v.getRegisterRequest().portNumber);
            MessagingNodesList newRequest = new MessagingNodesList(v, v.getNeighbors());

            /* Sends a message node to each of the messaging nodes telling that node who it needs to connect to */
            // send_message(v.getIndex(), newRequest.getBytes(), "");
        } // End for-each loop

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
                our_registry.list_weights();
            } else if (line.contains("setup-overlay")) {
                int connections_required = 4; /* Connections Required by default are 4 */
            
                String[] command = line.split(" ");
            
                if (command.length == 2) {
                    connections_required = Integer.parseInt(command[1]);
                    our_registry.construct_overlay(connections_required);
                } else {
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