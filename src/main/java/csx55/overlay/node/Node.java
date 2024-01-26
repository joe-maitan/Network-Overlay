package csx55.overlay.node;
import csx55.overlay.TCPServerThread;
import csx55.overlay.wireformats.*;

public class Node {

    TCPServerThread node_server;

    public void onEvent(Event type_of_event) {

    } // End onEvent() method

    public Node(final int PORT_NUM) {
        node_server = new TCPServerThread();
    } // End Node constructor

    
    
    
} // End Node interface
