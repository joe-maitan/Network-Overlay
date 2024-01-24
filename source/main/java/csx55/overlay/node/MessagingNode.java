package csx55.overlay;

import java.io.*;
import java.net.*;

public class MessagingNode { // The MessageNode class can be thought of as our client code
    private String node_name; // Node names, for example starts with Node-A
    private String message;
    private String type_of_message; // What is the protocol?

    /* Every node has key parts. A data in stream (read), a data out stream (write),
     * and a client_socket: This is what is going to establish our connection to the 
     * registry/server
     */
    static DataInputStream din;
    static DataOutputStream dout;
    static Socket client_socket;
} // End MessagingNode class
