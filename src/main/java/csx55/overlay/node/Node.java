package csx55.overlay.node;

import csx55.overlay.transport.*;
import csx55.overlay.wireformats.*;

public class Node {

    private TCPServerThread node_server;
    private TCPSender node_send;

    String node_ip_address;
    int node_port_number;
    
    
    public Node() {} // default node constructor

    public Node(final int PORT_NUM) {
        this.node_port_number = PORT_NUM;
        
        node_server = new TCPServerThread(this.node_port_number);
        
        Thread t = new Thread(node_server);
        
        t.start(); /* start the TCPServerThread */
    } // End Node constructor

    public void onEvent(Event type_of_event) {} // End onEvent() method

    public void send_message(int socket_index, byte[] arr, String message) {
        this.node_server.senders.get(socket_index).sendData(arr);
    } // End send_message() method
    
} // End Node class
