package csx55.overlay.node;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import csx55.overlay.util.StatisticsCollectorAndDisplay;
import csx55.overlay.wireformats.*;

public class Registry extends Node {

    /* Pairs the index of the Socket with the matching RegisterRequest of the MessagingNode */
    private HashMap<Integer, RegisterRequest> registeredMessagingNodes = new HashMap<>();

    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<String> edgesOfMessagingNodes;
    private HashMap<String, Integer> mapOfEdges;
    
    private int numberOfRegisteredNodes; /* number of nodes constituting the overlay */

    private AtomicInteger numberOfTaskCompleted = new AtomicInteger(0);

    public int linkCount = 0;

    public ArrayList<TaskSummaryResponse> statisticList = new ArrayList<>();
    public StatisticsCollectorAndDisplay display;

    public Registry() {} // End Registry default constructor

    public Registry(int port_number) {
        super(port_number); /* creates a node object with a ServerThread. Effectively creating the registry */
    } // End Registry(PORT) constructor

    public HashMap<Integer, RegisterRequest> getRegisteredMessagingNodes() {
        return registeredMessagingNodes;
    } // End getRegisteredMessagingNodes() method   

    public int getNumberOfRegisteredNodes() {
        return numberOfRegisteredNodes;
    } // End getNumberOfRegisteredNodes() method

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public ArrayList<String> getEdgesOfMessagingNodes() {
        return edgesOfMessagingNodes;
    }

    public void setEdgesOfMessagingNodes(ArrayList<String> edges) {
        this.edgesOfMessagingNodes = edges;
    }

    public HashMap<String, Integer> getMapOfEdges() {
        return mapOfEdges;
    }

    public void setMapOfEdges(HashMap<String, Integer> map) {
        this.mapOfEdges = map;
    }

    public boolean register_node(int socket_index, RegisterRequest reg_rq) {
        if (!getRegisteredMessagingNodes().containsKey(socket_index)) { /* Add the node to the Registry */
            getRegisteredMessagingNodes().put(socket_index, reg_rq); 
            ++numberOfRegisteredNodes;

            System.out.println(String.format("Registration request successful. The number of messaging nodes currently constituting the overlay is (%d).", numberOfRegisteredNodes));
            return true;
        } else {
            return false;
        } // End if-else statement
    } // End register_node() method

    public boolean deregister_node(int socket_index, DeregisterRequest dereg_rq) {
        if (registeredMessagingNodes.containsKey(socket_index)) {
            registeredMessagingNodes.remove(socket_index);
            getNodeServerThread().getPeerSockets().remove(socket_index);
            --numberOfRegisteredNodes;

            System.out.println(String.format("Deregistration request successful. The number of messaging nodes currently constituting the overlay is (%d)", numberOfRegisteredNodes));
            return true;
        } else {
            return false;
        } // End if-else statement
    } // End deregister_node() method

    public void start(int numberOfRounds) {
        TaskInitiate startRounds = new TaskInitiate(numberOfRounds);
        
        for (int i = 0; i < numberOfRegisteredNodes; ++i) {
            send_message(i, startRounds.getBytes(), "");
        } // End for loop
    } // End start() method

    public void construct_overlay(int numberOfConnections, int numberOfRegisteredNodes) {
        /* This is what connects the other MessagingNodes to one another, given a list of other messaging node sockets, they would connect to each. */
        Vertex currVertex = new Vertex(0, getRegisteredMessagingNodes().get(0)); /* start at the first messaging node socket */
    
        /* Conditions needed to setup overlay */
        if (numberOfRegisteredNodes <= numberOfConnections || 
        numberOfConnections < 1 || (numberOfConnections*numberOfRegisteredNodes) % 2 != 0 
        || (numberOfConnections < 2 && numberOfRegisteredNodes > 2)) {
            System.out.println("Cannot set up overlay.");
            return;
        }

        /* For each vertex, create neighbors for the vertices */
        for (int j = 1; j < numberOfRegisteredNodes; ++j) {
            Vertex neighborVertex = new Vertex(j, getRegisteredMessagingNodes().get(j));
            
            /* creates an edge between these two vertices */
            currVertex.addNeighbor(neighborVertex);
            neighborVertex.addNeighbor(currVertex);

            linkCount++;
            
            vertices.add(currVertex);
            currVertex = neighborVertex;
        } // End for loop

        vertices.add(currVertex);

        currVertex = vertices.get(0);

        for (Vertex v : vertices) {
            currVertex = v;

            if (currVertex.getNeighborsSize() < numberOfConnections) {
                int neighborIndex = currVertex.getIndex() + 1;
                
                while (currVertex.getNeighborsSize() < numberOfConnections && neighborIndex < vertices.size()) {
                    Vertex neighbor = vertices.get(neighborIndex);
                    
                    if (currVertex.hasNeighbor(neighborIndex) == false && neighbor.hasNeighbor(currVertex.getIndex()) == false) {
                        if (neighbor.getNeighborsSize() < numberOfConnections) {
                            currVertex.addNeighbor(neighbor);
                            neighbor.addNeighbor(currVertex);
                            linkCount++;
                        } // End if statement
                    } // End if statement

                    ++neighborIndex;
                } // End while loop
            } // End for loop
        } // End for-each loop loop

        for (Vertex v : vertices) {
            MessagingNodesList newRequest = new MessagingNodesList(v);
            send_message(v.getIndex(), newRequest.getBytes(), "");
        } // End for-each loop
    } // End construct_overlay() method

