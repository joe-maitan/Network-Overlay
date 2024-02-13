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
            dout.writeInt(sourceByte.length);
            dout.write(sourceByte);

            byte[] sinkByte = sinkNode.getBytes();
            dout.writeInt(sinkByte.length);
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
    
} // End Message class
