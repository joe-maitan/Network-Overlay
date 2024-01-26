package csx55.overlay.node_1;
import csx55.overlay.wireformats.*;

public interface Node {

    public void onEvent(Event type_of_event);

    // ServerSocket node_server; /* Every node will have its own server. This is what allows it to connect to other nodes. */
    // Socket[] node_connections; /* Keeps track of what other nodes the node is connected to. Data is sent along the socket. */
    
    
} // End Node interface
