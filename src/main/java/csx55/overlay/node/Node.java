package csx55.overlay.node;

import csx55.overlay.transport.*;
import csx55.overlay.wireformats.*;

public class Node {

    TCPServerThread node_server;

    public void onEvent(Event type_of_event) {} // End onEvent() method

    public Node() {} // default node constructor

    public Node(final int PORT_NUM) {
        node_server = new TCPServerThread(PORT_NUM);
        Thread t = new Thread(node_server);
        t.start();
    } // End Node constructor
    
} // End Node interface
