package csx55.overlay.node;
import csx55.overlay.wireformats.*;

// import java.io.*;
// import java.net.*;
import java.util.*;

public class Registry extends Node {

    /* this tracks the socket index of the messaging node as well as the RegisterRequest */
    HashMap<Integer, RegisterRequest> registered_messaging_nodes = new HashMap<>();

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

    public void register_node(int socket_index, RegisterRequest rq) {
        if (!registered_messaging_nodes.containsKey(rq)) {
            registered_messaging_nodes.put(socket_index, rq);
            System.out.println(node_server.socket_connetions.get(socket_index).getInetAddress() + " has connected to the registry!");
        } else { /* We do not add it to the registry */
            try{
                throw new Exception("Cannot add node to the registry");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } // End if-else statement
    } // End register_node() method

    public static void deregister_node(int socket_index, DeregisterRequest dereg_rq) {
        // if (registered_messaging_nodes.contains()) {
        //     registered_messaging_nodes.remove();
        // }
    } // End deregister_node() method

    public void construct_overlay() {
        assign_link_weights();
    } // End construct_overlay() method

    public void assign_link_weights() {
        
    } // End assign_link_weights() method
    public static void main(String[] args) {
        if (args.length < 1) {
            System.exit(1);
        } // End if-statement


        int port = Integer.parseInt(args[0]);

        Registry our_registry = new Registry(port); /* creates a new Node object that will host our Registry Server */

        // System.out.println("Starting our_registry thread");
        our_registry.node_server_thread.start(); /* starts our TCPServerThread that was created in our_registry obj */
        
        System.out.println("[Registry.java]: Registry Node is up and running"); /* The registry TCPServerThread is up and is looking for active connections */
        
        our_registry.read_command_line();
    } // End main method

} // End Registry class