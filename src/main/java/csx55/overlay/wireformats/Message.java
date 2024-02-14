package csx55.overlay.wireformats;

import java.io.*;

public class Message implements Event {

    private int payload;

    public Message() {} // End default constructor

    public Message(int p) {
        this.payload = p;
    } // End Message(sourceNode, sinkNode) constructor

    public Message(DataInputStream din) {
        setBytes(din);
    } // End Message(din) constructor

    public int getPayload() {
        return this.payload;
    } // End getPayload() method

    @Override
    public int getType() {
        return Protocol.MESSAGE;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        try {
            dout.writeInt(getType());
            dout.writeInt(payload);

            dout.flush();

            marshalledBytes = baOutputStream.toByteArray();
            
            baOutputStream.close();
            dout.close();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch
        
        return marshalledBytes;
    } // End getBytes() method

    @Override
    public void setBytes(DataInputStream din) {
        try {
            payload = din.readInt();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End setBytes(din) method

} // End Message class
