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
        try {
            numberPeerMessagingNodes = din.readInt();
            
            int lengthOfArr = din.readInt();
            RegisterRequest[] temp = new RegisterRequest[lengthOfArr];

            aRegisterRequests = temp;
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }
    } // End setBytes(din) method
    
    public static void main(String[] args) {
        RegisterRequest test_req = new RegisterRequest("Joe", 72);
        Vertex v1 = new Vertex(0, test_req);
        Vertex v2 = new Vertex(1, test_req);
        Vertex v3 = new Vertex(2, test_req);

        ArrayList<Vertex> temp_list = new ArrayList<>();
        temp_list.add(v1);
        v1.addNeighbor(v3);
        v1.addNeighbor(v2);

        temp_list.add(v2);
        temp_list.add(v3);

        MessagingNodesList test = new MessagingNodesList(v1, temp_list);
        byte[] arr = test.getBytes();

        ByteArrayInputStream baIn = new ByteArrayInputStream(arr);
        DataInputStream din = new DataInputStream(new BufferedInputStream(baIn));

        int msg_type = 0;

        try {
            msg_type = din.readInt();
        } catch (Exception err) {
            System.err.println(err.getMessage());
        }

        MessagingNodesList temp = new MessagingNodesList(din);

        if (test.getType() == msg_type && test.numberPeerMessagingNodes == temp.numberPeerMessagingNodes && test.aRegisterRequests.equals(temp.aRegisterRequests)) {
            System.out.println("MessagingNodesList success");
        } else {
            System.out.println("Womp womp, MessagingNodesList failed");
        }

    } // End main method
    
} // End MessagingNodesList
