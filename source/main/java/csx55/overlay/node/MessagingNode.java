package csx55.overlay;

import java.io.*;
import java.net.*;

public class MessagingNode extends Node { // The MessageNode class can be thought of as our client code
    private String node_name; // Node names, are these the machine names?
    private String message;
    private String type_of_message; // What is the protocol?

    public static int number_of_connections = 0;

    /* Every node has key parts. A data in stream (read), a data out stream (write),
     * and a client_socket: This is what is going to establish our connection to the 
     * registry/server
     */
    // static DataInputStream din; TCPReceiverThread
    // static DataOutputStream dout; TCPSenderThread
    // static Socket client_socket;

    public MessagingNode(String name) {
        this.node_name = name;
    } // End MessagingNode() constructor

    public void onEvent(String event) {
        /* connect the event to the kind of event in the event factory? And then based on that have the 
        * event factory send the message to the corresponding node
        */

        // factory(Event.getType(event));

    } // End onEvent() method

    public static void initialize_connection_to_registry(final String HOST_NAME, final int PORT_NUM) {
        try {
            client_socket = new Socket(HOST_NAME, PORT_NUM);
            MessagingNode new_node = new MessagingNode(client_socket.getInetAddress().getLocalHost());
            // Do we increment our number of connections?
            // Do we add it to the registry list here?
            list_of_nodes.add(new_node);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } // End try-catch block
    } // End initialize_connection_to_server() method

    public static void main(String[] args) {
        final String HOST_NAME = args[0];
        final int PORT_NUM = Integer.parseInt(args[1]);

        initialize_connection_to_registry(HOST_NAME, PORT_NUM);


    } // End main method

} // End MessagingNode class
