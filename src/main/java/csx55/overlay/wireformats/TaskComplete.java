package csx55.overlay.wireformats;

import java.io.*;

import csx55.overlay.node.MessagingNode;

public class TaskComplete implements Event {

    private String nodeIPAddress;
    private int nodePortNumber;

    public TaskComplete() {} // End default constructor

    public TaskComplete(MessagingNode n) {
        this.nodeIPAddress = n.msgNodeIP;
        this.nodePortNumber = n.msgNodePortNumber;
    } // End TaskComplete(n) constructor

    public TaskComplete(DataInputStream din) {
        setBytes(din);
    } // End TaskComplete(din) constructor

    public String getAddress() {
        return this.nodeIPAddress;
    } // End getAddress() method

    public int getPort() {
        return this.nodePortNumber;
    } // End getPort() method

    @Override
    public int getType() {
        return Protocol.TASK_COMPLETE;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        try {
            dout.writeInt(getType());

            byte[] ipAddressByte = nodeIPAddress.getBytes();
            int ipAddressLength = ipAddressByte.length;
            dout.writeInt(ipAddressLength);
            dout.write(ipAddressByte);

            dout.writeInt(nodePortNumber);
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
        try {
            byte[] ipStr = new byte[din.readInt()];
            din.readFully(ipStr);
            nodeIPAddress = new String(ipStr);
            nodePortNumber = din.readInt();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }
    } // End setBytes(din) method

} // End TaskComplete class