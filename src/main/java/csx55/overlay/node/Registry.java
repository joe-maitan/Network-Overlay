package csx55.overlay.node;
import csx55.overlay.wireformats.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Registry extends Node {

    static ServerSocket registry_server;
    static Socket[] messaging_node_sockets; /* Keep track of how many messaging nodes connect to the regsitry */

    static ArrayList<MessagingNode> registered_messaging_nodes = new ArrayList<>();
    private Map<String, MessagingNode> registered_msg_nodes = new HashMap<>();

    public Registry() {} // End Registry default constructor

    public Registry(int port_number) {
        super(port_number); /* creates a node object with a ServerThread. Effectively creating the registry */
    } // End Registry(PORT) constructor

    public static void register_node(MessagingNode new_node) {
        if (!registered_messaging_nodes.contains(new_node)) {
            registered_messaging_nodes.add(new_node);
        } /* else we do not add it to the registry */
    } // End register_node() method

    public static void deregister_node(MessagingNode node) {
        if (registered_messaging_nodes.contains(node)) {
            registered_messaging_nodes.remove(node);
        } /* else do not remove it from the registry */
    } // End deregister_node

    public void construct_overlay() {

    }

    public void assign_link_weights() {
        
    }

    public void onEvent(Event type_of_event) {

    } // End onEvent() method

    public static void main(String[] args) {
        if (args.length < 1) {
            System.exit(1);
        }

        int PORT_NUM = Integer.parseInt(args[0]);
        
        Registry our_registry = new Registry(PORT_NUM);

        /* since our_registry is created and the server thread is active do we accept connections here and
         * add them to the list or?
         */

        Scanner user_in = new Scanner(System.in);
        String registry_input = null;
        while (registry_input != "exit") {
            registry_input = user_in.nextLine();

            switch(registry_input) {
                case "list-messaging-nodes":
                    break;
                case "list-weights":
                    break;
                case "setup-overlay number-of-connections":
                    break;
                case "send-overlay-link-weights":
                    break;
                default:
                    System.out.println("Unrecognized command please try again.");
                    break;
            } // End switch statement
        } // End while loop

        user_in.close();
    
    } // End main method

} // End Registry class