    public void list_messaging_nodes() {
        RegisterRequest temp;
        for (int i = 0; i < registeredMessagingNodes.size(); ++i) {
            temp = registeredMessagingNodes.get(i);
            System.out.println(temp.getAddress() + " - " + temp.getPort());
        } // End for loop
    } // End list_messaging_nodes() method

    public void list_weights() {
        for (String edge : edgesOfMessagingNodes) { 
            System.out.println(edge + " - " + mapOfEdges.get(edge));
        } // End for each loop
    } // End list_weights() method

    public void send_overlay_link_weights() {
        LinkWeights overlayLinkWeights = new LinkWeights(getVertices());
        
        for (int i = 0; i < numberOfRegisteredNodes; ++i) { 
            send_message(i, overlayLinkWeights.getBytes(), ""); 
        } // End for loop
    
        setEdgesOfMessagingNodes(overlayLinkWeights.getEdges());
        setMapOfEdges(overlayLinkWeights.getMap());
    } // End list_weights() method

    public void taskComplete(Event event) {
        int numTasksComp = numberOfTaskCompleted.incrementAndGet();

        if (numTasksComp == numberOfRegisteredNodes) {
            System.out.println("[Registry] Received all taskComplete events.");
            
            try {
                Thread.sleep(15000);
            } catch (InterruptedException err) {
                System.err.println(err.getMessage());
            } // End try-catch block

            System.out.println("[Registry] Sending TaskSummaryRequest.\n");
            
            TaskSummaryRequest summary = new TaskSummaryRequest();
            for (int i = 0; i < numberOfRegisteredNodes; ++i) {
                send_message(i, summary.getBytes(), "");
            } // End for loop

            numTasksComp = 0;
        } // End if statement
    } // End taskComplete(event) method

    public synchronized void taskSummary(Event event) {
        TaskSummaryResponse sum_rsp = (TaskSummaryResponse) event;
        
        statisticList.add(sum_rsp);
        if (statisticList.size() == numberOfRegisteredNodes) {
            display = new StatisticsCollectorAndDisplay(statisticList);
            display.displayStatistics();
            statisticList.clear();
        } // End if statement
    } // End taskSummary(event) method
    
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
            case 5: /* message */
                // System.out.println("Registry has receieved Msg (not good)");
                // Message msg = (Message) event;
                break;
            case 8: /* Task Complete */
                taskComplete(event);
                break;
            case 10: /* Task Summary Response */
                numberOfTaskCompleted.set(0);
                taskSummary(event);
                break;
            default:
                System.out.println("Registry.java - Unrecognized Event.");
        } // End switch statement
    } // End onEvent() method

    public static void main(String[] args) {
        if (args.length < 1 || args.length > 1) {
            System.out.println("Registry - Invalid number of arguments. Exiting program.");
            System.exit(1);
        } // End if-statement
        
        final int PORT = Integer.parseInt(args[0]);

        Registry regsitry = new Registry(PORT); /* creates a new Node object that will host our Registry Server */
        regsitry.getServerThread().start(); /* starts our TCPServerThread that was created in our_registry obj */
        
        System.out.println("[Registry]: Registry Node is up and running"); /* The registry TCPServerThread is up and is looking for active connections */
        
        Scanner user_in = new Scanner(System.in);
        String line = null;
        
        while (line != "exit") {
            line = user_in.nextLine();
 
            if (line.equals("list-messaging-nodes")) {
                if (regsitry.numberOfRegisteredNodes > 0) {
                    regsitry.list_messaging_nodes();
                } else { 
                    System.out.println("No Messaging Nodes to list");
                }
            } else if (line.equals("list-weights")) {
                // if (our_registry.isOverlayConstructed()) {
                    regsitry.list_weights();
                // } else {
                //     System.out.println("Need to setup overlay");
                // }
            } else if (line.contains("setup-overlay")) {
                int connections_required = 4; /* Connections Required by default are 4 */
            
                String[] command = line.split(" ");
            
                if (command.length == 2) {
                    connections_required = Integer.parseInt(command[1]);
                    regsitry.construct_overlay(connections_required, regsitry.getNumberOfRegisteredNodes());
                } else {
                    regsitry.construct_overlay(connections_required, regsitry.getNumberOfRegisteredNodes());
                } // End if-else statement
            } else if (line.equals("send-overlay-link-weights")) {
                regsitry.send_overlay_link_weights();
            } else if (line.contains("start")) {
                String [] command = line.split(" ");

                int rounds = 0;
                if (command.length < 2) {
                    System.out.println("Invalid command for start, please include # of rounds.");
                } else {
                    rounds = Integer.parseInt(command[1]);
                    regsitry.start(rounds);
                } // End if-else statement
            } else if (line.equals("exit")) {
                regsitry.getNodeServerThread().close_server();
                break;
            } else {
                System.out.println("[Registry] Unrecognized command. Please try again");
            } // End if-else statements
        } // End while loop

        user_in.close();
    } // End main method

} // End Registry class