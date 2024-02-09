package csx55.overlay.wireformats;

import java.io.*;
import java.util.*;

import csx55.overlay.node.*;

public class MessagingNodesList implements Event {

    private int numberPeerMessagingNodes; /* Number of nodes the given messagingNode is connected to? */
    private RegisterRequest[] aRegisterRequests; /* MessagingNode info */

    public MessagingNodesList() {} // End default constructor

    public MessagingNodesList(Vertex v, ArrayList<Vertex> list) {
        numberPeerMessagingNodes = v.getNeighborsSize();
        aRegisterRequests = new RegisterRequest[numberPeerMessagingNodes];

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
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        try {
            dout.writeInt(getType());

            dout.writeInt(numberPeerMessagingNodes);

            for (int i = 0; i < aRegisterRequests.length; ++i) {
                byte[] temp = aRegisterRequests[i].getBytes();
                dout.write(temp, 0, temp.length);
            } // End for loop
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block

        return marshalledBytes;
    } // End getBytes() method

    @Override
    public void setBytes(DataInputStream din) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBytes'");
    } // End setBytes(din) method
    
    
} // End MessagingNodesList
