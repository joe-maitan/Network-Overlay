package csx55.overlay.wireformats;

import java.io.*;
import java.util.*;

import csx55.overlay.node.*;

public class MessagingNodesList implements Event {

    public int numberPeerMessagingNodes; /* Number of nodes the given messagingNode is connected to? */
    // The container of all of our messagingNodes

    public MessagingNodesList() {} // End default constructor

    public MessagingNodesList(ArrayList<MessagingNode> list_of_nodes) {

    } // End MessagingNodesList(list) constructor

    public MessagingNodesList(DataInputStream din) {
        setBytes(din);
    } // End MessagingNodesList(din) constructor

    @Override
    public int getType() {
        return Protocol.MESSAGING_NODES_LIST;
    } // End getType() method

    @Override
    public byte[] getBytes() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBytes'");
    } // End getBytes() method

    @Override
    public void setBytes(DataInputStream din) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBytes'");
    } // End setBytes(din) method
    
    
} // End MessagingNodesList
