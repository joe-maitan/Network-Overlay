package csx55.overlay.wireformats;

import java.io.*;
import java.util.*;

import csx55.overlay.node.*;

public class MessagingNodesList implements Event {

    private int numberPeerMessagingNodes; /* Number of connections the Node will establish */
    private ArrayList<RegisterRequest> msgNodePeerInfo = new ArrayList<>(); /* Contains each of the msgNodes peers register requests */
    
    public MessagingNodesList() {} // End default constructor

    public MessagingNodesList(Vertex v) {
        // System.out.println("v is " + v.getRegisterRequest().ipAddress);
        this.numberPeerMessagingNodes = v.getNeighborsSize();
        this.msgNodePeerInfo = new ArrayList<>();

        ArrayList<Vertex> peers = v.getNeighbors();

        // System.out.println("v's neighbors/peer nodes");
        // for (Vertex temp : peers) {
        //     System.out.println(temp.getRegisterRequest().ipAddress);
        // }

        RegisterRequest req;
        for (int i = 0; i < peers.size(); ++i) {
            req = peers.get(i).getRegisterRequest();
            
            this.msgNodePeerInfo.add(req);
        } // End for loop
    } // End MessagingNodesList(list) constructor

    public MessagingNodesList(DataInputStream din) {
        setBytes(din);
    } // End MessagingNodesList(din) constructor

    @Override
    public int getType() {
        return Protocol.MESSAGING_NODES_LIST;
    } // End getType() method

    public int getNumPeers() {
        return this.numberPeerMessagingNodes;
    } // End getNumPeers() method

    public ArrayList<RegisterRequest> getMsgNodePeerList() {
        return this.msgNodePeerInfo;
    } // End getMsgNodePeerList() method

    @Override
    public byte[] getBytes() {
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        try {
            dout.writeInt(getType());
            dout.writeInt(numberPeerMessagingNodes);

            for (RegisterRequest r : msgNodePeerInfo) {
                byte[] ipAddressBytes = r.getAddress().getBytes();
                int ipAddressLength = ipAddressBytes.length;

                dout.writeInt(ipAddressLength);
                dout.write(ipAddressBytes); /* write the IP address */
                dout.writeInt(r.getPort()); /* write the port # */
            } // End for loop

            dout.flush();
            marshalledBytes = baOutputStream.toByteArray();
           
            baOutputStream.close();
            dout.close();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block

        return marshalledBytes;
    } // End getBytes() method

    @Override
    public void setBytes(DataInputStream din) {
        String msgNodeIP;
        int msgNodePort;

        RegisterRequest temp;

        try {
            numberPeerMessagingNodes = din.readInt();
            msgNodePeerInfo = new ArrayList<>();

            for (int i = 0; i < numberPeerMessagingNodes; ++i) {
                int ipAddressLength = din.readInt();
                byte[] ipAddressByte = new byte[ipAddressLength];

                din.readFully(ipAddressByte);
                msgNodeIP = new String(ipAddressByte);

                msgNodePort = din.readInt();

                temp = new RegisterRequest(msgNodeIP, msgNodePort);
                msgNodePeerInfo.add(temp);
            } // End for loop
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }
    } // End setBytes(din) method
    
    // public static void main(String[] args) {

        
    //     RegisterRequest r1 = new RegisterRequest("joe", 72);
    //     RegisterRequest r2 = new RegisterRequest("eggo", 325);
    //     RegisterRequest r3 = new RegisterRequest("mitch", 1215);

    //     Vertex v1 = new Vertex(0, r1);
    //     Vertex v2 = new Vertex(0, r2);
    //     Vertex v3 = new Vertex(0, r3);

    //     v1.addNeighbor(v2);
    //     v1.addNeighbor(v3);

    //     System.out.println("length before marshalling: " + v1.getNeighbors().size());

    //     MessagingNodesList test = new MessagingNodesList(v1, v1.getNeighbors());
    //     byte[] arr = test.getBytes();
    
    //     ByteArrayInputStream baIn = new ByteArrayInputStream(arr);
    //     DataInputStream din = new DataInputStream(new BufferedInputStream(baIn));
    
    //     int msg_type = 0;
        
    //     try {
    //         msg_type = din.readInt();
    //         System.out.println("Successfully read in msg_type: " + msg_type);
    //     } catch (IOException err) {
    //         System.err.println("Error reading message type or deserializing: " + err.getMessage());
    //     }
    

    //     MessagingNodesList temp = new MessagingNodesList(din);

    //     System.out.println("length after unmarhsalling: " + temp.getMsgNodePeerList().size());


    //     System.out.println("Types are the same: " + (test.getType() == temp.getType()));
    //     System.out.println(test.msgNodePeerInfo.get(0).ipAddress);
    //     System.out.println(test.msgNodePeerInfo.get(0).portNumber);
    //     System.out.println(temp.msgNodePeerInfo.get(0).ipAddress);
    //     System.out.println(temp.msgNodePeerInfo.get(0).portNumber);
    // }
} // End MessagingNodesList
