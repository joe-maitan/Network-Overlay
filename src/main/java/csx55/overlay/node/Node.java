package csx55.overlay.node;

import java.io.*;
import java.util.*;
import java.net.*;
import csx55.overlay.transport.*;
import csx55.overlay.wireformats.*;

public abstract class Node { 

    public String node_ip_address;
    public int node_port_number;
    
    protected TCPServerThread node_server;
    public TCPSender node_send;
    public TCPReceiverThread node_read;

    public Thread node_server_thread;

    public HashMap<String, Integer> to_be_registered = new HashMap<>();
    
    public Node() {
        node_server = new TCPServerThread(this);
        node_server_thread = new Thread(node_server);
        node_ip_address = node_server.ipAddress;
        node_port_number = node_server.port_number;
    } // End default Node() constructor

    public Node(final int PORT_NUM) {
        node_server = new TCPServerThread(this, PORT_NUM);
        node_server_thread = new Thread(node_server);
        node_ip_address = node_server.ipAddress;
        node_port_number = node_server.port_number;
    } // End Node(PORT) constructor

    public abstract void onEvent(Event type_of_event, int socket_index);
    
    // public int send_message(int socket_index, byte[] arr, String message) {
    //     int status = 0;
    //     this.node_send = this.node_server.senders.get(socket_index); /* constructs our TCPSender obj */
        
    //     try {
    //         this.node_send.sendData(arr);
    //     } catch (IOException err) {
    //         System.err.println(err.getMessage());
    //     } // End try-catch block

    //     return status;
    // } // End send_message() method

    // public void receive_message(int socket_index, byte[]arr, String message) {
    //     this.node_read = this.node_server.readers.get(socket_index); /* constructs our TCPReciever obj */

    //     try {
    //         Thread read = new Thread(this.node_read);
    //         read.start(); /* start the TCPReceiver thread obj */
    //     } catch(Exception err) {
    //         System.out.println(err.getMessage());
    //     } // End try-catch block
    // } // End receive_message() method

    // public void add_socket(Socket s) {
    //     node_server.add_socket(s);
    // } // End add_socket() method
    
} // End Node class
