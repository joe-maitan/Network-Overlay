package csx55.overlay;

public class Node {

    ServerSocket node_socket;
    Socket[] sockets;

    // Spawn the TCPServerThread
    // for each accept, new connect, keep a record of sockets created,
    // and spawn a TCPRecieverThread and TCPSenderThread


    public void onEvent(Event new_event) {

    } // End onEvent() method

} // End Node interface