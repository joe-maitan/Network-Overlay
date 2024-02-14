package csx55.overlay.wireformats;

import java.io.*;
import java.util.*;

import csx55.overlay.node.*;

public class MessagingNodesList implements Event {

    private int numberPeerMessagingNodes; /* Number of connections the Node will establish */
    private ArrayList<RegisterRequest> msgNodePeerInfo = new ArrayList<>(); /* Contains each of the msgNodes peers register requests */
    
    public MessagingNodesList() {} // End default constructor

    public MessagingNodesList(Vertex v) {
        this.numberPeerMessagingNodes = v.getNeighborsSize();
        this.msgNodePeerInfo = new ArrayList<>();

        ArrayList<Vertex> peers = v.getNeighbors();

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
    
} // End MessagingNodesList
