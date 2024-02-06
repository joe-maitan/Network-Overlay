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

    public int numberOfRegisteredNodes;

    public Registry() {} // End Registry default constructor

    public Registry(int port_number) {
        super(port_number); /* creates a node object with a ServerThread. Effectively creating the registry */
    } // End Registry(PORT) constructor

    public void read_command_line() {
        Scanner user_in = new Scanner(System.in);
        String registry_input = null;
        while (registry_input != "exit") {
            registry_input = user_in.nextLine();

            switch(registry_input) {
                case "list-messaging-nodes":
                    System.out.println("listing messaging nodes");
                    // list_messaging_nodes();
                    break;
                case "list-weights":
                    System.out.println("listing link weights");
                    break;
                case "setup-overlay number-of-connections":
                    System.out.println("Setting up overlay");
                    break;
                case "send-overlay-link-weights":
                    System.out.println("Sending overlay link weights");
                    break;
                case "exit":
                    System.exit(1);
                    break;
                default:
                    System.out.println("Unrecognized command please try again.");
                    break;
            } // End switch statement
        } // End while loop

        user_in.close();
    } // End read_command_line() method

    public boolean register_node(int socket_index, RegisterRequest rq) {
        if (!registered_messaging_nodes.containsKey(rq)) {
            registered_messaging_nodes.put(socket_index, rq); /* Add the node to the Registry */
            ++numberOfRegisteredNodes;
            String success = String.format("Registration request successful. The number of messaaging nodes currently constituting the overlay is (%d).", numberOfRegisteredNodes);
            System.out.println(success);
            return true;
        } else { /* We do not add it to the registry */
            try{
                throw new Exception("Cannot add node to the registry.");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } // End if-else statement

        return false;
    } // End register_node() method

    public boolean deregister_node(int socket_index, DeregisterRequest dereg_rq) {
        if (registered_messaging_nodes.containsKey(socket_index)) {
            registered_messaging_nodes.remove(socket_index);
            --numberOfRegisteredNodes;
            String success = String.format("Deregistration request successful. The number of messaging nodes currently constituting the overlay is (%d)", numberOfRegisteredNodes);
            System.out.println(success);
            return true;
        } else {
            try {
                throw new Exception("Cannot deregister node.");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            } // End try-catch block
        } // End if-else statement

        return false;
    } // End deregister_node() method

    public void construct_overlay(int numberOfConnections) {
        /* This is what connects the other MessagingNodes to one another, given a list of other messaging node sockets, they would connect to each. */

        // If the number of connections specified is None or 0, we default the value to 4
        if (numberOfConnections == 0) {
            numberOfConnections = 4;
        } // End if-statement

        // Using the list connect to the other nodes

        // Assign the link weights to every connection
        assign_link_weights();
    } // End construct_overlay() method

    public void assign_link_weights() {
        
    } // End assign_link_weights() method

    @Override
    public void onEvent(Event event, int socketIndex) {
        int messageProtocol = event.getType();
        
        switch(messageProtocol) {
            case 0:
                RegisterRequest reg_rq = (RegisterRequest) event;
                
                boolean value = register_node(socketIndex, reg_rq);
                RegisterResponse response = new RegisterResponse(value, "");

                send_message(socketIndex, response.getBytes(), "");
                break;
            // case 1:
            //     /* Register Response */
            //     RegisterResponse reg_resp = (RegisterResponse) event;
            //     break;
            case 2:
                /* Deregister Request */
                DeregisterRequest de_rq = (DeregisterRequest) event;

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
        if (args.length < 1) {
            System.exit(1);
        } // End if-statement
        
        int port = Integer.parseInt(args[0]);

        Registry our_registry = new Registry(port); /* creates a new Node object that will host our Registry Server */
        our_registry.node_server_thread.start(); /* starts our TCPServerThread that was created in our_registry obj */
        
        System.out.println("[Registry]: Registry Node is up and running"); /* The registry TCPServerThread is up and is looking for active connections */
        
        our_registry.read_command_line();
    } // End main method

} // End Registry class