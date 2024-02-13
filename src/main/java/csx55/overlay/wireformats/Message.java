package csx55.overlay.wireformats;

import java.io.*;

public class Message implements Event {

    private String sourceNode;
    private String sinkNode;

    public Message() {} // End default constructor

    public Message(String source, String sink) {
        this.sourceNode = source;
        this.sinkNode = sink;
    } // End Message(sourceNode, sinkNode) constructor

    public Message(DataInputStream din) {
        setBytes(din);
    } // End Message(din) constructor

    public String getSourceNode() {
        return this.sourceNode;
    } // End getSourceNode() method

    public String getSinkNode() {
        return this.sinkNode;
    } // End getSinkNode() method

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

            byte[] sourceByte = sourceNode.getBytes();
            int sourceByteLength = sourceByte.length;
            dout.writeInt(sourceByteLength);
            dout.write(sourceByte);

            byte[] sinkByte = sinkNode.getBytes();
            int sinkByteLength = sinkByte.length;
            dout.writeInt(sinkByteLength);
            dout.write(sinkByte);

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
            byte[] sourceByte = new byte[din.readInt()];
            din.readFully(sourceByte);
            sourceNode = new String(sourceByte);

            byte[] sinkByte = new byte[din.readInt()];
            din.readFully(sinkByte);
            sinkNode = new String(sinkByte);
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }
    } // End setBytes(din) method

    public static void main(String[] args) {
        Message test = new Message("Joe", "72");

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

        if (test.getType() == other.getType() && test.getSourceNode().equals(other.getSourceNode()) && test.getSinkNode().equals(other.getSinkNode())) {
            System.out.println("message wireformat successful");
        } else {
            System.out.println("Womp womp");
        }
    }
    
} // End Message class
