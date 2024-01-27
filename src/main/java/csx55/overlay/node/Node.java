package csx55.overlay.node;

import csx55.overlay.transport.*;
import csx55.overlay.wireformats.*;

public class Node {

    TCPServerThread node_server;

    /* Do we implement a data structure here for keeping track of the nodes 
    here or in TCPServerThread?
    */

    String machine_host_name;
    int port_number;
    int message_type; /* register request */
    

    public void onEvent(Event type_of_event) {} // End onEvent() method

    public Node() {} // default node constructor

    public Node(final int PORT_NUM) {
        this.port_number = PORT_NUM;
        node_server = new TCPServerThread(this.port_number);
        // this.machine_host_name = node_server.getInetAddress().getHostName();
        Thread t = new Thread(node_server);
        t.start();
    } // End Node constructor
    
} // End Node class
