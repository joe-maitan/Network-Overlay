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

            marshalledBytes = baOutputStream.toByteArray();
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
        }
    } // End setBytes(din) method

    public static void main(String[] args) {
        Message test = new Message(72);

        byte[] arr = test.getBytes(); /* marshalling the data */
        ByteArrayInputStream baIn = new ByteArrayInputStream(arr);
        DataInputStream din = new DataInputStream(new BufferedInputStream(baIn));

        int msg_type = 0;

        try {
            msg_type = din.readInt();
            System.out.println("Successfully read in msg_type: " + (msg_type == 5));
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }

        Message other = new Message(din);

        if (test.getType() == other.getType() && test.getPayload() == other.getPayload()) {
            System.out.println("message wireformat successful");
        } else {
            System.out.println("Womp womp");
        }
    }
    
} // End Message class
