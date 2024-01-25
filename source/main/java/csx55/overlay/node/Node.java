package csx55.overlay;

public class Node {

    Socket[] sockets; // The node class needs a structure to keep track of the accepeted connections to a given node

    TCPServerThread new_node_server = new TCPServerThread(); // Spawn the TCPServerThread
    TCPSender data_out;

    public Node() {
        Thread t = new Thread(new_node_server);
        t.start();
        data_out = new TCPSender();
    } // End Node() constructor

    public static void looking_for_nodes() {
        // for each accept, new connect, keep a record of sockets created,
        // and spawn a TCPRecieverThread and TCPSenderThread
    } // End looking_for_nodes() method
    
    public void onEvent(Event new_event) {

    } // End onEvent() method

} // End Node interface