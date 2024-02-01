package csx55.overlay.node;

import java.io.*;
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
    
    
    public Node() {} // default node constructor

    public Node(final int PORT_NUM) {
        node_server = new TCPServerThread(PORT_NUM);
        
        /* TODO: Parse in the correct information */
        this.node_port_number = node_server.server_port_number;
        this.node_ip_address = node_server.getInetAddress();
        
        t = new Thread(node_server);

        /* DO NOT START THE THREAD IN THE CONSTRUCTOR */
        // t.start(); /* start the TCPServerThread, calls the run method below */
    } // End Node constructor

    public void onEvent(Event type_of_event) {
        // EventFactory fac = new EventFactory(type_of_event.getType());
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
