package csx55.overlay.node;

import java.io.*;
import java.util.*;
import java.net.*;
import csx55.overlay.transport.*;
import csx55.overlay.wireformats.*;

public class Node {

    protected TCPServerThread node_server;
    public TCPSender node_send;
    public TCPReceiverThread node_read;

    public Thread t;

    String node_ip_address;
    int node_port_number;

    public HashMap<String, Integer> to_be_registered = new HashMap<>();
    
    public Node() {} // default node constructor

    public Node(final int PORT_NUM) {
        node_server = new TCPServerThread(PORT_NUM);
        this.node_port_number = node_server.server_port_number;
        this.node_ip_address = node_server.ip_address;
        
        t = new Thread(node_server);
    } // End Node constructor

    public synchronized void onEvent(Event type_of_event) {
        int event_protocol = type_of_event.getType();
        
        switch(event_protocol) {
            case 0:
                /* Register Request */
                // grab the ip and port number of the node
                break;
            case 1:
                /* Register Response */
                break;
            case 2:
                /* Deregister Request */
                break;
            case 3:
                /* Deregister Response */
                break;
            case 4:
                /* Link weights */
                break;
            case 5:
                /* message */
                break;
            case 6:
                // Messaging Nodes list
                break;
            case 7:
                // task initiate
                break;
            case 8:
                // task complete
                break;
            case 9:
                // task summary request
                break;
            case 10:
                // task summary response
                break;
        } // End switch statement
    } // End onEvent() method

    public int send_message(int socket_index, byte[] arr, String message) {
        int status = 0;
        this.node_send = this.node_server.senders.get(socket_index); /* constructs our TCPSender obj */
        
        try {
            this.node_send.sendData(arr);
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block

        return status;
    } // End send_message() method

    public void receive_message(int socket_index, byte[]arr, String message) {
        this.node_read = this.node_server.readers.get(socket_index); /* constructs our TCPReciever obj */

        try {
            Thread read = new Thread(this.node_read);
            read.start(); /* start the TCPReceiver thread obj */
        } catch(Exception err) {
            System.out.println(err.getMessage());
        } // End try-catch block
    } // End receive_message() method

    public void add_socket(Socket s) {
        node_server.add_socket(s);
    } // End add_socket() method
    
} // End Node class
