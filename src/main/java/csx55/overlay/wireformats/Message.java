package csx55.overlay.wireformats;

import java.io.*;

public class Message implements Event {

    // Source Node (From)
    // Sink Node (To)

    public Message() {} // End default constructor

    // public Message(sourceNode, sinkNode) {} // End Message(sourceNode, sinkNode) constructor

    public Message(DataInputStream din) {
        setBytes(din);
    } // End Message(din) constructor

    @Override
    public int getType() {
        return Protocol.MESSAGE;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBytes'");
    } // End getBytes() method

    @Override
    public void setBytes(DataInputStream din) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBytes'");
    } // End setBytes(din) method
    
} // End Message class
