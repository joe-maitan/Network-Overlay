package csx55.overlay.wireformats;

import java.io.*;

import csx55.overlay.node.MessagingNode;

public class TaskComplete implements Event {

    private String nodeIPAddress;
    private int nodePortNumber;

    public TaskComplete() {} // End default constructor

    public TaskComplete(RegisterRequest r) {
        this.nodeIPAddress = r.getAddress();
        this.nodePortNumber = r.getPort();
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


    // public static void main(String[] args) {
    //     RegisterRequest req = new RegisterRequest("joe", 72);
    //     byte[] arr = req.getBytes();

    //     ByteArrayInputStream baIn = new ByteArrayInputStream(arr);
    //     DataInputStream din = new DataInputStream(new BufferedInputStream(baIn));

    //     int msg_type = 0; 

    //     try {
    //         msg_type = din.readInt();
    //     } catch (IOException err) {
    //         System.err.println(err.getMessage());
    //     }

    //     RegisterRequest temp = new RegisterRequest(din);

    //     if (req.getType() == msg_type && req.getAddress().equals(temp.getAddress()) && req.getPort() == temp.getPort()) {
    //         System.out.println("FUCK YEAH");
    //     } else {
    //         System.out.println("Womp womp");
    //     }
    // }

} // End TaskComplete class