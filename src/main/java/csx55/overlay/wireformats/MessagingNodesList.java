package csx55.overlay.wireformats;

import java.io.*;
import java.util.*;

import csx55.overlay.node.*;

public class MessagingNodesList implements Event {

    private int numberPeerMessagingNodes; /* Number of nodes the given messagingNode is connected to? */
    private RegisterRequest[] aRegisterRequests; /* MessagingNode info */

    public MessagingNodesList() {} // End default constructor

    public MessagingNodesList(Vertex v, ArrayList<Vertex> list) {
        aRegisterRequests = new RegisterRequest[v.getNeighborsSize()];

        for (int i = 0; i < list.size(); ++i) {
            aRegisterRequests[i] = list.get(i).getRegisterRequest();
        } // End for loop
    } // End MessagingNodesList(list) constructor

    public MessagingNodesList(DataInputStream din) {
        setBytes(din);
    } // End MessagingNodesList(din) constructor

    @Override
    public int getType() {
        return Protocol.MESSAGING_NODES_LIST;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBytes'");
    } // End getBytes() method

    @Override
    public void setBytes(DataInputStream din) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBytes'");
    } // End setBytes(din) method
    
    
} // End MessagingNodesList
