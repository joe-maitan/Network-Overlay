package csx55.overlay.wireformats;

import java.io.*;

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
    //     RegisterRequest test = new RegisterRequest("Joe", 72);
    //     TaskComplete test2 = new TaskComplete(test);

    //     byte[] arr = test2.getBytes();

    //     ByteArrayInputStream baIn = new ByteArrayInputStream(arr);
    //     DataInputStream din = new DataInputStream(new BufferedInputStream(baIn));

    //     int msg_type = 0;

    //     try {
    //         msg_type = din.readInt();
    //     } catch (IOException err) {
    //         System.err.println(err.getMessage());
    //     }

    //     TaskComplete temp = new TaskComplete(din);

    //     if (test2.getType() == temp.getType() && test2.getAddress().equals(temp.getAddress()) && test2.getPort() == temp.getPort()) {
    //         System.out.println("Who fucks? I fuck");
    //     } else {
    //         System.out.println("Womp womp");
    //     }
    // }

} // End TaskComplete class